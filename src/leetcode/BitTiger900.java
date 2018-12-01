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
    
	//945. Minimum Increment to Make Array Unique
    //wrong
    public int minIncrementForUnique(int[] A) {
    	int len = A.length;
    	if(len<2) return 0;
    	Arrays.sort(A);
    	int res = 0;
    	
    	for(int i=1;i<len;i++) {
    		if(A[i]<=A[i-1]) {
    			res += A[i-1] - A[i] +1;
    			A[i]=A[i-1]+1;
    		}
    	}
    	
    	return res;
    }
    
    //946. Validate Stack Sequences
    public boolean validateStackSequences(int[] pushed, int[] popped) {
    	Stack<Integer> stack = new Stack<>();
    	int popPosition = 0;
    	for(int pushNum : pushed) {
    		stack.push(pushNum);
    		while(!stack.empty() && stack.peek() == popped[popPosition]) {
    			stack.pop();
    			popPosition++;
    		}
    	}
    	return stack.empty();
    }
    
    
    //948. Bag of Tokens (Greedy + Two Pointers)
    //come up greedy idea with "Buy at the cheapest and sell at the most expensive."
    public int bagOfTokensScore(int[] tokens, int P) {
    	Arrays.sort(tokens); // cheapest to most expensive
    	int cheapPos=0; int expensivePos=tokens.length-1;
    	int totalPoints=0; int res = 0;
    	while(cheapPos<=expensivePos) {
    		if(P >= tokens[cheapPos]) {
    			P -= tokens[cheapPos];
    			cheapPos++;
    			totalPoints++;
    			res = Math.max(res, totalPoints); //check [28,76] and 62
    		}else if(totalPoints>0) {
    			totalPoints --;
    			P += tokens[expensivePos];
    			expensivePos--;
    		}else { // Power < tokens[cheapPos]
    			break;
    		}
    	}
    	return res;
//    	return totalPoints;
    }
    
    //929. Unique Email Addresses
    public int numUniqueEmails(String[] emails) {
        HashSet<String> set = new HashSet<String>();
    	
    	for(String s : emails) {
    		int indexAt = s.indexOf('@');
    		int firstAdd = s.indexOf('+');
    		String local ="";
    		if(firstAdd < indexAt) {
        		 local = s.substring(0,firstAdd);

    		}else {
        		 local = s.substring(0,indexAt);
    		}
    		String localName = local.replace(".", "");
    		String total = localName+s.substring(indexAt+1);
    		set.add(total);
    	}
    	return set.size();
    }
    
    
    
}
