package leetcode;

import java.util.*;

public class BitTiger400 {

	//412. Fizz Buzz
    public List<String> fizzBuzz(int n) {
        List<String> res = new LinkedList<>();
        if(n==0) return res;
        for(int i=0; i<n; i++) {
        	int number = i+1;
        	if(number%3==0 && number%5==0) {
        		res.add("FizzBuzz");
        	}else if(number%3==0) {
        		res.add("Fizz");
        	}else if(number%5==0) {
        		res.add("Buzz");
        	}else {
        		res.add(Integer.toString(number));
        	}
        }

    	return res;
    }
	
	
	//482. License Key Formatting (47.11%)
    public String licenseKeyFormatting(String S, int K) {
        if(S.length()==0 || K==0) return S;
        StringBuilder sb = new StringBuilder();
        int term=0;
        for(int i=S.length()-1;i>=0;i--){
        	if(S.charAt(i)=='-') continue;
            sb.append(S.charAt(i));
        	if(++term % K == 0) sb.append('-');
        }
        //remove '-' at the end of string
        int pt=sb.length()-1;
        while(pt>=0 && sb.charAt(pt)=='-') {

        	sb.deleteCharAt(pt);
            pt--;
        }
		return sb.reverse().toString().toUpperCase();
    }
}
