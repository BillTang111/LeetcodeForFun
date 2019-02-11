package leetcode;

import java.util.*;

import leetcode.SnakeGame.Position;

public class BitTiger300 {

	//301. Remove Invalid Parentheses ( back-tracking --> DFS & stack)
	//This is a hard question; please refer to discussion online
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
    	//back-tracking method that output correct results along the way
        remove_IPT(s, res, 0, 0, new char[] {'(', ')'});
    	return res;
    }
    
    public void remove_IPT(String s, List<String> res, int iStart, int jStart, char[] direction) {
    	for(int stack=0, i = iStart; i<s.length(); i++) {
    		if(s.charAt(i)==direction[0]) stack++; //"open" Parentheses
    		if(s.charAt(i) == direction[1]) stack--; //"close" Parenthses
    		if(stack>=0) continue; //still valid
    		//"open" < "close" -> not valid 
    		//-> remove 'close' recursively (back-tracking) to find all possible cases
    		for(int j=jStart; j<=i; j++) {// removing one at each position, skipping duplicates; "=" for ")("
    			if(s.charAt(j)==direction[1] && (j==jStart||s.charAt(j-1)!=direction[1])) {
    				remove_IPT(s.substring(0, j)+s.substring(j+1,s.length()), res, i, j, direction);
    			}
    		}
    		return; // Stop here. The recursive calls already handled the rest of the string.
     	}
    	// No invalid closed parenthesis detected. Now check opposite direction, 
    	//or reverse back to original direction.
    	String reverse = new StringBuilder(s).reverse().toString();
    	if(direction[0]=='(') {
    		remove_IPT(reverse, res, 0, 0, new char[] {')', '('});
    	}else{
    		res.add(reverse);
    	}
    }
    
    
    //322. Coin Change (DB) 
    //note: "Solution" is really great!!!
    //method 1: [back tracking (DFS)] to enumerate all subsets of coin frequencies 
    //and return the minimum. (try each coin c, try amount/c of them); time: O(amount ^ n)
    //method 2.1 & 2.2 : Dynamic Programming O(amount*denominations) 
    //Top-down (back-tracking) & Bottom-up (iterative)
    //F(S) - minimum number of coins needed to make change for amount S (see "solution" )
    //Method 1: Bottom-up (iterative)
    public int coinChange(int[] coins, int amount) {
    	//set up table
    	int coinLen = coins.length;
    	int[] dp = new int[amount+1]; //0, 1....6
    	Arrays.fill(dp, amount+1); //for minimum
    	dp[0] = 0; //init
    	for(int i=1; i<=amount;i++) { //amount
    		for(int j=0; j<coinLen; j++)//coin denomination
    		{
    			if(i>=coins[j]) {
        			dp[i] = Math.min(dp[i], dp[i-coins[j]]+1); 
    			}
    		}
    	}
		return dp[amount] == amount+1 ? -1 : dp[amount];
    }
    
    //DP: Top-down;  back-tracking; min length in the tree
//    public int coinChange2(int[] coins, int amount) {
//
//		return 0;
//    }
    
    //338. Counting Bits (DP)
    //f[i] = f[i / 2] + i % 2. 
    //Take number X for example, 10011001.
