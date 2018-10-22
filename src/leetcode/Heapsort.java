package leetcode;

/**
 * Contains static method sort to sort an int array using heapsort. Also
 * contains static methods to manipulate int max-heaps in an array. They are
 * public, for testing purposes.
 */

public class Heapsort {

	/**
	 * Sort b. (nlogn) similar to selection sort 1. build a max-heap b' from b /n
	 * 2. poll the max at root of max-heap b' -> sort b array 
	 */
	public static void heapsort(int[] b) {
		// Build b into a max-heap
		// invariant: b[0..k-1] is a max-heap AND
		// b is a permutation of its initial value
		for (int i = 0; i < b.length; i++) {
			// add(int v, int[] b, int n)
			add(b[i], b, i);
		}

		// Sort the max-heap
		// invariant: b[0..k-1] is a max-heap AND
		// b[0..k-1] <= b[k..] AND
		// b[k..] is sorted AND
		// b is a permutation of its initial value
		for (int j = b.length; j > 0; j--) {
			int temp =b[0]; //current max
			poll(b, j); //j=# of element in heap
			b[j-1]=temp;
		}
	}

	/**
	 * Max-heap invariant for b[0..n-1]: b[0..n-1] is viewed as a max-heap, i.e.
	 * 1.All elements b[0..n-1] are filled with a value 2. the children of b[i] are
	 * b[2i+1] and b[2i+2] 3. the parent of b[i] is b[(i-1)/2] 4. the children of a
	 * node b[i] are <= b[i]
	 */

	/**
	 * Add v to max-heap b[0..n-1] and return the new size. Precondition: b[0..n-1]
	 * is a max-heap.
	 * n = last elements in heap
	 */
	public static int add(int v, int[] b, int n) {
		b[n] = v;
		bubbleUp(b, n);
		return n + 1;
	}

	/**
	 * Max-Heapify b[0..n] by bubbling b[n] up. Precondition: b[0..n-1] is a
	 * max-heap
	 * n = last elements in heap
	 */
	public static void bubbleUp(int[] b, int n) {

		while (n > 0 && b[n] > b[(n - 1) / 2]) { // only need child pos > 0
			swap(b, n, (n - 1) / 2);
			n = (n - 1) / 2;
		}
	}

	/**
	 * Remove the maximum element, modify heap and return the size of the heap. Precondition:
	 * b[0..n-1] is a max-heap. If n = 0, throw IllegalArgumentException.
	 * n = last element in heap
	 */
	public static int poll(int b[], int n) {
		if(n==0) {
			throw new IllegalArgumentException();
		}
		n=n-1; //Remove the maximum element
		b[0]=b[n]; 
		bubbleDown(b, n);
		return n;
	}

    /** Max-Heapify b[0..n-1] by bubble b[0] down to its heap position.!
     * Precondition: b[0..n-1] is a max-heap except that the root
     * may have a larger child 
     * n = # of elements in heap*/
    public static void bubbleDown(int[] b, int n) {
        // Set c to larger of b[p]'s children
    	int p=0;
    	int c= 2*p+2;
    	if (c>n-1 || b[c-1] > b[c]) c= c-1;
    	
        // inv; tree is a max-heap except: b[k] may have a larger a child.
        //        Also b[p]'s largest child is b[c]
    	while(c<n && b[p]<b[c] ) {
    		swap(b,p,c);
            // Set c to larger of b[p]'s children
    		p=c; c= 2*p+2;
        	if (c>n-1 || b[c-1] > b[c]) {
        		c= c-1; //Smart: checked by while loop
        	}
    	}
    }
    
	// swap function
	public static void swap(int[] b, int i, int j) {
		int temp = b[j];
		b[j] = b[i];
		b[i] = temp;
	}

}
