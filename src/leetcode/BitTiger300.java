package leetcode;

import java.util.*;

public class BitTiger300 {

	//301. Remove Invalid Parentheses ( back-tracking --> DFS & stack)
	//This is a hard question; please refer to discussion online
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
    	//back-tracking method that output correct results along the way
        remove_IPT(s, res, 0, 0, new char[] {'(', ')'});
    	return res;
    }
    
    public void remove_IPT(String s, List<String> res, int iStart, int jStart, char[] direction) {
    	for(int stack=0, i = iStart; i<s.length(); i++) {
    		if(s.charAt(i)==direction[0]) stack++; //"open" Parentheses
    		if(s.charAt(i) == direction[1]) stack--; //"close" Parenthses
    		if(stack>=0) continue; //still valid
    		//"open" < "close" -> not valid 
    		//-> remove 'close' recursively (back-tracking) to find all possible cases
    		for(int j=jStart; j<=i; j++) {// removing one at each position, skipping duplicates; "=" for ")("
    			if(s.charAt(j)==direction[1] && (j==jStart||s.charAt(j-1)!=direction[1])) {
    				remove_IPT(s.substring(0, j)+s.substring(j+1,s.length()), res, i, j, direction);
    			}
    		}
    		return; // Stop here. The recursive calls already handled the rest of the string.
     	}
    	// No invalid closed parenthesis detected. Now check opposite direction, 
    	//or reverse back to original direction.
    	String reverse = new StringBuilder(s).reverse().toString();
    	if(direction[0]=='(') {
    		remove_IPT(reverse, res, 0, 0, new char[] {')', '('});
    	}else{
    		res.add(reverse);
    	}
    }
    
    
    //322. Coin Change (DB) 
    //note: "Solution" is really great!!!
    //method 1: [back tracking (DFS)] to enumerate all subsets of coin frequencies 
    //and return the minimum. (try each coin c, try amount/c of them); time: O(amount ^ n)
    //method 2.1 & 2.2 : Dynamic Programming O(amount*denominations) 
    //Top-down (back-tracking) & Bottom-up (iterative)
    //F(S) - minimum number of coins needed to make change for amount S (see "solution" )
    //Method 1: Bottom-up (iterative)
    public int coinChange(int[] coins, int amount) {
    	//set up table
    	int coinLen = coins.length;
    	int[] dp = new int[amount+1]; //0, 1....6
    	Arrays.fill(dp, amount+1); //for minimum
    	dp[0] = 0; //init
    	for(int i=1; i<=amount;i++) { //amount
    		for(int j=0; j<coinLen; j++)//coin denomination
    		{
    			if(i>=coins[j]) {
        			dp[i] = Math.min(dp[i], dp[i-coins[j]]+1); 
    			}
    		}
    	}
		return dp[amount] == amount+1 ? -1 : dp[amount];
    }
    
    //DP: Top-down;  back-tracking; min length in the tree
//    public int coinChange2(int[] coins, int amount) {
//
//		return 0;
//    }
    
    //338. Counting Bits
    public int[] countBits(int num) {
        
    	
    	return null;
    }
}
