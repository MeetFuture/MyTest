package com.tangqiang.test.bloomFilter;

import java.util.BitSet;

/**
 * 布隆过滤器
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-25 下午12:00:17
 * 
 * @description 先建立一个16亿二进制常量，然后将这16亿个二进制位全部置0。对于每个字符串，用8个不同的随机产生器（F1,F2,.....,F8）产生8个信息指纹(f1,f2,....,f8).再用一个随机数产生器G把这八个信息指纹映射到1到16亿中的8个自然数g1,g2,...,g8。现在把这8个位置的二进制位全部变为1。这样一个布隆过滤器就建好了。
 *              那么如何检测一个字符串是否已经存在了呢？ 现在用8个随机数产生器（F1,F2,...,F8）对这个字符串产生8个信息指纹s1,s2,...,s8，然后将这8个信息指纹对应到布隆过滤器的8个二进制位，分别是T1,T2,...,T8.如果字符串存在，那么显然T1,T2,...,T8对应的二进制位都应该是1。就是这样来判断字符串是否已经存在的。
 *              其实布隆过滤器就是对哈希算法的一个扩展，既然本质是哈希，那么就肯定会有不足，也就是说，肯定会有误判，一个字符串明明没有出现过而布隆过滤器判断出现了，虽然可能性很小，但是确实存在。
 *              那么如何减少这种概率呢，首先可以想到的是如果将8个信息指纹扩展到16个错误的概率肯定会降低，但是也要考虑到，这样的话，那么一个布隆过滤器所能存储的字符串数量也降低了1倍；另外就是选取很好的哈希函数，对字符串的哈希方法有很多种，其中不乏很好的哈希函数。
 *              布隆过滤器主要运用在过滤恶意网址用的，将所有的恶意网址建立在一个布隆过滤器上，然后对用户的访问的网址进行检测，如果在恶意网址中那么就通知用户
 *              。这样的话，我们还可以对一些常出现判断错误的网址设定一个白名单，然后对出现判断存在的网址再和白名单中的网址进行匹配，如果在白名单中，那么就放行。当然这个白名单不能太大，也不会太大，布隆过滤器错误的概率是很小的。有兴趣的读者可以去查阅，布隆过滤器的错误率。
 * 
 * @version 1.0 2014-8-25 tqiang create
 * 
 */
public class BloomFilter {

	private static final int DEFAULT_SIZE = 2 << 24;// 布隆过滤器的比特长度
	private static final int[] seeds = { 3, 5, 7, 11, 13, 31, 37, 61 };// 这里要选取质数，能很好的降低错误率
	private static BitSet bits = new BitSet(DEFAULT_SIZE);
	private static SimpleHash[] func = new SimpleHash[seeds.length];

	public static void addValue(String value) {
		for (SimpleHash f : func)
			// 将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1
			bits.set(f.hash(value), true);
	}

	public static void add(String value) {
		if (value != null)
			addValue(value);
	}

	public static boolean contains(String value) {
		if (value == null)
			return false;
		boolean ret = true;
		for (SimpleHash f : func)
			// 这里其实没必要全部跑完，只要一次ret==false那么就不包含这个字符串
			ret = ret && bits.get(f.hash(value));
		return ret;
	}

	public static void main(String[] args) {
		String value = "xkeyideal@gmail.com";
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
		}
		add(value);
		System.out.println(contains(value));
	}
}

class SimpleHash {// 这玩意相当于C++中的结构体

	private int cap;
	private int seed;

	public SimpleHash(int cap, int seed) {
		this.cap = cap;
		this.seed = seed;
	}

	public int hash(String value) {// 字符串哈希，选取好的哈希函数很重要
		int result = 0;
		int len = value.length();
		for (int i = 0; i < len; i++) {
			result = seed * result + value.charAt(i);
		}
		return (cap - 1) & result;
	}
}