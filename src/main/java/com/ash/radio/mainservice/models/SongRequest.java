package com.ash.radio.mainservice.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "songrequest")
public class SongRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public SongRequest() {

	}

	public SongRequest(int userId,String song) {
		User user=new User();
		user.setId(userId);
		this.setUser(user);
		this.setSong(song);
		this.setAccept(true);
	}

	@Id
	@GeneratedValue
	@Column(name = "id")
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	@Column(name = "song")
	String song;
	@Column(name = "isaccept")
	boolean accept;
	@Column(name = "requesttime")
	Date requestTime;
	@Column(name = "accepttime")
	Date acceptTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "requester", nullable = false)
	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
