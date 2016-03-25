package com.ash.radio.mainservice.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	int id;
	@Column(name = "name")
	String name;
	@Column(name = "role")
	int role;
	@Column(name = "password")
	String password;
	@Column(name = "deviceid")
	String deviceId;
	
	@Column(name = "gcmid")
	String gcmId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy="user")
	private Set<SongRequest> songRequests=new HashSet<SongRequest>(0);
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="sender")
	private Set<Message> messages=new HashSet<Message>(0);
	
	public Set<Message> getMessages() {
		return messages;
	}


	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}


	public String getDeviceId() {
		return deviceId;
	}

	
	public Set<SongRequest> getSongRequests() {
		return songRequests;
	}

	public void setSongRequests(Set<SongRequest> songRequests) {
		this.songRequests = songRequests;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGcmId() {
		return gcmId;
	}


	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
