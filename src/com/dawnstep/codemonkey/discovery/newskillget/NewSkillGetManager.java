package com.dawnstep.codemonkey.discovery.newskillget;

import java.util.ArrayList;
import java.util.List;

import com.dawnstep.codemonkey.service.data.database.NewSkillGet;

public class NewSkillGetManager {
	private NewSkillGetManager(){}
	private static final NewSkillGetManager newSkillGetManager = new NewSkillGetManager();
	private List<NewSkillGet> newSkillGetList = new ArrayList<NewSkillGet>();
	private String currentSkillGetKind;
	
	public List<NewSkillGet> getNewSkillGetList() {
		return newSkillGetList;
	}

	public void setNewsList(List<NewSkillGet> newSkillGetList) {
		this.newSkillGetList = newSkillGetList;
	}

	public static NewSkillGetManager getInstance() {
		return newSkillGetManager;
	}
	
	private int currentOffset = 0;
	
	public void addNewSkillGetItem(NewSkillGet item) {
		newSkillGetList.add(item);
		currentOffset++;
	}
	
	public int getOffset() {
		return currentOffset;
	}
	
	public void clear() {
		this.newSkillGetList.clear();
	}

	public String getCurrentSkillGetKind() {
		return currentSkillGetKind;
	}

	public void setCurrentSkillGetKind(String currentSkillGetKind) {
		this.currentSkillGetKind = currentSkillGetKind;
	}
	
	public String getNewSkillGetId(int position) {
		return newSkillGetList.get(position).getNewSkillGetId();
	}
}
