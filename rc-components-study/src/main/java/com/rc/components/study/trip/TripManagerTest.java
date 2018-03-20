package com.rc.components.study.trip;

import org.junit.Test;

public class TripManagerTest {
	
	
	public void test1(String route, int testNo){
		System.out.println("Test" + testNo);
		TripManager.distance(route);
		System.out.println();
	}
	
	public void test6(String start, String end , int maxStops){
		System.out.println("Test6");
		System.out.println("Input:"+ start + " to " + end);
		TripManager tripManager = new TripManager(start, end);
		int numOfMaxStops = tripManager.numOfMaxStops(maxStops);
		System.out.println("Output:" + numOfMaxStops);
		System.out.println();
	}
	
	public void test7(String start, String end , int exactStops){
		System.out.println("Test7");
		System.out.println("Input:"+ start + " to " + end);
		TripManager tripManager = new TripManager(start, end);
		int numOfExactStops = tripManager.numOfExactStops(exactStops);
		System.out.println("Output:" + numOfExactStops);
		System.out.println();
	}
	
	public void test8(String start, String end, int testNo){
		System.out.println("Test" + testNo);
		System.out.println("Input:"+ start + " to " + end);
		TripManager tripManager = new TripManager(start, end);
		int minDistance = tripManager.minDistance();
		System.out.println("Output:" + minDistance);
		System.out.println();
	}
	
	public void test10(String start, String end, int maxDistance){
		System.out.println("Test10");
		System.out.println("Input:"+ start + " to " + end);
		TripManager tripManager = new TripManager(start, end);
		int numOfMaxDistance = tripManager.numOfMaxDistance(maxDistance);
		System.out.println("Output:" + numOfMaxDistance);
		System.out.println();
	}
	
	@Test
	public void test(){
		test1("A-B-C" ,1);
		test1("A-D" ,2);
		test1("A-D-C" ,3);
		test1("A-E-B-C-D" ,4);
		test1("A-E-D" ,5);
		test6("C", "C", 3);
		test7("A", "C", 4);
		test8("A", "C", 8);
		test8("B", "B", 9);
		test10("C", "C", 30);
	}
	
	

}
