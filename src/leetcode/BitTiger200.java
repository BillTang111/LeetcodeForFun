package leetcode;

import java.util.*;


public class BitTiger200 {

	//200 Number of Islands
	//Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
	// idea: dfs or bfs
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length ==0) return 0;
        int num_islands=0;
        for(int r=0; r<grid.length; r++) {
        	for(int c=0; c<grid[0].length; c++) {
        		if(grid[r][c]=='1') {
        			num_islands++;
        			dfs_numIslands( grid, r, c);
        		}
        	}
        }
    	return num_islands; 
    }
    
    //helper function that sets 1 to 0 
    public void dfs_numIslands(char[][] grid, int r, int c) {
    	if (r<0 || c<0 || r>=grid.length || c>=grid[0].length || grid[r][c]=='0') return; 
    	//set current spot to 0
    	grid[r][c]='0';
    	//visit neighbor nodes in 4 directions
    	dfs_numIslands(grid, r-1, c);
    	dfs_numIslands(grid, r+1, c);
    	dfs_numIslands(grid, r, c-1);
    	dfs_numIslands(grid, r, c+1);
    }
	
	
	//206. Reverse Linked List
	//Reverse a singly linked list.
    public ListNode reverseList(ListNode head) {
        if(head == null) return head;
        ListNode prev = null;
        //loop
        while(head!=null) {
        	ListNode temp = head.next;
        	head.next = prev;
        	prev = head;
        	head = temp;
        }
    	return prev; 
    }
    
    //210. Course Schedule II
    //topological order in a directed graph.
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] in_degreeMap = new int[numCourses]; //in_degreeMap[i] = # of in edges
        List<List<Integer>> graphMap = new ArrayList<List<Integer>>(numCourses); //graphMap[i]=nodes after it
        buildGraph(in_degreeMap, graphMap, prerequisites);
        return solveByBFS(in_degreeMap, graphMap);
    }
    //Transform problem into a DAG
    private void buildGraph(int[] in_degreeMap, List<List<Integer>> graphMap, int[][] prerequisites) {
    	int num = in_degreeMap.length;
    	//init graphMap
    	while(num -- > 0) {
    		graphMap.add(new ArrayList<Integer>());
    	}
    	//build graph
    	for(int[] edge : prerequisites) {
    		in_degreeMap[edge[0]] ++;
    		graphMap.get(edge[1]).add(edge[0]);
    	}
    }
    //Kern's algo
    private int[] solveByBFS(int[] in_degreeMap, List<List<Integer>> graphMap) {
    	int num = in_degreeMap.length;
    	Queue<Integer> que = new LinkedList<>();
    	int[] classOrder = new int[num];
    	//add 0 in-degree to que
    	for(int i=0; i<num; i++) {
    		if(in_degreeMap[i]==0) que.add(i);
    	}
    	//BFS
    	int visited = 0;
    	while(!que.isEmpty()) {
    		int curNode = que.poll(); //poll is better than remove here (empty)
    		classOrder[visited++] = curNode;
    		for(int neighbor : graphMap.get(curNode)) {
    			//delete edge
    			in_degreeMap[neighbor]--;
    			if(in_degreeMap[neighbor]==0) que.add(neighbor);
    		}
    	}
    	return visited == num ? classOrder : new int[0]; 
     }
    
    //215. Kth Largest Element in an Array (quickSelect: very important)
    //method 1: 4ms; 90%； （n log n）
