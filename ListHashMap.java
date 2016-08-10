package data_structure;

import java.lang.reflect.Array;
import java.util.Scanner;

//K:the object of key	V:the object of value
public class ListHashMap<K,V> {
	private int length = 0;
	Node[] nodelist;
	
	private class Node{
		private K newkey = null;
		private V newvalue = null;
		private Node previous = null;
		private Node next = null;
		
		public Node(K key,V value){
			newkey = key;
			newvalue = value;
		}
	}
	
	@SuppressWarnings("unchecked")
	public ListHashMap(int length){
		//the JVM will erase the type of the generic class£¬
		//so there isn't any generic array like  Node[] nodelist = new Node[2];	
		this.length = length;
		nodelist = (Node[])Array.newInstance(Node.class, length);
		//I dont't know why the method:newInstance doesn't malloc the space of nodelist
		for(int i=0;i<length;i++)
			nodelist[i] = new Node(null,null);
	}
	
	public void add(K newkey,V newvalue){
		Node newNode = new Node(newkey,newvalue);
		int index = reffun(newkey);
		if(nodelist[index].next!=null)
			nodelist[index].next.previous = newNode;
		newNode.next = nodelist[index].next;	
		newNode.previous = nodelist[index];
		nodelist[index].next = newNode;
	}
	
	public V search(K key){
		V destVlaue = null;
		int index = reffun(key);
		Node temp = nodelist[index].next;
		while(temp!=null){
			if(temp.newkey.equals(key))
				return temp.newvalue;
			temp = temp.next;
		}		
		return null;
	}
	
	public int delete(K key){
		int index = reffun(key);
		Node temp = nodelist[index].next;
		while(temp!=null){
			if(temp.newkey.equals(key)){
				temp.previous.next = temp.next;
				if(temp.next!=null)
					temp.next.previous = temp.previous;
				return 0;
			}	
			temp = temp.next;
		}
		System.out.println("no sunch element");
		return -1;
	}
	
	public int reffun(K newkey){
		return newkey.hashCode()%length;
	}
	
	public int len(){
		return length;
	}
	
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		ListHashMap<String,Integer> hashmap= new ListHashMap<String,Integer>(10);
		while(true){
			System.out.println("1(insert)	2(delete)	3(search)");
			String operation = in.next();
			switch(operation){
			case "1":{
				System.out.print("key:	");
				String key = in.next();
				System.out.print("value:");
				int value = in.nextInt();
				hashmap.add(key, value);
				break;
			}
			case "2":{
				System.out.println("(delete)");
				System.out.print("key:	");
				String key = in.next();
				hashmap.delete(key);
				break;
			}
			case "3":{
				System.out.println("(search)");
				System.out.print("key:	");
				String key = in.next();
				Integer result = hashmap.search(key);
				if(result==null)
					System.out.println("can't find "+key);
				else
					System.out.println(result);
				break;
			}
			default:
				System.out.println("wrong operation!");
			}
		}
	}
}


