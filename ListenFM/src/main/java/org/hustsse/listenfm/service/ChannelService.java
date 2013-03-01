package org.hustsse.listenfm.service;

import java.util.List;

import org.hustsse.listenfm.dao.ChannelDao;
import org.hustsse.listenfm.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class ChannelService {

	@Autowired
	ChannelDao channelDao;

	@Transactional(readOnly=false)
	public void save(Channel c){
		channelDao.save(c);
	}

	public Channel findUniqueChannelByBusfmId(Long busfmId) {
		return channelDao.findUniqueBy("busfmId", busfmId);
	}

	public List<Channel> getAllBusfmChannels() {
		return channelDao.getAll();
	}

}
