package Calculator;

import java.util.HashMap;
import java.util.Map;

public class MyLinkedList {
	class Node{
		int key;
		String cityName;
		Node pre;
		Node next;
		Node(){}
		Node(int key, String cityName){
			this.key = key;
			this.cityName = cityName;
		}
	}
	
	private void add(Node node){
        head.next.pre = node;
        node.next = head.next;
        
        node.pre = head;
        head.next = node;
    }
    
    private void remove(Node node){
        node.pre.next = node.next;
        node.next.pre = node.pre;
        
        node.next = null;
        node.pre = null;
    }
    
    private void update(Node node){
        remove(node);
        add(node);
    }
    
    private Node pop(){
        Node last = tail.pre;
        remove(last);
        return last;
    }
    
    public int capacity;
    public int size;
    public Node head, tail;
    public Map<Integer, Node> map;
    
    public MyLinkedList(int capacity) {
    	this.capacity = capacity;
    	this.size = 0;
    	
    	head = new Node();
    	tail = new Node();
    	
    	head.next = tail;
    	tail.pre = head;
    	
    	map = new HashMap<>();
    }
    
    public String get(int key) {
        Node node = map.get(key);
        if(node == null){
            return "";
        }
        else{
            update(node);
            return node.cityName;
        }
    }
    
    public void put(int key, String cityName) {
        Node node = map.get(key);
        if(node != null){
            node.cityName = cityName;
            update(node);
        }
        else{
            Node newNode = new Node(key, cityName);
            add(newNode);
            map.put(key, newNode);
            if(size ++ == capacity){
                Node last = pop();
                map.remove(last.key);
                size --;
            }
        }
    }
}
