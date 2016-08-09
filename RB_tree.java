package data_structure;

import java.util.Scanner;
import java.util.Stack;

import data_structure.Binary_tree.Node;

/**
 * 
 * @author nickhu
 * @version 1.0
 * in this class,you will know how to construct a red-black tree
 * (I will realize the delete method later)
 */
public class RB_tree<T extends Comparable> {	
	public static final int BLACK = 0;
	public static final int RED = 0;
	//guard node
	private Node nil = new Node(null);	
	private Node root = nil;
	
	//inner class to realize Node
	public class Node{
		private T value = null;
		private Node parent = nil;
		private Node left =nil;
		private Node right =nil;
		//0:balck	1:red
		private int colour = 1;
		public Node(T newobject){
			value = newobject;
		}
	}
	
	public void insert(T node){
		Node newNode = new Node(node);
		Node index = nil;
		Node index_temp = root;
		//find the parent of newNode
		while(index_temp!=nil){
			index = index_temp;
			if(index_temp.value.compareTo(newNode.value)>0)
				index_temp = index_temp.left;
			else index_temp = index_temp.right;
		}
		//insert the newNode
		newNode.parent = index;
		if(index==nil)
			root = newNode;
		else if(index.value.compareTo(newNode.value)>0)
			index.left = newNode;
		else
			index.right = newNode;
		//fix the red-black tree
		insertFixup(newNode);
	}
	
	public void insertFixup(Node insertedNode){
		while(insertedNode.parent.colour==RED){
			//if insertedNode's parent is its grandparent's leftchild
			if(insertedNode.parent==insertedNode.parent.parent.left){
				Node uncleNode = insertedNode.parent.parent.right;
				//case 1:
				if(uncleNode.colour==RED){
					insertedNode.parent.colour = BLACK;
					uncleNode.colour = BLACK;
					insertedNode.parent.parent.colour = RED;
				}
				else{
				//case 2:turn to case 3
					if(insertedNode==insertedNode.parent.right){
						insertedNode = insertedNode.parent;
						leftRotate(insertedNode);
					}
				//case 3:
					insertedNode.parent.colour = BLACK;
					insertedNode.parent.parent.colour = RED;
					rightRotate(insertedNode.parent.parent);
				}
			}
			//if insertedNode's parent is its grandparent's rightchild
			else{
				Node uncleNode = insertedNode.parent.parent.left;
				//case 1:
				if(uncleNode.colour==RED){
					insertedNode.parent.colour = BLACK;
					uncleNode.colour = BLACK;
					insertedNode = insertedNode.parent.parent;
				}
				else{
					//case 2:(turn case 3)
					if(insertedNode==insertedNode.parent.left){
						insertedNode = insertedNode.parent;
						rightRotate(insertedNode);
					}
					insertedNode.parent.colour = BLACK;
					insertedNode.parent.parent.colour = RED;
					leftRotate(insertedNode.parent.parent);
				}
			}
		}
	}
	
	public void leftRotate(Node leftNode){
		Node rightNode = leftNode.right;
		//modify the relationship between leftNode and rightNode's leftchild
		leftNode.right = rightNode.left;
		if(rightNode.left!=nil)
			rightNode.left.parent = leftNode;
		//modify the relationship between rightNode and leftNode's parent
		rightNode.parent = leftNode.parent;
		if(root==leftNode)
			root = rightNode;
		else if(leftNode==leftNode.left)
			leftNode.parent.left = rightNode;
		else
			leftNode.parent.right = rightNode;		
		//modify the relationship between  leftNode and rightNode
		rightNode.left = leftNode;
		leftNode.parent = rightNode;
	}
	
	public void rightRotate(Node rightNode){
		Node leftNode = rightNode.left;
		//modify the relationship between leftNode's rightchild and rightNode
		rightNode.left = leftNode.right;
		if(leftNode.right!=nil)
			leftNode.right.parent = rightNode;
		//modify the relationship between leftNode and rightNode's parent
		leftNode.parent = rightNode.parent;
		if(root==rightNode)
			root = leftNode;
		else if(rightNode == rightNode.parent.left)
			rightNode.parent.left = leftNode;
		else
			rightNode.parent.right = leftNode;
		//modify the relationship between  leftNode and rightNode
		leftNode.right = rightNode;
		rightNode.parent = leftNode;
	}
	
	//middle order traverse
	public void traverse(){
		Stack<Node> temp_stack= new Stack<Node>();
		Node temp = root;
		System.out.println("middle order traverse");
		while(temp!=nil||temp_stack.size()!=0){
			while(temp!=nil){
				temp_stack.push(temp);
				temp=temp.left;
			}
			temp=temp_stack.pop();
			System.out.println(temp.value);
			temp=temp.right;
		}
	}
	
	public static void main(String[] args){
		RB_tree<String> rbtree = new RB_tree<String>();
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("1(insert)	2(reverse)");
			int operation=in.nextInt();
			if(operation==1){
				System.out.println("(insert)");
				String alphabet = new String(in.next());
				rbtree.insert(alphabet);
			}
			else if(operation==2){
				System.out.println("(reverse)");
				rbtree.traverse();
			}
			else
				System.out.println("wrong operation!");
				
		}
	}
	}

