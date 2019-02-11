package leetcode;

import java.util.*;

public class BitTiger800 {
	
	//811. Subdomain Visit Count (String Parsing; HashMap)
    public List<String> subdomainVisits(String[] cpdomains) {
    	Map<String, Integer> map = new HashMap();
    	for(String countPair : cpdomains) {
    		int spaceIdx = countPair.indexOf(' ');
    		int count = Integer.valueOf(countPair.substring(0, spaceIdx));
    		String domain = countPair.substring(spaceIdx+1);
    		//put domain to map
    		map.put(domain, map.getOrDefault(domain, 0)+count);
    		//put sub-domain to map
    		for(int i=0;i<domain.length();i++) {
    			if(domain.charAt(i)=='.') {
    				String subDomain = domain.substring(i+1);
    	    		map.put(subDomain, map.getOrDefault(subDomain, 0)+count);
    			}
    		}
    	}
    	//iterate HashMap as set to output
    	List<String> res = new ArrayList();
    	for(String domain : map.keySet()) {
    		res.add(map.get(domain)+" "+domain);
    	}
		return res;
    }
	
	//829. Consecutive Numbers Sum (math)
	//连续等差数列和：N can be expressed as k + 1, k + 2, ..., k + i,
	//consecutive sum  (start at k with length n) = N = k * n + (n + 1) * n / 2
	//=> N - (i + 1) * i / 2 = k * i
    public int consecutiveNumbersSum(int N) {
    	int res = 1; 
    	//Since for i = 1, N can always be written as one number sequence
    	for(int i=2; N - (i + 1) * i / 2>=0; i++) {
    		if((N - (i + 1) * i / 2)%i == 0) res++;
    	}
		return res; 
    }
    
    //too slow 
//    public int consecutiveNumbersSum(int N) {
//    	int res = 1; 
//    	for(int i=1;i<=N/2;i++) {
//    		int sum = 0;
//    		int cur = i;
//    		while(sum<N) {
//    			sum += cur;
//    			if(sum==N) res++;
//    			cur++;
//    		}
//    	}
//		return res; 
//    }

    //844. Backspace String Compare (Two Pointer)
    public boolean backspaceCompare(String S, String T) {
    	int s=S.length()-1, t=T.length()-1;
    	int countS=0, countT=0; //# of '#' can be used
    	while(s>=0 || t>=0) { //case: "bbbextm" "bbb#extm" (need to check all chars in both string)
    		
    		//Deletion on S 
    		while(s>=0 && (countS>0 || S.charAt(s)=='#')) {
    			if(S.charAt(s)=='#') {
    				countS++;
    			}else {
    				countS--;
    			}
    			s--;
    		}
    		char left = s>=0 ? S.charAt(s) : '@';
    		
    		//Deletion on T
    		while(t>=0 && (countT>0 || T.charAt(t)=='#')) {
    			if(T.charAt(t)=='#') {
    				countT++;
    			}else {
    				countT--;
    			}
    			t--;
    		}
    		char right = t>=0 ? T.charAt(t) : '@';
    		
    		if(left != right) return false;
    		s--; t--;
    	}
    	
		return true;
    }
}
