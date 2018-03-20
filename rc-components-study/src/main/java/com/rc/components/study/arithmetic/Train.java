package com.rc.components.study.arithmetic;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class Train {
	private Map<String, Integer> map = new HashMap<>();
	
	private List<Trip> trips = new ArrayList<>();
	
	
	private Function<Trip, Boolean> limit = null;
	
	{
		map.put("AB", 5);
		map.put("BC", 4);
		map.put("CD", 8);
		map.put("DC", 8);
		map.put("DE", 6);
		map.put("AD", 5);
		map.put("CE", 2);
		map.put("EB", 3);
		map.put("AE", 7);
	}
	
	//计算路程距离
	public void distance(String route){
		String[] stops = route.split("-");
		if (stops.length <= 1) {
			System.out.println("ILLEGAL ROUTE FORMAT");
			return;
		}
		int distance = 0;
		for(int i = 0; i < stops.length -1; i++){
			String subRoute = stops[i] + stops[i+1]; //拆分的route
			if (!map.containsKey(subRoute)) {
				System.out.println("NO SUCH ROUTE");
				return;
			}
			distance += map.get(subRoute);
		}
		System.out.println(distance);
	}
	
	
	
	
	public void distinct(String end,Trip trip){
		if (!limit.apply(trip)) {
			return;
		}
		String stop = trip.route.substring(trip.route.length()-1);
		for (Map.Entry<String, Integer> trian : map.entrySet()) {
			if (!trian.getKey().startsWith(stop)) {
				continue;
			} else if (trip.route.length() == 1) {
				trip = new Trip(trian.getKey(), trian.getValue());
			} else if (trip.route.endsWith(end)) {
				System.out.println(trip.route+trip.distance);
				return ;
			} else {
				trip = new Trip(trip.route + trian.getKey().substring(trian.getKey().length()-1), trip.distance + trian.getValue());
			}
			distinct(end, trip);
		}
		return;
	}
	
	//计算两地之间的所有路线(不重复搭乘同一趟火车)
	public void tripsWithoutSameTrain(String end, Trip trip){
		String start = trip.route.substring(trip.route.length()-1);
		//遍历
		Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> next = iterator.next();
			if (trip.route.contains(next.getKey())) {
				continue;
			}
			if (!next.getKey().startsWith(start)) {
				continue;
			} 
			Trip newTrip = new Trip(trip.route + next.getKey().substring(1), trip.distance + next.getValue());
			if (!newTrip.route.endsWith(end)) {
				tripsWithoutSameTrain(end, newTrip);
			} else {
				trips.add(newTrip);
				System.out.println("可达路线："+ newTrip.route + "，距离:" + newTrip.distance);
			}
		}
	}
	
	
	//返回一份地图的复制
	private Map<String, Integer> copyMap(Map<String, Integer> trains){
		HashMap<String, Integer> copy = new HashMap<>();
		if (null == trains) {
			copy.putAll(this.map);
		} else {
			copy.putAll(map);
		}
		return copy;
	}
	
	@Test
	public void test(){
//		limit = c->c.route.length() <= 4;
//		distinct("C", new Trip("C",0));
		/*distance("A-B-C");
		distance("A-D");
		distance("A-D-C");
		distance("A-E-B-C-D");
		distance("A-E-D");*/
		tripsWithoutSameTrain("C", new Trip("A", 0));
	}
	
	//线路基本信息
	private class Trip{
		String route; //线路
		Integer distance; //距离
		public Trip(String route, Integer distance) {
			this.route = route;
			this.distance = distance;
		}
		
		public void init(){
			route = "";
			distance = 0;
		}
	}
	
	
	public static void main(String[] args) {
		
		System.out.println(JSON.toJSONString("123".split("-")));
		System.out.println(JSON.toJSONString(null));
//		System.out.println(3&7);
//		System.out.println(7&5);
//		System.out.println(165&7);
//		System.out.println(222&222);
	}

}
