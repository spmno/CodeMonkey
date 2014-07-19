package com.dawnstep.codemonkey;

import java.util.List;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="news")
public class News {
	@DatabaseField(canBeNull = false)
	private String newsId;
	@DatabaseField(canBeNull = false)	
	private String title;
	@DatabaseField(canBeNull = false)
	private String content;
	@DatabaseField()
	private List<String> imagesURL;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	byte[] imageBytes;

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
	public String getNewsId() {
		return newsId;
	}
	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	public List<String> getImages() {
		return imagesURL;
	}
	public void setImages(List<String> images) {
		this.imagesURL = images;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

}
