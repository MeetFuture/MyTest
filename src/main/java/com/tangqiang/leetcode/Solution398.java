package com.tangqiang.leetcode;

import java.util.Random;

import com.tangqiang.leetcode.entity.ListNode;

/**
 * 382. Linked List Random Node
 */
public class Solution398 {
	private ListNode head;
	private Random random;

	/**
	 * @param head
	 *            The linked list's head. Note that the head is guaranteed to be not null, so it contains at least one node.
	 */
	public Solution398(ListNode head) {
		this.head = head;
		this.random = new Random();
	}

	/** Returns a random node's value. */
	public int getRandom() {
		long count = 1;
		ListNode node = head;
		int result = head.val;
		while (node.next != null) {
			node = node.next;
			count++;
			if (random.nextDouble() < 1 / (double) count) {
				result = node.val;
			}
		}
		return result;
	}

	/**
	 * Your Solution object will be instantiated and called as such: Solution obj = new Solution(head); int param_1 = obj.getRandom();
	 */

	public static void main(String[] args) {
		ListNode root = new ListNode(1);
		root.next = new ListNode(2);
		root.next.next = new ListNode(3);
		root.next.next.next = new ListNode(4);
		root.next.next.next.next = new ListNode(5);
		root.next.next.next.next.next = new ListNode(6);
		root.next.next.next.next.next.next = new ListNode(7);
		Solution398 obj = new Solution398(root);
		for (int i = 0; i < 100; i++) {
			System.out.println(obj.getRandom());
		}
	}
}
