package leetcode;
import java.util.*;

public class BitTiger900 {

	//904. Fruit Into Baskets (sliding window)
	//Translation: Find out the longest length of sub-arrays with at most 2 different numbers?
	//83 ms (there are)
    public int totalFruit(int[] tree) {
        HashMap<Integer, Integer> count = new HashMap<>();
        int max=0, l=0; 
        for(int r=0; r<tree.length; r++) {
        	count.put(tree[r], count.getOrDefault(tree[r], 0) + 1); //add tree[r] to basket
        	while(count.size()>2) { //close left window
        		count.put(tree[l],count.get(tree[l]) - 1); //remove tree[l] from basket
        		if(count.get(tree[l])==0) count.remove(tree[l]); 
        		l++;
        	}
        	max = Math.max(max, r-l+1);
        }
    	return max;
    }
    
	//945. Minimum Increment to Make Array Unique
    //wrong
    public int minIncrementForUnique(int[] A) {
    	int len = A.length;
    	if(len<2) return 0;
    	Arrays.sort(A);
    	int res = 0;
    	
    	for(int i=1;i<len;i++) {
    		if(A[i]<=A[i-1]) {
    			res += A[i-1] - A[i] +1;
    			A[i]=A[i-1]+1;
    		}
    	}
    	
    	return res;
    }
    
    //946. Validate Stack Sequences
    public boolean validateStackSequences(int[] pushed, int[] popped) {
    	Stack<Integer> stack = new Stack<>();
    	int popPosition = 0;
    	for(int pushNum : pushed) {
    		stack.push(pushNum);
    		while(!stack.empty() && stack.peek() == popped[popPosition]) {
    			stack.pop();
    			popPosition++;
    		}
    	}
    	return stack.empty();
    }
    
    
    //948. Bag of Tokens (Greedy + Two Pointers)
    //come up greedy idea with "Buy at the cheapest and sell at the most expensive."
    public int bagOfTokensScore(int[] tokens, int P) {
    	Arrays.sort(tokens); // cheapest to most expensive
    	int cheapPos=0; int expensivePos=tokens.length-1;
    	int totalPoints=0; int res = 0;
    	while(cheapPos<=expensivePos) {
    		if(P >= tokens[cheapPos]) {
    			P -= tokens[cheapPos];
    			cheapPos++;
    			totalPoints++;
    			res = Math.max(res, totalPoints); //check [28,76] and 62
    		}else if(totalPoints>0) {
    			totalPoints --;
    			P += tokens[expensivePos];
    			expensivePos--;
    		}else { // Power < tokens[cheapPos]
    			break;
    		}
    	}
    	return res;
//    	return totalPoints;
    }
    
    //929. Unique Email Addresses
    public int numUniqueEmails(String[] emails) {
        HashSet<String> set = new HashSet<String>();
    	
    	for(String s : emails) {
    		int indexAt = s.indexOf('@');
    		int firstAdd = s.indexOf('+');
    		String local ="";
    		if(firstAdd < indexAt) {
        		 local = s.substring(0,firstAdd);

    		}else {
        		 local = s.substring(0,indexAt);
    		}
    		String localName = local.replace(".", "");
    		String total = localName+s.substring(indexAt+1);
    		set.add(total);
    	}
    	return set.size();
    }
    
    
    //949. Largest Time for Given Digits (permutation)
    public String largestTimeFromDigits(int[] A) {
        if(A.length<4 || A==null ) return "";
        Arrays.sort(A);
        if(A[0]>2) return "";
        //use string or array for permutation
        String strToPermut ="";
        for(int a : A) {
        	strToPermut += a;
        }
        //string to store all 4x4 arrangement 
        List<String> lst = new LinkedList<String>();
        permutation_LTFD("", strToPermut,lst);
        //find largest time among them
        String time = "";
        for(String s: lst) {
        	s = s.substring(0,2)+":"+s.substring(2);
        	//compareTo is every helpful here at 0"0":00
        	if(s.charAt(0)<='2' && s.charAt(3)<'6' && s.compareTo("24:00")<0) {
        		//compareTo: Compares two strings lexicographically. 
        		time = time.compareTo(s) < 0 ? s : time;
        	}
        }
        return time;
    }
    
