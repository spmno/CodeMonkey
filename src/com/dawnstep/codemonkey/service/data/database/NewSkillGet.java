package com.dawnstep.codemonkey.service.data.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public class NewSkillGet {

	@DatabaseField(canBeNull = false, id = true)
	private int id;
	@DatabaseField
	private String title;
	@DatabaseField
	private String content;
	@DatabaseField(dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	@DatabaseField(foreignColumnName="id", foreign=true, foreignAutoRefresh = true, useGetSet=true)
	private NewSkillGetKind newSkillGetKindId;
	
	public NewSkillGetKind getNewSkillGetKindId() {
		return newSkillGetKindId;
	}
	public void setNewSkillGetKindId(NewSkillGetKind newSkillGetKindId) {
		this.newSkillGetKindId = newSkillGetKindId;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
}
