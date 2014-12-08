package com.tangqiang.huffman;

/**
 * 二叉树的节点类
 *
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-31 上午10:29:35
 * 
 * @version 1.0 2014-7-31 tqiang  create
 *
 */
public class TreeNode {

	int obj;// 子节的次数(权值)
	Byte b = null;// 子节

	int flag = -1;// 节点是左节点还是右节点 0 左 1右 -1根

	TreeNode parent;// 父节点
	TreeNode leftChild;// 左子节点
	TreeNode rightChild;// 右子节点

	public TreeNode(int obj, Byte b) {
		this.obj = obj;
		this.b = b;
	}

	public String toString() {
		return obj + "";
	}

}