    //this permutation method is what i could not do during the contest
    private void permutation_LTFD(String prefix, String strToPermut, List<String> lst) {
    	int strToPermut_len=strToPermut.length();
    	if(strToPermut_len==0) lst.add(prefix);
    	else {
    		for(int i=0;i<strToPermut_len;i++) {
    			permutation_LTFD( prefix + strToPermut.charAt(i), 
    					strToPermut.substring(0, i)+strToPermut.substring(i+1), lst);
    		}
    	}		
    }
    
    //950. Reveal Cards In Increasing Order 
    //method 1: thinking reversely  (easier to think)
    //method 2: simulation with Queue 
    public int[] deckRevealedIncreasing(int[] deck) {
    	int len = deck.length;
    	int[] res = new int[len];
    	Queue<Integer> indexOfResultArr_Que = new LinkedList<>();
    	Arrays.sort(deck);
    	for(int i=0;i<len;i++) {
    		indexOfResultArr_Que.add(i);
    	}
    	for(int i=0;i<len;i++) {
    		res[indexOfResultArr_Que.poll()]=deck[i];
//    		int head = indexOfResultArr_Que.poll(); //null pointer exception; last run, no add
//    		indexOfResultArr_Que.add(head); 
    		indexOfResultArr_Que.add(indexOfResultArr_Que.poll()); //add to tail
    	}
        return res;
    }
    
    
    //951. Flip Equivalent Binary Trees
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {

    	if(root1==null && root2==null) return true;
    	if(root1==null && root2!=null) return false;
    	if(root1!=null && root2==null) return false;
        if(root1.val != root2.val) return false;
        
        return (flipEquiv( root1.left, root2.right) && flipEquiv( root1.right, root2.left))
        		||(flipEquiv( root1.right, root2.right) && flipEquiv( root1.left, root2.left));
    }
    
    //952. Largest Component Size by Common Factor
    public int largestComponentSize(int[] A) {
        
    	return 0;
    }

    
//    953. Verifying an Alien Dictionary
    public boolean isAlienSorted(String[] words, String order) {
    	if(words==null || order.length()==0) return false;
        //put everything in Order to hashmap
    	HashMap<Character,Integer> map = new HashMap<>();
    	for(int i=0;i<order.length();i++) {
    		map.put(order.charAt(i), i);
    	}
    	
    	boolean res = true;
    	
    	if(words.length<2) {
    		return true;
    	}
    	
    	aLoopName:
    	for(int i=0; i<words.length-1;i++) {
    		int len1 = words[i].length();
    		int len2 = words[i+1].length();
    		int minLen = Math.min(len1, len2);
    		for(int j=0;j<minLen;j++) {
    			char cur1 = words[i].charAt(j);
    			char cur2 = words[i+1].charAt(j);
    			if(map.get(cur1)<map.get(cur2)) {
    				res &= true; 
    				continue aLoopName;
    			}else if(map.get(cur1)>map.get(cur2)) {
    				return false;
    			}
    		}
    		//case : ["apple","app"]
    		if(len1>len2) {
    			return false;
    		}
    	}
    	return res;
    }
    
//    954. Array of Doubled Pairs
    public boolean canReorderDoubled(int[] A) {

    	List<Integer> intList = new ArrayList<Integer>();
    	int Negnum = 0;
    	for (int i : A)
    	{	if(i<0) {
	    		Negnum++;
	    		intList.add(i*-1);
    		}else {
        	    intList.add(i);
    		}
    	}
    	if(Negnum % 2 != 0) return false;
    	Collections.sort(intList);
    	
    	return canReorderDoubledRecur(intList); 
    }
    
    public boolean canReorderDoubledRecur(List<Integer> intList) {
        
    	if(intList.size() == 0) return true;
    	
    	int doublePos = containDouble( intList,  intList.get(0));
    	if(doublePos<0) return false;
    	//remove this pair
    	doublePairRemove(intList, doublePos);
    	
    	return canReorderDoubledRecur(intList); 
    }
    
    private int containDouble(List<Integer> intList, int a) {
    	if( intList.size() == 0) return -1;
    	for(int i=0;i<intList.size();i++) {
    		if(intList.get(i) == a*2) return i;
    	}
    	return -1;
    }
    
    private void doublePairRemove(List<Integer> intList, int pos) {
    	intList.remove(pos);
    	intList.remove(0);
    }
    
