package org.hustsse.cloud.service;

import java.util.List;

import org.hustsse.cloud.dao.CommentDao;
import org.hustsse.cloud.dao.base.Page;
import org.hustsse.cloud.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentService {
	@Autowired
	CommentDao commentDao;

	public List<Comment> findCommentByTrackId(Long trackId, int pageNo, int pageSize) {
		Page<Comment> page = new Page<Comment>(pageSize);
		page.setPageNo(pageNo);
		page = commentDao.findPage(page, "from Comment where track.id = ? order by postTime desc", trackId);
		return page.getResult();
	}

	@Transactional(readOnly = false)
	public void addComment(Comment comment) {
		commentDao.save(comment);
	}



}
