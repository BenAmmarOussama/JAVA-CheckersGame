package eniso.BenAmmarOussama.PM_ASDII;

import java.io.Serializable;

// This class repersents an implementation of the Queue data structure


public class Queue<Type> implements Serializable {
	private static final long serialVersionUID = 6529685098267757690L;
	
	QueueNode<Type> front; //tete
	QueueNode<Type> rear; //queue
	
	static class QueueNode<Type> implements Serializable {
		private static final long serialVersionUID = 6529685098267757690L;
		Type element;
		QueueNode<Type> next;
		
		QueueNode(Type element){
			this.element = element;	
			this.next = null;
		}
	}
	
	public Queue() {
		this.front = null;
		this.rear = null;
	}
	
	public boolean isEmpty() {
		return (front == null); 
	}
	
	public void enqueue(Type element) {
		
		QueueNode<Type> newNode = new QueueNode<Type>(element);
		
		if (front == null) {
			front = newNode;
			rear = newNode;
		}
		else {
			rear.next = newNode;
			rear = newNode;
		}
	}
	
	public Type dequeue() {
		if (this.isEmpty() ) {
			return null;
		}
		else {
			QueueNode<Type> aux = front;
			if (rear == front ) {
				front = null;
				rear = null;
			}
			else {
				front = front.next;
			}
			return aux.element;
		}
	}
	
	public Type peek() 
	{ 
		if (front == null) { 
			System.out.println("Queue is empty"); 
			return null; 
		} 
		else { 
			return front.element; 
		} 
	}
}