    //957. Prison Cells After N Days
    public int[] prisonAfterNDays(int[] cells, int N) {
        Map<String, Integer> seen = new HashMap<>();
        while(N>0) {
        	seen.put(Arrays.toString(cells), N--); //string as key
        	int[] cells2 =  new int[8];
        	for(int i=1;i<7;i++) { //ignore 2 ends
        		cells2[i] = cells[i-1]==cells[i+1] ? 1 : 0;
        	}
        	cells = cells2;
        	if(seen.containsKey(Arrays.toString(cells))) {
        		//cycle = (first occur - second occur)
        		int cycle = seen.get(Arrays.toString(cells)) - N;
        		N = N % cycle;
        	}
        }
        return cells;
    }
    
    //958. Check Completeness of a Binary Tree (level-order with BFS)
    public boolean isCompleteTree(TreeNode root) {
    	Queue<TreeNode> bfsQue = new LinkedList<>();
    	bfsQue.offer(root);
    	while(bfsQue.peek() != null) { 
    		TreeNode node = bfsQue.poll();
    		bfsQue.offer(node.left); //"null" will be added !!!
    		bfsQue.offer(node.right);
    	}
    	
    	while(!bfsQue.isEmpty() &&bfsQue.peek()==null) {
    		bfsQue.poll();
    	}
    	
    	return bfsQue.isEmpty();
    }
    
    //961. N-Repeated Element in Size 2N Array
    public int repeatedNTimes(int[] A) {
    	int len = A.length;
    	HashMap<Integer, Integer> map = new HashMap<>();
    	
    	for(int a : A){
    		if(!map.containsKey(a)) {
    			map.putIfAbsent(a,1);
    		}else {
    			map.replace(a, map.get(a)+1);
    		}
    		
    		if(map.get(a)==len/2) return a;
    	}
    	
		return 0;
    }
    
    // 962. Maximum Width Ramp (stack)
    public int maxWidthRamp(int[] A) {
    	Stack<Integer> stack = new Stack<>();
    	int max = 0;
    	int len = A.length;
    	// keep a decreasing stack
    	for(int i=0; i<len; i++) {
    		if(stack.isEmpty() || A[stack.peek()]>A[i]) stack.push(i);
    	}
    	//binary search the first smaller number in the stack.
    	for(int i=len-1; i>=0; i--) {
    		while(!stack.isEmpty() && A[stack.peek()]<=A[i]) {
    			max = Math.max(max, i-stack.pop());
    		}
    	}
    	
		return max;  
    }
    
    
    //965. Univalued Binary Tree
    HashSet<Integer> set_isUnivalTree; 
    public boolean isUnivalTree(TreeNode root) {
        if(root == null) return true;
        set_isUnivalTree = new HashSet<>();
        isUnivalTree_help(root);
        return set_isUnivalTree.size() == 1;
    }
    
    public void isUnivalTree_help(TreeNode root) {
        if(root == null) return;
        if(!set_isUnivalTree.contains(root.val)) {
        	set_isUnivalTree.add(root.val);
        }
        
        isUnivalTree_help(root.left);
        isUnivalTree_help(root.right);
    }
    
    
    //966. Vowel Spell checker
    public String[] spellchecker(String[] wordlist, String[] queries) {
    	//For each lower pattern, record the first such match to hashmap cap.
    	HashMap<String, String> cap = new HashMap<>(); 	
    	//For each vowel pattern, record the first such match to hashmap vowel.
    	HashMap<String, String> vowel = new HashMap<>();
    	//Original word list 
    	HashSet<String> words = new HashSet<>(Arrays.asList(wordlist));
    	
    	for(String word : wordlist) {
    		String lowCap = word.toLowerCase();
    		//learn: 1. "[]" for regex group; 2. use "lowCap" (not "word") to prevent duplication from caption
    		String deVowel = lowCap.replaceAll("[aeiou]", "#"); 
    		cap.putIfAbsent(lowCap, word);
    		vowel.putIfAbsent(deVowel, word);
    	}
    	
    	for(int i=0; i<queries.length; i++) {
    		if(words.contains(queries[i])) continue;
    		String lowCap = queries[i].toLowerCase();
    		String deVowel = lowCap.replaceAll("[aeiou]", "#");
    		if(cap.containsKey(lowCap)) {
    			queries[i] = cap.get(lowCap);
    		}else if(vowel.containsKey(deVowel)) {
    			queries[i] = vowel.get(deVowel);
    		}else{ // no match 
    			queries[i] = "";
    		}
    	}
    	
    	return queries;
    }
    
    
    
