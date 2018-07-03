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
	 * Q4: Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
	 * BInary search; O(log MIN(m,n))    https://www.youtube.com/watch?v=KB9IcSCDQ9k
	 * k = 合并后中位数 = 左边元素的个数 = (n1+n2+1)/2
	 * m1 = 左边使用元素的个数
	 */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		//    	[l, r) 左闭右开，所以r不能=m-1。循坏条件是 l < r，但最后 l 可能等于 r， 表示第一个数组所有的元素都要使用
		//    	“<”: not enough large number on the left side


		return 0;

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
		int strNum = strs.length;
		//    	base case: empty string array
		if (strs.length == 0 || strs == null) {
			return "";
		}
		//    	if (strs.length == 1) {
		//    		return strs[0];
		//    	}
		//    	general case: use the first string
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
		//binary search to find the target value
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

		int i = 0, n = nums.length;
		while (i < n) {
	        // If the current value is in the range of (0,length) and it's not at its correct position, 
	        // swap it to its correct position.
	        // Else just continue;
			if (nums[i] >= 0 && nums[i] < n && nums[nums[i]] != nums[i])
				swap(nums, i, nums[i]);
			else
				i++;
		}
		int k = 1;

	    // Check from k=1 to see whether each index and value can be corresponding.
		while (k < n && nums[k] == k)
			k++;

	    // If it breaks because of empty array or reaching the end. K must be the first missing number.
		if (n == 0 || k < n)
			return k;
		else   // If k is hiding at position 0, K+1 is the number. It is either when array is [0], or [1].
			return nums[0] == k ? k + 1 : k;

	}

	private void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
}






