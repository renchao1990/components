package com.rc.components.study.arithmetic;

import java.util.Arrays;
import java.util.Random;

/**
 * 一个int数组
 * 求取数组的所有子数组中sum的最大值
 * @author: rc
 * @date: 2018年3月1日 下午8:56:00
 * @version: V1.0
 * @review: rc/2018年3月1日 下午8:56:00
 */
public class MaxSubArrSum {
	
	/**
	 * 最快求解算法，时间复杂度O(N)
	 * @param arr
	 * @return
	 */
	public static int maxSubSum(int[] arr){
		int maxSum = 0; //记录所出现的最大sum
		int thisSum = 0; //记录当前子数组的最大sum
		for (int i = 0; i < arr.length; i++) {
			thisSum += arr[i]; //遍历至当前角标的sum
			if (thisSum > maxSum) {
				maxSum = thisSum; //如果当前角标子数组的sum大，更新maxSum
			} else { //如果当前角标子数组的sum小，则不更新maxSum
				if (thisSum < 0) {
					thisSum = 0; //如果当前角标子数组的sum已经小于0了，则可抛弃当前角标之前的所有数组，在其后寻找新的更大sum值子数组
				}
			}
		}
		return maxSum;
	}
	
	/**
	 * 普通的2层循环求解法，时间复杂度O(N*N)
	 * @param arr
	 * @return
	 */
	public static int maxSubSum2(int[] arr){
		int maxSum = 0;
		for (int i = 0; i < arr.length; i++) { //子数组的起始角标
			int thisSum = 0;
			for (int j = i; j < arr.length; j++) { //子数组的结束角标
				thisSum += arr[j];
				if (thisSum > maxSum) {
					maxSum = thisSum;
				}
			}
		}
		return maxSum;
	}
	
	public static int[] createIntArr(int length){
		Random random = new Random();
		int[] arr = new int[length];
		for (int i = 0; i < arr.length; i++) {
			int a = random.nextInt(2);
			if (a == 0) {
				arr[i] = - random.nextInt(length);
			} else {
				arr[i] = random.nextInt(length);
			}
		}
		return arr;
	}
	
	
	public static void main(String[] args) {
		int[] arr = createIntArr(1000000);
		
		long start = System.currentTimeMillis();
		maxSubSum(arr);
		long end = System.currentTimeMillis();
		System.out.println("maxSubSum total time:" + (end - start));
		
		long start2 = System.currentTimeMillis();
		maxSubSum2(arr);
		long end2 = System.currentTimeMillis();
		System.out.println("maxSubSum2 total time2:" + (end2 - start2));
//		System.out.println(Arrays.toString(arr));
		/*
		 * 测试结果：
		 * 数组长度 	time1	time2
		 * 10000	1ms		27ms
		 * 100000	2ms		2244ms
		 * 1000000	6ms		238890ms
		 * 更长的情况下算法2的运行时间已经到了不合理的地步
		 */
	}
	

}










