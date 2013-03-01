package org.hustsse.listenfm.entity;

import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table
public class Channel extends IdEntity {

	private Long busfmId;
	private String name;

	public Channel() {}
	public Channel(Long id) {super(id);}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBusfmId() {
		return busfmId;
	}

	public void setBusfmId(Long busfmId) {
		this.busfmId = busfmId;
	}

}
