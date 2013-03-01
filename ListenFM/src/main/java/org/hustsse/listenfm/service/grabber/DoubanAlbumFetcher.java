package org.hustsse.listenfm.service.grabber;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hustsse.listenfm.entity.AlbumDoubanInfo;
import org.hustsse.listenfm.entity.Track;
import org.hustsse.listenfm.service.AlbumDoubanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DoubanAlbumFetcher extends FrequencyFetcher {

	private static BlockingQueue<Track> tracks = new LinkedBlockingDeque<Track>(2000);
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	private static ObjectMapper mapper = new ObjectMapper();
	private static final String QUERY_SPLIT = ", ";
	private static final String JSONP_CALLBACK = "t";

	@Autowired
	private AlbumDoubanInfoService albumDoubanInfoService;

	public DoubanAlbumFetcher() {
	}

	public void run() {
		while (true) {
			ensureFrequency();

			Track track = null;
			try {
				track = tracks.poll(Integer.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e1) {
			}

			// 构造douban api
			URIBuilder builder = new URIBuilder();
			long timestamp = System.currentTimeMillis();
			String q = track.getName() + QUERY_SPLIT + track.getSinger() + QUERY_SPLIT + track.getAlbumName();
			builder.setScheme("http").setHost("api.douban.com").setPath("/music/subjects").setParameter("q", q)
					.setParameter("callback", JSONP_CALLBACK).setParameter("alt", "xd").setParameter("max-results", "1")
					.setParameter("_", String.valueOf(timestamp));
			URI uri = null;
			try {
				uri = builder.build();
//				System.out.println(uri);
			} catch (URISyntaxException e) {
			}

			HttpGet randomTracksGet = new HttpGet(uri);
			randomTracksGet.addHeader("Referer", "http://bus.fm/");
			randomTracksGet.addHeader("User-Agent", Constants.USER_AGENT);

			HttpResponse response = null;
			try {
				response = httpClient.execute(randomTracksGet);
			} catch (Exception e) {
				e.printStackTrace();
				sleepAWhile(TimeUnit.SECONDS, Constants.RETRY_INTERVAL_SECONDS);
				continue;
			}
			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				// 失败了
				sleepAWhile(TimeUnit.SECONDS, Constants.RETRY_INTERVAL_SECONDS);
				continue;
			}
			HttpEntity body = response.getEntity();
			String json = null;
			try {
				json = EntityUtils.toString(body);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// json解析失败，跳过
			if(json == null) {
				continue;
			}

			// json掐头去尾
			json = json.substring(2, json.length() - 1);
			// 保存到数据库
			AlbumDoubanInfo i = toPojo(json);
			if(i == null) {
				continue;
			}
			i.setTrack(track);
			albumDoubanInfoService.save(i);
		}
	}

	private AlbumDoubanInfo toPojo(String json) {
		AlbumDoubanInfo i = null;
		try {
			// 使用树模型，基本上是依赖path(String)和get(int)完成的
			JsonNode root = mapper.readTree(json);
			i = new AlbumDoubanInfo();

			JsonNode entry = root.path("entry").get(0);
			//没搜到结果，返回null
			if(entry == null)
				return null;
			JsonNode attrs = entry.path("db:attribute");
			for (JsonNode n : attrs) {
				JsonNode name = n.path("@name");
				if ("pubdate".equals(name.asText())) {
					i.setReleaseDate(n.path("$t").asText());
				} else if ("singer".equals(name.asText())) {
					i.setSinger(n.path("$t").asText());
				} else if ("publisher".equals(name.asText())) {
					i.setPublisher(n.path("$t").asText());
				}
			}

			String id = entry.path("id").path("$t").asText();
			String[] array = id.split("/");
			i.setDoubanAlbumId(Long.valueOf(array[array.length - 1]));

			JsonNode links = entry.path("link");
			i.setLink(links.get(1).path("@href").asText());
			i.setImg(links.get(2).path("@href").asText());

			i.setTitle(entry.path("title").path("$t").asText());

			JsonNode rate = entry.path("gd:rating");
			i.setAverageScore(rate.path("@average").asText());
			i.setMaxScore(rate.path("@max").asText());
			i.setMinScore(rate.path("@min").asText());
			i.setNumRaters(rate.path("@numRaters").asInt());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i;
	}

	public static void enqueue(Track t) {
		try {
			tracks.offer(t, Integer.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
