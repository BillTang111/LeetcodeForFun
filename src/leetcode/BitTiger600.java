package leetcode;

import java.util.*;

public class BitTiger600 {

	//681. Next Closest Time (string<==>int; time conversion; ) (%57)
	//https://www.youtube.com/watch?v=IAet94C1FCc
	//method1: brute force (Simulate the clock going forward by one minute. ) O(24*60) O(1) O(1)
    public String nextClosestTime(String time) {
    	int cur = Integer.parseInt(time.substring(0, 2))*60;
    	cur +=  Integer.parseInt(time.substring(3));
    	HashSet<Integer> digAllowed = new HashSet<Integer>();
    	for(char c : time.toCharArray()) if(c!=':') {
    		digAllowed.add(c-'0');
    	}  	
    	//loop a minute a time
    	while(true) {
    		cur = (cur+1)%(24*60);
    		int [] digits = new int[] {cur/60/10, cur/60%10, cur%60/10, cur%60%10};
    		//labeled break
    		search:{ 
        		for(int d : digits) {
        			if(!digAllowed.contains(d)) break search;
        		}
        		//%02d means "format the integer with 2 digits, left padding it with zeroes",
        		return String.format("%02d:%02d", cur/60, cur%60);
    		}
    	}
    }
    
    
	
}
