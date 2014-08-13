package com.dawnstep.codemonkey.service.data.database;

import com.j256.ormlite.field.DatabaseField;

public class NewSkillGetKind {
	
	@DatabaseField(canBeNull = false, id = true)
	private int id;
	
	@DatabaseField
	private String title;
	
	public String getNewSkillGetKindId() {
		return String.valueOf(id);
	}
	public void setNewSkillGetKindId(String newSkillGetKindId) {
		this.id = Integer.valueOf(newSkillGetKindId).intValue();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
