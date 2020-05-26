package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;

// this class represents a customized implementation of the Stack data structure
// the user have access only to the last MAX_SIZE elements pushed in the stack
// this stack will be used to grant the player the ability to go back and undo a certain number of moves

public class Stack<Type> implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	
	private final int MAX_SIZE;
	private int size = 0;
	StackNode<Type> top; 

	static class StackNode<Type> implements Serializable {
		private static final long serialVersionUID = 6529685098267757690L; 
		Type element;
		StackNode<Type> next; 

		StackNode(Type move) 
		{ 
			this.element = move;
			this.next = null;
		} 
	}
	
	public Stack(int maxSize) {
		this.top = null;
		this.MAX_SIZE = maxSize;
	}

	public boolean isEmpty() {
		return (top == null); 
	} 

	public void push(Type element) 
	{ 
		StackNode<Type> newNode = new StackNode<Type>(element); 
		
		if (top == null) { 
			top = newNode; 
			
		} 
		else { 
			StackNode<Type> temp = top; 
			top = newNode; 
			newNode.next = temp; 
		} 
		size++;
		if (size > MAX_SIZE ) {
			StackNode<Type> aux = top;
			while (aux.next.next != null) {
				aux = aux.next;
			}
			aux.next = null;
		}
		System.out.println(element + " pushed to stack"); 
	} 

	public Type pop() 
	{ 
		Type popped = null; 
		if (top == null) { 
			System.out.println("Stack is Empty"); 
		} 
		else { 
			popped = top.element; 
			top = top.next; 
		} 
		return popped; 
	} 

	public Type peek() 
	{ 
		if (top == null) {   
			System.out.println("Stack is empty"); 
			return null; 
		} 
		else { 
			return top.element; 
		} 
	}
}
