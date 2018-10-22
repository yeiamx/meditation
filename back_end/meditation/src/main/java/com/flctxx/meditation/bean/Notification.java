package com.flctxx.meditation.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @Column(name = "id")
    private String id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "img_url")
    private String imgUrl;
    
    @Column(name = "res_url")
    private String resUrl;
    
    @Column(name = "type")
    private String type;
    
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Notification(String id, String title, String content, String imgUrl) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imgUrl = imgUrl;
	}
	
	public Notification(String id, String title, String content, String imgUrl, String resUrl) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imgUrl = imgUrl;
		this.resUrl = resUrl;
	}
	
	public Notification(String id, String title, String content, String imgUrl, String resUrl, String type) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imgUrl = imgUrl;
		this.resUrl = resUrl;
		this.type = type;
	}
	
	public Notification() {
		
	}
    
}
