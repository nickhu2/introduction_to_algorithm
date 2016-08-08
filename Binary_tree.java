package data_structure;

import java.util.*;

/**
 * @author nickhu
 * @version 1.0
 * 
 * A <code>Bianry_Tree</code> is a kind of data structure,it offers kinds of method including
 * insert/delete/traverse
 */
public class Binary_tree<T extends Comparable> {
	private int length=0;
	private Node root = null;
	
	/*inner class*/
	public class Node{
		private Node left;
		private Node right;
		private Node parent;
		private T value;
		public Node(T node){
			left = null;
			right = null;
			parent = null;
			value = node;
		}	
	}
	
	/**
	 * @param node the new element to be inserted
	 */
	public void insert(T node){
		Node newnode = new Node(node);
		Node temp_pos = root;
		Node temp_parent=null;
		if(root==null)
			root = newnode;
		else{
			while(temp_pos!=null){
				temp_parent=temp_pos;
				if(temp_pos.value.compareTo(newnode.value)>0)
					temp_pos=temp_pos.left;
				else
					temp_pos=temp_pos.right;
			}
			newnode.parent=temp_parent;
			if(newnode.value.compareTo(temp_parent.value)>0)
				temp_parent.right=newnode;
			else
				temp_parent.left=newnode;
		}
		System.out.println("insert complete");
		length++;
	}
	
	/**
	 * @param node the element to be delete
	 */
	public int delete(T node){
		if(root==null)
			return -1;
		else{
			Node temp=root;
			while(temp!=null){
				if(temp.value.compareTo(node)>0)
					temp=temp.left;
				else if(temp.value.compareTo(node)<0)
					temp=temp.right;
				else break;
			}
			if(temp==null){
				System.out.println("no such element!");
				return -1;
			}
			else{
				if(temp.left==null)
					transplant(temp.parent,temp,temp.right);
				else if(temp.right==null)
					transplant(temp.parent,temp,temp.left);
				else{
					Node newNode = nextNode(temp.right);
					if(newNode==temp.right){
						newNode.left = temp.left;
						temp.left.parent = newNode;
						transplant(temp.parent,temp,newNode);
					}
					else{
						transplant(newNode.parent,newNode,newNode.right);
						newNode.right=temp.right;
						temp.right.parent=newNode;
						transplant(temp.parent,temp,newNode);
						newNode.left=temp.left;
						temp.left.parent=newNode;
					}
				}
				length--;
				System.out.println("delete succeed~");
				System.out.println("there are "+length+" elements in the binary tree~");
				return 0;
			}
			
		}
	}
	
	//this method is used to replace the oldNode with the sub-tree:newNode
	public void transplant(Node parent,Node oldNode,Node newNode){
		if(oldNode.parent==null)
			root=newNode;
		else if(oldNode==oldNode.parent.left)
			oldNode.parent.left=newNode;
		else
			oldNode.parent.right=newNode;
		if(newNode!=null)
			newNode.parent=oldNode.parent;
	}
	
	//this method is used to find the smallest node in the  sub tree
	public Node nextNode(Node sub){
		Node smallest = new Node(null);
		smallest.left = sub;
		while(smallest.left!=null)
			smallest=smallest.left;
		return smallest;
	}
	
	/**
	 * print all the elements in the binarytree
	 * @param method the method of traversing.0:first order	1:middle order 2:last order
	 */
	public void traverse(int method){
		Stack<Node> temp_stack= new Stack<Node>();
		Node temp = root;
		switch(method){
			case 0:{
				System.out.println("first order traverse");
				while(temp!=null||temp_stack.size()!=0){
					while(temp!=null){
						temp_stack.push(temp);
						System.out.println(temp.value);
						temp=temp.left;
					}
					temp=temp_stack.pop();
					temp=temp.right;
				}
				break;
			}
			case 1:{
				System.out.println("middle order traverse");
				while(temp!=null||temp_stack.size()!=0){
					while(temp!=null){
						temp_stack.push(temp);
						temp=temp.left;
					}
					temp=temp_stack.pop();
					System.out.println(temp.value);
					temp=temp.right;
				}
				break;
			}
			case 2:{ 
				System.out.println("last order treverse");
				Node last_print=null;
				temp_stack.push(root);
				while(temp_stack.size()!=0){
					temp=temp_stack.peek();
					if((temp.left==null&&temp.right==null)||(temp.right==null&&last_print==temp.left)||(last_print==temp.right)){
						System.out.println(temp.value);
						last_print=temp;
						temp_stack.pop();
					}
					else{
						if(temp.right!=null)
							temp_stack.push(temp.right);
						if(temp.left!=null)
							temp_stack.push(temp.left);
					}
				}
				break;
			}
			default:{
				System.out.println("no such method");
			}
		}
	}
	
	public static void main(String[] args){
		Binary_tree<Integer> binarytree = new Binary_tree<Integer>();
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("1(insert)		2(delete)		3(reverse)");
			int operation=in.nextInt();
			switch(operation){
			case 1:{
				System.out.println("(insert)");
				Integer number=new Integer(in.nextInt());
				binarytree.insert(number);
				break;
			}
			case 2:{
				System.out.println("(delete)");
				Integer number=new Integer(in.nextInt());
				binarytree.delete(number);
				break;
			}
			case 3:{
				System.out.println("(reverse)");
				binarytree.traverse(in.nextInt());
				break;
			}
			default:{
				System.out.println("wrong operation!");
			}
				
			}
		}
	}
}
