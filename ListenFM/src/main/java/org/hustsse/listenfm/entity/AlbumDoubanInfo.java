package org.hustsse.listenfm.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class AlbumDoubanInfo extends IdEntity{

	// db:attribute
	private String releaseDate;
	private String singer;
	private String publisher;
	// album name
	private String title;

	// gd:rating
	private String averageScore;
	private String maxScore;
	private String minScore;
	private int numRaters;

	// douban album id
	private Long doubanAlbumId;

	// link to douban
	private String link;
	// img
	private String img;

	// association
	@JsonIgnore	//避免json化track的时候死循环
	private Track track;

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getMinScore() {
		return minScore;
	}

	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}

	public int getNumRaters() {
		return numRaters;
	}

	public void setNumRaters(int numRaters) {
		this.numRaters = numRaters;
	}

	public long getDoubanAlbumId() {
		return doubanAlbumId;
	}

	public void setDoubanAlbumId(Long doubanAlbumId) {
		this.doubanAlbumId = doubanAlbumId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}


	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="track_id")
	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}


}
