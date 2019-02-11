package leetcode;
import java.util.*;

public class BitTiger500 {
	
	//509. Fibonacci Number (DP)
    public int fib(int N) {
        if(N<2) return N;
        
        int[] table = new int[N+1];
        table[0]=0; table[1]=1;
        
        for(int i=2; i<=N; i++){
            table[i]=table[i-1]+table[i-2];
        }
        
        return table[N];
    }
    
    //516. Longest Palindromic Subsequence (DP)
    //https://www.youtube.com/watch?v=v8irqkTcJ6s time O(n2) space O(n)
    public int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
    	//induction rule: time O(n2) space O(n2)
    	// dp[i][j] = the length of longest Palindromic Subsequence in s[i ... j]
    	//case 1 : if s[i]==s[j], dp[i][j] = 2 + dp[i+1][j-1]
    	//case 2 : if s[i]!=s[j], dp[i][j] = Max(dp[i+1][j], dp[i][j-1]) 
    	int len = s.length();
    	int[][] dp = new int[len][len];
    	for(int i=len-1; i>=0; i--) { //right-left triangle of the matrix
    		dp[i][i] = 1; //base case
    		for(int j=i+1; j<len; j++) {
    			if(s.charAt(i)==s.charAt(j)) {
    				dp [i][j] = 2 + dp[i+1][j-1];
    			}else {
    				dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]); 
    			}
    		}
    	}
		return dp[0][len-1];
    }
    

	//528. Random Pick with Weight (accumulated freq sum & binary search)
	class Solution {

	    int[] wSum;//declare an object is ok; imperative value is not
	    Random rd;
	    
	    public Solution(int[] w) {
	    	int len = w.length;
	        this.rd = new Random();
	        for(int i=1; i<len; i++){
	            w[i] += w[i-1];
	        }
	        wSum = w;
	    }
	    
	    public int pickIndex() {
	        int len = wSum.length;
	        int idx = rd.nextInt(wSum[len-1]) + 1; //start from 1
		        //binary search in range (Question 35)
		    	int start=0, end = len-1;
		    	while(start+1<end) {
		   		    int mid = start + (end-start)/2;
		    		if(wSum[mid]<idx) {
		    			start = mid;
		    		}else if (wSum[mid]>idx) {
		    			end = mid;
		    		}else {
		    			return mid;
		    		}
		    	}
		    	//3 cases
		    	if(idx <= wSum[start]) {
		    		return start;
		    	}else if(idx > wSum[end]) {
		    		return end+1;
		    	}else {
		    		return end;
		    	}
	    }
	}
	    
//	    public int pickIndex() {
//	        int value = rd.nextInt(wSum[wSum.length-1])+1;
//            int idx = Arrays.binarySearch(wSum, value);
//            return idx >= 0 ? idx : -idx-1; // note：the negative is insertion point 
//	    }
	
	//535. Encode and Decode TinyURL
    //one auto increasing number (size of array) as primary key.
	public class Codec {

	    //one auto increasing number (size of array) as primary key.
	    List<String> url_lst = new ArrayList<>();
	    
	    // Encodes a URL to a shortened URL.
	    public String encode(String longUrl) {
	        url_lst.add(longUrl);
	        return String.valueOf(url_lst.size()-1);
	    }

	    // Decodes a shortened URL to its original URL.
	    public String decode(String shortUrl) {
	        int idx = Integer.valueOf(shortUrl);
	        return idx < url_lst.size() ? url_lst.get(idx) : "";
	    }
	}
	
	//547. Friend Circles 
	// method1: smart DFS; view matrix as graph 
	// no short-cut; visit every entry in the matrix -> ensure visiting every node O(n^2)
	//refer to "solution" for BFS; Union Find method
    public int findCircleNum(int[][] M) {
    	int group = 0;
    	boolean[] visited = new boolean[M.length];
    	for(int i=0;i<M.length;i++) { //ensure every node is visited
    		if(!visited[i]) {
    			findCircleNum_dfs(M, visited, i); 
    			group++; //increase every time when we start at a new root
    		}
    	}
		return group;
    }
    
    public void findCircleNum_dfs(int[][] M, boolean[] visited, int curRoot) {
    	visited[curRoot] = true;
    	for(int j=0; j<M.length;j++) {
    		if(!visited[j] && M[curRoot][j]==1) {
    			findCircleNum_dfs(M, visited, j);
    		}
    	}
    }
    
    //560. Subarray Sum Equals K (prefixSum array: a very common pre-computation technique)
    //https://www.youtube.com/watch?v=aYfwus5T3Bs
    // prefixSum array: 解决和 subarray sum 相关问题
    // prefixSum[x] = sum of subarray (0,x) = prefixSum[x-1] + nums[x]  O(n)
    // target ==> sum of subarray(i, j) == prefixSum(j) - prefixSum(i) == k;
    //time: O(n); space: O(n)
    public int subarraySum(int[] nums, int k) {
    	if(nums==null || nums.length==0) return 0;
    	HashMap<Integer, Integer> prefixSums = new HashMap<>();
    	prefixSums.put(0, 1); // allow prefixSum(j) - k == ‘0’
    	int result=0; int prefixSum = 0;
    	for(int num : nums) {
    		prefixSum += num;
    		//key point:prefixSum(j) - k ?= prefixSum(i);
    		result += prefixSums.getOrDefault(prefixSum-k, 0); 
    		prefixSums.put(prefixSum, prefixSums.getOrDefault(prefixSum, 0)+1);
    	}
		return result;
    }
    
}
