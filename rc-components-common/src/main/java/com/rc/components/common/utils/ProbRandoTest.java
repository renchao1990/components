package com.rc.components.common.utils;

public class ProbRandoTest {
	
	public static void main(String[] args) {
		ProbRandom pr = new ProbRandom(1000);
		pr.put("A", 300);
		pr.put("B", 200);
		pr.put("C", 300);
		int countA = 0;
		int countB = 0;
		int countC = 0;
		long start = System.currentTimeMillis();
		System.out.println(start);
		for (int i = 0; i < 10000; i++) {
			Object next = pr.next();
			if ("A".equals(next)) {
				countA++;
			}else if ("B".equals(next)) {
				countB++;
			}else if ("C".equals(next)) {
				countC++;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println("countA" + countA);
		System.out.println("countB" + countB);
		System.out.println("countC" + countC);
	}

}
