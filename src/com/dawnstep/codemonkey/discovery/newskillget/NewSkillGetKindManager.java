package com.dawnstep.codemonkey.discovery.newskillget;

import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.service.data.database.NewSkillGetKind;

public class NewSkillGetKindManager {
	private NewSkillGetKindManager(){}
	private static final NewSkillGetKindManager newSkillGetKindManager = new NewSkillGetKindManager();
	List<NewSkillGetKind> newSkillGetKindList = new ArrayList<NewSkillGetKind>();
	
	public List<NewSkillGetKind> getNewSkillGetKindList() {
		return newSkillGetKindList;
	}

	public void setNewsList(List<NewSkillGetKind> newSkillGetKindList) {
		this.newSkillGetKindList = newSkillGetKindList;
	}

	public static NewSkillGetKindManager getInstance() {
		return newSkillGetKindManager;
	}
	
	private int currentOffset = 0;
	
	public void addNewsItem(NewSkillGetKind item) {
		newSkillGetKindList.add(item);
		currentOffset++;
	}
	
	public int getOffset() {
		return currentOffset;
	}
	
	public void clear() {
		this.newSkillGetKindList.clear();
	}
}
