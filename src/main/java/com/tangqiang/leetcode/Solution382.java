package com.tangqiang.leetcode;

import java.util.Random;

/**
 * 382. Linked List Random Node
 */
public class Solution382 {
	private int[] nums;
	private Random random;

	public Solution382(int[] nums) {
		this.nums = nums;
		this.random = new Random();
	}

	public int pick(int target) {
		int count = 0;
		int result = -1;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == target) {
				count++;
				if (random.nextDouble() < 1 / (double) count) {
					result = i;
				}
			}
		}
		return result;
	}

}
