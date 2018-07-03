package leetcode;
import java.util.*;

public class main {
	public static void main(String[] args) {

		BitTigerTop100 pt = new BitTigerTop100();

//		int[] candidates = new int[] {2,5,2,1,2};
//		int target = 5;
//		System.out.println(pt.combinationSum2(candidates, target));
		
//		int[] candidates = new int[] {2,1,1};
//		System.out.println(pt.permuteUnique(candidates));
		
//		String ss = "aaab";
//		System.out.println(pt.partition(ss) );
		
		int[] candidates = new int[] {0,1,1,2,2};
		System.out.println(pt.firstMissingPositive(candidates) );
	}
}
