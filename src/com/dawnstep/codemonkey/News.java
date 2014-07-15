package com.dawnstep.codemonkey;

import java.io.InputStream;
import java.util.List;

public class News {
	private String newsId;
	private String title;
	private String content;
	private List<String> imagesURL;
	
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
	public List<String> getImages() {
		return imagesURL;
	}
	public void setImages(List<String> images) {
		this.imagesURL = images;
	}
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

}
