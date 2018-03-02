package com.tangqiang.leetcode.entity;

/**
 * TreeNode tree = new TreeNode(1);
		tree.left = new TreeNode(2);
		tree.right = new TreeNode(3);
		tree.left.left = new TreeNode(4);
		tree.left.right = new TreeNode(5);
		tree.right.left = new TreeNode(6);
		tree.right.right = new TreeNode(7);
 *
 * @author Tom
 * @date 2017年7月27日 上午8:31:01
 *
 * @version 1.0  2017年7月27日  Tom  create
 * 
 * @copyright Copyright © 2017-???? 广电运通 All rights reserved.
 */
public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;

	public TreeNode(int x) {
		val = x;
	}
}
