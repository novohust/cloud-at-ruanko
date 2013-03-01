package org.hustsse.listenfm.service.grabber;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hustsse.listenfm.entity.Channel;
import org.hustsse.listenfm.entity.Track;
import org.hustsse.listenfm.service.ChannelService;
import org.hustsse.listenfm.service.TrackService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class MusicGather implements ApplicationContextAware {
	@Autowired
	private BusfmChannelFetcher busfmChannelFetcher;
	@Autowired
	private Executor executor;
	private ApplicationContext appContext;

	private Resource songDir;
	private String songDirPath;

	private int downloadThreads = 3;
	private int doubanFrequency = 20;
	private int tracksInfoFrequency = 30;

	public void start() {
		// fetch all channel first
		busfmChannelFetcher.fetchPublicChannels();

		// 启动mp3下载线程
		for (int i = 0; i < downloadThreads; i++) {
			BusfmMp3Fetcher downloader = appContext.getBean(BusfmMp3Fetcher.class);
			downloader.setRootDir(new File(songDirPath));
			executor.execute(downloader);
		}

		// 启动豆瓣api抓取线程
		DoubanAlbumFetcher doubanFetcher = appContext.getBean(DoubanAlbumFetcher.class);
		doubanFetcher.setFrequency(doubanFrequency);
		executor.execute(doubanFetcher);

		// 启动track info抓取线程
		BusfmTracksInfoFetcher tracksInfoFetcher = appContext.getBean(BusfmTracksInfoFetcher.class);
		tracksInfoFetcher.setFrequency(tracksInfoFrequency);
		executor.execute(tracksInfoFetcher);
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContext = applicationContext;
	}

	public Resource getSongDir() {
		return songDir;
	}

	public void setSongDir(Resource songDir) throws IOException {
		this.songDir = songDir;
		this.songDirPath = songDir.getFile().getAbsolutePath();
	}

	public int getDownloadThreads() {
		return downloadThreads;
	}

	public void setDownloadThreads(int downloadThreads) {
		this.downloadThreads = downloadThreads;
	}

	public int getDoubanFrequency() {
		return doubanFrequency;
	}

	public void setDoubanFrequency(int doubanFrequency) {
		this.doubanFrequency = doubanFrequency;
	}

	public int getTracksInfoFrequency() {
		return tracksInfoFrequency;
	}

	public void setTracksInfoFrequency(int tracksInfoFrequency) {
		this.tracksInfoFrequency = tracksInfoFrequency;
	}
}
