package org.hustsse.listenfm.service.grabber;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hustsse.listenfm.entity.Channel;
import org.hustsse.listenfm.entity.Track_Old;
import org.hustsse.listenfm.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BusfmTracksInfoFetcher extends FrequencyFetcher {
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	/** 所有公共频道ID */
	public static List<Channel> channels = new ArrayList<Channel>();

	@Autowired
	private TrackService trackService;

	public BusfmTracksInfoFetcher() {
	}

	public void run() {
		long channelIdIndex = 0;
		int channelNum = channels.size();

		// 抓取busfm的歌曲信息
		while (true) {
			ensureFrequency();
			URIBuilder builder = new URIBuilder();
			long timestamp = System.currentTimeMillis() % 500;
			Long channelId = channels.get((int) (channelIdIndex++ % channelNum)).getBusfmId();
			builder.setScheme("http").setHost("bus.fm").setPath("/ajax/content").setParameter("id", String.valueOf(channelId)) // 轮换频道
					.setParameter("t", String.valueOf(timestamp));
			URI uri = null;
			try {
				uri = builder.build();
			} catch (URISyntaxException e) {
			}
			HttpGet randomTracksGet = new HttpGet(uri);
			randomTracksGet.addHeader("Referer", "http://bus.fm/");
			randomTracksGet.addHeader("User-Agent", Constants.USER_AGENT);

			HttpResponse response = null;
			try {
				response = httpClient.execute(randomTracksGet);
			} catch (Exception e1) {
				// 远程服务器拒绝，频率太快了？休眠一会儿重试
				e1.printStackTrace();
				sleepAWhile(TimeUnit.SECONDS,Constants.RETRY_INTERVAL_SECONDS);
				continue;
			}
			// 404或其他错误?休息一会儿再重试
			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				sleepAWhile(TimeUnit.SECONDS,Constants.RETRY_INTERVAL_SECONDS);
				continue;
			}
			HttpEntity body = response.getEntity();
			String json = null;
			try {
				json = EntityUtils.toString(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] songs = json.substring(1, json.length() - 1).split("\\],\\[");
			for (String track : songs) {
				saveTrack(channelId, track);
			}
		}
	}



	/**
	 * save a track info to db,notify the mp3 fetcher and douban fetcher to work
	 *
	 * @param channelId
	 * @param track
	 */
	private void saveTrack(Long channelId, String track) {
		// fix the array of a song
		if (track.startsWith("["))
			track = track.substring(1);
		if (track.endsWith("]"))
			track = track.substring(0, track.length() - 2);
		String[] elements = track.split("\",\"");
		for (int i = 0; i < elements.length; i++) {
			elements[i] = elements[i].replace("\"", "");
		}
		// 存数据库 TODO
		// mp3 url 放入Mp3Fetcher的队列中
		String id = elements[0];
		// 没有id，或已经存在，直接return
		if(StringUtils.isEmpty(id) || trackService.findUniqueTrackByBusfmId(Long.valueOf(id)) != null) {
			return;
		}
		String name = elements[1];
		String mp3 = elements[2];
		String singer = elements[3];
		String albumName = elements[4];
		String img = elements[5];
		Track_Old t = new Track_Old();
		t.setBusfmId(Long.valueOf(id));
		t.setName(name);
		t.setMp3(mp3);
		t.setSinger(singer);
		t.setAlbumName(albumName);
		t.setImg(img);
		t.setChannel(new Channel(channelId));
		trackService.save(t);

		// 下载MP3
		BusfmMp3Fetcher.enqueue(t);
		// 抓取豆瓣上的专辑信息
		DoubanAlbumFetcher.enqueue(t);
	}

}
