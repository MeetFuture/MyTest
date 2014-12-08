package com.tangqiang.collection.list;

public interface IList<T> extends Iterable<T>{
		
	void clear();
	int size();
	T get(int index);
	T set(int index,T t);
	boolean add(T t);
	void add(int index,T t);
	T remove(int index);
	boolean isEmpty();
}