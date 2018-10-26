package leetcode;

import java.util.*;
//Question 1 - 5, 11
public class BitTigerTop100 {

	private static final int Character = 0;

	/**
	 * Constructor
	 */
	public BitTigerTop100() {

	}

	/**
	 * 1. Two Sum
	 * use hash map (2 loops)
	 */
	public int[] twoSum1(int[] nums, int target) {
		HashMap <Integer, Integer> map = new HashMap<>();
		for (int i =0; i< nums.length; i ++) {
			map.put(nums[i], i);
		}

		for (int i =0; i< nums.length; i ++) {
			int complement = target - nums[i];
			//			"&& map.get(complement) != i" => can't take itself twice
			if (map.containsKey(complement)&& map.get(complement) != i) {
				return new int[] {i, map.get(complement)};
			}
		}
		throw new IllegalArgumentException("no such pair");
	}

	/**
	 * 1. Two Sum
	 * use hash map (1 loops)
	 */
	public int[] twoSum(int[] nums, int target) {
		HashMap <Integer, Integer> map = new HashMap<>();

		for (int i =0; i< nums.length; i ++) {
			int complement = target - nums[i];
			if (map.containsKey(complement)) {
				return new int[] {i, map.get(complement)};
			}
			//			only change: key not found -> add to the map 
			//			no worry about repetition 
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("no such pair");
	}

	/**
	 * 2. Add Two Numbers
	 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) 
	 * Output: 7 -> 0 -> 8 
	 * Explanation: 342 + 465 = 807.
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		//      initialize
		ListNode dumyhead = new ListNode(0);
		ListNode p = l1,  q = l2, curr = dumyhead; 
		int carry = 0;
		while(p != null || q != null){
			int num1 = (p != null) ? p.val : 0;
			int num2 = (q != null) ? q.val : 0;
			int sum = num1 + num2 + carry; 
			carry = sum/10;
			int currRes = sum % 10;
			curr.next = new ListNode(currRes);
			//          update 
			curr = curr.next;
			if(p != null) p = p.next;
			if(q != null) q = q.next;
		}
		if(carry > 0){
			curr.next = new ListNode(carry);
		}
		return dumyhead.next;
	}

	/**
	 * Q 3: hashSet by sliding windows (checking a char in a substring is O(1))
	 * test: "pwwkew", "abcabcc", "bbb"
	 * O(2n) time complex: In the worst case each character will be visited twice by i and j
	 */
	public static int lengthOfLongestSubstring1(String s) {
		HashSet<Character> set = new HashSet<Character>();
		int n = s.length();
		int max = 0;
		int left = 0, right = 0; //window [left, right]

		while (left < n && right < n) {
			if (!set.contains(s.charAt(right))) {
				//    			add new char to the set; update result
				set.add(s.charAt(right++)); //"++" has lower priority than charAt
				max = Math.max(max, right - left); //update result
			}else 
				//				increase left to "shrink" substring [i+1,j] one by one
			{
				set.remove(s.charAt(left++));
			}
		}
		return max;
	}

	/**
	 * Q 3: hashMap by sliding windows
	 * Instead of using a set to tell if a character exists or not, 
	 * we could define a mapping of the characters to its index. 
	 * Then we can skip the characters immediately when we found a repeated character.
	 * O(n) time complex.
	 */
	public static int lengthOfLongestSubstring(String s) {
		HashMap<Character, Integer> map = new HashMap<>(); //<current char, position in s>
		int n = s.length();
		int max = 0;

		for(int left = 0, right = 0; right < n; right++) {
			if(!map.containsKey(s.charAt(right))) {
				//			add new key to the map; update result
				//    			System.out.println("currently put into map: "+ s.charAt(right) + ", " + right);
				map.put(s.charAt(right), right);

			}else {
				//    		update left bound to j'+ 1
				left = Math.max(map.get(s.charAt(right)) + 1,left); //max to prevent left go backwards
				//    			System.out.println("currently update into map: "+ s.charAt(right) + ", " + right);
				map.put(s.charAt(right), right);
			}
			//    		System.out.println("currently left is: "+ left);
			max = Math.max(max, right-left+1);
			//    		System.out.println("currently max is: "+ max);
		}
		return max;
	}

	/**
	 * Q4: Find the median of the two sorted arrays. O(log (m+n)).
	 * BInary search; O(log MIN(m,n))    https://www.youtube.com/watch?v=KB9IcSCDQ9k
	 */
	   public double findMedianSortedArrays(int[] A, int[] B) {
	        int m = A.length;
	        int n = B.length;
	        if (m > n) { // to ensure m<=n
	            int[] temp = A; A = B; B = temp;
	            int tmp = m; m = n; n = tmp;
	        }
			//halfLen = 合并后 左边元素的'个数'； 左中位数下标 = (halfLen-1)
	        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
	        while (iMin <= iMax) { // 2 pointers to determine A 左边个数
	            int i = (iMin + iMax) / 2; //A 左边个数
	            int j = halfLen - i; //B 左边个数
	            if (i < iMax && B[j-1] > A[i]){
	                iMin = i + 1; // i is too small
	            }
	            else if (i > iMin && A[i-1] > B[j]) {
	                iMax = i - 1; // i is too big
	            }
	            else { // i is perfect
	                int maxLeft = 0;
	                if (i == 0) { maxLeft = B[j-1]; }  //A 左边不用。 用B左边 eg. [/ 7] [1 '2' 3]
	                else if (j == 0) { maxLeft = A[i-1]; } //B 左边不用 
	                else { maxLeft = Math.max(A[i-1], B[j-1]); } //[1 2 '3'] ['4' / 5 6 7]
	                if ( (m + n) % 2 == 1 ) { return maxLeft; } //odd case

	                int minRight = 0;
	                if (i == m) { minRight = B[j]; } 
	                else if (j == n) { minRight = A[i]; }
	                else { minRight = Math.min(B[j], A[i]); } //eg. [/ '7'] [1 '2' '3']

	                return (maxLeft + minRight) / 2.0;
	            }
	        }
	        return 0.0;
	    }

	/**
	 * Q5: Longest Palindromic Substring
	 * You may assume that the maximum length of s is 1000.
	 */
	//   DP method
	public static String longestPalindrome(String s) {
		int n = s.length();
		boolean [][] dp = new boolean [n][n];
		String res = null;

		//    	Build DP table
		for (int i = n - 1; i >= 0; i--) { //倒着来，因为dp[i] 得先知道 pd[i+1]
			for (int j = i; j < n; j++) {
				//		for (int i = 0; i < n; i++) {
				//			for (int j = i; j<n; j++) {
				//    			base case
				//				if (j == i+1 && s.charAt(j)==s.charAt(i+1)) {
				//					dp[i][j] = true;
				//				}else if (i == j){
				//					dp[i][j] = true;
				//				}else{
				// the base cases are all covered below: (j-i<3) makes them all true
				//    			DP table general case
				//				dp[i][j] = (dp[i+1][j-1] || j-i<3) && (s.charAt(i) == s.charAt(j)); 
				//order matters: (dp[i+1][j-1] || j-i<3) <=> (j - i < 3 || dp[i + 1][j - 1])
				dp[i][j] = (j - i < 3 || dp[i + 1][j - 1])&&(s.charAt(i) == s.charAt(j));
				if (dp[i][j] && (res ==null || j-i+1 > res.length())) {
					res = s.substring(i,j+1);
				}
				//				}
			}
		}
		return res;
	}
	
	//	7 Reverse Integer    
    public int reverse(int x) {
    	//cannot do this: "roll-over" -- eg: bound=100; input=101; (bound)input = -99
//        if(x == 0 || x>Math.pow(2, 32) || x<-1*Math.pow(2,32)+1) return 0;
        int result = 0;
        while(x != 0) {
        	int tail = x % 10;
        	int newResult = result*10 + tail; //this may overflow
        	//overflow check !!!
        	if(result != (newResult - tail)/10) return 0;
        	x /= 10;
        	result = newResult;
        }
    	return result;
    }
    
    // 9 Palindrome Number 
    //method 1: call reverse %62
    public boolean isPalindrome2(int x) {
    	if(x==0) {
    		return true;
    	}else if(x<0) {
    		return false;
    	}else {
    		int revers = reverse(x);
    		if(revers == x) {return true;}
    		return false; 
    	}
    }
    
    //method 2: % 94
    public boolean isPalindrome(int x) {
    	if(x<0) return false;
    	int origin = x;
    	int revers = 0;
    	while(x!=0) {
    		int tail = x%10;
    		revers = revers*10 + tail; //better remember how to check overflow; though not require (see above)
    		x /= 10;
    	}
    	return origin == revers;
    }
    
    // 10 regular expression matching (2D DP)
    public boolean isMatch(String s, String p) {
    	if(s == null || p == null) return false;
    	boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        // no need to initialize dp[i][0] as false
        // initialize dp[0][j]
    	dp[0][0] = true;
    	for(int j=1; j<dp[0].length; j++) {
    		if(p.charAt(j-1)== '*') { //note: "j-1": j is based on table from 1; j-1 for string from 0
    			//unchanged(x1) or delete(x0) 
    			if(dp[0][j-1] || (j>1 && dp[0][j-2])) {
        			dp[0][j] = true;
    			}
    		} 
    	}   	
    	//build bp table
    	for(int i=1; i<dp.length; i++) {
    		for(int j=1; j<dp[0].length; j++) {
    			//good single instance
    			if(s.charAt(i-1)==p.charAt(j-1) || p.charAt(j-1)=='.') {
    				dp[i][j] = dp[i-1][j-1];
    			}
    			//2 "*" cases
    			if(p.charAt(j-1)=='*') {
    				//no common previous element
    				if(p.charAt(j-2)!=s.charAt(i-1) && p.charAt(j-2)!='.') {
    					//delete functionality
    					dp[i][j] = dp[i][j-2];
    				}else { //some common element
    					//delete(x0) || no change (x1) || copy (x2 ... n)
    					dp[i][j] = dp[i][j-2] || dp[i][j-1] || dp[i-1][j];
    				}
    			}
    		}
    	}
    	return dp[s.length()][p.length()];
    }

	/**
	 * Q11: Container With Most Water
	 * Two Pointer Approach:
	 * At every step, we find out the area formed between them, update 
	 * maxarea and move the pointer pointing to the shorter line towards the other end by one step.
	 */
	public int maxArea(int[] height) {
		int maxArea=0;
		int left = 0, right = height.length-1;
		while (left < right) {
			maxArea = Math.max(maxArea, (right-left)*Math.min(height[left], height[right]));
			if(height[left]<height[right]) {
				left++;
			}else {
				right--;
			}
		}
		return maxArea;
	}

	/**
	 * 14. Longest Common Prefix (vertical scanning: compare each string vertically) 
	 */
	public String longestCommonPrefix1(String[] strs) {
		//    	base case: empty string array
		if (strs.length == 0 || strs == null) {
			return "";
		}
		for (int i = 0; i< strs[0].length(); i++) { // char numb
			char c = strs[0].charAt(i); //all strs compare with the first string 
			for (int j = 1; j < strs.length;j++) { // string numb
				if (strs[j].length() == i || c !=strs[j].charAt(i)) { //case"{'aa','a'}
					return strs[0].substring(0, i);
				}
			}
		}
		return strs[0];
	}

	/**
	 * 14. Longest Common Prefix (Divide and Conquer) 
	 * left for future
	 */
	public String longestCommonPrefix(String[] strs) {

		return strs[0];
	}

	/**
	 * 15. 3Sum: 3 numbers in nums add to 0. eg. [-1 0 1]
	 * Choose a base point -> two pointer method to do two sum
	 */
	//	helper methods: move right/left while checking conditions
	public int moveRight(int[] nums, int currLeft) {
		while(currLeft == 0 || (currLeft < nums.length && nums[currLeft] == nums[currLeft - 1])) {
			currLeft ++;
		}
		return currLeft;
	}

	public int moveLeft(int[] nums, int currRight) {
		while(currRight == nums.length -1 || (currRight >= 0 && nums[currRight] == nums[currRight+1])) {
			currRight --;
		}
		return currRight;
	}
	//	main function
	public List<List<Integer>> threeSum(int[] nums) {
		ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
		//		sort first 
		Arrays.sort(nums);
		int base = 0; //base position starts at 0;
		//loop the base position
		while(base < nums.length -2) {
			//			two pointers
			int baseNum = nums[base];
			int left = base +1, right = nums.length -1;
			//loop two pointers
			while (left < right) {
				int sum = baseNum + nums[left] + nums[right];
				if (sum == 0) { // correct triple -> add to result
					List<Integer> lst = new ArrayList<Integer>();
					lst.add(baseNum); lst.add( nums[left]); lst.add( nums[right]); 
					res.add(lst);
					//"==" case : move both pointers
					left = moveRight(nums, left+1);
					right = moveLeft(nums,right-1);
				}else if(sum<0){
					left = moveRight(nums, left+1);
				}else {
					right = moveLeft(nums,right-1);
				}
			}
			base = moveRight(nums, base+1); //chech and move the base pointer
		}
		return res;
	}

	/**
	 * 17. Letter Combinations of a Phone Number
	 * FIFO queue: remove the top + addLast
	 */
	public List<String> letterCombinations(String digits) {

		LinkedList<String> result = new LinkedList<String>();
		//		base case: 
		if (digits.isEmpty()) {
			return result;
		}

		result.add(""); //result = [""] which is not null
		//    	a mapping eg. "2" -> "abc"
		String [] mapping = new String [] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		//    	build result string using queue
		while(result.peek().length() != digits.length()) {
			String hd = result.remove();
			String CurMapping = mapping[digits.charAt(hd.length()) - '0']; //change char '2' to int 2
			for (char c : CurMapping.toCharArray()) {
				result.addLast(hd+c);
			}
		}
		return result;
	}

	/**
	 * 20. Valid Parentheses
	 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', 
	 * determine if the input string is valid.
	 */
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<Character>();
		for (char c : s.toCharArray()) {
			if(c == '(') {
				stack.push(')');
			}else if (c == '{') {
				stack.push('}');
			}else if (c=='[') {
				stack.push(']');
			}else if(stack.isEmpty()||stack.pop()!=c) {
				return false;
			}
		}
		return stack.isEmpty();
	}

