package org.hustsse.cloud.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类
 * 对应到数据库表category，主键id由IdEntity类控制
 * 2012-3-2
 * @author ivan-lee
 * */

@Entity
@Table
public class Category extends IdEntity {

	private String name;	//名称

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