    //967. Numbers With Same Consecutive Differences
    public int[] numsSameConsecDiff(int N, int K) {
        List<Integer> lst = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        
        for(int i=1;i<N;i++) {
            ArrayList<Integer> lst2 = new ArrayList<>(); //take care of lst.remove()
            
            for(int num : lst) {
            	int lastDigit = num % 10;
            	if(num>0 && lastDigit + K <10) { //take care of 010 (smart); 181
            		lst2.add(num*10 + lastDigit + K);
            	}
            	//'if' may process 2 digits at a iteration
            	if(num>0 && lastDigit - K >=0 && K>0) { //take care of 818; 2 0 {11, 11, 22, 22}
            		lst2.add(num*10 + lastDigit - K);
            	}
            }
            
        	lst = lst2;
        }
        return lst.stream().mapToInt(i->i).toArray(); //pipe line with steam 
    }
            
//          int indx = 0;
//        	while(indx<lst.size()) {
//        		
//        		if(lst.get(indx)%Math.pow(10, i-1)+K <= 9) { //181
//        			lst.set(indx, (int) (lst.get(indx)*10+(lst.get(indx)%Math.pow(10, i-1)+K)));
//            		indx++;
//        		}else if(lst.get(indx)%Math.pow(10, i-1)-K >= 0) { //707
//        			lst.set(indx, (int) (lst.get(indx)*10+(lst.get(indx)%Math.pow(10, i-1)-K)));
//            		indx++;
//        		}else {
//        			lst.remove(indx);
//        		}
//        	}
//        }
        
//        return convertIntegers(lst); 
//    }
    
//    public static int[] convertIntegers(List<Integer> integers)
//    {
//        int[] ret = new int[integers.size()];
//        for (int i=0; i < ret.length; i++)
//        {
//            ret[i] = integers.get(i).intValue();
//        }
//        return ret;
//    }
    
    //969. Pancake Sorting
    //Java flip the largest number to the tail
    //note: A[i] is a permutation of [1, 2, ..., A.length]
    public List<Integer> pancakeSort(int[] A) {
    	//Find the largest number
    	//Flip twice to the tail
    	int targetNum = A.length;
    	List<Integer> res = new ArrayList<>();
     	for(int i=0;i<A.length;i++) {
    		int indx = findTargetPos(A,targetNum);
    		flip(A,indx);
    		flip(A,targetNum-1);
    		res.add(indx+1);
    		res.add(targetNum--);
    	}
		return res;
        
    }
    
    private int findTargetPos(int[] A, int targetNum) {
    	for(int i=0;i<A.length;i++) {
    		if(A[i]==targetNum) return i;
    	}
		return -1;
    	
    }
    
	private void flip(int[] A,int indx){
		int i=0, j=indx;
		while(i<j) {
			int temp = A[i];
			A[i++] = A[j];
			A[j--] = temp;
		}
	}
	
	
    //970. Powerful Integers
    public List<Integer> powerfulIntegers(int x, int y, int bound) {
    	Set<Integer> res = new HashSet<>();
        int loopBoundx = x==0 ? x+1 : x;
        int loopBoundy = y==0 ? y+1 : y;
    	for(int i=0; i<=bound/loopBoundx;i++) {
    		for(int j=0; j<=bound/loopBoundy;j++) {
    			int cur = (int) (Math.pow(x, i)+Math.pow(y, j));
                if(y==0 && j>2) break; 
    			if(cur>bound) break;
    			else {
    				res.add(cur);
    			}
    		}
            if(x==0 && i>2) break; 
    		if(Math.pow(x, i)>bound) break;
    	}
    	
    	List<Integer> resLst = new ArrayList<>(res);
		return resLst;
        
    }
    
