package leetcode;

import java.util.*;


public class BitTiger200 {

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
    
    // 253. Meeting Rooms II
    //this is a really interesting question (see "solution")
    public class Interval {
    	    int start;
    	    int end;
    	    Interval() { start = 0; end = 0; }
    	    Interval(int s, int e) { start = s; end = e; }
    	 }
    
    //method 1: Chronological Ordering as taught in Algorithm class (time: n log n; space: n) -> 100% beats
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
    
    private void swap(int[] nums, int p1, int p2) {
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
    
    
	//200 Number of Islands
	//Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
	// idea: dfs or bfs
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length ==0) return 0;
        int num_islands=0;
        for(int r=0; r<grid.length; r++) {
        	for(int c=0; c<grid[0].length; c++) {
        		if(grid[r][c]=='1') {
        			++num_islands;
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
    
}
