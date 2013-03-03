package org.hustsse.cloud.dao;

import org.hustsse.cloud.dao.base.HibernateDao;
import org.hustsse.cloud.entity.Category;
import org.hustsse.cloud.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao extends HibernateDao<Comment, Long>{

}
