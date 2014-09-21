package com.dawnstep.codemonkey.utils;

public final class CodeMonkeyConfig {
	// news
	static public String getNewsNetPath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/infos.json";
		}
		return "http://115.29.139.76:3000/infos.json";
	}
	static public String getLastNewsDatePath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/infos/last_info_time.json";
		}
		return "http://115.29.139.76:3000/infos/last_info_time.json";
	}
	static public String getNetRootPath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000";
		}
		return "http://115.29.139.76:3000";
	}
	static public String getInfoPath() {
		return getNetRootPath() + "/infos";
	}
	
	static public String getNewSkillGetPath() {
		return getNetRootPath() + "/new_skill_gets";
	}
	//new skill get kind
	static public String getNewSkillGetKindNetPath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/new_skill_get_kinds.json";
		}
		return "http://115.29.139.76:3000/new_skill_get_kinds.json";
	}
	
	//new skill get kind
	static public String getNewSkillGetNetPath(String kindId) {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/new_skill_get_kinds/" + kindId + "/new_skill_gets.json";
		}
		return "http://115.29.139.76:3000/new_skill_get_kinds/" + kindId + "/new_skill_gets.json";
	}
	
	//authenticate
	static public String getAuthenticatePath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/sign_up.json";
		}
		return "http://115.29.139.76:3000/sign_up.json";
	}
}
