package leetcode;

import java.util.*;

public class BitTiger100 {

	// 102.Binary Tree Level Order Traversal
	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();

		// base case
		if (root == null)
			return res;

		// general case
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);

		while (!queue.isEmpty()) {
			List<Integer> tempLst = new ArrayList<Integer>();
			int size = queue.size();

			for (int i = 0; i < size; i++) {
				TreeNode curNode = queue.poll();
				// add to queue
				if (curNode.left != null) {
					queue.add(curNode.left);
				}
				if (curNode.right != null) {
					queue.add(curNode.right);
				}
				// add to lst
				tempLst.add(curNode.val);

			}

			// add to result
			res.add(tempLst);
		}
		return res;
	}

	// recursive method
	public List<List<Integer>> levelOrder2(TreeNode root) {
		return levelOrder(root, 0, new ArrayList<>());
	}

	public List<List<Integer>> levelOrder(TreeNode root, int depth, List<List<Integer>> list) {
		if (root == null)
			return list;
		if (depth >= list.size())
			list.add(new ArrayList<Integer>());
		list.get(depth).add(root.val);

		levelOrder(root.left, depth + 1, list);
		levelOrder(root.right, depth + 1, list);

		return list;
	}

	// 208 Implement Trie (Prefix Tree)
	// 前缀树或字典樹，是一种有序树，用于保存关联数组， 其中的键通常是字符串

	// helper class
	class TrieNode {
		// fields
		// char val; val could be hidden in TrieNode[26];
		boolean IsWord;
		TrieNode[] children = new TrieNode[26];

		TrieNode() {
		};
	}

	class Trie {
		TrieNode root;

		/** Initialize your data structure here. */
		public Trie() {
			root = new TrieNode();
		}

		/** Inserts a word into the trie. */
		public void insert(String word) {
			TrieNode cur_root = root;
			for (int i = 0; i < word.length(); i++) {
				char cur_char = word.charAt(i);
				int posInChildren = cur_char - 'a';
				if (cur_root.children[posInChildren] == null) {
					cur_root.children[posInChildren] = new TrieNode();
				}
				cur_root = cur_root.children[posInChildren];
			}
			cur_root.IsWord = true;
		}

		/** Returns if the word is in the trie. */
		public boolean search(String word) {
			TrieNode cur_root = root;
			for (int i = 0; i < word.length(); i++) {
				char cur_char = word.charAt(i);
				int posInChildren = cur_char - 'a';
				if (cur_root.children[posInChildren] == null) {
					return false;
				}
				cur_root = cur_root.children[posInChildren];
			}
			return cur_root.IsWord;
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			TrieNode cur_root = root;
			for (int i = 0; i < prefix.length(); i++) {
				char cur_char = prefix.charAt(i);
				int posInChildren = cur_char - 'a';
				if (cur_root.children[posInChildren] == null) {
					return false;
				}
				cur_root = cur_root.children[posInChildren];
			}
			return true;

		}
	}

	// 103.Binary Tree Zigzag Level Order Traversal (BFS)
	// If zigzag == false, it is from left to right; if zigzag == true, it is from
	// right to left.
	// And from right to left just need to use ArrayList.add(0, value) method
	// so smart!!!!
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> res = new LinkedList<List<Integer>>(); // appending faster than array list
		// base
		if (root == null)
			return res;
		// general
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		boolean zigzag = false; // left to right first
		que.add(root);

		while (!que.isEmpty()) {
			List<Integer> lst = new LinkedList<Integer>();
			int size = que.size();
			for (int i = 0; i < size; i++) {
				TreeNode curNode = que.poll();
				// add to lst
				if (zigzag) {
					lst.add(0, curNode.val); // insert at 0
				} else {
					lst.add(curNode.val);
				}
				// update queue
				if (curNode.left != null) {
					que.add(curNode.left);
				}
				if (curNode.right != null) {
					que.add(curNode.right);
				}
			}
			res.add(lst);
			zigzag = !zigzag; // change travel direction for next level
		}
		return res;
	}

	// 104. Max depth of Binary Tree
	public int maxDepth(TreeNode root) {
		if (root == null)
			return 0;

		return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
	}

	// 105. Given preorder and inorder traversal of a tree, construct the binary
	// tree.
	// https://articles.leetcode.com/construct-binary-tree-from-inorder-and-preorder-postorder-traversal/
	// The crucial observation to this problem is the tree’s root always coincides
	// with the first element in preorder traversal.
	// eliminate the search by using an efficient look-up mechanism such as hash
	// table. O(n2) -> O(n)
	public TreeNode buildTreeHelper(int[] preorder, int preStart, int preEnd, int[] inorder, int inStart, int inEnd,
			HashMap<Integer, Integer> inorderMap) {
		// base
		if (preStart > preEnd || inStart > inEnd)
			return null;

		// general
		int rootVal = preorder[preStart];
		TreeNode root = new TreeNode(rootVal);
		int rootPosInorder = inorderMap.get(rootVal);
		int leftNum = rootPosInorder - inStart;

		root.left = buildTreeHelper(preorder, preStart + 1, preStart + leftNum, inorder, inStart, inStart + leftNum,
				inorderMap);
		root.right = buildTreeHelper(preorder, preStart + 1 + leftNum, preEnd, inorder, rootPosInorder + 1, inEnd,
				inorderMap);

		return root;
	}

	public TreeNode buildTree(int[] preorder, int[] inorder) {
		// hashing an element’s value to its corresponding index in the inorder sequence
		// look-ups in constant time
		HashMap<Integer, Integer> inorderMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < inorder.length; i++) {
			inorderMap.put(inorder[i], i);
		}

		TreeNode root = buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderMap);

		return root;
	}

	// 359.Logger Rate Limiter
	/**
	 * Your Logger object will be instantiated and called as such: Logger obj = new
	 * Logger(); boolean param_1 = obj.shouldPrintMessage(timestamp,message);
	 */
	class Logger {
		private HashMap<String, Integer> map;
		private int timeGap = 10; // in seconds

		/** Initialize your data structure here. */
		public Logger() {
			map = new HashMap<>();
		}

		/**
		 * Returns true if the message should be printed in the given timestamp,
		 * otherwise returns false. If this method returns false, the message will not
		 * be printed. The timestamp is in seconds granularity.
		 */
		public boolean shouldPrintMessage(int timestamp, String message) {
			if (!map.containsKey(message) || timestamp - map.get(message) >= timeGap) {
				// good case: update
				map.put(message, timestamp);
				return true;
			} else {
				return false;
			}
		}
	}

	// 108.Convert Sorted Array to Binary Search Tree
	// Given an array where elements are sorted in ascending order, convert it to a
	// height balanced BST.
	// Binary Search Tree: 1: left < parent < right
	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length == 0)
			return null;

		TreeNode root = sortedArrayToBSTHelp(nums, 0, nums.length - 1);

		return root;
	}

	public TreeNode sortedArrayToBSTHelp(int[] nums, int start, int end) {
		// base case
		if (start > end)
			return null;

		// general cases
		int mid = (start + end + 1) / 2; // +1 for round up
		TreeNode root = new TreeNode(nums[mid]);
		root.left = sortedArrayToBSTHelp(nums, start, mid - 1);
		root.right = sortedArrayToBSTHelp(nums, mid + 1, end);

		return root;
	}

	// 114. Flatten Binary Tree to Linked List
	// Given a binary tree, flatten it to a linked list in-place.
	// pre-order traversal (center - left - right)
	public void flatten(TreeNode root) {
		// convert to flatten linked list version of root in place
		Flatten(root, null);
	}

	// the reverse order after flattening is post order traversal in (right, left,
	// root) order
	// like [6->5->4->3->2->1].
	public TreeNode Flatten(TreeNode root, TreeNode prev) {

		if (root == null)
			return prev;
		prev = Flatten(root.right, prev);
		// “prev” above is passed into next one to link to the tail
		prev = Flatten(root.left, prev);
		root.right = prev;
		root.left = null;
		prev = root;
		return prev;
	}

	// 121. Best Time to Buy and Sell Stock
	// Method 1: 1-D Dynamic Programming
	// https://www.youtube.com/watch?v=8pVhUpF1INw
	public int maxProfit(int[] prices) {
		// idea: 2 DP tables
		int len = prices.length;
		if (prices == null || len == 0)
			return 0;

		// minPrice[i]=lowest price till ith day = Min(minPrice[i-1],L[i])
		int[] minPrice = new int[len];
		minPrice[0] = prices[0];

		// maxProf[i] = max profit up to ith day =
		// Max(maxProf[i-1],prices[i]-minPrice[i])
		int[] maxProf = new int[len];
		maxProf[0] = 0;

		for (int i = 1; i < len; i++) {
			minPrice[i] = Math.min(minPrice[i - 1], prices[i]);
			maxProf[i] = Math.max(maxProf[i - 1], prices[i] - minPrice[i - 1]);
		}
		return maxProf[len - 1];
	}

	// Method 2: reduce to "max subarray problem" using Kadane's Algorithm.(leecode
	// 53)
	public int maxProfit2(int[] prices) {
		int len = prices.length;
		if (prices == null || len == 0)
			return 0;
		// idea: prices -> prices[i]-prices[i-1]==gains[i] -> reduce to "max subarray
		// problem"
		int[] gain = new int[len - 1];
		for (int i = 1; i < len; i++) {
			gain[i - 1] = prices[i] - prices[i - 1];
		}
		return Math.max(0, maxSubArray(gain)); // profit could be negative
	}

	// From leecode 53 (DP)
	public int maxSubArray(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		// idea: CurMax = Max(nums[i],nums[i]+CurMax)
		int curMax = nums[0];
		int max = nums[0];
		for (int i = 1; i < nums.length; i++) {
			curMax = Math.max(nums[i], nums[i] + curMax);
			if (curMax > max) {
				max = curMax;
			}
		}
		return max;
	}

	// 122. Best Time to Buy and Sell Stock II
	public int maxProfit_2(int[] prices) {
		int len = prices.length;
		if (prices == null || len == 0)
			return 0;
		// idea: (gain[i] = prices[i]-prices[i-1])
		int max = 0;
		int[] gain = new int[len - 1];
		for (int i = 1; i < len; i++) {
			gain[i - 1] = prices[i] - prices[i - 1];
			if (gain[i - 1] > 0) {
				max += gain[i - 1];
			}
		}
		return max;
	}

	// 124. Binary Tree Maximum Path Sum (recursion + dfs)
	public int maxPathSum(TreeNode root) {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		// return dfs_maxPathSum( root, Integer.MIN_VALUE); (wrong: nothing to
		// record/return "max")
		// Instead: use a int[] to wrap Integer.MIN_VALUE while keeping "maxPSum"
		// variable
		int[] maxPSum = new int[] { Integer.MIN_VALUE };
		dfs_maxPathSum(root, maxPSum);
		return maxPSum[0];
	}

	private int dfs_maxPathSum(TreeNode root, int[] maxPSum) {
		// base case
		// if(root==null) return 0; (no need; handled below)

		// recursive cases
		int l = root.left == null ? 0 : Math.max(0, dfs_maxPathSum(root.left, maxPSum));// if l < 0, cut out -> Max(0,
																						// dfs())
		int r = root.right == null ? 0 : Math.max(0, dfs_maxPathSum(root.right, maxPSum));
		int cur = root.val + l + r;
		// record max
		maxPSum[0] = Math.max(maxPSum[0], cur);
		// return cases:
		// 1. if l<0 && r<0 -> root.val
		// 2. if l <0 && r>0 -> root.val + r 3.if l>0 && r<0 -> root.val + l
		// 3. if l>0 && r>0 -> root.val + max(r,l) (can only visit a point (i.e root)
		// once)
		return root.val + Math.max(l, r);
	}

	// LeetCode 543: Diameter of Binary Tree
	public int diameterOfBinaryTree(TreeNode root) {
		if (root == null)
			return 0;
		int[] maxLen = new int[] { 0 };
		dfs_diameterOfBinaryTree(root, maxLen);
		return maxLen[0];
	}

	// return # of links to this root's parent
	private int dfs_diameterOfBinaryTree(TreeNode root, int[] maxLen) {
		int l = root.left == null ? 0 : dfs_diameterOfBinaryTree(root.left, maxLen) + 1;
		int r = root.right == null ? 0 : dfs_diameterOfBinaryTree(root.right, maxLen) + 1;
		int cur = l + r;
		// record the max
		maxLen[0] = Math.max(maxLen[0], cur);
		// return cases:
		return Math.max(l, r);
	}

	// LeetCode 687. Longest Univalue Path
	public int longestUnivaluePath(TreeNode root) {
		if (root == null)
			return 0;
		int[] maxLen = new int[] { 0 };
		dfs_longestUnivaluePath(root, maxLen);
		return maxLen[0];
	}

	// return # of links to this root's parent
	private int dfs_longestUnivaluePath(TreeNode root, int[] maxLen) {
		int l = root.left == null ? 0 : dfs_longestUnivaluePath(root.left, maxLen); // make sure recurse when root.val
																					// != root.left.val
		int r = root.right == null ? 0 : dfs_longestUnivaluePath(root.right, maxLen);
		int resl = (root.left != null && root.val == root.left.val) ? l + 1 : 0;
		int resr = (root.right != null && root.val == root.right.val) ? r + 1 : 0;
		int cur = resl + resr;
		// record the max
		maxLen[0] = Math.max(maxLen[0], cur);
		// return cases:
		// l != root & r != root -> 0;
		// l == root & r != root -> l's links + 1;
		// r == root & l != root -> r's links + 1;
		// l == root & r == root -> max(l's link, r's link);
		return Math.max(resl, resr);
	}
	
	//127. Word Ladder (Graph search; bidirectional BFS) ; 
	//花花酱 O(b^(d/2)) = O(n * (26 * l)); n = |wordList|; l = length(word)
    // termination: if beginSet x endSet != empty
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    	int len = wordList.get(0).length(); //words have save length
    	int ladderLen = 1; //for beginWord
    	HashSet<String> dict = new HashSet<>(wordList);
    	HashSet<String> beginSet = new HashSet<>();
    	HashSet<String> endSet = new HashSet<>();
    	HashSet<String> visited = new HashSet<>();
    	
    	if(!dict.contains(endWord)) return 0;
    	
    	beginSet.add(beginWord);
    	endSet.add(endWord);
    	visited.add(beginWord); visited.add(endWord);
    	
    	while(!beginSet.isEmpty() && !endSet.isEmpty()) {
    		//To perform a bidirectional search, keep two sets balanced; expend the smaller one first
    		if(beginSet.size() > endSet.size()) {
    			HashSet<String> temp = beginSet;
    			beginSet = endSet;
    			endSet = temp;
    		}

    		HashSet<String> nextSet = new HashSet<>();
    		for(String word : beginSet) {
    			char[] letters = word.toCharArray();
    			for(int i=0; i<len; i++) {
    				char old = letters[i];
    				for(char c='a'; c<='z'; c++) {
    					if(letters[i] == c) continue;
    					letters[i] = c; //brute force to try 26 letters on position i
    					String curWord = String.valueOf(letters);
    					if(endSet.contains(curWord)) return ladderLen+1; //goal
    					if(dict.contains(curWord) && !visited.contains(curWord)) { //for next level & prevent loop
    						visited.add(curWord);
    						nextSet.add(curWord);
    					}
    				}
    				letters[i] = old; //convert back
    			}
    		}
    		
    		beginSet = nextSet; // queue for next level
    		ladderLen++;
    	}
    	
		return 0;
    }
	
	//126. Word Ladder II (BFS + DFS) O(n*26*l)
    //BFS to create a graph; DFS to find paths
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
    	List<List<String>> res = new ArrayList<>();
    	HashSet<String> dict = new HashSet<>(wordList);
    	if(!dict.contains(endWord)) return res;
    	
    	// initialization for two-end BFS
    	HashSet<String> startSet = new HashSet<>();
    	HashSet<String> endSet = new HashSet<>();
    	startSet.add(beginWord);
    	endSet.add(endWord);
    	// map the list of parents to each node
    	HashMap<String, List<String>> graphMap = new HashMap<>();
    	//BFS to create a graph;
    	findLadders_bfs(startSet,endSet,dict,graphMap);
    	//DFS to find paths
    	List<String> tempLst = new ArrayList<>(); //save current path
    	tempLst.add(beginWord);
    	findLadders_dfs(graphMap,res,beginWord,endWord,tempLst);
    	
		return res;
        
    }
	
    /* use bfs to build the children graph*/
    private void findLadders_bfs(Set<String> startSet,Set<String> endSet, Set<String> dict, Map<String, List<String>> graphMap) {
    	boolean reversed = false;
    	boolean terminate = false;
    	while(!startSet.isEmpty()) {
    		//To perform a bidirectional search, keep two sets balanced;
    		if(startSet.size()>endSet.size()) {
    			reversed = !reversed;
    			Set<String> temp = startSet;
    			startSet = endSet;
    			endSet = temp;
    		}
    		dict.removeAll(startSet); //prevent looping; process start set this term; 
    		Set<String> nextSet = new HashSet<>();
    		for(String word : startSet) {
    			char[] letters = word.toCharArray();
    			int strLen = letters.length;
    			for(int i=0; i<strLen; i++) {
    				char old = letters[i];
    				for(char c='a'; c<='z';c++) {
    					if(c==old) continue;
    					letters[i] = c;
    					String newWord = String.valueOf(letters);
    					if(dict.contains(newWord) || endSet.contains(newWord)) {
    						String key = reversed ? newWord : word;
    						String value = reversed ? word : newWord;
    						graphMap.putIfAbsent(key, new ArrayList<String>());
    						graphMap.get(key).add(value);
    						if(endSet.contains(newWord)) {
    	    					//terminate!=true: screen out paths that are not shortest
    							terminate = true;
    						}else {
        						nextSet.add(newWord);
    						}
    						
    					}
    				}
					letters[i] = old;
    			}
    		}
    		startSet = nextSet;
    		if(terminate) break;
    	}
    }
    
    /* use dfs to find all the shortest paths based on child graph (parent -> children)*/
    private void findLadders_dfs(Map<String, List<String>> graphMap, List<List<String>> res, String cur, String endWord, List<String> tempLst) {
    	
    	if(cur.equals(endWord)) { //!!! required for string compare
    		//Explain for "new ArrayList<>(list)" : (create a new object, not affected by changes)
    		//https://stackoverflow.com/questions/51825663/why-res-addnew-arraylistlist-here
    		res.add(new ArrayList<>(tempLst));
    		return;
    	}
        // if not contained, that means this path is not shortest
    	if(!graphMap.containsKey(cur)) return;
    	for(String next : graphMap.get(cur)) {
    		tempLst.add(next);
    		findLadders_dfs(graphMap, res, next, endWord, tempLst);
    		tempLst.remove(next); 
    	}
    }
    

	// 128. Longest Consecutive Sequence O(n)
	public int longestConsecutive(int[] nums) {
		// add everything to the set
		HashSet<Integer> set = new HashSet<Integer>();
		for (int num : nums) {
			set.add(num);
		}
		// Idea: check whether set contains (key-1) or not
		// If yes, ignore (key - 1) for now; will be visited
		// If not, use (key) as low bound -> check(key+1) until key + ls + 1 not in set
		// find max ls
		int ans = 0;
		for (int num : nums) {
			if (!set.contains(num - 1)) {
				int ls = 0;
				// use num as lower bound to start
				while (set.contains(num++)) {
					ls++;
				}
				ans = Math.max(ans, ls);
			}
		}
		return ans;
	}
	
	/**
	 * 131. Palindrome Partitioning Given a string s, partition s such that every
	 * substring of the partition is a palindrome.
	 */
	public List<List<String>> partition(String s) {
		List<List<String>> result = new ArrayList<>();
		// base case
		if (s == null || s.length() == 0)
			return result;
		// general case
		backTrack_partition(s, result, new ArrayList<String>(), 0);
		return result;
	}

	// check and partition s[index, s.length)
	private void backTrack_partition(String s, List<List<String>> result, List<String> curLst, int index) {
		if (index >= s.length()) {
			result.add(new ArrayList<String>(curLst));
			return;
		}
		for (int i = index; i < s.length(); i++) {
			if (isPalindrome(s, index, i)) {
				curLst.add(s.substring(index, i + 1));
				backTrack_partition(s, result, curLst, i + 1); // NOTE:i+1 not index+1 (recursion in depth)
				curLst.remove(curLst.size() - 1);
			}
		}
	}

	// check if a string is a Palindrome for s[left, right]
	private boolean isPalindrome(String s, int left, int right) {
		while (left < right) {
			if (s.charAt(left++) != s.charAt(right--))
				return false;
		}
		return true;
	}


	// 133. Clone Graph (graph traversal - DFS/BFS)
	// https://www.youtube.com/watch?v=V0j1IEBkK4k
	// global variable
	// DFS -> 3ms || BFS -> 5ms
	HashMap<UndirectedGraphNode, UndirectedGraphNode> Map_cloneGraph = new HashMap<>();

	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {

		return dfs_cloneGraph(node);
	}

	private UndirectedGraphNode dfs_cloneGraph(UndirectedGraphNode node) {
		// idea: copy Node first,
		// then connect the copy to neighbors (add neighbors to copy's list)
		if (node == null)
			return null;
		if (Map_cloneGraph.containsKey(node))
			return Map_cloneGraph.get(node);
		// first copy
		UndirectedGraphNode dup = new UndirectedGraphNode(node.label);
		Map_cloneGraph.put(node, dup);
		// now, connect the dup with original node's neighbors
		for (UndirectedGraphNode neighbor : node.neighbors) {
			// 深度优先clone 这个neighbor (递归)
			UndirectedGraphNode dup_neighbor = dfs_cloneGraph(neighbor);
			dup.neighbors.add(dup_neighbor);
		}
		return dup;
	}

	// https://www.youtube.com/watch?v=_Zt6gwWRnHM&t=195s
	public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
		// idea: copy Node first, then connect the copy nodes
		if (node == null)
			return null;
		// map to record copied/visited node
		HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		UndirectedGraphNode myNode = new UndirectedGraphNode(node.label); // copy
		map.put(node, myNode);
		// queue for BFS
		Queue<UndirectedGraphNode> que = new LinkedList<UndirectedGraphNode>();
		que.add(node);
		// BFS for nodes in original graph to copy nodes
		while (!que.isEmpty()) {
			UndirectedGraphNode cur = que.poll();
			// all neighbors of current origin node
			for (UndirectedGraphNode neighbor : cur.neighbors) {
				// visit the unvisited node
				if (!map.containsKey(neighbor)) {
					que.add(neighbor);
					map.put(neighbor, new UndirectedGraphNode(neighbor.label));
				}
				// add copied neighbors to the copied node
				// copied neighbors: map.get(neighbor) -> copied node of neighbor
				// copied node: map.get(cur) -> copied node of cur
				map.get(cur).neighbors.add(map.get(neighbor));
			}
		}
		return myNode;
	}

	// 134. Gas Station
	public int canCompleteCircuit(int[] gas, int[] cost) {
		int len = gas.length;
		int totalGas = 0;
		int totalCost = 0;
		int start = 0;
		int tank = 0;
		for (int i = 0; i < len; i++) {
			totalGas += gas[i];
			totalCost += cost[i];
			tank += gas[i] - cost[i];
			if (tank < 0) {
				start = i + 1;
				tank = 0;
			}
		}
		if (totalGas - totalCost < 0)
			return -1;

		return start;
	}

	// 138. Copy List with Random Pointer
	// Note: list/hashMap stores "pointers" to objects!!
	// Idea: copy list of object-> create new object -> new pointers
	class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}
	};

	// Method: same as before; use hashMap
	public RandomListNode copyRandomList(RandomListNode head) {
		if (head == null)
			return null;
		HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
		// first step: copy nodes
		RandomListNode cur = head;
		while (cur != null) {
			map.put(cur, new RandomListNode(cur.label));
			cur = cur.next;
		}
		// second step: connect the copied nodes
		cur = head;
		while (cur != null) {
			map.get(cur).next = map.get(cur.next); // clone "next" relation
			map.get(cur).random = map.get(cur.random); // clone "random" relation
			cur = cur.next;
		}

		return map.get(head);
	}

	// 139. Word Break (DP)
	public boolean wordBreak(String s, List<String> wordDict) {
		int maxLen = 0; // find looking back boundary
		for (String word : wordDict) {
			maxLen = Math.max(word.length(), maxLen);
		}
		HashSet<String> set = new HashSet<>(wordDict);
		// table[i] = if [0, i) is in wordDict
		// table[0] = true: no word -> nothing to do -> thus, n+1 for 0 case
		boolean[] table = new boolean[s.length() + 1];
		table[0] = true;
		// build dp table
		// function: table[i] = if [0,i-j) is true && [i-j,i) is in dict
		for (int i = 1; i <= s.length(); i++) { // for substring + 1
			for (int j = 1; j <= maxLen && j <= i; j++) { // look backwards
				if (table[i - j] && set.contains(s.substring(i - j, i))) {
					table[i] = true;
					break;
				}
			}
		}
		return table[s.length()];
	}

	// 141. Linked List Cycle
	// slower version: hash map -> beats 12.5% (with extra space)
	public boolean hasCycle2(ListNode head) {
		if (head == null || head.next == null)
			return false;
		HashSet<ListNode> set = new HashSet<>();
		ListNode cur = head;
		while (cur.next != null) {
			if (!set.contains(cur)) {
				set.add(cur);
				cur = cur.next;
			} else {
				return true;
			}
		}

		return false;
	}

	// faster version: two pointers to see if fast meets slow (without extra space)
	public boolean hasCycle(ListNode head) {
		if (head == null)
			return false;
		ListNode slow = head;
		ListNode fast = head;
		while (fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast)
				return true;
		}
		return false;
	}



	// 144. Binary Tree Preorder Traversal
	// method 1: recursive
	public List<Integer> preorderTraversal(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (root == null)
			return result;
		dfs_preorderTraversal(root, result);
		return result;
	}

	private void dfs_preorderTraversal(TreeNode root, LinkedList<Integer> result) {
		if (root == null)
			return;
		result.add(root.val);
		dfs_preorderTraversal(root.left, result);
		dfs_preorderTraversal(root.right, result);
	}

	// method 2: non-recursive
	public List<Integer> preorderTraversal2(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (root == null)
			return result;
		Stack<TreeNode> stack = new Stack<>();
		stack.add(root);
		while (!stack.isEmpty()) {
			TreeNode center = stack.pop();
			result.add(center.val);
			if (center.right != null)
				stack.push(center.right);
			if (center.left != null)
				stack.push(center.left);
		}
		return result;
	}

	// 145. Binary Tree Postorder Traversal
	// recursive method
	public List<Integer> postorderTraversal(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (root == null)
			return result;
		dfs_postorderTraversal(root, result);
		return result;
	}

	private void dfs_postorderTraversal(TreeNode node, LinkedList<Integer> result) {
		if (node == null)
			return;
		dfs_postorderTraversal(node.left, result);
		dfs_postorderTraversal(node.right, result);
		result.add(node.val);
	}

	// non-recursive version
	public List<Integer> postorderTraversal2(TreeNode root) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (root == null)
			return result;
		Stack<TreeNode> stack = new Stack<>();
		stack.add(root);
		while (!stack.isEmpty()) {
			TreeNode center = stack.pop();
			result.addFirst(center.val); // mirror pre-order
			if (center.left != null)
				stack.push(center.left); // mirror pre-order
			if (center.right != null)
				stack.push(center.right);
		}
		return result;
	}

	// 146. LRU Cache
	// method 1: queue O(n)
	class LRUCache2 {
		int Total_cap;
		HashMap<Integer, Integer> storage;
		Queue<Integer> LRU_Que;

		public LRUCache2(int capacity) {
			Total_cap = capacity;
			storage = new HashMap<>();
			LRU_Que = new LinkedList<>();
		}

		// Get the value (will always be positive) of the key if the key exists in the
		// cache, otherwise return -1
		public int get(int key) {
			// update LRU time stamp && test
			if (!storage.containsKey(key)) {
				return -1;
			}
			// add to most recent visit
			LRU_Que.remove(key); // O(n): need to linearly search queue
			LRU_Que.add(key);
			return storage.get(key);
		}

		public void put(int key, int value) {
			if (storage.containsKey(key)) {
				LRU_Que.remove(key); // O(n)
				LRU_Que.add(key);
				storage.put(key, value);
			} else {
				// less than cap
				if (storage.size() + 1 <= Total_cap) {
					LRU_Que.remove(key);
					LRU_Que.add(key);
					storage.put(key, value);
				} else {
					// over cap -> remove
					int mostRUkey = LRU_Que.remove();
					storage.remove(mostRUkey);
					// now add
					LRU_Que.add(key);
					storage.put(key, value);
				}
			}
		}
	}

	// Method 2: doubled linked list O(1) by only changing pointers;
	class LRUCache {

		// data structure for
		private class DlinkNode {
			int key, value;
			DlinkNode prev, next;

			DlinkNode(int key, int value) {
				this.key = key;
				this.value = value;
				this.prev = null;
				this.next = null;
			}
		}

		HashMap<Integer, DlinkNode> map; // Key O(n)->O(1): remove by directly locate DlinkNode object without searching
		DlinkNode head, tail; //two dummy nodes
		int cap;

		// constructor
		public LRUCache(int capacity) {
			this.cap = capacity;
			map = new HashMap<>();
			head = new DlinkNode(-1, -1);
			tail = new DlinkNode(-1, -1);
			head.next = tail;
			tail.prev = head;
		}
		
		// re-allocate key to end tail of the linked list
		private void MoveNodeToTail(DlinkNode cur) {
			if (tail.prev == cur)
				return;
			// remove from cur location
			cur.prev.next = cur.next;
			cur.next.prev = cur.prev;
			// add cur to the tail
			cur.next = tail;
			cur.prev = tail.prev;
			tail.prev.next = cur;
			tail.prev = cur;
		}

		// Get the value (will always be positive) of the key if the key exists in the
		// cache, otherwise return -1
		public int get(int key) {
			if (!map.containsKey(key)) {
				return -1;
			}
			DlinkNode cur = map.get(key);
			MoveNodeToTail(cur); // target on object (not primitives) -> cut searching time
			return cur.value;
		}

		public void put(int key, int value) {
			if (map.containsKey(key)) {
				DlinkNode cur = map.get(key);
				MoveNodeToTail(cur);
				cur.value = value;
				return;
			}
			//if key is new, put it; 
			//before that, check if list is full
			if (map.size() == cap) {
				// remove the head
				map.remove(head.next.key);
				head.next = head.next.next;
				head.next.prev = head;
			}
			// add this new pair to the tail
			DlinkNode newNode = new DlinkNode(key, value);
			map.put(key, newNode);
			newNode.next = tail;
			newNode.prev = tail.prev;
			tail.prev.next = newNode;
			tail.prev = newNode;
		}
	}
	
	//157. Read N Characters Given Read4 (string; buffer reading)
	//https://www.youtube.com/watch?v=QihGE7m9ij0
	public int read4(char[] temp) {
		//copy 4 elements into temp
		//return # of char copied
		int index = 0;
		return index;
	}
	
    /**
     * @param buf Destination buffer !!!
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read_callSingleTime(char[] buf, int n) {
    	char[] temp = new char[4]; //Store our read chars from Read4
    	int total = 0; //total reads so far
    	
    	while(total<n) {
    		/*Read file and store characters in Temp. Count will store total chars read from Read4*/
    		int count = read4(temp);
    		
    	    // get the actual count
            /*Even if we read 4 chars from Read4, 
            we don't want to exceed N and only want to read chars till N.*/
    		count = Math.min(count, n-total);
    		
            //Transfer all the characters read from Read4 to our Destination buffer
    		for(int i=0;i<count;i++) {
    			buf[total] = temp[i];
    			total++;
    		}
    		
            //done. We can't read more characters.
    		if(count<4) break;
    	}
		return total;
    }
	
	//158. Read N Characters Given Read4 II - Call multiple times
	/* The read4 API is defined in the parent class Reader4.
    int read4(char[] buf); */
	
    /*
    Basically, call multiple times means, you want to resume where you left off last time.
    That means, if there are chars left in readChars 
    (from the Read4 from the previous call of ReadN), 
    you want to transfer them into buffer first.
    */
    
    //used buffer pointer (buffPtr) and buffer Counter (buffCnt) 
    //to store the data received in previous calls. 
    //In the while loop, if buffPtr reaches current buffCnt, 
    //it will be set as zero to be ready to read new data.
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    private int buffPtr=0;
    private int buffCnt=0;
    private char[] buff= new char[4];
    public int read_callMultiTime(char[] buf, int n) {
    	int ptr =0; // for buf 
    	while(ptr<n) {
            //no remaining characters in the temp
    		if(buffPtr==0) {
    			buffCnt = read4(buff);
    		}
    		// end of file
    		if(buffCnt==0) break;
            // copy from temp buffer to buf
    		while(ptr<n && buffPtr<buffCnt) {
    			buf[ptr++] = buff[buffPtr++];
    		}
    		// all chars in buff used up, set pointer to 0
    		if(buffPtr>=buffCnt) {
    			buffPtr=0;
    		}
    	}
		return ptr;
    }
	
	//159. Longest Substring with At Most Two Distinct Characters 
	//(sliding window - two pointer - hashMap) O(n)
	//method 1: HashMap 10ms 62% (better method use int[128] instead)
    public int lengthOfLongestSubstringTwoDistinct(String s) {
    	int len = s.length();
        if(len <= 2) return len;
        int maxSub = 0; int left = 0; int right = 0;
        HashMap<Character, Integer> indxMap = new HashMap<>();
        
        while(right<len) {
        	if(indxMap.size()<=2) {
        		char cur = s.charAt(right);
        		indxMap.put(cur, right); // update position || put new key in map
        		right++;
        	}
        	if(indxMap.size()>2) { 
        		int leftMost=len;
        		//traverse through the map to find the character with the left most index,
        		//and remove it from map
        		for(int pos : indxMap.values()) {
        			leftMost = Math.min(leftMost, pos);
        		}
        		char key = s.charAt(leftMost);
        		indxMap.remove(key);
        		left = leftMost+1;
        	} 
        	maxSub = Math.max(maxSub, right-left); //no +1 since right++ then put in next round
        }
        return maxSub;
    }
    
    //151. Reverse Words in a String
    // 8 ms: O(n2) waste time on split 
    public String reverseWords(String s) {
    	if(s.length() <1) return s;

    	String[] str = s.split("\\s+");
    	StringBuilder sb = new StringBuilder();
    	for(int i=str.length-1; i>=0;i--) {
    		sb.append(str[i]).append(" ");
    	}
		return sb.toString().trim();
        
    }
    
    //1ms : iterative O(n); use "lastIndexOf(int ch, int fromIndex)"
    public String reverseWords2(String s) {
    	int len = s.length();
    	if(len <1) return s;
    	StringBuilder sb = new StringBuilder();
    	int index = len-1;
    	while(index >=0) {
    		// skip spaces
    		if(s.charAt(index) == ' ') {
    			index --;
    			continue;
    		}
    		//find last word
    		int start = s.lastIndexOf(' ', index) + 1;
    		// add space
    		sb.append(' ').append(s.substring(start, index+1));
    		index = start -1;
    		
    	}
    	
        if(sb.length() > 0)sb.deleteCharAt(0);
		return sb.toString();
    }
    
    
    
    //173. Binary Search Tree Iterator
    //Stack; In-order 
    public class BSTIterator {
    	
    	Stack<TreeNode> stack = new Stack<TreeNode>();
    	
    	private void FillStack(TreeNode root) {
    		while(root!=null) {
    			stack.push(root);
    			root = root.left;
    		}
    	}
    	
    	
        public BSTIterator(TreeNode root) {
        	FillStack(root);
        }

        /** @return whether we have a next smallest number */
        // O(1) time and uses O(h) memory, where “h” is the height of the tree.
        public boolean hasNext() {
			return !stack.isEmpty();
        }

        /** @return the next smallest number */
        public int next() {
        	TreeNode rootCur = stack.pop();
        	FillStack(rootCur.right);
        	
			return rootCur.val;
        }
    }

	
	//175 Combine Two Tables (In SQL)
