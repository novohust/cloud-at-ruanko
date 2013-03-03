package org.hustsse.cloud.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 实体类 对应到数据库表comment，主键id由IdEntity类控制 2012-3-2
 *
 * @author ivan-lee
 * */

@Entity
@Table
public class Comment extends IdEntity {

	private String email;// 邮箱
	private String name;// 昵称
	private String content;// 内容
	private Date postTime;
	private Track track;
	@Transient
	private String emailHash;

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
		this.emailHash = DigestUtils.md5Hex(email);
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

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	@Transient
	public String getEmailHash() {
		return emailHash;
	}

	@Transient
	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="track_id")
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}
