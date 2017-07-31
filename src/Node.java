/* Node class is used to maintain the node structure of doubly linked list */

import java.io.Serializable;

public class Node<K, V> implements Serializable {
	private static final long serialVersionUID = 2L;
	private Node<K, V> next;
	private Node<K, V> prev;
	private K key;
	private V val;
	
	public Node(K key, V val) {
		this.key = key;
		this.val = val;
		this.next = null;
		this.prev = null;
	}
	
	public Node<K, V> getNext() {
		return next;
	}
	
	public Node<K, V> getPrev() {
		return prev;
	}
	
	public void setNext(Node<K, V> next) {
		this.next = next;
	}
	
	public void setPrev(Node<K, V> prev) {
		this.prev = prev;
	}
	
	public V getVal() {
		return val;
	}
	
	public K getKey() {
		return key;
	}
}