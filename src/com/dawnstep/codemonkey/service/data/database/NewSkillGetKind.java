package com.dawnstep.codemonkey.service.data.database;

import com.j256.ormlite.field.DatabaseField;

public class NewSkillGetKind {
	@DatabaseField
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