    //971. Flip Binary Tree To Match Preorder Traversal (DFS traverse; class scope local variable)
	int flipMatchVoyage_indx = 0;
	List<Integer> flipMatchVoyage_res = new ArrayList<>();
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
		return flipMatchVoyage_dfs( root, voyage) ? flipMatchVoyage_res : Arrays.asList(-1);
    }
    
    private boolean flipMatchVoyage_dfs(TreeNode root, int[] voyage) {
    	if(root==null) return true;
    	if(root.val != voyage[flipMatchVoyage_indx++]) return false;
    	//rotate
    	if(root.left!=null && root.left.val!=voyage[flipMatchVoyage_indx]) {
    		flipMatchVoyage_res.add(root.val);
    		return flipMatchVoyage_dfs( root.right, voyage)&&flipMatchVoyage_dfs( root.left, voyage);
    	}
    	return flipMatchVoyage_dfs( root.left, voyage)&&flipMatchVoyage_dfs( root.right, voyage);
    }
    
    
    //984. String Without AAA or BBB (Greedy)
    public String strWithout3a3b(int A, int B) {
    	StringBuilder sb = new StringBuilder();
    	while(A+B>0) {
    		int len = sb.length();
    		//3 letters case
    		if(len>1 && sb.charAt(len-1)==sb.charAt(len-2)) {
    			if(sb.charAt(len-1)=='a') {
    				sb.append('b');
    				B--;
    			}else {
    				sb.append('a');
    				A--;
    			}
    		}else { // general case (greedy)
    			if(A>B) {
    				sb.append('a');
    				A--;
    			}else {
    				sb.append('b');
    				B--;
    			}
    		}
    	}
    	return sb.toString();
    }
    
    //981. Time Based Key-Value Store
    class TimeMap {

    	class ValueTimePair{
    		public String value;
    		public int time;
    		public void seVal(String value) {
                this.value = value;
            }
    		public void setTime(int timestamp) {
                this.time = timestamp;
            }
    		
    	}
    	
    	HashMap<String, LinkedList<ValueTimePair>> map; 
        /** Initialize your data structure here. */
        public TimeMap() {
        	map = new HashMap<>();
        }
        
        public void set(String key, String value, int timestamp) {
            if(!map.containsKey(key)) {
            	ValueTimePair pair = new ValueTimePair();
            	pair.seVal(value);
            	pair.setTime(timestamp);
            	LinkedList<ValueTimePair> lst = new LinkedList();
            	lst.add(pair);
            	map.put(key, lst);
            }else {
            	ValueTimePair pair = new ValueTimePair();
            	pair.seVal(value);
            	pair.setTime(timestamp);
            	LinkedList<ValueTimePair> lst = map.get(key);
            	lst.addFirst(pair);
            	map.put(key, lst);
            }
        }
        
        public String get(String key, int timestamp) {
        	if(!map.containsKey(key)) {
        		return "";
        	}else {
        		LinkedList<ValueTimePair> lst = map.get(key);
        		for(ValueTimePair pair : lst) {
        			int timestamp_prev = pair.time;
        			if(timestamp_prev > timestamp) {
        				continue;
        			}else { // <=
        				return pair.value;
        			}
        		}
        	}

			return ""; //prev time is too large
        }
    }
    
    //983. Minimum Cost For Tickets (Dynamic Programming)
    //Let minCost(i) denote the minimum cost that will be payed for all trips on days 1 to day 365.
    public int mincostTickets(int[] days, int[] costs) {
    	boolean[] dayIncluded = new boolean[366]; //minCost(i)=0 for all i ≤ 0. start from 1
    	for(int day : days) {
    		dayIncluded[day] = true;
    	}
    	//DP 
    	int[] minCost = new int[366];
    	minCost[0] = 0;
    	for(int day=1;day<366;day++) {
    		if(!dayIncluded[day]) {
    			minCost[day] = minCost[day-1];
    		}else {
    			//one day pass
    			int min = minCost[day-1]+costs[0];
    			//7 days pass
    			min = Math.min(min, minCost[Math.max(0, day-7)]+costs[1]);
    			//30 days pass
    			min = Math.min(min, minCost[Math.max(0, day-30)]+costs[2]);
    			minCost[day] = min;
    		}	
    	}
		return minCost[365];
    }
    
    //985. Sum of Even Numbers After Queries
    // My way: O(lenA x lenQ)
    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int lenQ = queries.length;
        int lenA = A.length;
        int[] result = new int[lenQ]; 
        if(lenA==0) return result;
        
        for(int i=0; i<lenQ; i++){
            int idx = queries[i][1];
            A[idx] += queries[i][0];
            int curSum = 0;
            for(int j=0; j<lenA;j++){
                if(A[j]%2==0){
                    curSum += A[j];
                }
            }
            result[i]=curSum;
        }
        return result;
    }
    
    //  odd / even analysis, time O(n)
    public int[] sumEvenAfterQueries1(int[] A, int[][] queries) {
        int lenQ = queries.length;
        int lenA = A.length;
        int[] result = new int[lenQ]; 
        if(lenA==0) return result;
        int sum =0;
        for (int a : A) { if (a % 2 == 0) sum += a ; } // sum of even #s.
        for(int i=0; i<lenQ; i++){
            int idx = queries[i][1];
            if(A[idx] %2==0 ) sum -= A[idx];
            A[idx] += queries[i][0];
            if(A[idx] %2==0 ) sum += A[idx];
            result[i] = sum;
        }
        return result;
    }
    
    //986. Interval List Intersections （two pointer）    
    public Interval[] intervalIntersection(Interval[] A, Interval[] B) {
        List<Interval> lst = new ArrayList<>();
        
        int a=0, b=0;
        while(a<A.length && b<B.length) {
        	if(A[a].end < B[b].start) {
        		a++;
        	}else if(A[a].start > B[b].end) {
        		b++;
        	}else { //intersect
        		int xStart = Math.max(A[a].start, B[b].start);
        		int xEnd = Math.min(A[a].end, B[b].end);
        		lst.add(new Interval(xStart,xEnd)); //add intersection
        		//update pointer
        		if(A[a].end < B[b].end) {
        			a++;
        		}else {
        			b++;
        		}
        	}
        }
        
        int len = lst.size();
        Interval[] res = new Interval[len];
        for(int i=0; i<len; i++) {
        	res[i] = lst.get(i);
        }
    	return res;
    }
    

      //Definition for an interval.
      public class Interval {
          int start;
          int end;
          Interval() { start = 0; end = 0; }
          Interval(int s, int e) { start = s; end = e; }
     }

    
    //988. Smallest String Starting From Leaf (dfs)
    //http://www.noteanddata.com/leetcode-988-Smallest-String-Starting-From-Leaf-java-solution-note.html
    //对于bottom up 遍历， 通常我们需要做递归的post order从下向上遍历，
    //区别只在于子节点上的路径， 所以，对于任意一个节点，都从子节点中取最小值
    public String smallestFromLeaf(TreeNode root) {
        return dfs_smallestFromLeaf(root);
    }
    
    private String dfs_smallestFromLeaf(TreeNode root) {
    	if(root == null) return null;
    	
    	//post order traversal
    	String left = dfs_smallestFromLeaf( root.left); 
    	String right = dfs_smallestFromLeaf( root.right); 
    	char curC = (char) ('a'+root.val);
    	if(left==null && right==null) { //leaf
    		return "" + curC;
    	}else if(left==null || right==null) {
    		return left==null ? right+curC : left+curC;
    	}else { // both are not null
    		return left.compareTo(right)<0 ? left+curC : right+curC;
    	}
    }
    
    // ======= 123 ========
    
    //989. Add to Array-Form of Integer (Math)
    public List<Integer> addToArrayForm(int[] A, int K) {
        List<Integer> res = new ArrayList<>();
        
        //Take K as a carry. update carry K
        for (int i = A.length - 1; i >= 0; --i) {
        	int cur = (A[i] + K) % 10;
        	res.add(0,cur);		// this is new to me
        	K = (A[i] + K) / 10; // this is very smart
        }
        
        while(K>0) {
        	res.add(0, K%10);
        	K /= 10;
        }
		return res;
    }
    
    //990. Satisfiability of Equality Equations (Union Find / Disjoint Set： 并差集y  )
    // for Union Find explanation and more questions: https://www.youtube.com/watch?v=VJnUwsE4fWA

    
    //991. Broken Calculator (BFS --> Math)
    //My method from X to Y is beyond time limit 
    //consider how to change from Y to X
    //If Y is even, Y = Y / 2;  (because (Y + 1 + 1) / 2 = (Y / 2) + 1, 3 operations are more than 2.)
    //If Y is odd, Y = (Y + 1) / 2
    public int brokenCalc(int X, int Y) {
    	int steps = 0;
    	while(Y>X) {
    		if(Y%2 == 0) { //even
    			Y /= 2;
    		}else { //odd
    			Y = Y + 1;
    		}
    		steps++;
    	}
    	
    	//now Y is less than X
    	return steps + (X - Y);
    }
    
    
    
    
    
    
    
    
}

