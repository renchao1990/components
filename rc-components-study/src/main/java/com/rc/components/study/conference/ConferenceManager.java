package com.rc.components.study.conference;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Conference类
 * @author: rc
 * @date: 2018年3月19日 下午9:53:56
 * @version: V1.0
 * @review: rc/2018年3月19日 下午9:53:56
 */
public class ConferenceManager {
	
	private LinkedList<Talk> talks; //初始化会议列表
	
	private ArrayList<Session> sessions;
	
	
	private ConferenceManager(LinkedList<Talk> talks){
		talks.sort((t1,t2)->t2.getLength().compareTo(t1.getLength())); //按会议时间倒序
		this.talks =talks;
		this.sessions = new ArrayList<>();
		this.sessions.add(new Session("track1am",180)); //track1上午空闲
		this.sessions.add(new Session("track1pm",240)); //track1下午空闲
		this.sessions.add(new Session("track2am",180)); //track2上午空闲
		this.sessions.add(new Session("track2pm",240)); //track2下午空闲
	};
	
	public static ConferenceManager create(){
		LinkedList<Talk> linkedList = null;
		return new ConferenceManager(linkedList);
	}
	
	public void printPlanList(){
		plan();
		//track1am
		int time = 0;
		sessions.stream()
		.filter(s->s.name.equals("track1am"))
		.findFirst().get()
		.talks.forEach(s->s.toString(time));
	}
	
	//执行会议计划程序
	private ArrayList<Session> plan(){
		//talks为空表示已经执行过会议计划
		if (null == talks || talks.isEmpty()) {
			return sessions;
		}
		//talks不为空
		Talk talk;
		while ((talk = talks.poll()) != null) {
			Session mostFree = mostFree(); //获取空余时间最多的
			if (talk.getLength() > mostFree.free) {
				//说明所有tracks的时间满足不了会议的所有时间
				throw new RuntimeException("所有会议的总时间已经超出日程总时间");
			}
			mostFree.talks.add(talk); //将会议添加到此session
			mostFree.free -= talk.getLength(); //此session的空余时间减少
		}
		return sessions;
	}
	
	//查询所有session中空余时间最多的
	private Session mostFree(){
		Session temp = new Session("temp", 0);
		for (Session session : sessions) {
			temp = temp.free >= session.free ? temp :session;
		}
		return temp;
	}
	
	class Session{
		String name;
		Integer free;
		LinkedList<Talk> talks;
		public Session(String name, Integer free) {
			this.name = name;
			this.free = free;
			this.talks = new LinkedList<>();
		}
	}

}





