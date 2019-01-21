package com.gabor.party.main.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Events {

	@Id
	@GeneratedValue
	public long id;
	
	public String name;
	
	public Date startOfEvent;
	
	public String location;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartOfEvent() {
		return startOfEvent;
	}

	public void setStartOfEvent(Date startOfEvent) {
		this.startOfEvent = startOfEvent;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
