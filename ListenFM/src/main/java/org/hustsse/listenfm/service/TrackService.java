package org.hustsse.listenfm.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hustsse.listenfm.dao.TrackDao;
import org.hustsse.listenfm.entity.Track_Old;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TrackService {
	@Autowired
	TrackDao trackDao;

	@Transactional(readOnly = false)
	public void save(Track_Old c) {
		trackDao.save(c);
	}

	public Track_Old findUniqueTrackByBusfmId(Long busfmId) {
		return trackDao.findUniqueBy("busfmId", busfmId);
	}

	/**
	 * 随机取n首歌，比较低效的实现，暂且这么干
	 *
	 * @param num
	 * @param channelId
	 * @return
	 */
	public List<Track_Old> findRandomTracks(int num, Long channelId) {
		Set<Long> selectedIds = new HashSet<Long>(num);
		List<Track_Old> result = new ArrayList<Track_Old>(num);
		if (num <= 0)
			return null;
		Random r = new Random();
		Long count = count();
		if (count == null || count <= 0)
			return null;
		// 取全部的track返回
		if (num >= count)
			return findAllTracks();
		long minId = findMinId();
		for (int i = 0; i < num;) {
			long id = Math.abs(r.nextLong()) % count + minId;
			if (!selectedIds.contains(id)) {
				Track_Old t = findTrackById(id);
				if (t == null)
					continue;
				if(channelId != null && channelId != t.getChannel().getId())
					continue;
				else {
					result.add(t);
					i++;
					selectedIds.add(id);
				}
			}
		}
		return result;
	}

	private Track_Old findTrackById(long id) {
		return trackDao.findUniqueBy("id", id);
	}

	private List<Track_Old> findAllTracks() {
		return trackDao.getAll();
	}

	private Long findMaxId() {
		Long maxId = trackDao.findUnique("select max(t.id) from Track t");
		return maxId;
	}

	private Long findMinId() {
		Long minId = trackDao.findUnique("select min(t.id) from Track t");
		return minId;
	}

	private Long count() {
		return trackDao.findUnique("select count(t.id) from Track t");
	}
}