//	Select FirstName, LastName, City, State
//	From Person Left Join Address on Person.PersonId = Address.PersonId;
	
	//198. House Robber (DP)
	//Great discussion! 
	//https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
    //rob(i) = Math.max( rob(i - 2) + currentHouseValue, rob(i - 1) )
	// Note: This is O(n) in space; time O(n)
	public int rob(int[] nums) {
	    if(nums.length==0) return 0;
	    if(nums.length==1) return nums[0];
    	int len = nums.length;
    	int[] table = new int[len]; 
    	table[0] = nums[0];
    	table[1] = Math.max(nums[0], nums[1]);
    	for(int i=2;i<len;i++) {
    		table[i]=Math.max(table[i-2]+nums[i], table[i-1]);
    	}
    	return table[len-1];
    }

	//Method 2: O(1) space by using 2 variables
	public int rob2(int[] nums) {
	    if (nums.length == 0) return 0;
	    int prev1 = 0;
	    int prev2 = 0;
	    for (int num : nums) {
	        int tmp = prev1;
	        prev1 = Math.max(prev2 + num, prev1); //current
	        prev2 = tmp;
	    }
	    return prev1;
	}
	
	//176. Second Highest Salary
//	select max(Salary) as SecondHighestSalary
//	from Employee
//	where Salary < (select max(Salary) from Employee) 
	
	//    //192. Word Frequency (bash script)
//	cat words.txt | tr -s ' ' '\n' | sort | uniq -c | sort -r | awk '{print $2, $1}'

}
