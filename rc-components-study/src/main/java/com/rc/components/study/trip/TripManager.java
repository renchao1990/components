package com.rc.components.study.trip;

import java.util.HashMap;
import java.util.Map;

/**
 * 旅程管理类
 * 描述某一次旅程的所有搭乘线路的相关信息
 * @author: rc
 * @date: 2018年3月18日 下午8:18:18
 * @version: V1.0
 * @review: rc/2018年3月18日 下午8:18:18
 */
public class TripManager {
	
	private static final Map<String, Integer> TRAIN_MAP; //铁路线路图,包含所有火车班次路线
	
	static{
		TRAIN_MAP = new HashMap<>();
		TRAIN_MAP.put("AB", 5);
		TRAIN_MAP.put("BC", 4);
		TRAIN_MAP.put("CD", 8);
		TRAIN_MAP.put("DC", 8);
		TRAIN_MAP.put("DE", 6);
		TRAIN_MAP.put("AD", 5);
		TRAIN_MAP.put("CE", 2);
		TRAIN_MAP.put("EB", 3);
		TRAIN_MAP.put("AE", 7);
	}
	
	
	private String start; //起始站点
	
	private String end;	//到达站点
	
	private final Map<String, Integer> allRoutes; //start-end 所有搭乘方案(不重复搭乘某一趟火车)
	
	public TripManager(String start, String end){
		this.start = start;
		this.end = end;
		allRoutes = new HashMap<>();
		tripsWithoutSameTrain(null, 0);
	}
	
	/**
	 * 计算某一线路的距离
	 * @param route
	 * @return
	 */
	public static int distance(String route){
		System.out.println("Input:" + route);
		int distance = 0;
		String[] stops = route.split("-");
		if (stops.length <= 1) {
			System.out.println("ILLEGAL ROUTE FORMAT");
			return distance;
		}
		for(int i = 0; i < stops.length -1; i++){
			String subRoute = stops[i] + stops[i+1]; //拆分的route
			if (!TRAIN_MAP.containsKey(subRoute)) {
				System.out.println("Output:NO SUCH ROUTE");
				return 0;
			}
			distance += TRAIN_MAP.get(subRoute);
		}
		System.out.println("Output:" + distance);
		return distance;
	}
	
	//计算两地之间的所有路线(不重复搭乘同一趟火车)
	private void tripsWithoutSameTrain(String route, int distance){
		if (route == null) {
			route = start;
			distance = 0;
		}
		//遍历
		for (Map.Entry<String, Integer> e : TRAIN_MAP.entrySet()) {
			if (route.contains(e.getKey())) {
				continue;
			}
			if (!e.getKey().startsWith(route.substring(route.length()-1))) {
				continue;
			} 
			String newRoute = route + e.getKey().substring(1);
			int newDistance = distance + e.getValue();
			if (!newRoute.endsWith(end)) {
				tripsWithoutSameTrain(newRoute, newDistance);
			} else {
				allRoutes.put(newRoute, newDistance);
//				System.out.println("可达路线："+ newRoute + "，距离:" + newDistance);
			}
		}
	}
	
	/**
	 * 计算所有搭乘线路中的最小距离
	 * @return
	 */
	public int minDistance(){
		int minDistance = 0;
		String route = "";
		for (Map.Entry<String, Integer> e : allRoutes.entrySet()) {
			if (minDistance == 0) {
				minDistance = e.getValue();
				route = e.getKey();
			} else {
				minDistance = minDistance < e.getValue() ? minDistance : e.getValue();
				route = minDistance < e.getValue() ? route : e.getKey();
			}
		}
		System.out.println("最短路线: " + route);
		return minDistance;
	}
	
