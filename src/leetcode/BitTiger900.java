package leetcode;
import java.util.*;

public class BitTiger900 {

	//904. Fruit Into Baskets (sliding window)
	//Translation: Find out the longest length of sub-arrays with at most 2 different numbers?
	//83 ms (there are)
    public int totalFruit(int[] tree) {
        HashMap<Integer, Integer> count = new HashMap<>();
        int max=0, l=0; 
        for(int r=0; r<tree.length; r++) {
        	count.put(tree[r], count.getOrDefault(tree[r], 0) + 1); //add tree[r] to basket
        	while(count.size()>2) { //close left window
        		count.put(tree[l],count.get(tree[l]) - 1); //remove tree[l] from basket
        		if(count.get(tree[l])==0) count.remove(tree[l]); 
        		l++;
        	}
        	max = Math.max(max, r-l+1);
        }
    	return max;
    }
    
	
	
}
