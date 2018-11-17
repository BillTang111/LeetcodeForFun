package leetcode;
import java.util.*;

public class BitTiger700 {

	//771. Jewels and Stones
    public int numJewelsInStones(String J, String S) {
    	if(J.length()==0 || S.length()==0) return 0;
        HashSet<Character> set = new HashSet<>();
        int res = 0;
        for(int i=0; i<J.length(); i++) {
        	set.add(J.charAt(i));
        }
        for(int j=0;j<S.length();j++) {
        	if(set.contains(S.charAt(j))){
        		res++;
        	}
        }
    	return res;
    }
}