//    public int findKthLargest(int[] nums, int k) {
//        int N = nums.length;
//        Arrays.sort(nums);
//        return nums[N - k]; 
//    }
    // method 2: quickSelect solution based on quick sort 
    public int findKthLargest(int[] nums, int k) {
    	if(nums == null || nums.length==0) return 0;
    	int left=0, right = nums.length-1;
    	while(true) {
    		int pos = partition(nums,  left, right);
    		if(pos+1 == k) {
    			return nums[pos];
    		}else if(pos+1>k) {
    			right = pos -1;
    		}else {
    			left = pos +1;
    		}
    	}
    }
    
    //make elements value between [0, pivot] are all >= pivot
    // left/big ... pivot ... right/small
    private static int partition(int[] array, int left, int right){
        int pivot = array[left];
        int l = left + 1;
        int r = right;
        while(l<= r) {
        	if(array[l] < pivot && array[r] > pivot) swap(array, l++, r--);
            if(array[l] >= pivot) l++;
            if(array[r] <= pivot) r--;
        }
        swap(array, left, r);
        return r;
    }
    


    //224. Basic Calculator (stack; math)
    // method 1: 18ms 37% beats
    // there is 5ms method without using stack;
    public int calculate1(String s) {
        if(s == null) return 0;
        int result = 0;
        int sign = 1; //brilliant: convert '-' and '+'  to '1' and '-1' on stack
        int num = 0; //brilliant: allow "234" to integer without using string  
        Stack<Integer> stack = new Stack<Integer>(); //Use stack to save signs for "( )". Very clever idea!
        stack.push(sign); //init as only positive number 
        
        for(int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);
            //convert 1-2-(30-20-10) to result = 0[+1][-2][-30][+20][+10]
            if(cur>= '0' && cur<='9') {
            	num = num*10 + (cur -'0');
            }else if (cur == '+' || cur == '-') {
            	result += sign*num; //meet new sign: do calc with previous sign
            	sign = stack.peek() * (cur == '+' ? 1 : -1); //open bracket; update sign for later use
            	num=0;
            }else if (cur == '(') {
            	stack.push(sign);
            }else if (cur == ')') {
            	stack.pop();
            }
        }
        result += sign*num; //last part 
        return result;
    }
    
    
    
    //227. Basic Calculator II (Stack)
    public int calculate(String s) {
        if(s == null || s.length()==0) return 0;
        Stack<Integer> stack = new Stack<>(); //stack for numbers to "+ or -"
        char sign = '+'; //(+)33-2x2
        int curSum = 0;
        //process "*" and "/" on stack
        for(int i=0;i<s.length();i++){
            //digit
            if(Character.isDigit(s.charAt(i))){
                curSum = curSum*10 + (s.charAt(i) - '0');
            }
            //sign (conclude former calculation)
            if(!Character.isDigit(s.charAt(i)) && s.charAt(i)!=' ' || i == s.length()-1){
                if(sign == '+'){
                    stack.push(curSum);
                }else if(sign == '-'){ //note the “sign” is delayed by 1
                    stack.push(-curSum); 
                }else if(sign == '*'){
                    stack.push(stack.pop() * curSum);
                }else if(sign == '/'){
                    stack.push(stack.pop() / curSum);
                }
                curSum = 0;
                sign = s.charAt(i); //update sign
            }
        }
        //process add and subtract
        int result=0;
        for(int num : stack){
            result += num;
        }
        return result;
    }
    
    
    //236. Lowest Common Ancestor of a Binary Tree (recursion)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    	//base
    	if(root == p || root == q || root == null) return root; 
    	//recursion
    	TreeNode left = lowestCommonAncestor( root.left, p, q);
    	TreeNode right = lowestCommonAncestor( root.right, p, q);
    	if(left == null) {
    		return right;
    	}else if(right == null) {
    		return left;
    	}else {
    		return root;
    	}
        
    }
	
    //238. Product of Array Except Self: Time(O(n)); Space O(1)
    //Given an array nums of n integers where n > 1,  return an array output such that 
    //output[i] is equal to the product of all the elements of nums except nums[i].
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        // Calculate lefts and store in res.
        int left = 1; 
        res[0]=1;
        for(int i=1; i<n; i++) {
        	left *= nums[i-1];
        	res[i]=left;
        }
        // Calculate rights and the product from the end of the array.
        int right = 1;
        for(int j=n-2; j>=0; j--) {
        	right *= nums[j+1];
        	res[j] *= right;
        }
        return res;
    }
    
    //239. Sliding Window Maximum (Deque/ monotonic queue)
    //Deque: A linear collection that supports element insertion and removal at both ends. 
    //The name deque is short for "double ended queue"
    //https://www.youtube.com/watch?v=2SXqBsTR6a8
    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums==null || k<=0) return nums;
        int n = nums.length;
        int[] result = new int[n-k+1];
        // store index (dq[0]: indx of biggest and oldest)
        Deque<Integer> dq = new LinkedList<>();
        for(int i=0; i<n; i++){
            // remove numbers out of range k
            if(!dq.isEmpty() && dq.peek()<i-k+1){
                dq.poll();
            }
            // remove smaller numbers in k range as they are useless
            while(!dq.isEmpty() && nums[dq.peekLast()]<nums[i]){
                dq.pollLast();
            }
            // dq contains index... r contains content
            dq.offer(i);
            if(i-k+1>=0){
                result[i-k+1]=nums[dq.peek()];
            }
        }
        return result;
    }
    
    
    // 253. Meeting Rooms II
    //this is a really interesting question (see "solution")
    public class Interval {
    	    int start;
    	    int end;
    	    Interval() { start = 0; end = 0; }
    	    Interval(int s, int e) { start = s; end = e; }
    	 }
    
    //method 1: Chronological Ordering as taught in Algorithm class (time: n log n; space: n) -> 100% beats
    //(2 pointers)
    public int minMeetingRooms1(Interval[] intervals) {
    	if(intervals.length==0) return 0;
        //build start and end times
    	int len = intervals.length;
    	int[] starts = new int[len];
    	int[] ends = new int[len];
    	for(int i=0;i<len;i++) {
    		starts[i] = intervals[i].start;
    		ends[i] = intervals[i].end;
    	}
    	//sort in n log n (merge sort; comparator; anonymous class)
    	Arrays.sort(starts);
    	Arrays.sort(ends);
    	//loop on starts to assign rooms
    	int numRooms=1, end_p=0; //start at the second room
    	for(int start_p=1; start_p<starts.length; start_p++) {
    		if(starts[start_p] >= ends[end_p]) { //1 meeting ends
    			end_p++; 
    			//room number -1 + 1 
    		}else {
    			numRooms++;
    		}
    	}
    	return numRooms;
    }
    
    //method 2 :heap (priority queue) (time: n log n; space: n)
    //idea: sort on start time; keep all the current room in heap with earliest finishing time at top
    
    //254. Factor Combinations (backtracking)
    //int "start" ensures list will only increase i.e 12 = 2 6 != 6 2 (smart)
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> tempLst = new ArrayList<>();
        getFactors_dfs( n, tempLst, result, 2);
        return result;
    }
    
    //int "start" ensures list will only increase i.e 12 = 2 6 != 6 2 (smart)
    public void getFactors_dfs(int n, List<Integer> tempLst, List<List<Integer>> result, int start) {
    	//base case
        if(n==1) {
        	if(tempLst.size()>1) { // for [37]
        		List newLst = new ArrayList(tempLst); // pass by reference
        		result.add(newLst);
        	}
        	return;
        }
        
        for(int i=start; i<=n; i++){ //"=" is a must
        	if(n % i==0) { // i is a divider, great, add it
        		tempLst.add(i); 
        		getFactors_dfs( n/i, tempLst, result, i); //continue on this divider
        	    tempLst.remove(tempLst.size()-1); // backtracking 
            }
        }
        //no match (prime)
        // return;
    }
    
    //269. Alien Dictionary
    //Kahn's Algorithm: works by choosing vertices in the same order as the eventual topological sort 
    //topological sort; Topology Traversal ( similar BFS);  
    //	建图 --> in-degree=0 --> BFS 
    public String alienOrder(String[] words) {
        if(words==null || words.length==0) return "";
    	
    	Map<Character, Set<Character>> graphMap = new HashMap<>(); //map to store graph
    	Map<Character, Integer> degreeMap = new HashMap<>();
    	
    	//1. build graph: degreeMap && graphMap 
    	for(String word : words) {
    		for(char c : word.toCharArray()) {
    			degreeMap.put(c, 0);
    		}
    	}
    	for(int i=0;i<words.length-1;i++) {
    		String curWord = words[i];
    		String nextWord = words[i+1];
    		int minLen = Math.min(curWord.length(), nextWord.length());
    		for(int j=0; j<minLen;j++) { //compare each char in two words 
    			char c1=curWord.charAt(j);
    			char c2=nextWord.charAt(j);
    			if(c1!=c2) {
    				Set<Character> set = new HashSet<>();
    				if(graphMap.containsKey(c1)) {
    					set = graphMap.get(c1);
    				}
    				if(!set.contains(c2)) {
    					set.add(c2);
    					graphMap.put(c1, set);
    					degreeMap.put(c2, degreeMap.get(c2)+1);
    				}
    				break; //only 1 ordering between words
    			}
    		}
    	}
    	//2. find 0 in-degree nodes to start
    	Queue<Character> queue = new LinkedList<Character>(); 
    	for(char c : degreeMap.keySet()) {
    		if(degreeMap.get(c)==0) {
    			queue.add(c); 
    		}
    	}
    	//3. BFS and find order along the way --> Topology Traversal
    	StringBuilder res = new StringBuilder();
    	while(!queue.isEmpty()) {
    		char cur = queue.remove();
    		res.append(cur);
    		if(graphMap.containsKey(cur)) { //required: a node pointed by no one is possible
        		for(char neighbor : graphMap.get(cur)) {
        			degreeMap.put(neighbor, degreeMap.get(neighbor)-1); //remove edge pointed by cur
        			if(degreeMap.get(neighbor) == 0) {
        				queue.add(neighbor);
        			}
        		}
    		}
    	}
    	if(degreeMap.size() != res.length()) return ""; //cycle is detracted (not DAG)
  
		return res.toString();   
    }
    
    
	//273. Integer to English Words
	//Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
    public String numberToWords(int num) {
        if(num == 0) return "Zero";
        StringBuilder sb = new StringBuilder();
    	for(int i=0; i<radix.length; i++) {
    		int curNum = num / radix[i];
    		if(curNum==0) continue;
    		sb.append(trans(curNum)).append(THOUSANDS[i]).append(" ");
    		num %= radix[i];
    	}
    	
    	return sb.toString().trim();
    }
    
    final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    final String[] THOUSANDS = {"Billion", "Million", "Thousand", ""};
    final int[] radix = {1000000000, 1000000, 1000, 1};
    
    private String trans(int numOfHundred) {
    	if(numOfHundred == 0) {
    		return "";
    	}else if (numOfHundred < 20) {
    		return LESS_THAN_20[numOfHundred] + " ";
    	}else if (numOfHundred < 100) {
    		return TENS[numOfHundred/10] + " " + trans(numOfHundred%10);  
    	}else {//< 1000
    		return LESS_THAN_20[numOfHundred/100] + " Hundred " + trans(numOfHundred%100);
    	}
    }
    
    //283. Move Zeroes 
    // TWO POINTER
    public void moveZeroes(int[] nums) {

        int j = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] != 0) {
                swap(nums, i, j);
                j++;
            }
        }
    }
    
    private static void swap(int[] nums, int p1, int p2) {
    	int temp = nums[p1];
    	nums[p1] = nums[p2];
    	nums[p2] = temp;
    }
    
    //297. Serialize and Deserialize Binary Tree (DFS) (12 ms; 93% )
    //You just need to ensure that a binary tree can be serialized to a string and 
    //this string can be deserialized to the original tree structure.
    public class Codec {
    	//used to do <<Object>> comparison
        private static final String spliter = ",";
        private static final String NN = "n";

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
        	StringBuilder sb = new StringBuilder();
        	serialize_dfs( root, sb);
			return sb.toString();
        }
        
        private void serialize_dfs(TreeNode root, StringBuilder sb) {
        	if(root==null) {
        		sb.append(NN).append(spliter); //null
        	}else {
            	sb.append(root.val).append(spliter);
            	serialize_dfs(root.left, sb);
            	serialize_dfs(root.right, sb);
        	}
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
        	String[] dataArr = data.split(spliter);
        	List<String> dataLst = new LinkedList<String> (Arrays.asList(dataArr));	
			return deserialize_dfs(dataLst);
        }
        
        //helper funct (use collection interface for removal purpose;)
        public TreeNode deserialize_dfs(List<String> dataLst) {
            String cur = dataLst.remove(0);
        	if(cur.equals(NN)) { //Note: cur=="n" not working since cur is an object !!!
        		return null;
        	}
        	TreeNode root = new TreeNode(Integer.parseInt(cur));
        	root.left = deserialize_dfs(dataLst);
        	root.right = deserialize_dfs(dataLst);
        	return root;
        }
    }
    
    //289. Game of Life (somehow becomes a bit manipulation problem)
    // see discussion; it is really smart 
    //To solve it in place, we use 2 bits to store 2 stat
    //To get the next state, simply do board[i][j] >> 1
    public void gameOfLife(int[][] board) {
    	int m = board.length; int n = board[0].length;
        for(int i=0; i< m; i++) {
        	for(int j=0; j<n; j++) {
        		int lives = liveNeighbors( board,  m,  n, i, j);
        		
                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
        		if(lives==3 && board[i][j] == 0) {
        			board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
        		} 
        		
        		if(lives>=2 && lives<=3 && board[i][j] == 1) {
        			board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
        		} 
        	}
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }
    
    private int liveNeighbors(int[][] board, int m, int n, int i, int j) {
    	int lives=0;
    	for(int x = Math.max(0, i-1); x <= Math.min(m-1,i+1); x++) {
    		for(int y = Math.max(0, j-1); y<=Math.min(n-1,j+1); y++) {
    			//To get the current state, simply do board[i][j] & 1
    			lives += board[x][y] & 1;
    		}
    	}
    	return lives-(board[i][j] & 1);
    } 
    
    //290. Word Pattern (bijection)
    public boolean wordPattern(String pattern, String str) {
        Map map = new HashMap();
        String[] words = str.split(" ");
        if(words.length != pattern.length()) return false;
        
        //go through the pattern letters and words in parallel 
        //and compare the indexes where they last appeared.
        //use Integer (not "int") to allow "=="; (Auto-boxing int to integer beyond (-128, 127))
        for(Integer i=0; i<pattern.length(); i++){ 
        	if(map.put(pattern.charAt(i), i) != map.put(words[i], i)) { //see return of "PUT" in library
        		return false;
        	}
        }
        return true;
    }
    
    //291. Word Pattern II (backtracking)
    //backtracking:  keep trying to use a character in the pattern to match different length of substrings 
    //in the input string, keep trying till we go through the input string and the pattern.
    public boolean wordPatternMatch(String pattern, String str) {
    	Map<Character, String> map = new HashMap<>();
    	Set<String> set = new HashSet<>(); //hash set to avoid duplicate matches
    	return isMatch_dfs(  str, 0, pattern, 0, map, set);
    }
    
    public boolean isMatch_dfs( String str, int idx_str, String pat, int idx_patt, Map<Character, String> map, Set<String> set) {
    	//base case
    	if(idx_patt == pat.length() && idx_str == str.length()) return true;
    	if(idx_patt == pat.length() || idx_str == str.length()) return false;
        // get current pattern character
    	Character cur_patt = pat.charAt(idx_patt);
    	
        // if the pattern character exists
    	if(map.containsKey(cur_patt)) {
    		String cur_str = map.get(cur_patt);
    	    // then check if we can use it to match str[i...i+s.length()-1]
    		if(!str.startsWith(cur_str, idx_str)) { // "startsWith(String prefix, int offset)"
    			return false;
    		}
    	    // if it can match, great, continue to match the rest
    		return isMatch_dfs( str, idx_str+cur_str.length(), pat, idx_patt+1,  map, set);
    	}
    	
        // pattern character does not exist in the map => try every possible string length in str
    	for(int k=idx_str; k<str.length(); k++) {
    		String cur_str = str.substring(idx_str, k+1);
    		//duplicate with other pattern's value ==> skip this value; try other values
    		if(set.contains(cur_str)) continue;
    	    // create / update structure
    		map.put(cur_patt, cur_str);
    		set.add(cur_str);
    		
    	    // continue to match the rest
    		if(isMatch_dfs(  str, k+1, pat, idx_patt+1, map, set)) {
    			return true;
    		}
    		
    	     // backtracking
    		map.remove(cur_patt);
    		set.remove(cur_str);
    	}
        // we've tried our best but still no luck
        return false;
    }
    
    
    //295. Find Median from Data Stream (heap)
    // heap: http://pages.cs.wisc.edu/~vernon/cs367/notes/11.PRIORITY-Q.html
    //method 1: brute force; ArrayList sort; time: O(n elements * n log n sort) ==> exceed
    //method 2: balanced binary search tree 
    //method 3: min heap + max heap; time: O(n elements * log n)
    class MedianFinder {
    	//Max-heap "small" has the smaller half of the numbers. (max heap => by a Comparator)
    	private Queue<Integer> small_maxH;
    	//Min-heap "large" has the larger half of the numbers. (min heap => natural ordering)
    	private Queue<Integer> large_minH;
    	
        /** initialize your data structure here. */
        public MedianFinder() {
//        	small_maxH = new PriorityQueue(1, new Comparator<Integer>() {
//     		   public int compare(Integer o1, Integer o2) {
//     			   return o2 - o1; 
//     		   };
//        });
        	//Lambda to define comparator for max heap
        	small_maxH = new PriorityQueue<Integer>((Integer a, Integer b) -> b - a );
        	large_minH = new PriorityQueue<Integer>();
        }
        
        public void addNum(int num) {
        	//keep 2 heaps balanced (0 < = small size - large size  <=1)
        	if(small_maxH.isEmpty() || small_maxH.peek()>num) {
        		small_maxH.add(num);
        		if(small_maxH.size()-large_minH.size() >1) {
        			large_minH.add(small_maxH.poll());
        		}
        	}else {
        		large_minH.add(num);
        		if(small_maxH.size()-large_minH.size() < 0) {
        			small_maxH.add(large_minH.poll());
        		}
        	}

        }
        
        public double findMedian() {
			return large_minH.size() == small_maxH.size()
					? (large_minH.peek() + small_maxH.peek())/2.0
					: small_maxH.peek();
        }
    }
    

    
}
