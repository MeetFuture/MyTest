package com.tangqiang.collection.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 自定义链表
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.collection.list.MyLinkedList.java 
 * @date 2014-6-23 下午6:48:39
 *
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class MyLinkedList<T> implements IList<T> {
	
	private int theSize;
	private int modCount;
	private Node<T> beginMarker;
	private Node<T> endMarker;
	
	public MyLinkedList(){
		clear();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	@Override
	public void clear() {
		beginMarker = new Node<T>(null, null, null);
		endMarker = new Node<T>(null, beginMarker, null);
		beginMarker.next = endMarker;
		theSize = 0;
		modCount++;
	}

	@Override
	public int size() {
		return theSize;
	}

	@Override
	public boolean isEmpty() {
		return theSize==0;
	}
	
	@Override
	public T get(int index) {
		return getNode(index).data;
	}

	@Override
	public T set(int index, T t) {
		Node<T> p = getNode(index);
		T oldValue = p.data;
		p.data = t;
		return oldValue;
	}

	@Override
	public boolean add(T t) {
		add(theSize, t);
		return true;
	}

	@Override
	public void add(int index, T t) {
		addBefore(getNode(index), t);
	}

	@Override
	public T remove(int index) {
		return remove(getNode(index));
	}

	private T remove(Node<T> p){
		p.prev.next = p.next;
		p.next.prev = p.prev;
		theSize--;
		modCount++;
		return p.data;
	}
	
	private void addBefore(Node<T> p,T x){
		Node<T> newNode = new Node<T>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}
	
	private Node<T> getNode(int index){
		Node<T> p;
		if(index<0 || index>theSize){
			throw new IndexOutOfBoundsException();
		}
		if(index<theSize/2){
			p = beginMarker.next;
			for(int i=0;i<index;i++){
				p = p.next;
				}
			}else{
				p = endMarker;
				for(int i=theSize;i>index;i--){
					p = p.prev;
				}
			}
		return p;
	}
	
	private static class Node<T>{
		public T data;
		public Node<T> prev;
		public Node<T> next;
		
		public Node(T data,Node<T> prev,Node<T> next){
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private class LinkedListIterator implements Iterator<T>{
		
		private Node<T> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		@Override
		public boolean hasNext() {
			return current != endMarker;
		}

		@Override
		public T next() {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		@Override
		public void remove() {
			if(modCount!=expectedModCount){
				throw new ConcurrentModificationException();
			}
			if(!okToRemove){
				throw new IllegalStateException();
			}
			MyLinkedList.this.remove(current.next);
			expectedModCount++;
		}
		
	}
	
}
