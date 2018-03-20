package com.rc.components.study.conference;

/**
 * Talk类
 * @author: rc
 * @date: 2018年3月19日 下午9:55:09
 * @version: V1.0
 * @review: rc/2018年3月19日 下午9:55:09
 */
public class Talk {
	
	private String title; //议题
	
	private Integer length; //时长，分钟数


	public Talk(String line) {
		this.title = line.substring(0, line.lastIndexOf(" "));
		String substring = line.substring(line.lastIndexOf(" ") + 1);
		if (substring.equals("lightning")) {
			this.length = 5;
		} else {
			this.length = Integer.parseInt(substring);
		}
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}


	public Object toString(int time) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
