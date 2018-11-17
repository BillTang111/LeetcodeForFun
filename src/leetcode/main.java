package leetcode;
import java.util.*;

public class main {
	
    public static void print2D(int mat[][]){
        // Loop through all rows
        for (int[] row : mat) {
 
            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));
        }
    }
    
	public static void main(String[] args) {

		BitTiger000 pt = new BitTiger000();
		BitTiger100 bt2 = new BitTiger100();

//		int[] candidates = new int[] {2,5,2,1,2};
//		int target = 5;
//		System.out.println(pt.combinationSum2(candidates, target));
		
//		int[] candidates = new int[] {2,1,1};
//		System.out.println(pt.permuteUnique(candidates));
		
//		String ss = "aaab";
//		System.out.println(pt.partition(ss) );
		
//		int[] candidates = new int[] {3,4,-1,1};
//		System.out.println(pt.firstMissingPositive(candidates) );
		
//		System.out.println(pt.multiply("123", "456"));
		
//        int eggs = 3;
//        int floors = 100;
//        System.out.printf("(DP) Minimum number of drops required in worst case with eggs: %s and floors: %s is : %s",eggs,floors,pt.leastDrop(eggs,floors));
		
//		int[][] candidates = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
//		System.out.println(pt.spiralOrder(candidates));
		
//		String word1 = "horse", word2 = "ros";
//		int res = pt.minDistance(word1, word2);
//		System.out.println(res);
		
//    	int[][] dp = new int[3][3]; //0....n or m 
//    	print2D(dp);
    	
//        int[] map = new int[256];
//        map['c'] ++;
//        int a = 'c';
//        System.out.println(a);
        
//		String word1 = "ADOBECODEBANC", word2 = "ABC";
//		String res = pt.minWindow(word1, word2);
//		System.out.println(res);
		
//		int[] input = new int[]{1,2,3};
//		int res = pt.largestRectangleArea(input);
//		System.out.println(res);
		
//		String s = "102";
//		int res = pt.numDecodings(s);
//		System.out.println(res);
		
		int[] g = new int[]{1,2,3,4,5};
		int[] c = new int[]{3,4,5,1,2};
		bt2.canCompleteCircuit(g,c);


	}
}
