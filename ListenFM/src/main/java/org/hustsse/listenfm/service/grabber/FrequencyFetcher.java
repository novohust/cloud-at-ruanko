package org.hustsse.listenfm.service.grabber;

import java.util.concurrent.TimeUnit;

public abstract class FrequencyFetcher implements Runnable{

	/** 请求频率，每分钟最多请求多少次 */
	protected int frequency = -1;
	protected long intervalMillis = -1;
	protected long lastFetchTime = -1;

	public FrequencyFetcher(int frequency) {
		setFrequency(frequency);
	}

	public FrequencyFetcher() {

	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
		intervalMillis = 60 * 1000 / frequency;
	}

	protected void ensureFrequency() {
		long time = System.currentTimeMillis();
		long timeElapsed = time - lastFetchTime; // 距离上次抓取时间
		if (lastFetchTime > 0 && timeElapsed < intervalMillis) {
			try {
				TimeUnit.MILLISECONDS.sleep(intervalMillis - timeElapsed);
			} catch (InterruptedException e) {
			}
		}

		lastFetchTime = System.currentTimeMillis();
	}

	protected void sleepAWhile(TimeUnit unit, Long timeout) {
		try {
			unit.sleep(timeout);
		} catch (InterruptedException e) {
		}
	}

}