//    Divide it in 2 parts:
//    	<1>the last digit ( 1 or 0, which is " i&1 ", equivalent to " i%2 " )
//    	<2>the other digits ( the number of 1, which is " f[i >> 1] ", equivalent to " f[i/2] " )
    public int[] countBits(int num) {
    	int[] table = new int[num+1];
    	for(int i=0;i<=num;i++) {
    		table[i]=table[i>>1] + i%2;
    		//    for (int i=1; i<=num; i++) f[i] = f[i / 2] + (i % 2); (slower)
    	}
    	return table;
    }
    
    

    //353. Design Snake Game
    public class SnakeGame {
        
        LinkedList<Position> snake;
        int[][] food;
        int width; int height;
        int score;
        
        /** Initialize your data structure here.
            @param width - screen width
            @param height - screen height 
            @param food - A list of food positions
            E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
        public SnakeGame(int width, int height, int[][] food) {
            this.width = width; 
            this.height = height;
            this.food = food; 
            snake = new LinkedList<Position>();
            snake.add(new Position(0,0));
            score = 0;
        }
        
        /** Moves the snake.
            @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down 
            @return The game's score after the move. Return -1 if game over. 
            Game over when snake crosses the screen boundary or bites its body. */
        public int move(String direction) {
            if(score == -1 ) return -1;  //game over
//            Position head = snake.peekFirst(); //pass by reference will change real memory
            Position head = new Position(snake.peekFirst().x, snake.peekFirst().y);
            //move head
//            if(direction == "U"){ (wrong)
//            if(direction.equals("U")){ //(right string compare!!!)
            switch(direction){
    	        case "U": head.x--; break;
    	        case "L": head.y--; break;
    	        case "R": head.y++; break;
    	        case "D": head.x++; break;
            }
            
            //check if head hits border
            if(head.x >= height || head.x<0 || head.y>=width || head.y<0) {
                score = -1;
                return -1;
            }
            //check if head hits body
//            for(Position body : snake){
//                if(body.isEqual(head)){
//                    score = -1;
//                    return -1;
//                }
//            }
            //for wrong case: [null,0,1,1,1,1,2,2,2,2,3,3,-1] 
            // right: [null,0,1,1,1,1,2,2,2,2,3,3,3]
            for(int i=1;i<snake.size()-1;i++){ //!!! skip tail because it potentially moves with head at same time
                Position body = snake.get(i);
                if(body.isEqual(head)) return -1;	       
            }
            //not hit ==> move body
            snake.addFirst(head);
            //check if eats a food
            if(score < food.length){
                Position fd = new Position(food[score][0], food[score][1]);
                if(fd.isEqual(head)){
                    score++;
                }
            }
            while(snake.size()>score+1) snake.removeLast(); //remove tail
            return score;
        }
        
        //self-defined class
        class Position{
            int x;
            int y;
            
            public Position(int x, int y){
                this.x = x;
                this.y = y;
            }
            
            public boolean isEqual(Position p){
                return p.x == this.x && p.y == this.y;
            }
        }
        
    }
    
    
    //380. Insert Delete GetRandom O(1)
    class RandomizedSet {

    	HashMap<Integer, Integer> locatsMap;
    	ArrayList<Integer> numsLst;
    	Random rand = new Random();
    	
        /** Initialize your data structure here. */
        public RandomizedSet() {
       	  locatsMap = new HashMap<>();
       	  numsLst = new ArrayList<>();
       	 
        }
        
        /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
        public boolean insert(int val) {
        	if(locatsMap.containsKey(val)) return false;
        	locatsMap.put(val, numsLst.size());
        	numsLst.add(val);
			return true;
        }
        
        /** Removes a value from the set. Returns true if the set contained the specified element. */
        public boolean remove(int val) {
        	if(!locatsMap.containsKey(val)) return false;
        	
        	int removeLoc = locatsMap.get(val);
        	if(removeLoc != numsLst.size()-1) { //not last one to be removed
               	int lastVal = numsLst.get(numsLst.size()-1);
               	numsLst.set(removeLoc, lastVal);
               	locatsMap.replace(lastVal, removeLoc);
        	}
        	numsLst.remove(numsLst.size()-1);
        	locatsMap.remove(val);
        	return true;
            
        }
        
        /** Get a random element from the set. */
        public int getRandom() {
			return numsLst.get(rand.nextInt(numsLst.size()));
        }
    }
    
    //393. UTF-8 Validation (bit manipulation)
    public boolean validUtf8(int[] data) {
    	if(data==null || data.length==0) return false;
    	int count = 0;
    	for(int d : data){
    		if(count == 0) {
    			if((d >> 5) == 0b110) count=1;
    			else if((d >> 4) == 0b1110) count=2;
    			else if((d >> 3) == 0b11110) count=3;
    			else if((d >> 7) == 1) return false;
    		}else {
    			if((d>>6)!=0b10) return false;
    			count --;
    		}
    	}
		return count == 0;
    }
    
    //399. Evaluate Division (Graph -- DFS or Union Find)
    //https://leetcode.com/problems/evaluate-division/discuss/171649/1ms-DFS-with-Explanations
    public double[] calcEquation(String[][] eq, double[] vals, String[][] q) {
    	//build the graph
    	HashMap<String, HashMap<String, Double>> graph = buildGraph( eq,  vals); 
    	//Calculate relations in query
    	double[] result = new double[q.length];
    	for(int i=0;i<q.length;i++) {
    		result[i] = dfs_getPathWeight( q[i][0], q[i][1], graph, new HashSet<>());
    	}
    	
		return result;

    }

    private HashMap<String, HashMap<String, Double>> buildGraph(String[][] eq, double[] vals){
    	HashMap<String, HashMap<String, Double>> graph = new HashMap<>(); 
    	String A; String B;
    	for(int i=0;i<eq.length;i++) {
    		A=eq[i][0];
    		B=eq[i][1];
    		graph.putIfAbsent(A, new HashMap<>());
    		graph.get(A).put(B, vals[i]);
    		graph.putIfAbsent(B, new HashMap<>());
    		graph.get(B).put(A, 1/vals[i]);
    	}
		return graph;
    }
    
    private double dfs_getPathWeight(String start, String end, 
    		HashMap<String, HashMap<String, Double>> graph, HashSet visited) {
    	//Accepting case is (graph.get(u).containsKey(v)) rather than (u.equals(v)) 
    	//for it takes O(1) but (u.equals(v)) takes O(n) 
    	//for n is the length of the longer one between u and v.
    	visited.add(start);
    	
        /* Rejection case. */
    	if(!graph.containsKey(start) || !graph.containsKey(end)) return -1.0;
    	
        /* Accepting case. */
    	if(start == end) return 1.0;
    	if(graph.get(start).containsKey(end)) return graph.get(start).get(end);
    	
    	
    	for( String neighbor : graph.get(start).keySet() ) {
    		if(!visited.contains(neighbor)) {
    			Double productWeight = dfs_getPathWeight( neighbor, end, graph, visited);
    			if(productWeight>0) {
            		Double curWeight = graph.get(start).get(neighbor);
            		return curWeight * productWeight;
    			}
    		}
    	}
		return -1;
    }

}
