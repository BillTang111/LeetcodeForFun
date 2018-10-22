package leetcode;

import java.util.*;

public class BigTiger100_200 {

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
		private int timeGap = 10; //in seconds

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
			}else {
				return false;
			}
		}
	}
	
	//108.Convert Sorted Array to Binary Search Tree
	//Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
	//Binary Search Tree: 1: left < parent < right 
    public TreeNode sortedArrayToBST(int[] nums) {
    	if(nums==null || nums.length==0) return null;
    	
    	TreeNode root = sortedArrayToBSTHelp(nums, 0, nums.length-1);
    	
        return root;
    }
    
    public TreeNode sortedArrayToBSTHelp(int[] nums, int start, int end) {
    	//base case
    	if(start > end) return null;
    	
    	//general cases
    	int mid = (start + end +1)/2; //+1 for round up
    	TreeNode root = new TreeNode(nums[mid]);
    	root.left = sortedArrayToBSTHelp( nums, start, mid-1);
    	root.right = sortedArrayToBSTHelp( nums, mid+1, end);
    	
        return root;
    }

    //114. Flatten Binary Tree to Linked List
    //Given a binary tree, flatten it to a linked list in-place.
    //pre-order traversal (center - left - right)
    public void flatten(TreeNode root) {
    	//convert to flatten linked list version of root in place
    	Flatten( root,null);
    }
    
    //the reverse order after flattening is post order traversal in (right, left, root) order 
    //like [6->5->4->3->2->1].
    public TreeNode Flatten(TreeNode root, TreeNode prev) {
    	
    	if(root == null) return prev;
    	prev = Flatten( root.right,  prev);
    	//“prev” above is passed into next one to link to the tail
    	prev = Flatten( root.left,  prev);
    	root.right = prev;
    	root.left = null;
    	prev=root;
    	return prev;
    }

    //121. Best Time to Buy and Sell Stock
    // Method 1: 1-D Dynamic Programming
    //https://www.youtube.com/watch?v=8pVhUpF1INw
    public int maxProfit(int[] prices) {
    	//idea: 2 DP tables
    	int len = prices.length;
    	if(prices==null || len==0) return 0;
    	
    	//minPrice[i]=lowest price till ith day = Min(minPrice[i-1],L[i])
    	int[] minPrice = new int[len];
    	minPrice[0]=prices[0];
    	
    	//maxProf[i] = max profit up to ith day = Max(maxProf[i-1],prices[i]-minPrice[i])
    	int[] maxProf = new int[len];
    	maxProf[0]=0;
    	
    	for(int i=1;i<len;i++) {
    		minPrice[i]=Math.min(minPrice[i-1], prices[i]);
    		maxProf[i]=Math.max(maxProf[i-1], prices[i]-minPrice[i-1]);
    	}
        return maxProf[len-1]; 
    }
    
    //Method 2: reduce to "max subarray problem" using Kadane's Algorithm.(leecode 53)
    public int maxProfit2(int[] prices) {
    	int len = prices.length;
    	if(prices==null || len==0) return 0;
    	//idea: prices -> prices[i]-prices[i-1]==gains[i] -> reduce to "max subarray problem"
    	int[] gain = new int[len-1];
    	for(int i=1;i<len;i++) {
    		gain[i-1]=prices[i]-prices[i-1];
    	}
    	return Math.max(0, maxSubArray(gain)); //profit could be negative
    }
    
    //From leecode 53 (DP)
    public int maxSubArray(int[] nums) {
    	if(nums==null || nums.length==0) {
    		return 0;
    	}
    	//idea: CurMax = Max(nums[i],nums[i]+CurMax)
    	int curMax=nums[0]; int max=nums[0];
    	for(int i=1; i<nums.length;i++) {
    		curMax = Math.max(nums[i],nums[i]+curMax);
    		if(curMax>max) {
    			max=curMax;
    		}
    	}
    	return max;
    }
    
    //122. Best Time to Buy and Sell Stock II
    public int maxProfit_2(int[] prices) {
    	int len = prices.length;
    	if(prices==null || len==0) return 0;
    	//idea: (gain[i] = prices[i]-prices[i-1])
    	int max = 0;
    	int[] gain = new int[len-1];
    	for(int i=1;i<len;i++) {
    		gain[i-1]=prices[i]-prices[i-1];
    		if(gain[i-1]>0) {
    			max += gain[i-1];
    		}
    	}
    	return max;
    }
    
    //124. Binary Tree Maximum Path Sum (recursion + dfs)
    public int maxPathSum(TreeNode root) {
    	if(root == null) {
    		throw new IllegalArgumentException();
    	}
    	//return dfs_maxPathSum( root, Integer.MIN_VALUE); (wrong: nothing to record/return "max")
    	//Instead: use a int[] to wrap Integer.MIN_VALUE while keeping "maxPSum" variable
    	int[] maxPSum = new int[] {Integer.MIN_VALUE};
    	dfs_maxPathSum( root, maxPSum); 
        return maxPSum[0];
    }
    
    private int dfs_maxPathSum(TreeNode root, int[] maxPSum) {
    	//base case
    	//if(root==null) return 0; (no need; handled below)
    	
    	//recursive cases
    	int l = root.left==null ? 0 : Math.max(0, dfs_maxPathSum( root.left,  maxPSum));//if l < 0, cut out -> Max(0, dfs())
    	int r = root.right==null ? 0 : Math.max(0, dfs_maxPathSum( root.right, maxPSum));
    	int cur = root.val + l + r;
    	//record max
    	maxPSum[0] = Math.max(maxPSum[0], cur);
    	//return cases:
    	//1. if l<0 && r<0 -> root.val
    	//2. if l <0 && r>0 -> root.val + r  3.if l>0 && r<0 -> root.val + l
    	//3. if l>0 && r>0 -> root.val + max(r,l) (can only visit a point (i.e root) once)
    	return root.val + Math.max(l, r);
    }
    
    //LeetCode 543:  Diameter of Binary Tree 
    public int diameterOfBinaryTree(TreeNode root) {
    	if(root==null) return 0;
    	int[] maxLen = new int[] {0};
    	dfs_diameterOfBinaryTree( root, maxLen);
    	return maxLen[0];
    }
    
    //return # of links to this root's parent
	private int dfs_diameterOfBinaryTree(TreeNode root, int[] maxLen) {
		int l = root.left == null ? 0 : dfs_diameterOfBinaryTree( root.left, maxLen)+1; 
		int r = root.right == null ? 0 : dfs_diameterOfBinaryTree( root.right, maxLen)+1;
		int cur = l + r;
		//record the max
		maxLen[0] = Math.max(maxLen[0], cur);
		//return cases: 
		return Math.max(l, r);
	}
    
    //LeetCode 687. Longest Univalue Path 
    public int longestUnivaluePath(TreeNode root) {
    	if(root==null) return 0;
    	int[] maxLen = new int[] {0};
    	dfs_longestUnivaluePath( root, maxLen);
    	return maxLen[0];
    }

    //return # of links to this root's parent
	private int dfs_longestUnivaluePath(TreeNode root, int[] maxLen) {
		int l = root.left == null ? 0 : dfs_longestUnivaluePath( root.left, maxLen); // make sure recurse when root.val != root.left.val
		int r = root.right == null ? 0 : dfs_longestUnivaluePath( root.right, maxLen);
		int resl = (root.left != null && root.val == root.left.val) ? l+1 : 0;
		int resr = (root.right != null && root.val == root.right.val) ? r+1 : 0;
		int cur = resl + resr;
		//record the max
		maxLen[0] = Math.max(maxLen[0], cur);
		//return cases: 
		//l != root & r != root -> 0;
		//l == root & r != root -> l's links + 1;
		//r == root & l != root -> r's links + 1;
		//l == root & r == root -> max(l's link, r's link);
		return Math.max(resl, resr);
	}
    
	//128. Longest Consecutive Sequence O(n)
    public int longestConsecutive(int[] nums) {
    	//add everything to the set
        HashSet<Integer> set = new HashSet<Integer>();
        for(int num : nums) {
        	set.add(num);
        }
    	//Idea: check whether set contains (key-1) or not
        //If yes, ignore (key - 1) for now; will be visited
        //If not, use (key) as low bound -> check(key+1) until key + ls + 1 not in set
        //find max ls
        int ans = 0;
        for(int num : nums) {
        	if(!set.contains(num-1)) {
        		int ls = 0;
        		//use num as lower bound to start
        		while(set.contains(num++)) {
        			ls++;
        		}
        		ans=Math.max(ans, ls);
        	}
        }
    	return ans;
    }
    
    //133. Clone Graph (graph traversal - DFS/BFS)
    //https://www.youtube.com/watch?v=V0j1IEBkK4k
    //global variable
    //DFS -> 3ms || BFS -> 7ms
    HashMap<UndirectedGraphNode, UndirectedGraphNode> Map_cloneGraph = new HashMap<>();
    
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        
    	return dfs_cloneGraph(node) ;
    }
    
    private UndirectedGraphNode dfs_cloneGraph(UndirectedGraphNode node) {
    	// idea: copy Node first, 
    	// then connect the copy to neighbors (add neighbors to copy's list)
    	if(node == null) return null;
    	if(Map_cloneGraph.containsKey(node)) return Map_cloneGraph.get(node);
    	//first copy
    	UndirectedGraphNode dup = new UndirectedGraphNode(node.label); 
    	Map_cloneGraph.put(node, dup);
    	//now, connect the dup with original node's neighbors
    	for(UndirectedGraphNode neighbor : node.neighbors) {
    		//深度优先clone 这个neighbor (递归)
        	UndirectedGraphNode dup_neighbor = dfs_cloneGraph(neighbor); 
        	dup.neighbors.add(dup_neighbor);
    	}
    	return dup;
    }
    
    //https://www.youtube.com/watch?v=_Zt6gwWRnHM&t=195s
    public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
    	// idea: copy Node first, then connect the copy nodes 
    	if(node == null) return null;
    	//map to record copied/visited node
    	HashMap<UndirectedGraphNode,UndirectedGraphNode> map = new HashMap<>();
    	map.put(node, null);
    	//queue for BFS
    	Queue<UndirectedGraphNode> que = new LinkedList<UndirectedGraphNode>();
    	que.add(node);
    	//BFS for nodes in original graph
    	while(!que.isEmpty()) {
    		UndirectedGraphNode cur = que.poll();
    	}
    	
    	return null; 
    }
    
}
