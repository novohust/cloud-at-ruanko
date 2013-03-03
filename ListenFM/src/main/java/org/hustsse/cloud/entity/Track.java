package org.hustsse.cloud.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 实体类 对应到数据库表track，主键id由IdEntity类控制 2012-3-2
 *
 * @author ivan-lee
 * */

@Entity
@Table
public class Track extends IdEntity {

	private String audioUrl; // mp3文件url
	private String lyricsUrl;// 歌词
	private String name;// 歌曲名
	private String album;// 专辑名
	private String albumImgUrl;// 专辑图片url
	private String artist;// 歌手
	private Date releaseDate;// 发布日期
	private Date uploadTime;// 上传日期
	private long downloadTimes;// 下载次数
	private long playTimes;// 播放次数
	private Category category;
	private List<Comment> comments;

	public Track() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Track(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public String getLyricsUrl() {
		return lyricsUrl;
	}

	public void setLyricsUrl(String lyricsUrl) {
		this.lyricsUrl = lyricsUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getAlbumImgUrl() {
		return albumImgUrl;
	}

	public void setAlbumImgUrl(String albumImgUrl) {
		this.albumImgUrl = albumImgUrl;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public long getDownloadTimes() {
		return downloadTimes;
	}

	public void setDownloadTimes(long downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public long getPlayTimes() {
		return playTimes;
	}

	public void setPlayTimes(long playTimes) {
		this.playTimes = playTimes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="track")
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