	/**
	 * 计算所有搭乘线路中的最少停战数
	 * @return
	 */
	public int minStops(){
		int minStops = 0;
		for (Map.Entry<String, Integer> e : allRoutes.entrySet()) {
			if (minStops == 0) {
				minStops = e.getKey().length() - 1;
			} else {
				minStops = minStops < e.getKey().length() - 1 ? minStops : e.getKey().length() - 1;
			}
		}
		return minStops;
	}
	
	
	/**
	 * @param maxStops
	 * @return 总停站数小于等于maxStops的线路数量
	 */
	public int numOfMaxStops(int maxStops){
		int num = 0;
		//start-end线路之后可以续接任意组end-end的线路
		TripManager endToEnd = new TripManager(end, end); //新建end-end的线路
		//根据end-end的最小停站数确定续接的end-end的最大组数
		int maxEndToEndNum = (maxStops - this.minStops())/endToEnd.minStops();
		for (Map.Entry<String, Integer> e : allRoutes.entrySet()) {
			String tempRoute = e.getKey();
			if (tempRoute.length()-1 <= maxStops) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，途径站点:" + (tempRoute.length()-1));
			}
			if (tempRoute.length()-1 < maxStops) {
				num += groupEndToEndMaxStops(maxEndToEndNum, maxStops, tempRoute, endToEnd);
			}
		}
		return num;
	}
	//end-end线路续接(递归)
	private int groupEndToEndMaxStops(int maxEndToEndNum, int maxStops, String route, TripManager endToEnd){
		if (maxEndToEndNum <= 0) {
			return 0;
		}
		int num = 0;
		for (Map.Entry<String, Integer> e : endToEnd.allRoutes.entrySet()) {
			String tempRoute = route + e.getKey().substring(1);
			if (tempRoute.length()-1 == maxStops) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，途径站点:" + (tempRoute.length()-1));
			}
			if (tempRoute.length()-1 < maxStops) {
				num += groupEndToEndMaxStops(maxEndToEndNum - 1, maxStops, tempRoute, endToEnd);
			}
		}
		return num;
	}
	
	
	/**
	 * @param exactStops
	 * @return 总停站数等于exactStops的线路数量
	 */
	public int numOfExactStops(int exactStops){
		int num = 0;
		//start-end线路之后可以续接任意组end-end的线路
		TripManager endToEnd = new TripManager(end, end); //新建end-end的线路
		//根据end-end的最小停站数确定续接的end-end的最大组数
		int maxEndToEndNum = (exactStops - this.minStops())/endToEnd.minStops();
		for (Map.Entry<String, Integer> e : this.allRoutes.entrySet()) {
			String tempRoute = e.getKey();
			if (tempRoute.length()-1 == exactStops) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，途径站点:" + (tempRoute.length()-1));
			}
			if (tempRoute.length()-1 < exactStops) {
				num += groupEndToEndExactStops(maxEndToEndNum, exactStops, tempRoute, endToEnd);
			}
		}
		return num;
		
	}
	//end-end线路续接(递归)
	private int groupEndToEndExactStops(int maxEndToEndNum, int exactStops, String route, TripManager endToEnd){
		if (maxEndToEndNum <= 0) {
			return 0;
		}
		int num = 0;
		for (Map.Entry<String, Integer> e : endToEnd.allRoutes.entrySet()) {
			String tempRoute = route + e.getKey().substring(1);
			if (tempRoute.length()-1 == exactStops) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，途径站点:" + (tempRoute.length()-1));
			}
			if (tempRoute.length()-1 < exactStops) {
				num += groupEndToEndExactStops(maxEndToEndNum - 1, exactStops, tempRoute, endToEnd);
			}
		}
		return num;
	}
	
	/**
	 * @param maxDistance
	 * @return 总距离小于等于maxDistance的线路数量
	 */
	public int numOfMaxDistance(int maxDistance){
		int num = 0;
		//start-end线路之后可以续接任意组end-end的线路
		TripManager endToEnd = new TripManager(end, end); //新建end-end的线路
		//根据end-end的最小停站数确定续接的end-end的最大组数
		int maxEndToEndNum = (maxDistance - this.minDistance())/endToEnd.minDistance();
		for (Map.Entry<String, Integer> e : this.allRoutes.entrySet()) {
			String tempRoute = e.getKey();
			int tempDistance = e.getValue();
			if (tempDistance < maxDistance) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，路线距离:" + tempDistance);
				num += groupEndToEndMaxDistance(maxEndToEndNum, maxDistance, tempRoute, tempDistance, endToEnd);
			}
		}
		return num;
		
	}
	//end-end线路续接(递归)
	private int groupEndToEndMaxDistance(int maxEndToEndNum, int maxDistance, String route, int distance, TripManager endToEnd){
		if (maxEndToEndNum <= 0) {
			return 0;
		}
		int num = 0;
		for (Map.Entry<String, Integer> e : endToEnd.allRoutes.entrySet()) {
			String tempRoute = route + e.getKey().substring(1);
			int tempDistance = distance + e.getValue();
			if (tempDistance < maxDistance) {
				num++;
				System.out.println("可达路线："+ tempRoute + "，路线距离:" + tempDistance);
				num += groupEndToEndMaxDistance(maxEndToEndNum - 1, maxDistance, tempRoute, tempDistance, endToEnd);
			}
		}
		return num;
	}


}












