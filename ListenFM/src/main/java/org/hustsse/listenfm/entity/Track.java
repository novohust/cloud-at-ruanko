package org.hustsse.listenfm.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class Track extends IdEntity {
	/* 从busfm抓到的属性 */
	private Long busfmId;
	private String name;
	/** mp3 url，初始为busfm ftp服务器上的url，下载后会被替换成本地路径 */
	private String mp3;
	private String singer;
	private String albumName;
	/** 图片 url，初始外链豆瓣或bus.fm，下载后替换成本地路径 TODO */
	private String img;

	// association
	/** 频道 */
	private Channel channel;
	/** 专辑在豆瓣上的信息 */
	private AlbumDoubanInfo albumDoubanInfo;

	public Track() {
	};

	public Track(Long id) {
		super(id);
	};

	public Long getBusfmId() {
		return busfmId;
	}

	public void setBusfmId(Long busfmId) {
		this.busfmId = busfmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMp3() {
		return mp3;
	}

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "channel_id")
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "track")
	public AlbumDoubanInfo getAlbumDoubanInfo() {
		return albumDoubanInfo;
	}

	public void setAlbumDoubanInfo(AlbumDoubanInfo albumDoubanInfo) {
		this.albumDoubanInfo = albumDoubanInfo;
	}
}
