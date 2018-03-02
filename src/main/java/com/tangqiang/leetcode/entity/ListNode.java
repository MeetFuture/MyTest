package com.tangqiang.leetcode.entity;

/***
 * ListNode root = new ListNode(7);
		root.next = new ListNode(2);
		root.next.next = new ListNode(4);
		root.next.next.next = new ListNode(3);

		ListNode root1 = new ListNode(5);
		root1.next = new ListNode(6);
		root1.next.next = new ListNode(4);
		// root.next.next.next.next = new ListNode(5);
		// root.next.next.next.next.next = new ListNode(6);
		// root.next.next.next.next.next.next = new ListNode(7);
 *
 * @author Tom
 * @date 2017年7月27日 上午8:30:33
 *
 * @version 1.0  2017年7月27日  Tom  create
 * 
 * @copyright Copyright © 2017-???? 广电运通 All rights reserved.
 */
public class ListNode {
	public int val;
	public ListNode next;

	public ListNode(int x) {
		val = x;
	}
}