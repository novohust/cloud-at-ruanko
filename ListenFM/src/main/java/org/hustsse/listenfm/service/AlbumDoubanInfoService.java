package org.hustsse.listenfm.service;

import org.hustsse.listenfm.dao.AlbumDoubanInfoDao;
import org.hustsse.listenfm.entity.AlbumDoubanInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class AlbumDoubanInfoService {
	@Autowired
	AlbumDoubanInfoDao channelDao;

	@Transactional(readOnly=false)
	public void save(AlbumDoubanInfo c){
		channelDao.save(c);
	}
}
