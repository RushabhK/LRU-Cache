/* DoublyLL class is used to create a queue using doubly linked list */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoublyLL<K, V> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Node<K, V> mostRecent;  // Most recently used node
	private Node<K, V> leastRecent; // Least recently used node
	
	public DoublyLL() {
		mostRecent = null;
		leastRecent = null;
	}
	
	/* Insert as the Most recently used node */
	public void insert(Node<K, V> node) {
		if(mostRecent == null) { // Empty list
			mostRecent = node;
			leastRecent = node;
		}
		else {
			mostRecent.setNext(node);
			node.setPrev(mostRecent);
			node.setNext(null);
			mostRecent = node;
		}
	}
	
	/* Delete the given node */
	public void delete(Node<K, V> node) {
		if(node == null){
			return;
		}
		if(node == mostRecent) { // node is the Most recently used node
			Node<K, V> newRecent = mostRecent.getPrev();
			if(newRecent == null) // If only one node is present
			{
				mostRecent = null;
				leastRecent = null;
			}
			else {
				mostRecent = newRecent;
				mostRecent.setNext(null);
			}
		}
		else if(node == leastRecent) { // node is the Least recently used node
			leastRecent = leastRecent.getNext();
			leastRecent.setPrev(null);
		}
		else { // node is present somewhere in the middle
			Node<K, V> prev = node.getPrev();
			Node<K, V> next = node.getNext();
			prev.setNext(next);
			next.setPrev(prev);
			node = null;
		}
	}
	
	
	public Node<K, V> getLRU() {
		return leastRecent;
	}
	
	public Node<K, V> getMRU() {
		return mostRecent;
	}
	
	/* Prints the Doubly Linked List */
	public void print() {
		Node<K, V> temp = leastRecent;
		while(temp != null) {
			System.out.print(temp.getVal() + " ");
			temp = temp.getNext();
		}
		System.out.println();
	}
	
	/* Returns list of all keys for testing */
	public List<K> getKeyList() {
		List<K> l = new ArrayList<K>();
		Node<K, V> temp = leastRecent;
		while(temp != null) {
			l.add(temp.getKey());
			temp = temp.getNext();
		}
		return l;
	}
	
	/* Returns list of all values for testing */
	public List<V> getValueList() {
		List<V> l = new ArrayList<V>();
		Node<K, V> temp = leastRecent;
		while(temp != null) {
			l.add(temp.getVal());
			temp = temp.getNext();
		}
		return l;
	}
	
	public static void main(String[] args) {
		// Manual Testing
		DoublyLL<Integer, String> l = new DoublyLL<Integer, String>();
		HashMap<Integer, Node<Integer, String> > h = new HashMap<Integer, Node<Integer, String> >();
		for(int i=1; i<=10; i++) {
			Node<Integer, String> node = new Node<Integer, String>(i, Integer.toString(i));
			h.put(i, node);
			l.insert(node);
			l.print();
		}
		l.delete(h.get(10));
		l.delete(h.get(1));
		l.print();
	}
}
