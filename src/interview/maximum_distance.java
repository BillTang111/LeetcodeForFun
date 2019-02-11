package interview;

import java.util.*;

public class maximum_distance {
	public static int maxDifference(int n,int[] from, int[] to) {
		if (n <= 1 || from == null || from.length == 0 ||to == null || to.length == 0) return 0;
		unionFind uf = new unionFind(n);
		for(int i=0; i<from.length; i++){
			uf.union(from[i], to[i]);
		}
		return uf.maxCount == Integer.MIN_VALUE ? 0 : uf.maxCount;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] from = new int[]{1,1,2,2,3,4};
		int[] to = new int[]{2,3,3,4,4,5};
		System.out.println(maximum_distance.maxDifference(5, from, to));
	}

}

class unionFind {
	int[] father;
	HashMap<Integer, int[]> map = new HashMap<>(); //store min and max
	int maxCount = Integer.MIN_VALUE;
	
	unionFind(int n) {
		father = new int[n+1];
		for(int i=0; i<n+1; i++){
			father[i] = i;
//			ArrayList<Integer> list = new ArrayList<>();
//			list.add(i);
//			list.add(i);
			int[] list = new int[2];
			list[0]=i; list[1] =i;
			map.put(i, list);
		}
		
	}
	
	public void union(int node1, int node2) {
		int find1 = find(node1);
		int find2 = find(node2);
		if(find1 != find2) {
			father[find1] = find2;
			int[] list1 = map.get(find1);
			int[] list2 = map.get(find2);
			int min = Math.min(list1[0], list2[0]);
			int max = Math.max(list1[1], list2[1]);
			list2[0]= min;
			list2[1]= max;
			maxCount = Math.max(maxCount, max-min);
		}
	}
	
	public int find(int node) {
		
		if(father[node] == node) {
			return node;
		}
		father[node] = find(father[node]);
		return father[node];
	}
}
