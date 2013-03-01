package org.hustsse.listenfm.service.grabber;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hustsse.listenfm.entity.Channel;
import org.hustsse.listenfm.service.ChannelService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusfmChannelFetcher {
	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	@Autowired
	private ChannelService channelService;

	/**
	 * fetch all public channels from bus.fm and save to db
	 */
	public void fetchPublicChannels() {
		HttpGet randomTracksGet = new HttpGet("http://bus.fm/");
		randomTracksGet.addHeader("User-Agent", Constants.USER_AGENT);

		HttpResponse response = null;
		try {
			response = httpClient.execute(randomTracksGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (response == null || response.getStatusLine().getStatusCode() != 200) {
			// 失败了
			return;
		}
		HttpEntity body = response.getEntity();
		String html = null;
		try {
			html = EntityUtils.toString(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = Jsoup.parse(html);
		Elements channelSpans = doc.select("#channellist > span");
		for (Element span : channelSpans) {
			// 只保存公共频道
			if (span.hasClass("ch-public")) {
				Channel c = new Channel();
				String cid = span.attr("cid");
				Long channelId;
				// 已经抓取过了，跳过
				if (StringUtils.isEmpty(cid) || null != channelService.findUniqueChannelByBusfmId((channelId = Long.valueOf(cid)))) {
					continue;
				}
				c.setBusfmId(channelId);
				c.setName(span.select("a").get(0).text());
				channelService.save(c);
			}
		}

		BusfmTracksInfoFetcher.channels = channelService.getAllBusfmChannels();

	}
}
