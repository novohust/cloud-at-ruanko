package org.hustsse.listenfm.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类
 * 对应到数据库表comment，主键id由IdEntity类控制
 * 2012-3-2
 * @author ivan-lee
 * */

@Entity
@Table
public class Comment extends IdEntity{

	private String email;//邮箱
	private String name	;//昵称
	private String content	;//内容

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Comment(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}



}
