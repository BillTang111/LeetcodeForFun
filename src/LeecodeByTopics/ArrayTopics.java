package LeecodeByTopics;

import java.util.Arrays;

public class ArrayTopics {
	
	/**
	 * 832. Flipping an Image
	 * @param A
	 * @return
	 */
    public int[][] flipAndInvertImage(int[][] A) {
    	//First reverse each row,Then, invert the image
    	for(int[] a : A) {
        	int l=0, r=a.length-1;
        	while(l<=r) {
        		//First reverse each row
        		int temp=a[l];
        		a[l]=a[r];
        		a[r]=temp;
            	//Then, invert the image       		
        		a[l]=1-a[l];
        		if(l!=r) a[r]=1-a[r]; //double counting
        		l++; r--;
        	}
    	}
		return A; 
    }
    
    /**
     * 867. Transpose Matrix
     */
    public static int[][] transpose(int[][] A) {
    	int col_len=A.length;
    	int row_len=A[0].length;
    	int[][] res = new int[row_len][col_len];
    	for(int i=0;i<col_len;i++) {
    		for(int j=0;j<row_len;j++) {
    			res[j][i]=A[i][j];
    		}
    	}
        return res;
    }
    
    /**
     *766. Toeplitz Matrix
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        if(matrix == null){ //save over 10s 
            return false;
        }
    	for(int i=0;i<matrix.length;i++) {
    		for(int j=0;j<matrix[0].length;j++) {
    			if(i>0 && j>0 && matrix[i][j]!=matrix[i-1][j-1]) return false;
    		}
    	}
        return true;
    }
    
    /**
     * 566. Reshape the Matrix
     */
    public static int[][] matrixReshape(int[][] nums, int r, int c) {
    	//base case
    	if(nums==null) return nums;    	
    	int R=nums.length, C=nums[0].length;
    	//unable to reshape
    	if(R*C!=r*c) return nums;
    	
    	//reshape
    	int c_temp=0, r_temp=0;
    	int[][] res = new int[r][c];
    	for(int i=0; i<R; i++) {
    		for(int j=0;j<C;j++) {
    			res[r_temp][c_temp] = nums[i][j];
    			System.out.println(ArrayTopics.print2D(res));
    			c_temp++; 
    			if(c_temp>=c) {
    				c_temp=0; r_temp++;
    			}
    			if(r_temp>=r) r_temp=0;
    		}
    	}
		return res;  
    }
    
    //reverse elements in array
    public static int[] reverse(int[] a) {
    	int l=0, r=a.length-1;
    	while(l<r) {
    		int temp=a[l];
    		a[l]=a[r];
    		a[r]=temp;
    		l++; r--;
    	}
		return a;
    }
	
	public static String printArr(int[] a) {
		String res = "[";
		for(int i=0;i<a.length;i++) {
			if(i!=0) res += ",";
			res+= a[i];
		}
		return res + "]";
	}
	
    public static String print2D(int mat[][]){
        // Loop through all rows
		String res = "[";
        for (int[] a : mat) {
    		res += "[";
    		for(int i=0;i<a.length;i++) {
    			if(i!=0) res += ",";
    			res+= a[i];
    		}
    		res += "]";
			res += ",";
        }
        res = res.substring(0, res.length()-1);
		res += "]";
        return res;
    }
}

