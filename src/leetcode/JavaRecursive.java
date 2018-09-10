package leetcode;
import java.util.*;

/*** 
 * This class is used to Re-implement various methods(search, sort, ...) learnt in CS2110
 */
public class JavaRecursive {
	
	// --- Recursive Development ------------------------
	/** = sum of  digits in the decimal representation of n.
    e.g. sum(0) = 0, sum(3) = 3
    sum(34) = 7. 
    sum(1356) = 15.     6  + sum of the digits in 135
    Precondition: n >= 0. */
	public int sumDigs(int n) {
		//base
		if(n<10) {
			return n;
		}
		//recursive
		return n%10+sumDigs(n/10);
		  
	  }
	
	  /** = the length of s --without using function s.length. */
	  public static int len(String s) {
		  int n = s.length();
		  //base (0 or 1)
		  if(n<=1) return n;
		  //recursive (inv: length>=2)
		  return 1+len(s.substring(1));
	  }
	
	  /** = number of 'e's in s */
	  public static int countEm(String s)  {
		  //base
		  if(s.length()==0) {
			  return 0;
		  }
		  //recursive
		  if(s.charAt(0)=='e') {
			  return countEm(s.substring(1))+1;
		  }
		  return countEm(s.substring(1));
	  }
	
	  /** = s with every char duplicated. */
	  public static String dup(String s) {
		  //base
		  if(s.length()==0) return "";
		  //recursive
		  return ""+s.charAt(0)+s.charAt(0)+dup(s.substring(1));
	  }
	
	  /** = "s is a palindrome" --reads the same backward and forward. */
	  public static boolean isPal(String s) {
		  //base (abba  abcba)
		  if(s.length()<=1) return true;
		  //recursive
		  int n = s.length();
		  return s.charAt(0)==s.charAt(n-1) && isPal(s.substring(1, n-1));
	  }
	
	  /** = the permutations of s.
	    e.g. the permutations of "abc" are 
	    "abc", "acb", "bac", "bca", "cab", "cba"
	    Precondition: the chars of s are all different.*/
	  public static ArrayList<String> perms(String s) {
		  ArrayList<String> res = new ArrayList<String>();
		  //base
		  if(s.length()==0) {
			  res.add(s); // base case - the only perm of "" is ""
			  return res;
		  }
		  // loop over all possible first characters
		  // inv: result contains all perms beginning with characters in s[0..i]
		  for(int i=0; i<s.length(); i++) {
			  	char first=s.charAt(i);
			    // append each permutation of the rest of the characters (divide and conquer)
			  	ArrayList<String> rest = perms(s.substring(0, i)+s.substring(i+1));
			  	//loop to add each of the rest to head
			  	for(String str: rest) {
			  		res.add("" + first + str);
			  	}
		  }
		  return res;
	  }
	  
	    /** Return the shortest substring x of s such that s = x + x + ⋯ + x.
	     * Examples: For s = "" return ""
	     *           For s = "xxxxxxxxx" return "x"
	     *           For s = "xyxyxyxy" return "xy"
	     *           For s = "hellohellohello" return "hello"
	     *           For s = "hellohelloworld" return "hellohelloworld"
	     *           For s = "hellohel" return "hellohel"
	     */
	    public static String deduplicate(String s) {
	    	//loop to try 0...i substring
	    	for(int i=1; i<s.length(); i++) {
	    		String sub=s.substring(0, i);
	    		if(subIsPattern(s, sub)) return sub;
	    	}
	        return s;
	    }
	    //help function
	    private static boolean subIsPattern(String s, String sub) {
	    	int sub_len = sub.length();
	    	//if length is over
	    	if(s.length()<sub_len) return false;
	    	//if not a pattern
	    	if(!(sub.equals(s.substring(0, sub_len)))) {
	    		return false;
	    	}
	    	return s.length() == sub_len || subIsPattern(s.substring(sub_len), sub);
	    }
}
