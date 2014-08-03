package com.dawnstep.codemonkey;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class NewsImage {
	@DatabaseField()
	private String imageURL;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] imageBytes;
	@DatabaseField(foreignColumnName="id", foreign=true, foreignAutoRefresh = true, useGetSet=true)
	private News newsId;
	
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imagesURL) {
		this.imageURL = imagesURL;
	}
	public byte[] getImageBytes() {
		return imageBytes;
	}
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
	public News getNewsId() {
		return newsId;
	}
	public void setNewsId(News newsId) {
		this.newsId = newsId;
	}

}
