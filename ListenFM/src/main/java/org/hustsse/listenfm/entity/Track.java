package org.hustsse.listenfm.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类
 * 对应到数据库表track，主键id由IdEntity类控制
 * 2012-3-2
 * @author ivan-lee
 * */

@Entity
@Table
public class Track  extends IdEntity{

	private String mp3_url;	//mp3文件url


	private String lyrics;//歌词
	private String name	;//歌曲名
	private String album;//专辑名
	private String album_img_url;//专辑图片url
	private String artist;//歌手
	private Date release_date;//发布日期
	private Date upload_date;//上传日期
	private long download_times;//下载次数
	private long play_times;//播放次数
	private long category_id;//类别id

	public Track() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Track(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public String getMp3_url() {
		return mp3_url;
	}
	public void setMp3_url(String mp3_url) {
		this.mp3_url = mp3_url;
	}
	public String getLyrics() {
		return lyrics;
	}
	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
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
	public String getAlbum_img_url() {
		return album_img_url;
	}
	public void setAlbum_img_url(String album_img_url) {
		this.album_img_url = album_img_url;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public Date getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public long getDownload_times() {
		return download_times;
	}
	public void setDownload_times(long download_times) {
		this.download_times = download_times;
	}
	public long getPlay_times() {
		return play_times;
	}
	public void setPlay_times(long play_times) {
		this.play_times = play_times;
	}
	public long getCategory_id() {
		return category_id;
	}
	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}
}
