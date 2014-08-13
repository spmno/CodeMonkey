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
	
	//new skill get kind
	static public String getNewSkillGetKindNetPath() {
		if(Debug.ENABLE) {
			return "http://192.168.2.231:3000/new_skill_get_kinds.json";
		}
		return "http://115.29.139.76:3000/new_skill_get_kinds.json";
	}
}
