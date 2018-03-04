package com.rc.components.study.arithmetic;

public class ReverseStr {
	
	public static String reverse(String str){
		int length = str.length();
		if (1 == length) {
			return str;
		}
		String before = str.substring(0, length/2);
		String after = str.substring(length/2, length);
		return reverse(after) + reverse(before);
		
	}
	
	
	public static void main(String[] args) {
		String string = "123456789";
		System.out.println(reverse(string));
	}

}
