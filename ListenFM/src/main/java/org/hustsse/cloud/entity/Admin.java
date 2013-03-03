package org.hustsse.cloud.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类
 * 对应到数据库表admin，主键id由IdEntity类控制
 * 2012-3-2
 * @author ivan-lee
 * */

@Entity
@Table
public class Admin extends IdEntity{

	private String name;//登录名
	private String password;//密码

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
