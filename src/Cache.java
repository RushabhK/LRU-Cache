/* Cache class maintains:
 * 	i. Mapping of key vs node in the Doubly linked list 
 * 	ii.Cached specific functions 
*/

import java.io.Serializable;
import java.util.HashMap;

public class Cache<K, V> implements Serializable {
	private static final long serialVersionUID = 3L;
	private HashMap<K, Node<K, V> > location;
	
	public Cache() {
		location = new HashMap<K, Node<K, V> > ();
	}
	
	public boolean isPresent(K key) {
		if(location.get(key) != null)
			return true;
		return false;
	}
	
	public Node<K, V> getNode(K key) {
		return location.get(key);
	}
	
	public void insert(K key, Node<K, V> node) {
		location.put(key, node);
	}
	
	public void delete(K key) {
		location.remove(key);
	}
	
	public int getSize() {
		return location.size();
	}
	
	public void print() {
		for (HashMap.Entry<K, Node<K, V> > entry : location.entrySet()) {
		    K key = entry.getKey();
		    Node<K, V> value = entry.getValue();
		    System.out.println(key + " : " + value);
		}
	}
	
	/* Return the HashMap of key vs value for testing */
	public HashMap<K, V> getHashMap() {
		HashMap<K, V> h = new HashMap<K, V>();
		for (HashMap.Entry<K, Node<K, V> > entry : location.entrySet()) {
		    K key = entry.getKey();
		    V value = entry.getValue().getVal();
		    h.put(key, value);
		}
		return h;
	}
}
