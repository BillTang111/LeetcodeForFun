package leetcode;

public class SearchAndSort {
	
    /** = index of x in b ---or the length of b if x is not in b. */
    public static int linearSearch(int[] b, int x) {
    	int i;
    	for(i=0; i<b.length; i++) {
    		if(b[i]==x) return i;
    	}
        return i;
    }

    /** Assume virtual elements b[-1] = -infinity and b.[b.length] = +infinity.<br>
       Return a value i that satisfies R: b[i] <= x < b[i+1] 
       b is sorted*/
    public static int binarySearch(int[] b, int x) {
    	int i=-1, t=b.length;
    	while(i != t-1) {
    		int mid = (i+t)/2;
    		//h<mid<t
    		if(b[mid]<=x) {
    			i=mid;
    		}else {
    			t=mid;
    		}
    	}
        return i;
    }
    
    /** Sort b --put its elements in ascending order. O(n^2)*/
    //	{{{select and swap the smallest to the front each time}}}
    public static void selectionSort(int[] b) {
        // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}, i.e.
        //          0---------------j--------------- b.length
        // inv : b |  sorted, <=   |    >=          |
        //          --------------------------------
        for(int i=0; i<b.length; i++) {
        	int minIdx = min(b,i,b.length-1);
        	//swap
        	swap(b, minIdx, i);
        }
    }

    /** Return the position of the minimum value of b[h..k].<br>
        Precondition: h < k. */
    public static int min(int[] b, int h, int k) {
        int minIdx= h; // will contain index of minimum
        int i= h;
        // {inv: b[minIdx] is the minimum of b[h..i]}, i.e.
        //
        //    h-------minIdx------------i---------k
        // b | b[minIdx] is min of this  |    ?    |
        //    --------------------------------
        while(i<=k) {
        	if(b[i]<b[minIdx]) {
        		minIdx=i;
        	}
        	i++;
        }
        return minIdx;
    }
    
    /** Sort b[h..k] --put its elements in ascending order. O(n^2)*/
    //	{{{ add ('insert') a number to the sorted part and sort them each time}}}
    public static void insertionSort(int[] b, int h, int k) {
        // inv: h <= j <= k+1  and  b[h..j-1] is sorted
        //          h---------------j-------------k
        // inv : b |  sorted,      |     ?         |
        //          -------------------------------
    	for(int j=h; j<k+1;j++) {
    		//insert j into b[h..j-1]
    		insertValue(b, h, j);
    	}
    }
    
    /** Precondition: b[h..k-1] is sorted, and b[k] contains a value.<br>
    Sort b[h..k] */
	public static void insertValue(int[] b, int h, int k) {
		while(k>h && b[k]<b[k-1]) {
			//swap
			swap(b, k, k-1);
			k--;
		} 
	}
	
    /** Sort b[h..k] using partitioning O(n^2)  */ 
	//O(n*log n) on average; fewer swap and comparisons
	//method: partitioning (pivot)
    public static void quickSort(int[] b, int h, int k) {
    	//base case (only 1 element)
    	if(k-h+1<2) return;
    	//sort current array
    	int j = partition(b, h, k);
    	//divide by recursion
    	quickSort(b, h, j-1);
    	quickSort(b, j+1, k);
    }
    
    /** b[h..k] has at least two elements.<br>
    Let x be the value initially in b[h].<br>
    Permute b[h..k] and return integer j satisfying R:<br><br>
  	b[h..j-1] <= b[j] = x <= b[j+1..k] (j is the pivot)
     */
	public static int partition(int[] b, int h, int k) {
		int j = h; int t = k;
		while(j<t) {
			if(b[j+1]<=b[j]) {
				swap(b,j+1,j); j++;
			}else {
				swap(b,j+1,t); t--;
			}
		}
	    return j;
	}
    
	//swap function
	public static void swap(int[] b, int i, int j) {
    	int temp = b[j];
    	b[j]=b[i];
    	b[i]=temp;
	}

    /** Return the values of b, separated by ", " and with
    the whole list delimited by "[" and "]". */
  public static String toString(int[] b) {
	  String res = "[";
	  for(int i=0; i<b.length;i++) {
		  res=res+b[i];
		  if(i!=b.length-1) {
			  res=res+", ";
		  }
	  }
      return res+"]";
  }
    
  //================================================
  /** Sort b[h..k]. O(nlogn)*/
  //method: divide and conquer
  public static void mergeSort(int[] b, int h, int k) {
	  //base case
	  if(k-h+1<2) return;
	  //divide by recursion
	  int t = (h+k)/2;
	  mergeSort(b, h, t);
	  mergeSort(b, t+1, k);
	  //conquer
	  merge(b,h,t,k);
  }
    
  /** Segments b[h..e] and b[e+1..k] are already sorted.<br>
  Permute their values so that b[h..k] is sorted.
  Note: two sub-parts are pre-sorted due to recursion returns
   */
	public static void merge (int b[], int h, int e, int k) {
		//loop to insert b[e+1..k]
		for(int i=e+1; i<k+1; i++) {
			//insertion sort 
			int pos=i;
			while(pos>0 && b[pos]<b[pos-1]) {
				swap(b,pos,pos-1);
				pos--;
			} 
		}
	}
    
    //=================================
	//QuickSort Version 2: Middle element as pivot
	//much clearer 
    public static void quickSort2(int[] b, int h, int k) {
    	//base case (only 1 element)
    	if(k-h+1<2) return;
    	//sort current array
    	int j = partition2(b, h, k);
    	//divide by recursion
    	quickSort2(b, h, j-1);
    	quickSort2(b, j+1, k);
    }
	
    //make elements value between [0, leftBound] are all < pivot
    // pivot ... left/small ... right/big ...
    private static int partition2(int[] array, int left, int right){
        int pivot = array[left];
        int l = left + 1;
        int r = right;
        while(l<= r) {
        	if(array[l] > pivot && array[r] < pivot) swap(array, l++, r++);
            if(array[l] <= pivot) l++;
            if(array[r] >= pivot) r--;
        }
        swap(array, left, r);
        return r;
    }
    
    
//    private static int partition2(int[] array, int left, int right){
//        int pivotIndex = left + (right - left)/2;
//        int pivot = array[pivotIndex];
//        swap(array, pivotIndex, right);
//
//        int leftBound = left;
//        int rightBound = right - 1;
//        while(leftBound <= rightBound){
//            if(array[leftBound] <= pivot){
//                leftBound++;
//            }else if(array[rightBound] >= pivot){
//                rightBound--;
//            }else{ // array[leftBound] > pivot || array[rightBound] < pivot
//                swap(array, leftBound++, rightBound--);
//            }
//        }
//        swap(array, leftBound, right);
//        return leftBound;
//    }
}
