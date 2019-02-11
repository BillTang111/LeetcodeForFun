package leetcode;

import java.util.*;

public class BitTiger600 {
	
	//621. Task Scheduler (find pattern cleverly)
	// int[] as dictioinary: char as index and count as content 
    public int leastInterval(char[] tasks, int n) {
        int[] dict = new int[26];
        for(char c: tasks) {
            dict[c-'A']++;
        }
        Arrays.sort(dict);
        int i=25;
        while(i>=0&&dict[i]==dict[25]) i--;
        return Math.max(tasks.length, (dict[25]-1)*(n+1)+25-i);
    }
    
    //642. Design Search Autocomplete System (前缀树, priority Queue，system design)
    //前缀树 用来search； priority Queue 用来output
    class AutocompleteSystem {

    	class TrieNode {
    		Map<Character, TrieNode> children; // trie tree structure for searching
    		Map<String, Integer> freqenciesMap; // for outputting ;can use int[27] here since it is only a - z and ' '
    		TrieNode(){
    			this.children = new HashMap<>();
    			this.freqenciesMap = new HashMap<>();
    		}
    	}
    	
    	//pair structure for priority queue
    	class Sent_count_pair {
    		String sentence;
    		int count;
    		Sent_count_pair(String sen, int count){
    			this.sentence = sen;
    			this.count = count;
    		}
    	}
    	
    	//fields 
    	TrieNode root; 
    	String prefix;
    	
    	//init structures: build the trie tree
        public AutocompleteSystem(String[] sentences, int[] times) {
            root = new TrieNode();
            prefix = "";
            //build tree with sentences (one sentence piled on the tree at a time)
            for(int i=0; i<sentences.length; i++) {
            	addSentence(sentences[i], times[i]);
            }
        }
        
        //key function: add a sentence to the trie tree 
        private void addSentence(String sentence, int freq) {
        	TrieNode curNode = root; // build tree from the top 
        	
        	//one char per level (build the tree)
        	for(char c : sentence.toCharArray()) {
        		TrieNode next = curNode.children.get(c);
        		if(next == null) {
        			next = new TrieNode();
        			curNode.children.put(c, next); 
        		}
        		curNode = next;
        		curNode.freqenciesMap.put(sentence, curNode.freqenciesMap.getOrDefault(sentence, 0)+freq);
        	}
        	
        }
        
        public List<String> input(char c) {
        	List<String> res = new ArrayList<>();
        	if(c=='#') {
        		addSentence(prefix, 1);
        		prefix = "";
        		return res;
        	}
        	
        	prefix = prefix + c;
        	// search from the top
        	TrieNode curNode = root; 
        	//traverse the tree
        	for(char cc : prefix.toCharArray()) {
        		TrieNode next = curNode.children.get(cc);
        		if(next == null) {
        			return res;
        		}
        		curNode = next;
        	}
        	
        	//the input char node was found; 
        	//now build a priority queue based on its children; then pop the top three sentences
        	// define the comparator; (note: max first -> p2 - p1)
        	PriorityQueue<Sent_count_pair> pq = new PriorityQueue<>((p1,p2) -> 
        		(p1.count == p2.count ? p1.sentence.compareTo(p2.sentence) : p2.count - p1.count));
        	
        	for(String str : curNode.freqenciesMap.keySet()) {
        		pq.add(new Sent_count_pair(str, curNode.freqenciesMap.get(str)));
        	}
        	//pop the top 3
        	for(int i=0; i<3 && !pq.isEmpty(); i++) {
        		res.add(pq.poll().sentence);
        	}
			return res;
        }
    }
 

	//681. Next Closest Time (string<==>int; time conversion; ) (%57)
	//https://www.youtube.com/watch?v=IAet94C1FCc
	//method1: brute force (Simulate the clock going forward by one minute. ) O(24*60) O(1) O(1)
    public String nextClosestTime(String time) {
    	int cur = Integer.parseInt(time.substring(0, 2))*60;
    	cur +=  Integer.parseInt(time.substring(3));
    	HashSet<Integer> digAllowed = new HashSet<Integer>();
    	for(char c : time.toCharArray()) if(c!=':') {
    		digAllowed.add(c-'0');
    	}  	
    	//loop a minute a time
    	while(true) {
    		cur = (cur+1)%(24*60);
    		int [] digits = new int[] {cur/60/10, cur/60%10, cur%60/10, cur%60%10};
    		//labeled break
    		search:{ 
        		for(int d : digits) {
        			if(!digAllowed.contains(d)) break search;
        		}
        		//%02d means "format the integer with 2 digits, left padding it with zeroes",
        		return String.format("%02d:%02d", cur/60, cur%60);
    		}
    	}
    }
    
