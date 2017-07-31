/* LRUCache class uses DoublyLL and Cache classes to provide:
 * 	i. LRU cache apis of get and put
 * 	
*/

import java.io.Serializable;

public class LRUCache<K, V> implements Serializable {
	private static final long serialVersionUID = 4L;
	private int sizeLimit; // Size limit of the LRUCache(Maximum size)
	private DoublyLL<K, V> list; // Doubly linked list to maintain LRU and MRU
	private Cache<K, V> cache; // Maps key vs node location in Doubly linked list
	
	public LRUCache(int sizeLimit) {
		this.sizeLimit = sizeLimit;
		cache = new Cache<K, V>();
		list = new DoublyLL<K, V> ();
	}
	
	public DoublyLL<K, V> getList() {
		return list;
	}
	
	public Cache<K, V> getCache() {
		return cache;
	}
	
	/* Returns the value associated with the key in the LRU cache */
	public synchronized V get(K key) {
		if(cache.isPresent(key)) { // Key present in cache
			Node<K, V> node = cache.getNode(key);
			V retVal = node.getVal();
			list.delete(node); // Delete from list
			cache.delete(key); // Delete from cache
			node = new Node<K, V>(key, retVal);
			list.insert(node); // Insert into list as the Most recently used
			cache.insert(key, node); // Update the cache
			return retVal;
		}
		return null; // Key not present in the cache
	}
	
	/* Inserts the key, value as most recently used element in the LRU cache */
	public synchronized void put(K key, V val) {
		if(cache.isPresent(key)) { // Key already present in cache
			Node<K, V> node = cache.getNode(key);
			list.delete(node); // Delete old node from list
			Node<K, V> newNode = new Node<K, V> (key, val);
			list.insert(newNode); // Insert new node into list
			cache.delete(key); // Update the cache
			cache.insert(key, newNode);			
		}
		else { // New key to be inserted
			if(cache.getSize() == sizeLimit) { // Cache is full
				Node<K, V> leastRecent = list.getLRU(); // Get least recently used node
				K leastRecentKey = leastRecent.getKey();
				cache.delete(leastRecentKey); // Delete least recently used from cache
				Node<K, V> newNode = new Node<K, V>(key, val);
				cache.insert(key, newNode); // Insert new node into cache
				list.delete(leastRecent); // Update list
				list.insert(newNode);
			}
			else { // Cache has space
				Node<K, V> newNode = new Node<K, V>(key, val);
				cache.insert(key, newNode); // Insert into cache
				list.insert(newNode); // Insert into list
			}
		}
	}
	

	public static void main(String args[]) {
		// Manual Testing
		LRUCache<Integer, String> obj = new LRUCache<Integer, String>(3);
		obj.put(1, "First");
		System.out.println(obj.get(1));		
		obj.list.print();
		obj.put(2, "Second");			
		obj.list.print();
		System.out.println(obj.get(2));			
		obj.list.print();
		obj.get(2);
		obj.list.print();
		obj.put(3, "Third");
		obj.list.print();
	}

}