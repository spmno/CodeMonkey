package com.dawnstep.codemonkey;

import java.io.InputStream;

public class News {
	private String title;
	private String content;
	private InputStream[] images;
	
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
	public InputStream[] getImages() {
		return images;
	}
	public void setImages(InputStream[] images) {
		this.images = images;
	}

}
