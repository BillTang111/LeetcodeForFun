package leetcode;
import java.util.*;

public class BitTiger500 {

	
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
    
    
    
}
