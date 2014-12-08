package com.tangqiang.huffman;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * 压缩
 *
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-31 上午10:40:58
 * 
 * @version 1.0 2014-7-31 tqiang  create
 *
 */
public class HuffmanTree {

	public static void main(String[] args) {
		HuffmanTree tree = new HuffmanTree();
		// byte b = tree.string2Byte("10000011");
		// System.out.println(b);

		// 1.统计文件的子节个数
		HashMap<Byte, Integer> map = tree.countBytes("E:\\TermIdList.txt");
		System.out.println(map);

		// 2.得到哈夫曼树
		TreeNode root = tree.createHuffman(map);

		// 3.得到哈夫曼编码
		HashMap<Byte, String> mapCode = tree.hfmTree2HfmCode(root);

		// 4.得到01串
		String str = tree.byte2HfmCode(mapCode, "E:\\TermIdList.txt");
		System.out.println(str);

		// 5.将01串每8位转成子节
		byte[] bs = tree.hfmCode2ByteArray(str);
		for (int i = 0; i < bs.length; i++) {
			System.out.println(bs[i]);
		}

		String path = "E:\\TermIdList.txtRar";
		// 写出压缩文件
		tree.writeHfmFile(mapCode, bs, path);
	}

	/**
	 * 统计指定文件中每个子节出现的次数
	 * 
	 * @param path要统计的文件
	 * @return 返回HashMap<Byte, Integer> <子
	 * 
	 *         节，次数>
	 */
	public HashMap<Byte, Integer> countBytes(String path) {
		HashMap<Byte, Integer> map = new HashMap<Byte, Integer>();
		try {
			// 输入流
			FileInputStream fis = new FileInputStream(path);
			// 包装成缓冲流
			BufferedInputStream bis = new BufferedInputStream(fis);
			// 读取一个子节
			int t = bis.read();
			while (t != -1) {
				byte b = (byte) t;
				map.put(b, (map.get(b) == null ? 1 : map.get(b) + 1));
				// 判断该子节是否出现过
				// if (map.containsKey(b)) {
				// // 如果统计过，就取出以前的次数，+1
				// int count = map.get(b);
				// count++;
				// map.put(b, count);
				// } else {
				// // 第一次碰到该子节，就存放在map中，次数为1
				// map.put(b, 1);
				// }
				// 读取下一个子节
				t = bis.read();
			}
			bis.close();
			fis.close();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
		return map;
	}

	/**
	 * 将数组中的元素作为权值构建哈夫曼树
	 * 
	 * @param array
	 * @return
	 */
	public TreeNode createHuffman(HashMap<Byte, Integer> map) {
		// 1.构建节点队列
		PriorityQueue<TreeNode> nodeList = createNodeQueue(map);

		// 2.建树
		TreeNode root = createTreeByQueue(nodeList);
		return root;

	}

	/**
	 * 根据数组构造节点排序队列
	 * 
	 * @param array
	 *            要构造节点的权值数组 return
	 * 
	 *            返回装有节点的排序队列
	 */
	private PriorityQueue<TreeNode> createNodeQueue(HashMap<Byte, Integer> map) {
		// 自动排序队列
		PriorityQueue<TreeNode> nodeList = new PriorityQueue<TreeNode>(11, new MyComparable());
		// 1.将HashMap<Byte,Integer>的key作为权值，构造节点

		Set<Byte> keys = map.keySet();

		for (byte key : keys) {
			int value = map.get(key);
			// 定义该节点对应的次数和子节
			TreeNode node = new TreeNode(value, key);

			nodeList.add(node);
		}
		return nodeList;
	}

	// 自定义比较器
	class MyComparable implements Comparator<TreeNode> {

		@Override
		public int compare(TreeNode o1, TreeNode o2) {
			return o1.obj - o2.obj;
		}

	}

	/**
	 * 2.根据排序队列构造哈弗曼树
	 * 
	 * @param nodeList
	 *            排序队列
	 * @return 返回构造发哈夫曼树的根节点
	 */
	public TreeNode createTreeByQueue(PriorityQueue<TreeNode> nodeList) {

		while (nodeList.size() > 1) {
			// 取出权值最小的两个节点
			TreeNode n1 = nodeList.poll();
			TreeNode n2 = nodeList.poll();

			// 将两个节点构造成一棵树，使用两个节点的权值和作为根节点的权值
			TreeNode n3 = new TreeNode(n1.obj + n2.obj, null);

			n3.leftChild = n1;
			n3.rightChild = n2;

			n1.flag = 0;// 左0
			n2.flag = 1; // 右1

			n1.parent = n3;
			n2.parent = n3;

			// 将新的树放入队列
			nodeList.add(n3);

		}
		// 如果到这里证明队列中只有一个节点或者没有节点
		TreeNode root = nodeList.poll();

		return root;
	}

	/**
	 * 根据哈夫曼树得到该树上每个叶子节点的哈
	 * 
	 * 夫曼编码, 、 每个叶子节点对应一个子节 可以认为是得
	 * 
	 * 到了每个子节对应的哈夫曼编码
	 * 
	 * @param root
	 *            哈夫曼树
	 * @return HashMap<Byte,String> K：叶子节
	 * 
	 *         点对应的子节 V：该节点的哈夫曼编码
	 */
	public HashMap<Byte, String> hfmTree2HfmCode(TreeNode root) {

		HashMap<Byte, String> map = new HashMap<Byte, String>();

		getTreeCode(root, null, map, "");

		// getTreeCode2(root, null, map);

		return map;
	}

	/**
	 * 获得哈夫曼树上的叶子节点的哈夫曼编码
	 * 
	 * @param root
	 *            :当前要遍历的节点 parent:当
	 * 
	 *            前遍历的节点的父节点 map:存储哈夫曼编码的映射 s:记
	 * 
	 *            录当前节点哈夫曼编码的字符串
	 * 
	 */
	private void getTreeCode(TreeNode root, TreeNode panent, HashMap<Byte, String> map, String s) {

		if (root != null) {

			if (root.flag != -1) {
				// 输出跟节点
				s += root.flag;
			}

			// 获取左子节点
			TreeNode left = root.leftChild;
			// 如果有，
			getTreeCode(left, root, map, s);

			// 获得右字节点
			TreeNode right = root.rightChild;
			getTreeCode(right, root, map, s);

		} else {
			map.put(panent.b, s);
		}
	}

	/**
	 * 根据哈夫曼编码，将文件中的子节按照文件
	 * 
	 * 内容的顺序使用哈夫曼编码表示，得到一个01字符串
	 * 
	 * @param map
	 *            ：码表 return 使用哈夫曼编
	 * 
	 *            码表示的01串
	 */
	public String byte2HfmCode(HashMap<Byte, String> map, String path) {
		String str = "";
		try {
			// 输入流
			FileInputStream fis = new FileInputStream(path);
			// 包装成缓冲流
			BufferedInputStream bis = new BufferedInputStream(fis);

			int t = bis.read();
			while (t != -1) {
				byte b = (byte) t;
				// 得到该子节对应的编码,并拼接到一起成一个01串
				String code = map.get(b);
				str = str + code;
				t = bis.read();
			}

		} catch (Exception ef) {
			ef.printStackTrace();
		}
		return str;
	}

	/**
	 * 将01字符串每8位作为一个子节的8个bit转成
	 * 
	 * 一个子节 如果字符串总长度%8=num,则num为01串补0的个
	 * 
	 * 数 000 return 数组的最后一个子节表示倒数第二个子节是
	 * 
	 * 补了几个0的
	 * 
	 */
	public byte[] hfmCode2ByteArray(String str) {

		int len = 0;// 子节数组的长度
		int length = str.length();

		if (length % 8 == 0) {// 如果能够被整除
			len = length / 8 + 1;
		} else {// 如果不能被整除
			len = length / 8 + 1 + 1;
		}

		// 定义数组来存放转化来的子节
		byte[] bs = new byte[len];
		int index = 0;

		while (str.length() >= 8) {
			// 截取下来的8个01
			String newStr = str.substring(0, 8);
			// 除去已经被截取的前8位
			str = str.substring(8);

			// 将8个01转成子节
			byte b = string2Byte(newStr);
			bs[index] = b;
			index++;
		}

		if (str.length() > 0) {
			// 循环完之后，str的长度肯定是<8的。通过补0的方式凑成8位
			int num = 8 - str.length();// 计算补0的个数
			for (int i = 0; i < num; i++) {
				str += "0";
			}

			// 将补0的8个位转成子节
			byte b = string2Byte(str);
			bs[index] = b;

			// 将补0的个数存放在子节数组的最后一个位置
			bs[bs.length - 1] = (byte) num;
		} else {
			// 如果没有补0，就在最后一个下标位置存一个0
			bs[bs.length - 1] = 0;
		}

		return bs;
	}

	/**
	 * 将8位01串转成子节 00000011
	 */
	private byte string2Byte(String str) {
		short b = Short.valueOf(str, 2);
		return (byte) b;
	}

	/**
	 * 将码表和子节数据写到文件，即为压缩之后
	 * 
	 * 的文件
	 * 
	 * @param codeMap
	 *            :码表 data 子节数据 path:要
	 * 
	 *            存的压缩文件路径
	 * 
	 */
	public void writeHfmFile(HashMap<Byte,

	String> codeMap, byte[] data, String path) {
		try {
			// 根据文件路径建立文件输出流
			FileOutputStream fos = new FileOutputStream(path);
			// 包装成对象输出流
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			// // 写HashMap对象
			// oos.writeObject(codeMap);

			oos.writeInt(codeMap.size());
			System.out.println("码表长度：" + codeMap.size());

			Set<Byte> keys = codeMap.keySet();
			for (byte key : keys) {
				String value = codeMap.get(key);
				oos.writeByte(key);
				oos.writeObject(value);
			}

			// 写数据长度
			oos.writeInt(data.length);

			// 写数据
			oos.write(data);

			oos.flush();
			oos.close();
			fos.close();
		} catch (Exception ef) {
			ef.printStackTrace();
		}

	}

	/**
	 * 获得哈夫曼编码的方法二
	 * */
	private void getTreeCode2(TreeNode root, TreeNode parent, HashMap<Byte, String> map) {

		if (root != null) {

			// 获取左子节点
			TreeNode left = root.leftChild;
			// 如果有，
			getTreeCode2(left, root, map);

			// 获得右字节点
			TreeNode right = root.rightChild;
			getTreeCode2(right, root, map);

		} else {// 如果执行else,则parent肯定是叶子节点
			String s = "" + parent.flag;
			TreeNode node = parent.parent;
			while (node != null && node.flag != -1) {
				s = node.flag + s;
				node = node.parent;
			}
			map.put(parent.b, s);

		}

	}

	/**
	 * 遍历树
	 * 
	 * @param root
	 *            树的根节点
	 */
	public void printTree(TreeNode root) {

		if (root != null) {
			// 输出跟节点
			System.out.println(root);

			// 获取左子节点
			TreeNode left = root.leftChild;
			// 如果有，
			printTree(left);

			// 获得右字节点
			TreeNode right = root.rightChild;
			printTree(right);
		}
	}
}
