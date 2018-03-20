package com.rc.components.study.arithmetic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 * 括号格式校验
 * @author: rc
 * @date: 2018年3月14日 下午1:51:51
 * @version: V1.0
 * @review: rc/2018年3月14日 下午1:51:51
 */
public class ParenthesesValid {
    private final String S = "(";
    private final String M = "[";
    private final String L = "{";
    private final String RS = ")";
    private final String RM = "]";
    private final String RL = "}";
    
    public boolean isValid(String s) {
        if(null == s || s.length() < 2) return false;
        LinkedList<String> list = new LinkedList<>(Arrays.asList(s.split("")));
        return tryValid(list);
    }
    
    private boolean tryValid(List<String> list){
    	int originalSize = list.size();
    	if (null == list || list.size() % 2 == 1) {
			return false;
		} else if (list.size() == 0) {
			return true;
		}
        for (int i=0; i < list.size()-1; i++) {
			while (isCouple(list.get(i), list.get(i+1))) {
				list.remove(i+1);
				list.remove(i);
				if (i >= list.size() - 1) {
					break;
				}
			}
		}
        if (originalSize == list.size()) {
			return false;
		}
        return tryValid(list);
    }
    
    private boolean isCouple(String b, String a){
    	return (S.equals(b)&& RS.equals(a))
    			|| (M.equals(b) && RM.equals(a))
    			|| (L.equals(b) && RL.equals(a));
    }
    
    private String getOther(String a){
    	if (S.equals(a)) {
			return RS;
		}
    	if (M.equals(a)) {
    		return RM;
    	}
    	if (L.equals(a)) {
    		return RL;
    	}
    	return null;

    }
    
    
    public boolean check(String str){ //错误解法
        if(null == str || str.length() % 2 == 1 ) return false;
    	if (str.length() == 2) {
			String[] strs = str.split("");
			if (isCouple(strs[0], strs[1])) {
				return true;
			}
		}
    	String start = str.substring(0, 1);
    	String end = getOther(start);
    	if (end == null) {
			return false;
		}
    	int lastIndex = str.lastIndexOf(end);
    	if (lastIndex < 0) {
			return false;
		} else if (lastIndex == str.length()-1) {
			return check(str.substring(1, str.length() -1));
		} else {
			String left = str.substring(0, lastIndex + 1);
			String right = str.substring(lastIndex + 1, str.length());
			return check(left) && check(right);
		}
    	
    }
    
    public boolean valid(String s){
    	if(null == s || s.length() % 2 == 1 ) return false;
    	if (s.length() == 0) return true;
    	s = s.replaceAll("\\(\\)", "");
    	s = s.replaceAll("\\[\\]", "");
    	s = s.replaceAll("\\{\\}", "");
    	return valid(s);
    }
    
    @Test
    public void test(){
//    	System.out.println(isValid("(("));
    	System.out.println(check("){"));
    }
    

}
