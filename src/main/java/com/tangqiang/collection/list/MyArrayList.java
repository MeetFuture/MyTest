package com.tangqiang.collection.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 自定义线性表
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.collection.list.MyArrayList.java 
 * @date 2014-6-23 下午6:48:26
 *
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class MyArrayList<T> implements IList<T>{
	
	private static final int DEFAULT_CAPACITY = 10;
	
	private int theSize;
	
	private T [] theItems;

	public MyArrayList(){
		clear();
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}

	@Override
	public void clear() {
		theSize = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}

	@SuppressWarnings("unchecked")
	public void ensureCapacity(int newCapacity){
		if(newCapacity<theSize){
			return;
		}
		T[] old = theItems;
		theItems = (T[]) new Object[newCapacity];
		for(int i=0;i<theSize;i++){
			theItems[i] = old[i];
		}
	}
	
	@Override
	public int size() {
		return theSize;
	}

	@Override
	public T get(int index) {
		if(index<0||index>theSize){
			throw new ArrayIndexOutOfBoundsException();
		}
		return theItems[index];
	}

	@Override
	public T set(int index, T t) {
		if(index<0||index>theSize){
			throw new ArrayIndexOutOfBoundsException();
		}
		T old = theItems[index];
		theItems[index] = t;
		return old;
	}

	@Override
	public boolean add(T t) {
		add(theSize,t);
		return true;
	}

	@Override
	public void add(int index, T t) {
		if(theItems.length==theSize){
			ensureCapacity(theSize*2+1);
		}
		for(int i=theSize;i>index;i--)
			theItems[i] = theItems[i-1];
		theItems[index]  = t;
		theSize++;
	}

	@Override
	public T remove(int index) {
		T removeItem = theItems[index];
		for(int i=index;i<theSize-1;i++){
			theItems[i] = theItems[i+1];
		}
		theSize--;
		return removeItem;
	}
	
	private class ArrayListIterator implements Iterator<T>{

		private int current = 0;
		
		@Override
		public boolean hasNext() {
			return current<theSize;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			return theItems[current++];
		}

		@Override
		public void remove() {
			MyArrayList.this.remove(current--);
		}
		
	}

	@Override
	public boolean isEmpty() {
		return theSize==0;
	}
	
}