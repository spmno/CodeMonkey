package com.dawnstep.codemonkey.service.data.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;


public class News implements Cloneable {
	@DatabaseField(canBeNull = false, id = true)
	private int id;
	@DatabaseField(canBeNull = false)	
	private String title;
	@DatabaseField(canBeNull = false)
	private String content;

	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getNewsId() {
		return String.valueOf(id);
	}
	public void setNewsId(String newsId) {
		this.id = Integer.valueOf(newsId).intValue();
	}

	@Override
	public Object clone() {
		News news = null;  
        try{  
        	news = (News)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return news; 
	}
}
