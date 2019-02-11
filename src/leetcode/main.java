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
		
		int[] x = { 9, 2, 4, 7, 3, 7, 10 };
		int[] xb = { 9, 2, 4, 7, 3, 7, 10 };
		
		int[] expect = {2, 3, 4, 7, 7, 9, 10};
		int low = 0;
		int high = x.length - 1;

		int a = partition(x, low, high);
		System.out.println("this is the pivot for my: "+ a);
		System.out.println(Arrays.toString(x));
		
		int b = partition2(xb, low, high);
		System.out.println("this is the pivot for b: "+ b);
		System.out.println(Arrays.toString(xb));
	}
	
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
	
	static int partition2(int[] array, int left, int right){
        int pivotIndex = left + (right - left)/2;
        int pivot = array[pivotIndex];
        swap(array, pivotIndex, right);

        int leftBound = left;
        int rightBound = right - 1;
        while(leftBound <= rightBound){
            if(array[leftBound] < pivot){
                leftBound++;
            }else if(array[rightBound] >= pivot){
                rightBound--;
            }else{
                swap(array, leftBound++, rightBound--);
            }
        }
        swap(array, leftBound, right);
        return leftBound;
    }
	
	
	
    static void swap(int[] array, int left, int right){
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }
    
    
}
