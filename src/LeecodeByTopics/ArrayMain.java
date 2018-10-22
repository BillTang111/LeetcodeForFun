package LeecodeByTopics;

public class ArrayMain {
	
	public static void main(String[] args) {
		int[] b= {3, 8, 5, -2};
		ArrayTopics.reverse(b);
		
		int[][] multi = new int[][]{{1,2,3,4}};
		int[][] a = ArrayTopics.matrixReshape(multi, 2, 2);
		System.out.println(ArrayTopics.print2D(a));
		
		
	}
}
