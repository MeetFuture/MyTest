package com.tangqiang.collection.list;

public class MyListTest {
	public static void main(String[] args) {
		
		MyLinkedList<String> listLink = new MyLinkedList<String>();
		listLink.add("a");
		listLink.add("b");
		listLink.add("c");
		listLink.add("A");
		listLink.add("B");
		listLink.add("C");
		
		listLink.remove(2);
		System.out.println(listLink);
		System.out.println(listLink.get(2));
	}
}
