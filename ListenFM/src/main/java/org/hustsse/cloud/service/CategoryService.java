package org.hustsse.cloud.service;

import java.util.List;

import org.hustsse.cloud.dao.CategoryDao;
import org.hustsse.cloud.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;

	public List<Category> findAllCategories() {
		return categoryDao.getAll();
	}


}
