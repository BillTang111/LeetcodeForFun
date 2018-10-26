package leetcode;

import java.util.*;

class LRUCache {
	int Total_cap;
	HashMap<Integer, Integer> storage;
	Queue<Integer> LRU_Que;

	public LRUCache(int capacity) {
		Total_cap = capacity;
		storage = new HashMap<>();
		LRU_Que = new LinkedList<>();
	}

	// Get the value (will always be positive) of the key if the key exists in the
	// cache, otherwise return -1
	public int get(int key) {
		// update LRU time stamp && test
		if (!storage.containsKey(key)) {
			return -1;
		}
		// add to most recent visit
		LRU_Que.remove(key);
		LRU_Que.add(key);
		return storage.get(key);
	}

	public void put(int key, int value) {
		if (storage.containsKey(key)) {
			LRU_Que.remove(key);
			LRU_Que.add(key);
			storage.put(key, value);
		}else {
			// less than cap
			if (storage.size() + 1 <= Total_cap) {
				LRU_Que.remove(key);
				LRU_Que.add(key);
				storage.put(key, value);
			} else {
				// over cap -> remove
				int mostRUkey = LRU_Que.remove();
				storage.remove(mostRUkey);
				// now add
				LRU_Que.add(key);
				storage.put(key, value);
			}
		}
	}

}
