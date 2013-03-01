package org.hustsse.listenfm.service.grabber;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.hustsse.listenfm.entity.Track;
import org.hustsse.listenfm.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BusfmMp3Fetcher implements Runnable {

	private static DefaultHttpClient httpClient = new DefaultHttpClient();
	{
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20); // 真是蛋疼的设定，默认是2，这意味着对一个server最多只能有两个连接，坑爹啊！
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		httpClient = new DefaultHttpClient(cm);
	}
	private File rootDir;

	public File getRootDir() {
		return rootDir;
	}

	public void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}

	private static BlockingQueue<Track> tracks = new LinkedBlockingDeque<Track>(4000);

	@Autowired
	private TrackService trackService;

	public BusfmMp3Fetcher() {

	}

	byte[] buffer = new byte[1024 * 10];

	public void run() {
		while (true) {
			Track t = null;
			try {
				t = tracks.poll(Integer.MAX_VALUE, TimeUnit.DAYS);
			} catch (InterruptedException e1) {
			}
			String url = t.getMp3();
			HttpGet get = new HttpGet(url);
			get.addHeader("Referer", "http://bus.fm/js/Jplayer.swf");
			get.addHeader("User-Agent", Constants.USER_AGENT);

			HttpResponse response = null;
			try {
				response = httpClient.execute(get);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 404或其他错误?休息一会儿再重试
			if (response == null || response.getStatusLine().getStatusCode() != 200) {
				try {
					TimeUnit.SECONDS.sleep(Constants.RETRY_INTERVAL_SECONDS);
				} catch (InterruptedException e) {
				}
				continue;
			}

			// 下载mp3，文件名取url中的文件名
			HttpEntity entity = response.getEntity();
			try {
				String filePath = writeToDisk(url, entity);
				t.setMp3(filePath);
			}catch (RuntimeException e) {
				continue;
			}
			trackService.save(t);
		}
	}

	private String writeToDisk(String url, HttpEntity entity) {
		String[] a = url.split("/");
		String subDirName = a[a.length - 2];
		String fileName = a[a.length - 1];
		String localPath = "/" + subDirName + "/" + fileName;
		if(!rootDir.exists()) {
			rootDir.mkdir();
		}
		File subDir = new File(rootDir.getAbsolutePath() + File.separator + subDirName);
		if (!subDir.exists() || subDir.isFile()) {
			subDir.mkdir();
		}
		File mp3File = new File(subDir.getAbsolutePath() + File.separator + fileName);
		if (mp3File.exists()) {
			return localPath;
		}
		InputStream in = null;
		BufferedOutputStream out = null;
		try {
			in = entity.getContent();
			out = new BufferedOutputStream(new FileOutputStream(mp3File, true));
			int readed = 0;
			while ((readed = in.read(buffer)) != -1) {
				out.write(buffer, 0, readed);
			}
			return localPath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static boolean enqueue(Track t) {
		try {
			return tracks.offer(t, Integer.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

}