    //684. Redundant Connection (Union Find)
    //https://www.youtube.com/watch?v=4hJ721ce010 (hua hua)
    //Method 1: DFS version by building the graph (O(n^2) for each node, traverse once) beat 1%
    public int[] findRedundantConnection1(int[][] edges) {
    	if(edges == null) return null;
    	
    	int[] res = new int[2];
    	Map<Integer, List<Integer>> graph = new HashMap<>();
    	for(int[] edge : edges) {
    		Set<Integer> visited = new HashSet<>();
    		int start = edge[0];
    		int end = edge[1];
            
    		if(hasPath(start, end, graph, visited)) {
    			//if there already is a path from s to end
    			// this new "edge" is create a cycle
    			return edge; 
    		}
    		//store the uon-cyclic edge to the graph
    		if(!graph.containsKey(edge[0])) {
                List<Integer> lst = new ArrayList<Integer>(); 
                lst.add(edge[1]);
    			graph.put(edge[0], lst);
    		}else {
    			graph.get(edge[0]).add(edge[1]);
    		}
            
            System.out.println("update edge[0]: "+ graph.get(edge[0]));
    		
    		if(!graph.containsKey(edge[1])) {
                List<Integer> lst = new ArrayList<Integer>(); 
                lst.add(edge[0]);
    			graph.put(edge[1], lst);
    		}else {
    			graph.get(edge[1]).add(edge[0]);
    		}
    	}
		return null;
    }
    
    //dfs to find the path from s to end
    private boolean hasPath(int start, int end, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
    	if(start == end) return true;
    	if(!graph.containsKey(start) || !graph.containsKey(end)) return false;
    	visited.add(start);

    	//visit each neighbors of this node
    	List<Integer> neighbors = graph.get(start);
    	for(int neighbor : neighbors ) {
    		if(visited.contains(neighbor)) continue;
    		if(hasPath(neighbor, end, graph, visited)) return true; //this dfs is smart
    	}
    	return false;
    }
    
    //method 2: Union Find  Amortized O(n*) = O(1); 2 ms beats 100%
    //The Union by Rank and Path Compression can optimize the time complexity from O(n) to O(logn) 
    // union Find is a algorithm/data structure: 
    //Find(x) : 找cluster or group 的 根节点/cluster-id
    //Union(x,y): merge 2 clusters
    public int[] findRedundantConnection(int[][] edges) {
        //parent[i] = the direct parent of child i
    	int[] parent = new int[edges.length+1]; // node from 1 ... N
    	Arrays.fill(parent, -1); // every nodes' parent are themselves (N groups to begin with)
    	
    	//build and "Path Compression" the UF tree
    	for(int i=0; i<edges.length; i++) {
    		int start = edges[i][0];
    		int end = edges[i][1];
    		//if both nodes have the same root -> already in the same group -> (s, end) is a repetitive edge
    		if(find_Redundant(start, parent) == find_Redundant(end, parent)) return edges[i];
    		//unite to the same group (for there is an edge)
    		union_Redundant(start,end, parent);
    	}
    	return null; // no repeat 
    }
    
    private int find_Redundant(int cur, int[] parent){
    	if(parent[cur] == -1) return cur; //group root found
    	//"Path Compression": Flatten the tree by recursively making every children of root point to the root
    	parent[cur] = find_Redundant( parent[cur], parent);  //经典做法
		return parent[cur];
    }
    
    //combine two groups by "connecting their roots together"
    private void union_Redundant(int start, int end, int[] parent) {
    	int root1 = find_Redundant( start, parent);
    	int root2 = find_Redundant( end, parent);
    	if(root1 == root2) return; // already in the same group -> do nothing 
    	parent[root1] = root2; // connect 2 group by making root2 the parent of root1('s group)
    }
    
    //695. Max Area of Island
    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null) return 0;
        int maxArea=0;
    	for(int i=0;i<grid.length;i++) {
    		for(int j=0; j<grid[0].length;j++) {
    			if(grid[i][j]==1) {
    				int curArea = dfs_maxAreaOfIsland(grid, i, j);
    				maxArea = Math.max(curArea, maxArea);
    			}
    		}
    	}
    	return maxArea;
    }
    
    //set 1 to 0 as visited by DFS and return area in this island
    public int dfs_maxAreaOfIsland(int[][] grid, int col, int row) {
    	if(col<0 || col>=grid.length || row<0 || row>=grid[0].length || grid[col][row]==0) return 0;
    	
        grid[col][row] = 0;
 
    	return 1 + dfs_maxAreaOfIsland(grid, col-1, row) 
    	+ dfs_maxAreaOfIsland(grid, col+1, row) 
    	+ dfs_maxAreaOfIsland(grid, col, row-1)
    	+ dfs_maxAreaOfIsland(grid, col, row+1);
    }
    
	
}
