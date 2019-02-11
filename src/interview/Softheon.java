package interview;
import java.util.*;

public class Softheon {
	//1.给一个字符串，找到第一个重复的单词。
	// find first repeated word in a sentence（比如”he had had he“返回"had", 而不是”he“）
    public String findFirstRepeat(String A) {

    	String[] parts = A.split(" ");
    	Set<String> set = new HashSet<>();
    	for(String s : parts) {
    		if(set.contains(s)) return s;
    		set.add(s);
    	}
    	return "";
    }
    
    //2. max difference 
    public int maxDifference(int n,int[] from, int[] to) {
    	Map<Integer, List<Integer>> graph = new HashMap<>();
    	Set<Integer> nodes = new HashSet<>();
    	int lenF = from.length; int lenT = to.length;
    	if(lenF<1 || lenT<1) return 0;
    	//build the graph
    	for(int f=0; f<lenF && f<lenT; f++) {
    		nodes.add(from[f]); nodes.add(to[f]); 
    		if(graph.containsKey(from[f])) {
    			graph.get(from[f]).add(to[f]);
    		}else {
    			List<Integer> lst = new ArrayList<>();
    			lst.add(to[f]);
    			graph.put(from[f], lst);
    		}
    		
    		if(graph.containsKey(to[f])) {
    			graph.get(to[f]).add(from[f]);
    		}else {
    			List<Integer> lst = new ArrayList<>();
    			lst.add(from[f]);
    			graph.put(to[f], lst);
    		}
    	}
    	
    	//visit each group; update the global max
    	int maxDiff = -1;
    	Set<Integer> visited = new HashSet<>();
    	for(int i : nodes) {
    		if(visited.contains(i)) continue;
    		int localMax = findLocalMax(i, visited, graph);
    		maxDiff = maxDiff < localMax ? localMax : maxDiff;
    	}
    	
    	return maxDiff;
    } 
    
    //BFS traverses the graph
    private int findLocalMax(int node, Set<Integer> visited, Map<Integer, List<Integer>> graph) {
    	int max = node; int min = node;
    	
    	Queue<Integer> que = new LinkedList<>();
    	que.add(node);
    	
    	while(!que.isEmpty()) {
    		int cur = que.poll();
    		visited.add(cur);
    		if(cur>max) {
    			max = cur;
    		}else if(cur<min) {
    			min = cur;
    		}
    		
    		for(int neighbor : graph.get(cur)) {
        		if(visited.contains(neighbor)) continue;
    			que.add(neighbor);
    		}
    	}
    	
    	return max - min;
    }
    
    
    // 3.lc63 
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid == null) return 0;
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        
        int[][] path = new int[m][n]; //number of ways to reach (m,n)
        //special care for base case
        path[0][0] = obstacleGrid[0][0]==0 ? 1:0;
        if(path[0][0] == 0) return 0;
        
        for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
                if(obstacleGrid[i][j] == 1){
                    path[i][j] = 0;
                }else if(i==0){ //base case
                    if(j>0){
                        path[i][j] = path[i][j-1]; 
                    }
                }else if(j==0){ //base case
                    if(i>0){
                        path[i][j] = path[i-1][j];
                    }
                }else{
                    path[i][j] = path[i - 1][j] + path[i][j - 1];
                }
			}
		}
        return path[m-1][n-1];
    }
}

class Result {

    /*
     * Complete the 'numberOfTokens' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER expiryLimit
     *  2. 2D_INTEGER_ARRAY commands
     */

    public static int numberOfTokens(int expiryLimit, List<List<Integer>> commands) {
    // Write your code here
    if(commands == null || commands.size()==0) return 0;
    Map<Integer, Token> map = new HashMap<>();
    for(int i=0; i<commands.size();i++){
        List<Integer> command = commands.get(i);
        if(command.get(0)==0){ //create
            int cur_id = command.get(1);
            if(map.containsKey(cur_id)){
                continue;
            }else{
                int timeEx = command.get(2) + expiryLimit;
                map.put(cur_id, new Token(cur_id, timeEx));
            }
        }else if(command.get(0)==1){ //reset
                int cur_id = command.get(1);
                int cur_time = command.get(2);
                if (map.containsKey(cur_id)) {
                    Token t = map.get(cur_id);
                    if(cur_time>t.expTime){
                        map.remove(cur_id);
                    }else{
                        t.expTime = cur_time + expiryLimit;
                        map.put(cur_id, t);
                    }
                } else {
                    continue;
                }
        }
    }

    return map.size();
    }

}

class Token{
    int id;
    int expTime;

    public Token(int id, int expTime){
        this.id = id;
        this.expTime = expTime;
    }
}