	/**
	 * 21. Merge Two Sorted Linked Lists
	 * Input: 1->2->4, 1->3->4  Output: 1->1->2->3->4->4
	 */
	//    public class ListNode {
	//        int val;
	//        ListNode next;
	//        ListNode(int x) { val = x; }
	//    }
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode dummy_head = new ListNode(0);
		ListNode current = dummy_head;
		ListNode l1_cur = l1, l2_cur = l2;
		while (l1_cur != null || l2_cur != null) {
			if (l2_cur == null || (l1_cur!=null &&l1_cur.val <= l2_cur.val )) {
				current.next = l1_cur;
				l1_cur = l1_cur.next;
			}else {
				current.next = l2_cur;
				l2_cur = l2_cur.next;
			}
			current = current.next;
		}
		return dummy_head.next; 
	}

	/**
	 * 22. Generate Parentheses
	 * Given n pairs of parentheses, 
	 * write a function to generate all combinations of well-formed parentheses.
	 * backtracking 是相当于枚举
	 * https://www.youtube.com/watch?v=SwyEvjkUnPU
	 * time: O(n!) -- Catalan number: Catalan序列是一个整数序列 (0,n-1), (1, n-2), ... (n-1, 0)
	 * space: O(n)
	 */
	public List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<String>();
		if(n==0) return result;
		//add '(' and ')' to result one by one via "back-tracking"
		backTrack(result, "", n, n);
		return result;

	}
	//helper function: "left"= how many '(' left on the left side
	// "str" stores the current result 
	public static void backTrack(List<String> res, String str, int left, int right) {
		// (( ))) is incorrect
		if(left > right) {
			return;
		}
		if(left==0 && right==0) {
			res.add(str);
			return;
		}
		if(left >0) {
			backTrack(res, str+"(", left-1, right);
		}
		if(right>0) {
			backTrack(res, str+")", left, right-1);
		}
	}

	/**
	 * 29. Divide Two Integers
	 * Input: dividend = 10, divisor = 3, Output: 3
	 */
	public int divide(int dividend, int divisor) {
		if(divisor == 0) {return Integer.MAX_VALUE;}
		if(dividend == Integer.MIN_VALUE && divisor==-1) {return Integer.MAX_VALUE;}

		//Reduce the problem to positive long integer to make it easier.
		//Use long to avoid integer overflow cases. (eg. max / 1) !!!
		// This casting is required (otherwise, won't work)
		long divid = (long) dividend;
		long divis = (long) divisor;
		//change sign to positive -> start calc quotient 
		int sign = 1; //as sign of result is positive
		if (divid < 0) {
			divid = - divid;
			sign = - sign;
		}
		if (divis < 0) {
			divis = - divis; //make it positive only
			sign = - sign;
		}

		//shift-left (x2) in while loop to get result
		int result=0;
		while (divid >= divis) {
			int shift = 0;
			//"divisor << shift": do calculation in loop header without changing divisor itself
			//把这个while loop 看作“probing”（试探）
			while(divid >= divis << shift) {
				shift ++;
			}
			//update value and record result
			divid -= divis<<(shift-1);
			result += (1<<(shift-1)); 
		}
		return result*sign;
	}

	/**
	 * 31. Next Permutation
	 * Implement next permutation, which rearranges numbers into 
	 * the lexicographically next greater permutation of numbers.
	 * The replacement must be in-place and use only constant extra memory.
	 * In-place algorithm updates input sequence only through replacement or swapping of elements. 
	 * 
	 */
	public void nextPermutation(int[] nums) {
		//base case
		if (nums==null || nums.length==0) return;
		if (nums.length == 1) return;  // eg. [1]
		//initialization
		int n = nums.length;
		int pointer = n - 2;
		while (nums[pointer]>=nums[pointer+1]) {
			pointer--;
			//If such arrangement is not possible, sorted in ascending order
			if (pointer < 0) break;
		}
		if (pointer < 0) {
			Arrays.sort(nums);
			return;
		}
		//find the numb 'directly' greater than arr[pointer]
		int lgrInx = pointer+1;
		while (lgrInx<n && nums[pointer] < nums[lgrInx]) {
			lgrInx++;
		}
		//swap pointer with the lowest digit to its right
		int temp = nums[pointer];
		nums[pointer] = nums[lgrInx-1];
		nums[lgrInx-1] = temp;
		//sort from pointer left to end 
		Arrays.sort(nums, pointer+1, n);
	}

	/**
	 * 33. Search in Rotated Sorted Array
	 * You are given a target value to search. 
	 * If found in the array return its index, otherwise return -1.
	 * USE: Binary Search
	 */
	public int search(int[] nums, int target) {
		//base case
		if(nums == null || nums.length ==0) return -1;
		int left = 0, right= nums.length-1; //position of initial left and right pointers
		
		while(left+1 < right) {
			//calculate mid (prevent integer overflow)
			int mid = left + (right-left)/2;
			System.out.println("the curr left is:"+ left );
			System.out.println("the curr right is:"+ right );
			System.out.println("the curr mid is:"+ mid );
			if(nums[mid]==target) return mid;
			//case 1
			if(nums[left]<= nums[mid]) {
				if(nums[left] <= target && target <= nums[mid]) right = mid;
				else left = mid;
			}else if (nums[mid]< nums[right]){//case 2 
				if(nums[mid] <= target && target <= nums[right]) left = mid;
				else right = mid;
			}
		}

		if(target == nums[left]) return left;
		if(target == nums[right]) return right;
		return -1;
	}

	/**
	 * 38. Count and Say
	 *  Brute force: 2 for loops
	 *  USE: Class StringBuilder
	 */
	public String countAndSay(int n) {
		//base case
		if (n<=0) return "";
		if (n==1) return "1";
		// general case: build from 2 to n
		String str = "1"; //initial

		for(int i=2; i<=n; i++) {
			int count = 0;
			char prev='.';
			StringBuilder sb = new StringBuilder(); //used to build string
			for(int j=0; j<str.length(); j++) {
				if(prev == str.charAt(j) || prev == '.') {
					count++;
					//    				System.out.println("this is count: "+ count);
				}else{
					sb.append(Integer.toString(count) + prev);
					count = 1; //restart counting
				}
				prev = str.charAt(j); //update prev
				//    			System.out.println("I am here:"+str);
			}
			sb.append(Integer.toString(count) + prev); //append the last set of strings
			str = sb.toString(); //update str
			//			System.out.println("I am here2:"+str);
		}
		return str;
	}

	/**
	 * 39. Combination Sum
	 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target), 
	 * find all unique combinations in candidates where the candidate numbers sums to target.
	 * The same repeated number may be chosen from candidates unlimited number of times.
	 * Back-Tracking
	 * Better Printing: https://www.youtube.com/watch?v=zIY2BWdsbFs 
	 */
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//base case:
		if(candidates==null ||candidates.length==0 || target<0 ) return result;
		//general case:
		Arrays.sort(candidates);  
		backTrack_combinationSum(candidates, target, 0, result, new ArrayList<Integer>());
		return result;
	}

	/**
	 * A helper function for combinationSum
	 * @param candidates: original array
	 * @param target: current residue
	 * @param index:  index of number in candidates to be added
	 * @param result: return
	 * @param curSeq: List of Integer currently building
	 */
	private void backTrack_combinationSum(int[] candidates, int target, int index,
			List<List<Integer>> result, List<Integer> curSeq) {
		//create new object for current list of int since curSeq changes/update every time !!!
		if (target == 0) {
			result.add(new ArrayList<Integer>(curSeq));
		}else if (target > 0) {
			for (int i = index; i<candidates.length;i++) { //防重复，防倒流
				if (candidates[i]>target) break; //back to one level above
				//otherwise, add current number to curSeq (递归常用写法： 加了再减去)
				curSeq.add(candidates[i]);
				//        		System.out.println("_________________");
				//        		System.out.println("this is curSeq:"+ curSeq.toString());
				//        		System.out.println("this is target:"+ target);
				//        		System.out.println("this is index:"+ i);
				//        		System.out.println("this is candidates[i]:"+ candidates[i]);
				backTrack_combinationSum(candidates, target-candidates[i], i, result, curSeq);
				curSeq.remove(curSeq.size()-1);
			}
		}
	}

	/**
	 * 40. Combination Sum II
	 * Given a collection of candidate numbers (candidates) and a target number (target), 
	 * find all unique combinations in candidates where the candidate numbers sums to target.
	 * Each number in candidates may only be used once in the combination.
	 */
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//base case:
		if(candidates==null ||candidates.length==0 || target<0 ) return result;
		//general case:
		Arrays.sort(candidates);  
		backTrack_combinationSum2(candidates, target, 0, result, new ArrayList<Integer>());
		return result;
	}

	private void backTrack_combinationSum2(int[] candidates, int target, int index,
			List<List<Integer>> result, List<Integer> curSeq) {
		//create new object for current list of int since curSeq changes/update every time !!!
		if (target == 0) {
			result.add(new ArrayList<Integer>(curSeq));
		}else if (target > 0) {
			for (int i = index; i<candidates.length;i++) { //防重复，防倒流
				//i==index:current level的第一个数 要保留
				if(i != index && candidates[i]==candidates[i-1]) continue;  	// skip duplicates
				//otherwise, add current number to curSeq (递归常用写法： 加了再减去)
				curSeq.add(candidates[i]);
				backTrack_combinationSum2(candidates, target-candidates[i], i+1, result, curSeq); //"i+1"to next number
				curSeq.remove(curSeq.size()-1);
			}
		}
	}
	
	//42.Trapping Rain Water Difficulty O(n) time; O(1) space
    public int trap(int[] height) {
    	int len = height.length;
    	//base case
        if(len<3) return 0;
        //iterate with two pointers
        int l = 0, r = len-1, totalWater = 0;
        // find the left and right edge which can hold water
        while(l<r && height[l]<height[l+1]) l++;
        while(l<r && height[r]<height[r-1]) r--;
        //iterate over from both sides
        while(l < r) {
        	int lH = height[l], rH = height[r];
        	//always move the lower height pointer
        	if(lH <= rH) {
            	while(l<r && lH>=height[++l]) totalWater += lH - height[l];
        	}else {
            	while(l<r && rH>=height[--r]) totalWater += rH - height[r];
        	}
        }
    	return totalWater;
    }

	/**
	 * 78. Subsets
	 * Given a set of distinct integers, nums, return all possible subsets (the power set).
	 */
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//base case: 
		if(nums == null || nums.length==0) return result;
		//general case:
		Arrays.sort(nums);
		backTrack_subsets(nums, 0, result, new ArrayList<Integer>());
		return result;
	}

	private void backTrack_subsets(int[] nums, int index, List<List<Integer>> result, List<Integer> curLst) {
		result.add(new ArrayList<Integer>(curLst));
		for(int i = index; i<nums.length;i++) {
			curLst.add(nums[i]);
			backTrack_subsets(nums, i+1, result, curLst);
			curLst.remove(curLst.size()-1);
		}
	}

	/**
	 * 90. Subsets II
	 * Given a collection of integers that might contain duplicates, nums, 
	 * return all possible subsets (the power set).
	 */
	public List<List<Integer>> subsetsWithDup(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		//base case: 
		if(nums == null || nums.length==0) return result;
		//general case:
		Arrays.sort(nums);
		backTrack_subsets2(nums, 0, result, new ArrayList<Integer>());
		return result;
	}

	private void backTrack_subsets2(int[] nums, int index, List<List<Integer>> result, List<Integer> curLst) {
		result.add(new ArrayList<Integer>(curLst));
		for(int i = index; i<nums.length;i++) {
			if(i > index && nums[i] == nums[i-1]) continue;  // skip duplicates
			curLst.add(nums[i]);
			backTrack_subsets2(nums, i+1, result, curLst);
			curLst.remove(curLst.size()-1);
		}
	}

	/**
	 * 46. Permutations
	 * Given a collection of distinct integers, return all possible permutations.
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		//base case
		if (nums==null || nums.length == 0 ) return result;
		//general case
		backTrack_permute(nums, result, new ArrayList<Integer>(), new HashSet<Integer>());

		return result;
	}

	private void backTrack_permute(int[] nums, List<List<Integer>> result, List<Integer> curLst, HashSet<Integer> visited) {
		if(curLst.size() == nums.length) {
			result.add(new ArrayList<Integer>(curLst)); //add to result
			return;
		}
		for (int i=0; i< nums.length;i++) {
			if(!visited.contains(nums[i])) {
				visited.add(nums[i]);
				curLst.add(nums[i]);
				backTrack_permute(nums, result, curLst, visited);
				visited.remove(nums[i]);
				curLst.remove(curLst.size()-1);
			}
		}
	}

	/**
	 * 47. Permutations II
	 * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
	 */
	public List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> result = new ArrayList<>();
		//base case
		if (nums==null || nums.length == 0 ) return result;
		//general case
		Arrays.sort(nums);
		backTrack_permute2(nums, result, new ArrayList<Integer>(), new boolean[nums.length]);
		return result;
	}

	private void backTrack_permute2(int[] nums, List<List<Integer>> result, List<Integer> curLst, boolean[] visited) {
		if(curLst.size() == nums.length) {
			result.add(new ArrayList<Integer>(curLst)); //add to result
			return;
		}
		for (int i=0; i< nums.length;i++) {
			//ADD: screen out duplicates
			if(i>0 && nums[i]==nums[i-1] && visited[i-1]==true) continue; 
			if(!visited[i]) {
				System.out.println("i am here");
				visited[i]=true; //update visited number
				curLst.add(nums[i]);
				System.out.println("curLst:"+curLst);
				backTrack_permute2(nums, result, curLst, visited);
				visited[i]=false;
				curLst.remove(curLst.size()-1);
			}
		}
	}

	/**
	 * 131. Palindrome Partitioning
	 * Given a string s, partition s such that every substring of the partition is a palindrome.
	 */
	public List<List<String>> partition(String s) {
		List<List<String>> result = new ArrayList<>();
		//base case
		if (s==null || s.length() == 0 ) return result;
		//general case
		backTrack_partition(s, result, new ArrayList<String>(), 0);
		return result;
	}

	//check and partition s[index, s.length)
	private void backTrack_partition(String s, List<List<String>> result, List<String> curLst, int index) {
		if(index>=s.length()) {
			result.add(new ArrayList<String>(curLst));
			return;
		}
		for(int i=index; i<s.length(); i++) {
			if(isPalindrome(s,index,i)) {
				curLst.add(s.substring(index, i+1));
				backTrack_partition(s, result, curLst, i+1); //NOTE:i+1 not index+1 (recursion in depth)
				curLst.remove(curLst.size()-1);
			}
		}
	}

	//check if a string is a Palindrome for s[left, right]
	private boolean isPalindrome(String s, int left, int right) {
		while(left < right)
		{
			if(s.charAt(left++) != s.charAt(right--))
				return false;
		}
		return true;
	}

	/**41. First Missing Positive
	 * Given an unsorted integer array, find the smallest missing positive integer.
	 * In Place Swapping 
	 * @param nums
	 * @return
	 */
	public int firstMissingPositive(int[] nums) {
		//a smart way to use while loop: increase i only when needed (unlike for loop)
		int i=0, n=nums.length;
		while(i<n) { //sort array in place
			// If the current value is in the range of (0,length) and it's not at its correct position, 
			// swap it to its correct position.
			// Else just continue;
			System.out.println("the nums is"+Arrays.toString(nums));
			if(nums[i] >= 0 && nums[i]<n && nums[nums[i]]!=nums[i]) { //repair:java.lang.ArrayIndexOutOfBoundsException
				swap(nums, nums[i],i);
			}else {
				i++;
			}
		}
		int j=1; //start from position 1 (smallest positive integer)
		while(j<n && nums[j]==j ) {
			//    		System.out.println("the j is"+j);
			j++;
		}

		// If it breaks because of empty array or reaching the end. K must be the first missing number.
		if (n == 0 || j < n) {
			return j;
		}else {
			// If k is hiding at position 0, K+1 is the number. It is either when array is [0], or [1], chains.
			return (nums[0]==j) ? j+1 : j;
		}

	}

	private void swap(int[] nums, int old_num, int pos) {
		int temp = nums[pos];
		nums[pos]=nums[old_num];
		nums[old_num] = old_num; //key for this problem
	}

	/**
	 * 43. Multiply Strings
	 * @param num1
	 * @param num2
	 * @return
	 */
	public String multiply(String num1, String num2) {
		int n1=num1.length(), n2=num2.length();
		int[] result = new int[n1+n2];
		System.out.println(Arrays.toString(result) );
		for(int i=n1-1; i>=0;i--) {
			for(int j=n2-1; j>=0;j--) {
				int multip = (num1.charAt(i) -'0') * (num2.charAt(j) -'0');
				int carry = i+j;
				int base = i+j+1; //here: to use the formula "i x j = [i+j,i+j+1]"
				int sum = result[base] + multip; //the idea of "carry" is really like "tail recursive"
				result[base] = sum%10; result[carry] += sum/10;
			}
		}
		System.out.println(Arrays.toString(result) );
		//build string
		StringBuilder sb = new StringBuilder();
		for (int digit : result) {
			if(sb.length()==0 && digit==0) { //remove 0 in the front
				continue;
			}
			sb.append(digit);
		}
		return (sb.length()==0)? "0" : sb.toString();
	}

	/**
	 * 49. Group Anagrams
	 * @param strs
	 * @return
	 * intuition1: Two strings are anagrams if and only if their sorted strings are equal.
	 * intuition2: Two strings are anagrams if and only if their character counts
	 */

	public List<List<String>> groupAnagrams(String[] strs) {


		return null;

	}

	/*
	 * iPihone drop DP problem
	 */
	public int leastDrop(int iPhone, int floor) {
		int [][] min_drops = new int[iPhone+1][floor+1]; 
		//base case: 
		for(int m=0; m<iPhone+1; m++) {
			min_drops[m][0]=0; //no floor requires no drop
			min_drops[m][1]=1; //one floor requires one drop
		}

		for(int n=0; n<floor+1; n++) {
			min_drops[0][n]=0; //no iPhone, no drop
			min_drops[1][n]=n; //one iPhone, brute force search
		}
		//general case: 
		for(int m=2; m<iPhone+1; m++) {
			for(int n=2; n<floor+1; n++) {
				min_drops[m][n]=Integer.MAX_VALUE;
				for(int k=1; k<n+1; k++) {
					//iPhone breaks at floor k
					int iPhoneBreak = min_drops[m-1] [k-1];
					//iPhone not breaks at floor k
					int iPhoneNotBreak = min_drops[m] [n-k];
					//formula: min_drops(m, n)= Min(Max(min_drops(m, n-i), min_drops(m-1, i-1)) + 1)
					int temp = Math.max(iPhoneBreak, iPhoneNotBreak)+1;
					min_drops[m][n] = Math.min(temp, min_drops[m][n]);
				}
			}
		}

		return min_drops[iPhone][floor];
	}

	/*
	 * 53. Maximum Subarray
	 * DP: For the current entry, add previous sum or not
	 * formula: max = Max(max,Max(nums[i], nums[i]+maxCur)) 
	 */
	public int maxSubArray(int[] nums) {
		if(nums==null || nums.length==0) {
			return 0;
		}

		int maxCur = nums[0], max = nums[0]; //initialize at 0 entry; starting loop from 1
		for (int i=1; i<nums.length;i++) { 
			maxCur=Math.max(nums[i], nums[i]+maxCur); //DP: add maxCur (previous sum) or not
			max= Math.max(max, maxCur);
		}
		return max;
	}

	/***
	 * 54. Spiral Matrix
	 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
	 * @param matrix
	 * @return
	 */
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> result = new ArrayList<Integer>();
		if (matrix==null || matrix.length==0 || matrix[0].length==0) return result;
		//initialize four pointers
		int top = 0, bot = matrix.length-1, left = 0, right=matrix[0].length-1;
		//loop till no longer able to circle
		while(left<right && top<bot) {
			for(int i=left; i<right; i++) result.add(matrix[top][i]);
			for(int i=top; i<bot; i++) result.add(matrix[i][right]);
			for(int i=right; i>left;i--) result.add(matrix[bot][i]);
			for(int i=bot; i>top; i--) result.add(matrix[i][left]);
			left++; right--; top++; bot--;
		}
		//3 conditions left
		if(left==right) {	//top<bot
			for(int i=top; i<=bot; i++) {
				result.add(matrix[i][right]);
			}
		}else if (top==bot) {		//top==bot
			for(int i=left; i<=right; i++) {
				result.add(matrix[top][i]);
			}
		}
		//else (we do nothing)
		return result; 
	}

	/**
	 * 59. Spiral Matrix II
	 * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
	 */
	public int[][] generateMatrix(int n) {
		int[][] matr = new int[n][n];
		int num = 1;

		//4 pointers
		int row_start = 0;
		int row_end = n-1;
		int col_start = 0;
		int col_end = n-1;

		//loop to build matrix ("=" for inner part of matrix)
		while(row_start <= row_end && col_start <=col_end) {
			for(int i = col_start; i <= col_end; i++) {
				matr[row_start][i] = num ++; //update result
			}
			row_start ++; //update pointer
			for(int j = row_start; j<= row_end; j++) {
				matr[j][col_end] = num++;
			}
			col_end --; //update pointer
			for(int i=col_end; i>=col_start; i-- ) {
				matr[row_end][i] = num ++;
			}
			row_end --; 
			for(int j=row_end; j>=row_start; j--) {
				matr[j][col_start] = num++;
			}
			col_start ++;
		}
		return matr;
	}

	/**
	 * 55: Jump Game (DP)
	 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
	 * Each element in the array represents your maximum jump length at that position.
	 * 
	 * Usually, solving and fully understanding a dynamic programming problem is a 4 step process
		Start with the recursive backtracking solution
		Optimize by using a memoization table (top-down[3] dynamic programming)
		Remove the need for recursion (bottom-up dynamic programming)
		Apply final tricks to reduce the time / memory complexity
	 */

	/** https://segmentfault.com/a/1190000006121957
	 * Version 1: backtracking O(2^n)
	 * @param nums
	 * @return
	 */
	//a recursive helper function 
	public boolean canJumpFromPos(int position, int[] num) {
		int len = num.length;
		//destination reached (base case)
		if(position == len -1) {
			return true;
		}
		//backtracking(every possible cases) via recursion
		int maxJump = Math.min(len -1, num[position]+position);
		for (int nextPos = position+1; nextPos <=maxJump; nextPos++) {
			if( canJumpFromPos(nextPos, num)) {
				return true;
			}
		}
		return false;
	}

	public boolean canJump_backTrack(int[] nums) {
		return canJumpFromPos(0, nums);   
	}

	/** 
	 * Version 2: Dynamic Programming Top-down O(n^2)
	 * Build table memo[] recording Good, Bad, Unkonwn for current index
	 * https://leetcode.com/problems/jump-game/solution/
	 * @param nums
	 * @return
	 */
	//enables for a variable to be a set of predefined constants
	enum Index{
		GOOD, BAD, UNKNOWN
	}

	//DP table to be built
	Index[] memo;

	//method to build table
	public boolean canJumpFromPos2(int position, int[] num) {
		int len = num.length;
		//first checks if the index is known
		System.out.println(position);
		if(memo[position] != Index.UNKNOWN) {
			return memo[position]==Index.GOOD ? true : false;
		}

		//backtracking(every possible cases) via recursion while building the table
		int maxJump = Math.min(len - 1, num[position]+position);
		for (int nextPos = position+1; nextPos <=maxJump; nextPos++) {
			if(canJumpFromPos2(nextPos, num)) {
				memo[position] = Index.GOOD; //update the table
				return true;
			}
		}
		memo[position] = Index.BAD;
		return false;
	}

	public boolean canJump2(int[] nums) {
		//Initialize the table to be all UNKNOWN
		//wrong: Index[] memo = new Index[nums.length];
		// this will create a new object not shared by the other method
		memo = new Index[nums.length];
		for(int i=0; i<memo.length; i++) {
			memo[i]=Index.UNKNOWN;
		}
		//base case: initialize the last index to be GOOD
		memo[memo.length-1] = Index.GOOD;
		return canJumpFromPos2(0, nums);   
	}

	/** 
	 * Version 3: Dynamic Programming T Bottom-up -- going backward O(n^2)
	 * eliminating recursion --> tail recursive; for-loop. 
	 * In practice, this achieves better performance as we no longer 
	 * have the method stack overhead and might even benefit from some caching. 
	 * More importantly, this step opens up possibilities for future optimization. 
	 * The recursion is usually eliminated by trying to reverse the order of the steps from the top-down approach.
	 */
	//22.9%
	public boolean canJump3(int[] nums) {

		//Init index table (new one)
		Index[] table = new Index[nums.length];
		for (int i=0; i<table.length;i++) {
			table[i]=Index.UNKNOWN;
		}
		table[table.length-1] = Index.GOOD;

		//looping from right to left to find if table[0]==Good
		for(int pos=nums.length-2; pos>=0; pos--) {
			int maxJumpPos = Math.min(nums.length-1, pos+nums[pos]);
			for(int prob=maxJumpPos; prob>pos; prob--) {
				if(table[prob]==Index.GOOD) {
					table[pos]=Index.GOOD;
					break;
				}
			}
		}

		return table[0]==Index.GOOD;
	}

	/** 
	 * Version 4: Greedy
	 */
	public boolean canJump(int[] nums) {
		//base case
		if(nums.length<2) {
			return true;
		}
		//loop to find reachability
		int maxReach = 0;
		for(int pos=0; pos<=nums.length-1 && pos<=maxReach ;pos++) {

		}

		return false;
	}

	/**
	 * 45. Jump Game II (BFS)
	 * You can assume that you can always reach the last index.
	 * https://www.youtube.com/watch?v=r3pZd9ghqxk
	 */
	public int jump(int[] nums) {
		if(nums.length<2) return 0;
		int level=0, idx=0, cur_Max=0, next_max=0;
		//inner for loop could have idx >cur_max --> break infinite loop 
		while(idx <= cur_Max) {
			//traverse current level, and update the max reach of next level
			while(idx <= cur_Max) {
				next_max=Math.max(next_max, nums[idx]+idx);
				idx++;
			}
			level++;
			cur_Max = next_max;
			if(next_max>=nums.length-1) return level;
		}
		return 0;
	}

	/**
	 * 56. Merge Intervals (two pointers)
	 * sort then merge
	 */
	private class Interval {
		int start;
		int end;
		Interval() { start = 0; end = 0; }
		Interval(int s, int e) { start = s; end = e; }
	}

	public List<Interval> merge(List<Interval> intervals) {
		List<Interval> res = new ArrayList<Interval>();
		int n = intervals.size();
		int[] start = new int[n];
		int[] end = new int[n];

		//sort start[] and end[]
		for(int i=0; i<n;i++) {
			start[i]=intervals.get(i).start;
			end[i]=intervals.get(i).end;
		}
		Arrays.sort(start);
		Arrays.sort(end);

		// for distinct Interval, the latter one's start must > previous one's end.
		for(int i=0, j=0; i<n; i++) { // j is the start of interval
			if(i==n-1 || start[i+1]>end[i]) {
				res.add(new Interval(start[j],end[i]));
				j=i+1; //start from (previous end + 1)
			}
		}
		return res;
	}

	/**
	 * 62. Unique Paths (DP)
	 */
	public int uniquePaths(int m, int n) {
		//base case: with this, 1ms -> 0ms
		if(m==0 || n==0) {
			return 0;
		}

		if(m==1 || n==1) {
			return 1;
		}

		//general case 
		int [][] Path = new int[m][n];

		for(int i=0;i<m;i++){
			Path[i][0] = 1; //only 1 way 
		}

		for(int j=0;j<n;j++) {
			Path[0][j] = 1;
		}

		for(int i=1;i<m;i++) {
			for(int j=1;j<n;j++) {
				Path[i][j]=Path[i-1][j]+Path[i][j-1];
			}
		}

		return Path[m-1][n-1];
	}

	/**70. Climbing Stairs
	 * 
	 */
	//DP method
	public int climbStairs(int n) {
		//base case
		if(n<3) return n;

		//general case
		//dp[i] = # ways to step i
		int[] dp = new int[n+1]; //0...n

		dp[0]=0;
		dp[1]=1;
		dp[2]=2;

		for(int i=3;i<n+1;i++) {
			dp[i] = dp[i-1]+dp[i-2];
		}
		return dp[n];
	}

	//Fibonacci Number
	public int climbStairs_Fibonacci(int n) {
		if(n<3) return n;

		int fst = 1;
		int sec = 2;
		int thrd = 0;

		for(int i=3;i<n+1; i++) {
			thrd = fst + sec;
			fst = sec;
			sec = thrd;
		}
		return sec;
	}

	/**
	 * 72. Edit Distance
	 **/
	public int minDistance(String word1, String word2) {
		int m = word1.length(), n = word2.length();
		if(m==0) return n;
		if(n==0) return m;

		//define: dp[i][j] = min cost converting first i of word1 to first j of word 2
		int[][] dp = new int[m+1][n+1]; //0....n or m 

		//consider any operation from word1 to word2.
		for(int i=0;i<m+1;i++) {
			for(int j=0;j<n+1;j++) {
				//base case
				if(i == 0) {
					dp[0][j] = j;
				}else if(j == 0) {
					dp[i][0] = i;
				}else if(word1.charAt(i-1)==word2.charAt(j-1)){//word[0] ==> dp[1]
					//general case 
					dp[i][j]=dp[i-1][j-1];
				}else {
					dp[i][j] = 1 + Math.min(dp[i][j-1], Math.min(dp[i-1][j], dp[i-1][j-1]));
				}
			}
		}
		return dp[m][n];
	}

	/**
	 * 76. Minimum Window Substring
	 * GOOD summary of "sub-string" / "two pointer"
	 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
	 * */
	//Find t in s 
	public String minWindow(String s, String t) {
		//base case
		if(s.length()==0||t.length()==0||s.length()<t.length()) return "";

		//general case
		char[] tArr = t.toCharArray();
		char[] sArr = s.toCharArray();

		int[] map = new int[256]; //array as map(key=char, value= count of unmatched in T)
		for(char c: tArr) map[c]++;

		int begin=0, end=0, counter=tArr.length; //counter==# of unmatched chars
		int minLength=Integer.MAX_VALUE, head = 0; 

		//move end pointer
		while(end<sArr.length) {
			if(map[sArr[end]]>0) { //do only what it is asked to do!!! (decrease counter)
				//    			map[sArr[end]]--; (can't differ normal with target chars in map)
				counter--;
			}
			map[sArr[end]]--;
			//move begin pointer when one of sub-array is found
			while(counter==0){
				int curLength = end - begin + 1;
				if(curLength < minLength) {
					minLength = curLength;
					head = begin;
				}
				map[sArr[begin]]++;
				if (map[sArr[begin]] > 0) {
					counter++; //differ normal with target chars in map
				}
				begin++;
			}
			end++;
		}
		//find s in t or not 
		return head+minLength>sArr.length? "" : s.substring(head, head+minLength);
	}

	/***
	 * 84. Largest Rectangle in Histogram
	 * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1, 
	 * find the area of largest rectangle in the histogram.
	 * https://www.youtube.com/watch?v=KkJrGxuQtYo
	 */
	public int largestRectangleArea(int[] heights) {
		if(heights == null || heights.length == 0) {return 0;}
		int largestRec = 0;
		Stack<Integer> stack = new Stack<>();

		for(int i = 0; i < heights.length; i++){ 
			//pop when current height decreases (right boundary is found)
			while(!stack.isEmpty() && heights[i] < heights[stack.peek()]){ 
				int pos = stack.pop(); 
				int left = stack.isEmpty() ? -1 : stack.peek(); 
				int height = heights[pos]; 
				largestRec = Math.max(height * (i - left - 1), largestRec);  //"i" is the current right position
			}
			//push everyone onto stack
			stack.push(i); 
		}

		//deal with the final ones
		while(!stack.isEmpty()){
			int right = heights.length;
			int pos = stack.pop();
			int left = stack.isEmpty() ? -1 : stack.peek();
			largestRec = Math.max(largestRec, heights[pos] * (right - left - 1));
		}
		return largestRec;
	}

	/***
	 * 89. Gray Code
	 * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code.
	 */
	public List<Integer> grayCode(int n) {
		List<Integer> res = new ArrayList<Integer>();
		for(int i=0; i< 1<<n; i++) {
			res.add(i^i>>1);
		}
		return res;
	}

	/***
	 * 91. Decode Ways (DP)
	 * Given a non-empty string containing only digits, determine the total number of ways to decode it.
	 */
	public int numDecodings(String s) {
		//corner case
		if(s.length()==0) return 0;

		//general case
		int len = s.length();
		int[] dp = new int[len]; //1-D table with entry == #of ways to decode from 0 ... i
		dp[0]= s.charAt(0)== '0'? 0 : 1; //eg. 02 -> 0 way
		for(int i=1; i<len; i++) {
			if(dp[0]==0) break;

			//2 cases when 2 digits can combine
			if(s.charAt(i-1)=='1' ) {
				dp[i] = 1 + dp[i-1];
			}else if(s.charAt(i-1)=='2' && s.charAt(i)<='6') {
				dp[i] = 1 + dp[i-1];
				//        		System.out.println("i am here");

			}else {
				dp[i]++; //always legitimate by itself
				System.out.println(dp[i]);
			}		
		}
		return dp[len-1];
	}


	

}









