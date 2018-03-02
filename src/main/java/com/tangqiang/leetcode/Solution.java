package com.tangqiang.leetcode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.tangqiang.leetcode.entity.ListNode;
import com.tangqiang.leetcode.entity.TreeNode;

public class Solution {

	public static void main(String[] args) throws Exception {

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
		Date date = new Date();
		System.out.println(date);
		System.out.println(sdf1.format(date));
		System.out.println(sdf2.format(date));
		// new Solution().execute();
	}

	private void execute() throws Exception {
		long time1 = System.currentTimeMillis();
		List<Integer> result = null;
		spiralOrderTest(new int[25][15]);

		long time2 = System.currentTimeMillis();
		System.out.println(result + "	  Time:" + (time2 - time1) + "/ms");
	}

	/** 657. Judge Route Circle */
	public boolean judgeCircle(String moves) {
		char[] cahrs = moves.toCharArray();
		int x = 0, y = 0;
		for (char c : cahrs) {
			switch (c) {
			case 'U':
				y = y + 1;
				break;
			case 'D':
				y = y - 1;
				break;
			case 'R':
				x = x + 1;
				break;
			case 'L':
				x = x - 1;
				break;
			default:
				break;
			}
		}
		return x == 0 && y == 0;
	}

	public void spiralOrderTest(int[][] matrix) throws Exception {
		Integer[][] board = new Integer[matrix.length][matrix[0].length];

		int[] A = new int[] { 0, 1 }, B = new int[] { 1, 0 }, C = new int[] { 0, -1 }, D = new int[] { -1, 0 };
		int[] duration = A;
		int count = matrix.length * matrix[0].length;
		int x = 0;
		int y = 0;
		for (int i = 0; i < count; i++) {
			board[x][y] = matrix[x][y];
			matrix[x][y] = 1;
			SolutionUtil.println(20);
			SolutionUtil.printArr(matrix);
			Thread.sleep(50);

			x = x + duration[0];
			y = y + duration[1];
			if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != null) {
				x = x - duration[0];
				y = y - duration[1];
				duration = duration == A ? B : duration == B ? C : duration == C ? D : A;
				x = x + duration[0];
				y = y + duration[1];
			}
		}
	}

	/** 54. Spiral Matrix */
	public List<Integer> spiralOrder(int[][] matrix) {
		List<Integer> list = new ArrayList<Integer>();
		if (matrix.length == 0 || matrix[0].length == 0) {
			return list;
		}
		// 把板子扩大
		Integer[][] board = new Integer[matrix.length + 2][matrix[0].length + 2];
		for (int i = 0; i < matrix.length; i++) {
			int[] ch = matrix[i];
			for (int j = 0; j < ch.length; j++) {
				board[i + 1][j + 1] = matrix[i][j];
			}
		}
		int[] A = new int[] { 0, 1 }, B = new int[] { 1, 0 }, C = new int[] { 0, -1 }, D = new int[] { -1, 0 };
		int[] duration = A;
		int count = matrix.length * matrix[0].length;
		int x = 1;
		int y = 0;
		for (int i = 0; i < count; i++) {
			x = x + duration[0];
			y = y + duration[1];
			if (board[x][y] == null) {
				x = x - duration[0];
				y = y - duration[1];
				duration = duration == A ? B : duration == B ? C : duration == C ? D : A;
				x = x + duration[0];
				y = y + duration[1];
			}
			list.add(board[x][y]);
			board[x][y] = null;
		}
		return list;
	}

	/** 324. Wiggle Sort II */
	public void wiggleSort(int[] nums) {
		Arrays.sort(nums);
		int middle = nums.length / 2 + (nums.length % 2);
		int[] arrFirst = new int[middle];
		System.arraycopy(nums, 0, arrFirst, 0, arrFirst.length);
		int[] arrLast = new int[nums.length - middle];
		System.arraycopy(nums, middle, arrLast, 0, arrLast.length);
		for (int i = 0; i < arrFirst.length; i++) {
			nums[i * 2] = arrFirst[i];
		}
		for (int i = 0; i < arrLast.length; i++) {
			nums[i * 2 + 1] = arrLast[i];
		}
	}

	/** 402. Remove K Digits */
	public String removeKdigits(String num, int k) {
		if (num.length() == k) {
			return "0";
		}
		StringBuffer result = new StringBuffer(num);
		for (int i = 0; i < k; i++) {
			int len = result.length();
			if (len >= 2) {
				boolean remove = false;
				for (int j = 1; j < len; j++) {
					// 找到最大的删
					if (result.charAt(j) < result.charAt(j - 1)) {
						result.deleteCharAt(j - 1);
						remove = true;
						break;
					}
				}
				if (!remove) {
					result.delete(len - k + i, len);
					break;
				}
			} else {
				return "0";
			}
		}
		while (result.length() > 0 && result.charAt(0) == '0') {
			result.deleteCharAt(0);
		}
		return result.length() > 0 ? result.toString() : "0";
	}

	/** 79. Word Search */
	public boolean exist(char[][] board, String word) {
		// 把板子扩大
		char[][] boardNew = new char[board.length + 2][board[0].length + 2];
		for (int i = 0; i < board.length; i++) {
			char[] ch = board[i];
			for (int j = 0; j < ch.length; j++) {
				boardNew[i + 1][j + 1] = board[i][j];
			}
		}

		char[] chars = word.toCharArray();
		char first = chars[0];
		boolean result = false;
		for (int i = 0; i < boardNew.length; i++) {
			char[] ch = boardNew[i];
			for (int j = 0; j < ch.length; j++) {
				if (first == boardNew[i][j]) {
					boardNew[i][j] = ' ';
					result = result || existWordHelper(boardNew, new int[] { i, j + 1 }, chars, 1);
					result = result || existWordHelper(boardNew, new int[] { i, j - 1 }, chars, 1);
					result = result || existWordHelper(boardNew, new int[] { i + 1, j }, chars, 1);
					result = result || existWordHelper(boardNew, new int[] { i - 1, j }, chars, 1);
					boardNew[i][j] = first;
				}
			}
		}
		return result;
	}

	private boolean existWordHelper(char[][] board, int[] nextPoint, char[] chars, int index) {
		boolean result = false;
		if (chars.length == index) {
			return true;
		}
		char c = chars[index];
		int X = nextPoint[0];
		int Y = nextPoint[1];
		if (board[X][Y] == c) {
			board[X][Y] = ' ';
			result = result || existWordHelper(board, new int[] { X, Y + 1 }, chars, index + 1);
			result = result || existWordHelper(board, new int[] { X, Y - 1 }, chars, index + 1);
			result = result || existWordHelper(board, new int[] { X + 1, Y }, chars, index + 1);
			result = result || existWordHelper(board, new int[] { X - 1, Y }, chars, index + 1);
			board[X][Y] = c;
		}
		return result;
	}

	/** 593. Valid Square */
	public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
		long x = (p1[0] + p2[0] + p3[0] + p4[0]);
		long y = (p1[1] + p2[1] + p3[1] + p4[1]);
		long[] c1 = new long[] { p1[0] * 4 - x, p1[1] * 4 - y };
		long[] c2 = new long[] { p2[0] * 4 - x, p2[1] * 4 - y };
		long[] c3 = new long[] { p3[0] * 4 - x, p3[1] * 4 - y };
		long[] c4 = new long[] { p4[0] * 4 - x, p4[1] * 4 - y };

		long len1 = (long) (Math.pow(c1[0], 2) + Math.pow(c1[1], 2));
		long len2 = (long) (Math.pow(c2[0], 2) + Math.pow(c2[1], 2));
		long len3 = (long) (Math.pow(c3[0], 2) + Math.pow(c3[1], 2));
		long len4 = (long) (Math.pow(c4[0], 2) + Math.pow(c4[1], 2));

		boolean pLen = len1 != 0 && len1 == len2 && len1 == len3 && len1 == len4;
		if (pLen) {
			long len12 = (long) (Math.pow(c1[0] - c2[0], 2) + Math.pow(c1[1] - c2[1], 2));
			long len13 = (long) (Math.pow(c1[0] - c3[0], 2) + Math.pow(c1[1] - c3[1], 2));
			long len14 = (long) (Math.pow(c1[0] - c4[0], 2) + Math.pow(c1[1] - c4[1], 2));
			if (len12 == len13) {
				return len12 + len13 == len14;
			} else if (len12 == len14) {
				return len12 + len14 == len13;
			} else if (len13 == len14) {
				return len13 + len14 == len12;
			}
		}
		return false;
	}

	/** 640. Solve the Equation */
	public String solveEquation(String equation) {
		equation = equation.replaceAll("-", "+-");
		String[] arr = equation.split("=");
		String[] leftArr = arr[0].split("\\+");
		String[] rightArr = arr[1].split("\\+");
		int x = 0;
		int b = 0;
		for (String string : leftArr) {
			if (!"".equals(string)) {
				if (string.contains("x")) {
					x += "x".equals(string) ? 1 : "-x".equals(string) ? -1 : Integer.valueOf(string.replaceAll("x", ""));
				} else {
					b -= Integer.valueOf(string);
				}
			}
		}

		for (String string : rightArr) {
			if (!"".equals(string)) {
				if (string.contains("x")) {
					x -= "x".equals(string) ? 1 : "-x".equals(string) ? -1 : Integer.valueOf(string.replaceAll("x", ""));
				} else {
					b += Integer.valueOf(string);
				}
			}
		}

		return x == 0 && b == 0 ? "Infinite solutions" : x == 0 ? "No solution" : ("x=" + (b / x));
	}

	/** 78. Subsets */
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		list.add(new ArrayList<Integer>());
		if (nums.length == 0) {
			return list;
		}

		long count = (long) Math.pow(2, nums.length);
		for (long i = 1; i < count; i++) {
			List<Integer> listTmp = new ArrayList<Integer>();
			for (int j = 0; j < nums.length; j++) {
				if (((i >> j) & 1) == 1) {
					int poi = j;
					listTmp.add(nums[poi]);
				}
			}
			list.add(listTmp);
		}
		return list;
	}

	/** 20. Valid Parentheses */
	public boolean isValid20(String s) {
		Map<Character, Character> map = new HashMap<Character, Character>();
		map.put('{', '}');
		map.put('(', ')');
		map.put('[', ']');
		Stack<Character> stack = new Stack<Character>();

		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			Character store = map.get(c);
			if (store != null) {
				stack.push(store);
			} else {
				if (stack.empty()) {
					return false;
				}
				store = stack.pop();
				if (c != store) {
					return false;
				}
			}
		}
		return stack.empty();
	}

	/** 388. Longest Absolute File Path */
	public int lengthLongestPath(String input) {
		// dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext
		input = input.replaceAll("    ", "\t");

		for (int i = 0;; i++) {
			String reg = "\n" + ((i == 0) ? "\t" : (i + "\t"));
			String replace = "\n" + (i + 1);
			if (input.contains(reg)) {
				input = input.replaceAll(reg, replace);
			} else {
				break;
			}
		}
		int result = lengthLongestPathMethod(1, 0, input);
		return result;
	}

	private int lengthLongestPathMethod(int depth, int currentLen, String dir) {
		System.out.println("depth:" + depth + "	currentLen:" + currentLen + "	dir:" + dir);
		int result = 0;
		String reg = "\n" + depth;
		if (dir.contains(reg)) {
			String[] arr = dir.split(reg);
			currentLen = currentLen + arr[0].length() + 1;
			for (int i = 1; i < arr.length; i++) {
				int tmp = lengthLongestPathMethod(depth + 1, currentLen, arr[i]);
				result = Math.max(result, tmp);
			}
		} else if (dir.contains(".")) {
			result = currentLen + dir.length();
		}
		return result;
	}

	/** 279. Perfect Squares */
	public int numSquares(int n) {
		System.out.println(n);
		if (n == 0 || n == 1 || n == 2 || n == 3) {
			return n;
		}
		int result = Integer.MAX_VALUE;
		int sqrtMax = (int) Math.sqrt(n);
		for (int i = sqrtMax; i >= 1; i--) {
			int pre = i;
			int left = (int) (n - Math.pow(i, 2));
			int numCount = 0;
			int numCountTmp = numSquares(left);
			while (left != 0) {
				int tmp = (int) Math.sqrt(left);
				if (tmp > pre) {
					break;
				}
				numCount++;
				left = (int) (left - Math.pow(tmp, 2));
			}
			if (left != 0) {
				break;
			}
			numCount = Math.min(numCount, numCountTmp) + 1;
			result = Math.min(result, numCount);
		}

		return result;
	}

	/** 242. Valid Anagram */
	public boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		if (t.length() == 0) {
			return true;
		}
		int[] tmp = new int[26];
		char[] arrs = s.toCharArray();
		for (int i = 0; i < arrs.length; i++) {
			tmp[arrs[i] - 'a']++;
		}
		char[] arrt = t.toCharArray();
		for (int i = 0; i < arrt.length; i++) {
			tmp[arrt[i] - 'a']--;
			if (tmp[arrt[i] - 'a'] < 0) {
				return false;
			}
		}
		return true;
	}

	/** 438. Find All Anagrams in a String */
	public List<Integer> findAnagrams(String s, String p) {
		List<Integer> list = new ArrayList<Integer>();
		int lens = s.length();
		int lenp = p.length();
		if (lens < lenp) {
			return list;
		}

		int[] arr = new int[26];
		for (int i = 0; i < lenp; i++) {
			arr[p.charAt(i) - 'a']++;
			arr[s.charAt(i) - 'a']--;
		}
		if (findAnagramsMethod(arr)) {
			list.add(0);
		}
		for (int i = 0; i < lens - lenp; i++) {
			arr[s.charAt(lenp + i) - 'a']--;
			arr[s.charAt(i) - 'a']++;
			if (findAnagramsMethod(arr)) {
				list.add(i + 1);
			}
		}
		return list;
	}

	private boolean findAnagramsMethod(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != 0) {
				return false;
			}
		}
		return true;
	}

	/** 567. Permutation in String */
	public boolean checkInclusion(String s1, String s2) {
		int lenS1 = s1.length();
		int lenS2 = s2.length();
		if (lenS1 == 0) {
			return true;
		}
		if (lenS1 > lenS2) {
			return false;
		}
		if (lenS1 == 1) {
			return s2.indexOf(s1) >= 0;
		}
		int[] arr1 = new int[26];
		int[] arr2 = new int[26];
		for (int i = 0; i < lenS1; i++) {
			arr1[s1.charAt(i) - 'a']++;
			arr2[s2.charAt(i) - 'a']++;
		}
		if (checkInclusionMethod(arr1, arr2)) {
			return true;
		}
		for (int i = 0; i < lenS2 - lenS1; i++) {
			arr2[s2.charAt(lenS1 + i) - 'a']++;
			arr2[s2.charAt(i) - 'a']--;
			if (checkInclusionMethod(arr1, arr2)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkInclusionMethod(int[] arr1, int[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	/** 89. Gray Code */
	public List<Integer> grayCode(int n) {
		int max = 1 << n;
		List<Integer> list = new ArrayList<Integer>();
		Set<Integer> set = new LinkedHashSet<Integer>(n);
		for (int i = 1; i < max; i++) {
			set.add(i);
		}
		int pre = 0;
		list.add(0);
		while (!set.isEmpty()) {
			Iterator<Integer> iterator = set.iterator();
			while (iterator.hasNext()) {
				int tmp = iterator.next();
				if (Integer.bitCount(tmp ^ pre) == 1) {
					pre = tmp;
					list.add(tmp);
					set.remove(tmp);
					break;
				}
			}
		}
		return list;
	}

	/** 394. Decode String */
	public String decodeString(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			char chari = sb.charAt(i);
			if (chari == ']') {
				for (int j = i - 1; j >= 0; j--) {
					char charj = sb.charAt(j);
					if (charj == '[') {
						for (int k = j - 1; k >= 0; k--) {
							char chark = sb.charAt(k);
							if (chark < '0' || chark > '9' || k == 0) {
								if (k == 0 && !(chark < '0' || chark > '9')) {
									k = -1;
								}
								Integer num = Integer.valueOf(sb.substring(k + 1, j));
								String repeat = sb.substring(j + 1, i);
								sb.delete(k + 1, i + 1);
								for (int count = 0; count < num; count++) {
									sb.insert(k + 1, repeat);
								}
								i = k + 1;
								break;
							}
						}
						break;
					}
				}
			}
		}
		return sb.toString();
	}

	// private void AAA() {
	// Map<String,List<T>> map = new HashMap<String, List<T>>();
	// for (T t : list) {
	// List<T> tmp = map.getOrDefault(t.a,new ArrayList<T>());
	// tmp.add(t);
	// map.put(t.a,tmp);
	// }
	// }

	/** 390. Elimination Game */
	public int lastRemaining(int n) {
		boolean toRight = true;
		int start = 1; // 开始 1
		int end = n; // 结束 n
		int split = 1; // 数字间隔 1
		int len = n; // 数字总数

		while (end - start != 0) {
			if (toRight) {
				start = start + split;
			} else {
				int skip = len % 2;
				if (skip == 1) {
					start = start + split;
				}
			}
			toRight = !toRight;
			split = split * 2;
			len = len / 2;

			end = start + (len - 1) * split;
		}
		return end;
	}

	/** 137. Single Number II */
	public int singleNumberII(int[] nums) {
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i = i + 3) {
			if (i + 2 > nums.length || nums[i] != nums[i + 2]) {
				return nums[i];
			}
		}
		return 0;
	}

	/** 482. License Key Formatting */
	public String licenseKeyFormatting(String S, int K) {
		StringBuffer sb = new StringBuffer(S.replaceAll("-", "").toUpperCase());
		int len = sb.length();
		int poix = 2;
		int index = len - K;
		while (index > 0) {
			sb.insert(index, '-');
			index = len - K * poix++;
		}
		return sb.toString();
	}

	/** 319. Bulb Switcher */
	public int bulbSwitch(int n) {
		// int count = 0;
		// for (int i = 1; i <= n; i++) {
		// int tmp = 0;
		// int max = Math.min(i, i / 2 + 1);
		// for (int j = 1; j < max; j++) {
		// if (i % j == 0) {
		// tmp = 1 - tmp;
		// }
		// }
		// tmp = 1 - tmp;
		// count += tmp;
		// }
		// return count;
		return (int) Math.sqrt(n);
	}

	/** 46. Permutations */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (nums.length == 0) {
			return result;
		}
		int count = 1;
		for (int i = 1; i <= nums.length; i++) {
			count *= i;
		}
		for (int i = 0; i < count; i++) {
			int index = i;
			int[] select = new int[nums.length];
			List<Integer> list = new ArrayList<Integer>();
			for (int j = nums.length; j >= 1; j--) {
				int poi = index % j;
				index = index / j;
				int get = 0;
				for (int k = 0; k < select.length; k++) {
					get += select[k] == 0 ? 1 : 0;
					if (get - 1 == poi) {
						select[k] = 1;
						list.add(nums[k]);
						break;
					}
				}
			}
			System.out.println(i + "	" + list);
			result.add(list);
		}
		return result;
	}

	/** 287. Find the Duplicate Number */
	public int findDuplicate(int[] nums) {
		int slow = 0;
		int next = 0;
		while (slow == 0 || slow != next) {
			System.out.println("1slow:" + slow + "	next:" + next);
			slow = nums[slow];
			next = nums[nums[next]];
		}
		System.out.println("1slow:" + slow + "	next:" + next);
		slow = 0;
		while (slow != next) {
			slow = nums[slow];
			next = nums[next];
			System.out.println("2slow:" + slow + "	next:" + next);
		}

		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if ((nums[i] ^ nums[j]) == 0) {
					return nums[i];
				}
			}
		}
		return 0;
	}

	/**
	 * 423. Reconstruct Original Digits from English<br>
	 * zero one two three four five six seven eight nine<br>
	 * z zero <br>
	 * u four<br>
	 * r three<br>
	 * h eight<br>
	 * t two<br>
	 * o one<br>
	 * f five<br>
	 * v seven<br>
	 * x six<br>
	 * i nine
	 */
	public String originalDigits(String s) {
		char[] chars = s.toCharArray();
		int[] count = new int[10];
		for (char c : chars) {
			if (c == 'z')
				count[0]++; // z zero
			else if (c == 'u')
				count[4]++; // u four
			else if (c == 'r')
				count[3]++; // r three --four zero
			else if (c == 'h')
				count[8]++; // h eight -- three
			else if (c == 't')
				count[2]++; // t two -- eight three
			else if (c == 'o')
				count[1]++; // o one -- two zero four
			else if (c == 'f')
				count[5]++; // f five -- four
			else if (c == 'v')
				count[7]++; // v seven -- five
			else if (c == 'x')
				count[6]++; // x six
			else if (c == 'i')
				count[9]++; // i nine -- six five eight
		}
		count[3] = count[3] - count[4] - count[0];
		count[8] = count[8] - count[3];
		count[2] = count[2] - count[8] - count[3];
		count[1] = count[1] - count[2] - count[0] - count[4];
		count[5] = count[5] - count[4];
		count[7] = count[7] - count[5];
		count[9] = count[9] - count[6] - count[5] - count[8];

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count.length; i++) {
			for (int j = 0; j < count[i]; j++) {
				sb.append(i);
			}
		}
		return sb.toString();
	}

	public String originalDigits1(String s) {
		List<Integer> listResult = new ArrayList<Integer>();
		char[] chars = s.toCharArray();
		char[] charPre = new char[] { 'z', 'u', 'r', 'h', 't', 'o', 'f', 'v', 's', 'i' };
		String[] numsPre = new String[] { "zero", "four", "three", "eight", "two", "one", "five", "seven", "six", "nine" };
		int[] numPre = new int[] { 0, 4, 3, 8, 2, 1, 5, 7, 6, 9 };
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (char c : chars) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}

		for (int i = 0; i < charPre.length; i++) {
			int countChar = map.getOrDefault(charPre[i], 0);
			if (countChar > 0) {
				char[] numChars = numsPre[i].toCharArray();
				for (char c : numChars) {
					map.put(c, map.getOrDefault(c, 0) - countChar);
				}
				for (int j = 0; j < countChar; j++) {
					listResult.add(numPre[i]);
				}
			}
		}
		Collections.sort(listResult);
		StringBuilder sb = new StringBuilder(listResult.size());
		for (int i = 0; i < listResult.size(); i++) {
			sb.append(listResult.get(i));
		}
		return sb.toString();
	}

	/** 554. Brick Wall */
	public int leastBricks(List<List<Integer>> wall) {
		Map<Integer, Integer> mapEdges = new HashMap<Integer, Integer>();
		int maxEdges = 0;
		for (List<Integer> list : wall) {
			int count = 0;
			for (int i = 0; i < list.size() - 1; i++) {
				count += list.get(i);
				int store = mapEdges.getOrDefault(count, 0) + 1;
				maxEdges = Math.max(maxEdges, store);
				mapEdges.put(count, store);
			}
		}
		return wall.size() - maxEdges;
	}

	public int leastBricks1(List<List<Integer>> wall) {
		int[][] wallMessage = new int[wall.size()][2];
		int minBricks = wall.size();
		int minCount = 0;

		while (true) {
			int count = 0;
			int minCountTmp = Integer.MAX_VALUE;
			for (int i = 0; i < wall.size(); i++) {
				if (wallMessage[i][1] == minCount) {
					count++;
					List<Integer> list = wall.get(i);
					if (wallMessage[i][0] < list.size()) {
						// 统计数据 墙的边缘加上下一个墙的长度
						wallMessage[i][1] = wallMessage[i][1] + list.get(wallMessage[i][0]);
						// 数据位移
						wallMessage[i][0] = wallMessage[i][0] + 1;
					}
				}
				minCountTmp = Math.min(minCountTmp, wallMessage[i][1]);
			}
			System.out.println(minCount + "	" + minCountTmp + "	" + count);
			if (count == wall.size() && minCount == minCountTmp) {
				break;
			}
			if (minCount != 0 && minCount != minCountTmp) {
				minBricks = Math.min(minBricks, wall.size() - count);
			}
			minCount = minCountTmp;
		}
		return minBricks;
	}

	private List<List<Integer>> leastBricksHelper() {
		List<List<Integer>> list = new ArrayList<List<Integer>>();

		int[] a = new int[] { 1, 2, 2, 1 };

		List<Integer> listT = new ArrayList<Integer>();
		listT.add(1);
		listT.add(2);
		listT.add(2);
		listT.add(1);
		list.add(listT);

		List<Integer> listT2 = new ArrayList<Integer>();
		listT2.add(3);
		listT2.add(1);
		listT2.add(2);
		list.add(listT2);

		List<Integer> listT3 = new ArrayList<Integer>();
		listT3.add(1);
		listT3.add(3);
		listT3.add(2);
		list.add(listT3);

		List<Integer> listT4 = new ArrayList<Integer>();
		listT4.add(2);
		listT4.add(4);
		list.add(listT4);

		List<Integer> listT5 = new ArrayList<Integer>();
		listT5.add(3);
		listT5.add(1);
		listT5.add(2);
		list.add(listT5);

		List<Integer> listT6 = new ArrayList<Integer>();
		listT6.add(1);
		listT6.add(3);
		listT6.add(1);
		listT6.add(1);
		list.add(listT6);

		return list;
	}

	/**
	 * 452. Minimum Number of Arrows to Burst Balloons
	 * 
	 * 000000001111111111<br>
	 * 111100000000000000<br>
	 * 000001111100000000<br>
	 * 001111000000000000<br>
	 * 000000000111000000<br>
	 * 
	 * 112212112322111111
	 * */
	public int findMinArrowShots(int[][] points) {
		Arrays.sort(points, new Comparator<int[]>() {
			public int compare(int[] o1, int[] o2) {
				int res = o1[0] - o2[0];
				if (res == 0) {
					res = o1[1] - o2[1];
				}
				return res;
			}
		});

		int countArrow = 0;
		for (int i = 0; i < points.length; i++) {
			int a = points[i][0];
			int b = points[i][1];
			if (b != 0) {
				countArrow++;
				for (int j = i + 1; j < points.length; j++) {
					int x = points[j][0];
					int y = points[j][1];
					if (y != 0) {
						if ((x >= a && x <= b) || (y >= a && y <= b)) {
							a = Math.max(a, x);
							b = Math.min(b, y);

							points[j][0] = 0;
							points[j][1] = 0;
						}
					}
					if (x > b) {
						break;
					}
				}
			}
		}

		return countArrow;
	}

	public int findMinArrowShots1(int[][] points) {
		int boardLen = 0;
		for (int i = 0; i < points.length; i++) {
			int[] balloon = points[i];
			balloon[0] = balloon[0] - 1;
			balloon[1] = balloon[1] - 1;
			boardLen = Math.max(boardLen, points[i][1]);
		}
		int[] board = new int[boardLen];
		for (int i = 0; i < points.length; i++) {
			int[] balloon = points[i];
			for (int j = balloon[0]; j < balloon[1]; j++) {
				board[j] += 1;
			}
		}
		int countArrow = 0;
		while (true) {
			int max = 0;
			for (int i = 0; i < board.length; i++) {
				max = Math.max(max, board[i]);
			}
			if (max == 0) {
				break;
			}
			countArrow++;
			for (int i = 0; i < board.length; i++) {
				if (board[i] == max) {
					for (int j = 0; j < points.length; j++) {
						int[] balloon = points[j];
						if (balloon[0] <= i && balloon[1] >= i) {
							for (int k = balloon[0]; k < balloon[1]; k++) {
								board[k] -= 1;
							}
							balloon[0] = 0;
							balloon[1] = 0;
						}
					}
					break;
				}
			}
		}
		return countArrow;
	}

	/** 318. Maximum Product of Word Lengths */
	public int maxProduct(String[] words) {
		int[] arr = new int[words.length];
		for (int i = 0; i < words.length; i++) {
			char[] chars = words[i].toCharArray();
			int tmp = 0;
			for (int j = 0; j < chars.length; j++) {
				tmp |= 1 << (chars[j] - 'a');
			}
			arr[i] = tmp;
		}
		int result = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if ((arr[i] & arr[j]) == 0) {
					result = Math.max(result, words[i].length() * words[j].length());
				}
			}
		}
		return result;
	}

	/** 583. Delete Operation for Two Strings adbdcdc cdcdbda */
	public int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		int arr[][] = new int[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			for (int j = 0; j <= len2; j++) {
				if (i == 0 || j == 0)
					arr[i][j] = 0;
				else {
					if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
						arr[i][j] = arr[i - 1][j - 1] + 1;
					} else {
						arr[i][j] = Math.max(arr[i - 1][j], arr[i][j - 1]);
					}
				}
			}
		}
		int max = arr[len1][len2];
		return len1 + len2 - 2 * max;
	}

	/** 494. Target Sum */
	public int findTargetSumWays(int[] nums, int S) {
		int result = 0;
		result += findTargetSumWaysMethod(nums, S, 0, 0);
		return result;
	}

	private int findTargetSumWaysMethod(int[] nums, int S, int index, int currentCount) {
		int result = 0;
		if (index == nums.length - 1) {
			result += S == currentCount + nums[index] ? 1 : 0;
			result += S == currentCount - nums[index] ? 1 : 0;
			return result;
		}
		result += findTargetSumWaysMethod(nums, S, index + 1, currentCount + nums[index]);
		result += findTargetSumWaysMethod(nums, S, index + 1, currentCount - nums[index]);
		return result;
	}

	/** 22. Generate Parentheses [ "((()))","(()())","(())()","()(())","()()()" ] */
	public List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<String>();
		String str = new String();
		generateParenthesisMethod(result, 0, 0, str, n);
		return result;
	}

	private void generateParenthesisMethod(List<String> result, int countL, int countR, String str, int num) {
		if (countL < countR || countL > num || countR > num) {
			return;
		}
		if (countL == num && countR == num) {
			result.add(str.toString());
			return;
		}
		generateParenthesisMethod(result, countL + 1, countR, str + "(", num);
		generateParenthesisMethod(result, countL, countR + 1, str + ")", num);
	}

	/** 216. Combination Sum III */
	public List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (n < k || n <= 0 || k <= 0 || k > 9 || n > 45) {
			return result;
		}
		int[] arr = new int[10];
		int[] compare = new int[10];

		for (int i = 1; i <= 9; i++) {
			arr[i] = i;
			compare[i] = 9 - k + i;
		}
		while (true) {
			int count = 0;
			for (int i = 1; i <= k; i++) {
				count += arr[i];
			}
			if (count == n) {
				List<Integer> list = new ArrayList<Integer>();
				for (int i = 1; i <= k; i++) {
					list.add(arr[i]);
				}
				result.add(list);
			}

			if (arr[1] == 9 - k + 1) {
				break;
			}

			for (int i = k; i >= 1; i--) {
				if (arr[i] < compare[i]) {
					arr[i]++;
					for (int j = i + 1; j <= 9; j++) {
						arr[j] = arr[j - 1] + 1;
					}
					break;
				}
			}
		}
		return result;
	}

	/** 392. Is Subsequence */
	public boolean isSubsequence(String s, String t) {
		int index = 0;
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			index = t.indexOf(chars[i], index);
			if (index++ < 0) {
				return false;
			}
		}
		return true;
		// if (s.length() == 0) {
		// return true;
		// }
		// char[] chars = t.toCharArray();
		// int len = s.length(), count = 0;
		// char tmp = s.charAt(count);
		// for (int i = 0; i < chars.length; i++) {
		// if (chars[i] == tmp) {
		// count = count + 1;
		// if (count == len) {
		// break;
		// }
		// tmp = s.charAt(count);
		// }
		// }
		// return count == len;
	}

	/** 343. Integer Break */
	public int integerBreak(int n) {
		if (n <= 4) {
			return n == 1 ? 1 : n == 2 ? 1 : n == 3 ? 2 : n == 4 ? 4 : 0;
		}

		int left = n % 3;
		int nums = n / 3;
		if (left == 0) {
			return (int) Math.pow(3, nums);
		} else if (left == 1) {
			return (int) Math.pow(3, nums - 1) * 4;
		} else {
			return (int) Math.pow(3, nums) * 2;
		}
	}

	/** 539. Minimum Time Difference */
	public int findMinDifference(List<String> timePoints) {
		int[] times = new int[timePoints.size()];
		for (int i = 0; i < timePoints.size(); i++) {
			String[] arr = timePoints.get(i).split("\\:");
			int minutes = Integer.valueOf(arr[0]) * 60 + Integer.valueOf(arr[1]);
			times[i] = minutes;
		}
		Arrays.sort(times);
		int result = 60 * 24;
		for (int i = 0; i < times.length - 1; i++) {
			result = Math.min(result, times[i + 1] - times[i]);
		}
		result = Math.min(result, 60 * 24 + times[0] - times[times.length - 1]);
		return result;
	}

	/** 357. Count Numbers with Unique Digits */
	public int countNumbersWithUniqueDigits(int n) {
		if (n == 0) {
			return 0;
		}
		int count = 10;
		for (int i = 2; i <= n; i++) {
			int tmp = 9;
			for (int j = i - 1; j > 0; j--) {
				tmp *= (10 - j);
			}
			count += tmp;
		}
		return count;
	}

	/** 498. Diagonal Traverse */
	public int[] findDiagonalOrder(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return new int[] {};
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int[] result = new int[row * col];
		int x = 1, y = -1;
		boolean upper = false;
		for (int i = 0; i < result.length; i++) {
			x = x + (upper ? 1 : -1);
			y = y + (upper ? -1 : 1);
			if (x < 0 || x >= row || y < 0 || y >= col) {
				if (upper) {
					if (x >= row) {
						x = row - 1;
						y = y + 2;
					} else if (y < 0) {
						y = y + 1;
					}
				} else {
					if (y >= col) {
						y = col - 1;
						x = x + 2;
					} else if (x < 0) {
						x = x + 1;
					}
				}

				upper = !upper;
			}
			result[i] = matrix[x][y];
		}
		return result;
	}

	/** 18. 4Sum */
	public List<List<Integer>> fourSum(int[] nums, int target) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				int count = nums[i] + nums[j];
				List<Integer> list = map.getOrDefault(count, new ArrayList<Integer>());
				if (!list.contains(i) && !list.contains(j)) {
					list.add(i);
					list.add(j);
					map.put(count, list);
				}
			}
		}

		List<List<Integer>> result = new ArrayList<List<Integer>>();
		List<String> listSame = new ArrayList<String>();
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				int count = target - nums[i] - nums[j];
				if (map.containsKey(count)) {
					List<Integer> list = map.get(count);
					for (int k = 0; k < list.size(); k = k + 2) {
						int a = list.get(k);
						int b = list.get(k + 1);
						if (a != i && a != j && b != i && b != j) {
							int[] arr = new int[] { nums[a], nums[b], nums[i], nums[j] };
							Arrays.sort(arr);
							String same = arr[0] + "-" + arr[1] + "-" + arr[2] + "-" + arr[3];
							if (!listSame.contains(same)) {
								listSame.add(same);
								List<Integer> listResult = new ArrayList<Integer>();
								listResult.add(nums[a]);
								listResult.add(nums[b]);
								listResult.add(nums[i]);
								listResult.add(nums[j]);

								result.add(listResult);
							}

						}
					}
				}
			}
		}
		return result;
	}

	/** 454. 4Sum II */
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int a : A) {
			for (int b : B) {
				int count = a + b;
				map.put(count, map.getOrDefault(count, 0) + 1);
			}
		}
		int result = 0;
		for (int c : C) {
			for (int d : D) {
				int count = -c - d;
				result += map.getOrDefault(count, 0);
			}
		}
		return result;
	}

	/** 445. Add Two Numbers II */
	public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
		List<Integer> listNums1 = new ArrayList<Integer>();
		List<Integer> listNums2 = new ArrayList<Integer>();
		ListNode ln1 = l1;
		ListNode ln2 = l2;
		while (ln1 != null || ln2 != null) {
			if (ln1 != null) {
				listNums1.add(ln1.val);
				ln1 = ln1.next;
			}
			if (ln2 != null) {
				listNums2.add(ln2.val);
				ln2 = ln2.next;
			}
		}
		List<Integer> listNumLonger = listNums1.size() > listNums2.size() ? listNums1 : listNums2;
		List<Integer> listNumShorter = listNums1.size() <= listNums2.size() ? listNums1 : listNums2;
		int over = 0;
		ListNode result = null;
		for (int i = listNumLonger.size() - 1; i >= 0; i--) {
			int indexS = listNumShorter.size() - (listNumLonger.size() - i);
			int tmp = listNumLonger.get(i) + (indexS >= 0 ? listNumShorter.get(indexS) : 0) + over;
			ListNode tmpNode = new ListNode(tmp % 10);
			tmpNode.next = result;
			result = tmpNode;
			over = tmp / 10;
		}
		if (over == 1) {
			ListNode tmpNode = new ListNode(over);
			tmpNode.next = result;
			result = tmpNode;
		}
		return result;
	}

	/** 477. Total Hamming Distance */
	public int totalHammingDistance(int[] nums) {
		if (nums.length <= 1) {
			return 0;
		}
		int len = Integer.toBinaryString((int) Math.pow(10, 9)).length();
		int[] zeros = new int[len];
		int[] ones = new int[len];
		for (int num : nums) {
			for (int i = 0; i < len; i++) {
				int bit = (1 << i) & num;
				zeros[i] += bit == 0 ? 1 : 0;
				ones[i] += bit == 0 ? 0 : 1;
			}
		}
		int count = 0;
		for (int i = 0; i < len; i++) {
			count += zeros[i] * ones[i];
		}
		return count;
	}

	/** 592. Fraction Addition and Subtraction */
	public String fractionAddition(String expression) {
		expression = expression.replaceAll("-", "\\+-");
		if (expression.startsWith("+")) {
			expression = expression.substring(1);
		}
		String[] arr = expression.split("\\+");
		int[] numerators = new int[arr.length];
		int[] denominators = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			String[] tmp = arr[i].split("/");
			numerators[i] = Integer.valueOf(tmp[0]);
			denominators[i] = Integer.valueOf(tmp[1]);
		}
		long denominator = 1, numerator = 0;
		// 分母相乘
		for (int i = 0; i < denominators.length; i++) {
			denominator *= denominators[i];
		}
		// 分子相加
		for (int i = 0; i < numerators.length; i++) {
			numerator += numerators[i] * denominator / denominators[i];
		}
		long nd = Math.abs(fractionAdditionMethod(numerator, denominator));
		String result = numerator / nd + "/" + denominator / nd;
		return result;
	}

	public long fractionAdditionMethod(long x, long y) {
		if (y == 0) {
			return x;
		} else {
			return fractionAdditionMethod(y, x % y);
		}
	}

	/** 503. Next Greater Element II */
	public int[] nextGreaterElements(int[] nums) {
		int len = nums.length;
		int[] numsTmp = new int[2 * len];
		System.arraycopy(nums, 0, numsTmp, 0, len);
		System.arraycopy(nums, 0, numsTmp, len, len);

		int[] result = new int[len];
		for (int i = 0; i < len; i++) {
			boolean b = false;
			for (int j = i + 1; j < i + len; j++) {
				if (numsTmp[j] > nums[i]) {
					result[i] = numsTmp[j];
					b = true;
					break;
				}
			}
			if (!b) {
				result[i] = -1;
			}
		}
		return result;
	}

	/** 238. Product of Array Except Self */
	public int[] productExceptSelf(int[] nums) {
		int n = nums.length;
		int[] result = new int[n];
		int count = 1;
		for (int i = 0; i < n; i++) {
			result[i] = count;
			count *= nums[i];
		}
		count = 1;
		for (int i = n - 1; i >= 0; i--) {
			result[i] *= count;
			count *= nums[i];
		}
		return result;
	}

	/** 565. Array Nesting */
	public int arrayNesting(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < nums.length; i++) {
			set.add(i);
		}
		int max = 0;
		while (set.size() > 0) {
			int count = 1;
			int begin = set.iterator().next();
			set.remove(begin);
			int tmp = begin;
			while (nums[tmp] != begin) {
				count++;
				tmp = nums[tmp];
				set.remove(tmp);
			}
			max = Math.max(max, count);
		}
		return max;
	}

	/** 623. Add One Row to Tree */
	public TreeNode addOneRow(TreeNode root, int v, int d) {
		if (d == 1) {
			TreeNode tmpL = root;
			root = new TreeNode(v);
			root.left = tmpL;
		}
		addOneRowMethod(root, v, 2, d);
		return root;
	}

	private void addOneRowMethod(TreeNode node, int value, int deep, int d) {
		if (node == null) {
			return;
		}
		if (deep == d) {
			TreeNode tmpL = node.left;
			node.left = new TreeNode(value);
			node.left.left = tmpL;

			TreeNode tmpR = node.right;
			node.right = new TreeNode(value);
			node.right.right = tmpR;
			return;
		} else {
			addOneRowMethod(node.left, value, deep + 1, d);
			addOneRowMethod(node.right, value, deep + 1, d);
		}
	}

	/** 215. Kth Largest Element in an Array */
	public int findKthLargest(int[] nums, int k) {
		Arrays.sort(nums);
		int result = nums[nums.length - k];
		return result;

	}

	/** 347. Top K Frequent Elements */
	public List<Integer> topKFrequent(int[] nums, int k) {
		List<Integer> listResult = new ArrayList<Integer>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int count = map.get(nums[i]) == null ? 0 : map.get(nums[i]);
			map.put(nums[i], count + 1);
		}

		List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		for (int i = 0; i < k; i++) {
			listResult.add(list.get(i).getKey());
		}
		return listResult;
	}

	/** 451. Sort Characters By Frequency */
	public String frequencySort(String s) {
		StringBuilder sb = new StringBuilder(s.length());
		char[] chars = s.toCharArray();
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < chars.length; i++) {
			int count = map.get(chars[i]) == null ? 0 : map.get(chars[i]);
			map.put(chars[i], count + 1);
		}

		List<Map.Entry<Character, Integer>> list = new ArrayList<Map.Entry<Character, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Character, Integer>>() {
			public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<Character, Integer> tmp = list.get(i);
			Character c = tmp.getKey();
			int count = tmp.getValue();
			for (int j = 0; j < count; j++) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/** 260. Single Number III */
	public int[] singleNumberIII(int[] nums) {
		int[] result = new int[2];
		Arrays.sort(nums);
		int count = 0;
		for (int i = 0; i < nums.length;) {
			if (i + 1 >= nums.length || nums[i] != nums[i + 1]) {
				result[count++] = nums[i];
				i = i + 1;
			} else {
				i = i + 2;
			}
		}
		return result;
	}

	/** 462. Minimum Moves to Equal Array Elements II */
	public int minMoves2(int[] nums) {
		Arrays.sort(nums);
		int i = 0;
		int j = nums.length - 1;
		int count = 0;
		while (i < j) {
			count += nums[j--] - nums[i++];
		}
		return count;
	}

	/** 419. Battleships in a Board */
	public int countBattleships(char[][] board) {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			char[] col = board[i];
			for (int j = 0; j < col.length; j++) {
				if (board[i][j] == 'X') {
					count++;
					board[i][j] = '.';
					int tmpi = i + 1;
					int tmpj = j + 1;
					while (tmpi < board.length && board[tmpi][j] == 'X') {
						board[tmpi][j] = '.';
						tmpi++;
					}
					while (tmpj < col.length && board[i][tmpj] == 'X') {
						board[i][tmpj] = '.';
						tmpj++;
					}
				}
			}
		}
		return count;
	}

	/** 495. Teemo Attacking */
	public int findPoisonedDuration(int[] timeSeries, int duration) {
		if (timeSeries.length == 0) {
			return 0;
		}
		int count = 0;
		int poistion = 0;
		for (int i = 0; i < timeSeries.length; i++) {
			int po = timeSeries[i];
			if (poistion > po) {
				count += po - timeSeries[i - 1];
			} else {
				count += duration;
			}
			poistion = po + duration;
		}
		return count;
	}

	/** 257. Binary Tree Paths */
	public List<String> binaryTreePaths(TreeNode root) {
		List<String> result = new ArrayList<String>();
		if (root != null) {
			String s = root.val + "";
			if (root.left == null && root.right == null) {
				result.add(s);
			} else {
				binaryTreePathsMethod(root.left, result, s);
				binaryTreePathsMethod(root.right, result, s);
			}
		}
		return result;
	}

	private void binaryTreePathsMethod(TreeNode node, List<String> list, String s) {
		if (node != null) {
			s += "->" + node.val;
			if (node.left == null && node.right == null) {
				list.add(s);
			} else {
				binaryTreePathsMethod(node.left, list, s);
				binaryTreePathsMethod(node.right, list, s);
			}
		}
	}

	/** 69. Sqrt(x) */
	public int mySqrt(int x) {
		long my = x / 2, tmp = x / 2, result = 0;
		while ((result = ((long) x - my * my)) != 0) {
			if (my * my < x && (my + 1) * (my + 1) > x) {
				return (int) my;
			}
			tmp = tmp / 2 == 0 ? 1 : tmp / 2;
			my = my + (result > 0 ? 1 : -1) * tmp;
		}
		return (int) my;
	}

	/** 367. Valid Perfect Square */
	public boolean isPerfectSquare(int num) {
		long my = num / 2, tmp = num / 2, result = 0;
		while ((result = ((long) num - my * my)) != 0) {
			if (my * my < num && (my + 1) * (my + 1) > num) {
				return false;
			}
			tmp = tmp / 2 == 0 ? 1 : tmp / 2;
			my = my + (result > 0 ? 1 : -1) * tmp;
		}
		return my * my == num;
	}

	/** 58. Length of Last Word */
	public int lengthOfLastWord(String s) {
		s = " " + s.trim();
		return s.length() - s.lastIndexOf(' ') - 1;
	}

	/** 88. Merge Sorted Array */
	public void merge(int[] nums1, int m, int[] nums2, int n) {
		System.arraycopy(nums2, 0, nums1, m, n);
		Arrays.sort(nums1, 0, m + n);
	}

	/** 67. Add Binary */
	public String addBinary(String a, String b) {
		if (a.length() == 0 || b.length() == 0) {
			return a + b;
		}
		char[] longer = a.length() > b.length() ? a.toCharArray() : b.toCharArray();
		char[] shorter = a.length() <= b.length() ? a.toCharArray() : b.toCharArray();
		int in = 0;
		StringBuilder sb = new StringBuilder(longer.length + 1);
		for (int i = 0; i < longer.length; i++) {
			int cli = longer[longer.length - i - 1] == '1' ? 1 : 0;
			int csi = shorter.length - i - 1 >= 0 && shorter[shorter.length - i - 1] == '1' ? 1 : 0;
			in += cli + csi;
			sb.append(in % 2);
			in = in / 2;
		}
		sb.append(in == 1 ? "1" : "");
		return sb.reverse().toString();
	}

	/** 374. Guess Number Higher or Lower */
	public int guessNumber(int n) {
		int my = n / 2, tmp = n / 2, result = 0;
		while ((result = guess(my)) != 0) {
			tmp = tmp / 2 == 0 ? 1 : tmp / 2;
			my = my + result * tmp;
		}
		return my;
	}

	int guess(int num) {
		return num == 1 ? 0 : num < 1 ? 1 : -1;
	}

	/** 203. Remove Linked List Elements */
	public ListNode removeElements(ListNode head, int val) {
		ListNode parent = new ListNode(0);
		parent.next = head;
		ListNode pre = parent;
		ListNode curr = head;

		while (curr != null) {
			if (curr.val == val) {
				pre.next = curr.next;
			} else {
				pre = curr;
			}
			curr = pre.next;
		}
		return parent.next;
	}

	/** 27. Remove Element */
	public int removeElement(int[] nums, int val) {
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != val) {
				nums[count] = nums[i];
				count++;
			}
		}
		return count;
	}

	/** 26. Remove Duplicates from Sorted Array */
	public int removeDuplicates(int[] nums) {
		if (nums.length <= 1) {
			return nums.length;
		}
		int count = 1;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != nums[i - 1]) {
				nums[count] = nums[i];
				count++;
			}
		}
		return count;
	}

	/** 35. Search Insert Position */
	public int searchInsert(int[] nums, int target) {
		int result = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			if (target > nums[i]) {
				result = 1 + i;
				break;
			}
		}
		return result;
	}

	/** 70. Climbing Stairs */
	public int climbStairs(int n) {
		int result = 1;
		int a = 1;
		int b = 1;
		for (int i = 1; i < n; i++) {
			a = b;
			b = result;
			result += a;
		}
		return result;
	}

	/** 83. Remove Duplicates from Sorted List */
	public ListNode deleteDuplicates(ListNode head) {
		if (head != null) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(head.val);
			ListNode tmp = head.next;
			ListNode pre = head;
			while (tmp != null) {
				if (set.contains(tmp.val)) {
					pre.next = tmp.next;
				} else {
					pre = tmp;
					set.add(tmp.val);
				}
				tmp = pre.next;
			}
		}
		return head;
	}

	/** 13. Roman to Integer */
	public int romanToInt(String s) {
		int result = 0;
		StringBuffer sb = new StringBuffer(s);
		String[][] arr = new String[][] { { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" }, { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
				{ "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" }, { "", "M", "MM", "MMM" } };
		for (int i = arr.length - 1; i >= 0; i--) {
			String[] arrTmp = arr[i];
			for (int j = arrTmp.length - 1; j > 0; j--) {
				if (sb.indexOf(arrTmp[j]) == 0) {
					sb.delete(0, arrTmp[j].length());
					result += j * Math.pow(10, i);
					break;
				}
			}
		}
		return result;
	}

	/** 12. Integer to Roman */
	public String intToRoman(int num) {
		String[][] arr = new String[][] { { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" }, { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
				{ "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" }, { "", "M", "MM", "MMM" } };
		StringBuilder roman = new StringBuilder();
		roman.append(arr[3][num / 1000 % 10]);
		roman.append(arr[2][num / 100 % 10]);
		roman.append(arr[1][num / 10 % 10]);
		roman.append(arr[0][num % 10]);
		return roman.toString();
	}

	/** 541. Reverse String II */
	public String reverseStr(String s, int k) {
		StringBuffer sb = new StringBuffer(s);
		int count = sb.length() / k + (sb.length() % k == 0 ? 0 : 1);
		for (int i = 0; i < count; i = i + 2) {
			int start = k * i;
			int end = (start + k) > s.length() ? s.length() : (start + k);
			StringBuffer sbTmp = new StringBuffer(sb.substring(start, end));
			sb.replace(start, end, sbTmp.reverse().toString());
		}
		return sb.toString();
	}

	/** 624. Maximum Distance in Arrays */
	public int maxDistance(List<List<Integer>> arrays) {
		String[] arr = new String[20000];
		for (int i = 0; i < arrays.size(); i++) {
			List<Integer> list = arrays.get(i);
			int min = list.get(0);
			int max = list.get(list.size() - 1);
			arr[min + 10000] = arr[min + 10000] == null ? ("" + i) : (arr[min + 10000] + "," + i);
			if (list.size() > 1) {
				arr[max + 10000] = arr[max + 10000] == null ? ("" + i) : (arr[max + 10000] + "," + i);
			}
		}

		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				for (int j = arr.length - 1; j >= 0; j--) {
					if (arr[j] != null) {
						if (!arr[i].equals(arr[j])) {
							return Math.abs(j - i);
						}
					}
				}
			}
		}
		return 0;
	}

	/** 100. Same Tree */
	public boolean isSameTree(TreeNode p, TreeNode q) {
		boolean result = true;
		if (p == null && q == null) {
			return true;
		} else if (p != null && q != null) {
			result = result && q.val == p.val && isSameTree(q.left, p.left) && isSameTree(p.right, q.right);
		} else {
			return false;
		}
		return result;
	}

	/** 404. Sum of Left Leaves */
	public int sumOfLeftLeaves(TreeNode root) {
		return sumOfLeftLeavesMethod(root, false);
	}

	public int sumOfLeftLeavesMethod(TreeNode node, boolean left) {
		int count = 0;
		if (node != null) {
			if (node.left == null && node.right == null && left) {
				count += node.val;
			} else {
				count += sumOfLeftLeavesMethod(node.left, true);
				count += sumOfLeftLeavesMethod(node.right, false);
			}
		}
		return count;
	}

	/** 349. Intersection of Two Arrays */
	public int[] intersection(int[] nums1, int[] nums2) {
		int[] numS = nums1.length > nums2.length ? nums2 : nums1;
		int[] numL = nums1.length <= nums2.length ? nums2 : nums1;
		int[] tmp = new int[numS.length];
		Set<Integer> set = new HashSet<Integer>();
		int count = 0;
		for (int i = 0; i < numS.length; i++) {
			set.add(numS[i]);
		}
		for (int i = 0; i < numL.length; i++) {
			if (set.remove(numL[i])) {
				tmp[count++] = numL[i];
			}
		}
		int[] result = new int[count];
		System.arraycopy(tmp, 0, result, 0, count);
		return result;
	}

	/** 191. Number of 1 Bits */
	public int hammingWeight(int n) {
		return Integer.bitCount(n);
	}

	/** 231. Power of Two */
	public boolean isPowerOfTwo(int n) {
		return n > 0 ? Integer.bitCount(n) == 1 : false;
	}

	/** 326. Power of Three */
	public boolean isPowerOfThree(int n) {
		if (n > 0) {
			String three = Integer.toString(n, 3) + "0";
			char[] chars = three.toCharArray();
			if (chars[0] == '1') {
				for (int i = 1; i < chars.length; i++) {
					if (chars[i] != '0') {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/** 48. Rotate Image */
	public void rotate(int[][] matrix) {
		int N = matrix.length;
		int middleX = N / 2;
		int middleY = N % 2 == 0 ? N / 2 : (N / 2 + 1);
		for (int i = 0; i < middleX; i++) {
			for (int j = 0; j < middleY; j++) {
				int tmp = matrix[i][j];
				int i1 = j;
				int j1 = N - i - 1;
				int i2 = j1;
				int j2 = N - i1 - 1;
				int i3 = j2;
				int j3 = N - i2 - 1;
				matrix[i][j] = matrix[i3][j3];
				matrix[i3][j3] = matrix[i2][j2];
				matrix[i2][j2] = matrix[i1][j1];
				matrix[i1][j1] = tmp;
			}
		}
	}

	/** 506. Relative Ranks */
	public String[] findRelativeRanks(int[] nums) {
		String[] res = new String[nums.length];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}
		Arrays.sort(nums);
		for (int i = 0; i < nums.length; i++) {
			String ss = (i == nums.length - 1) ? "Gold Medal" : (i == nums.length - 2) ? "Silver Medal" : (i == nums.length - 3) ? "Bronze Medal" : ("" + (nums.length - i));
			res[map.get(nums[i])] = ss;
		}
		return res;
	}

	/** 112. Path Sum */
	public boolean hasPathSum(TreeNode root, int sum) {
		boolean result = false;
		if (root != null) {
			result = hasPathSumMethod(root, root.val, sum);
		}
		return result;
	}

	private boolean hasPathSumMethod(TreeNode node, int currentSum, int sum) {
		boolean result = false;
		if (node != null) {
			if (node.left == null && node.right == null) {
				return currentSum == sum;
			}
			if (node.left != null) {
				result = result || hasPathSumMethod(node.left, currentSum + node.left.val, sum);
			}
			if (node.right != null) {
				result = result || hasPathSumMethod(node.right, currentSum + node.right.val, sum);
			}
		}
		return result;
	}

	/*** 113. Path Sum II **/
	public List<List<Integer>> pathSumII(TreeNode root, int sum) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if (root != null) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(root.val);
			pathSumIIMethod(root, root.val, sum, result, list);
		}
		return result;
	}

	private void pathSumIIMethod(TreeNode node, int currentSum, int sum, List<List<Integer>> result, List<Integer> list) {
		if (node.left == null && node.right == null && currentSum == sum) {
			result.add(list);
		}
		if (node.left != null) {
			List<Integer> listLeft = new ArrayList<Integer>(list);
			listLeft.add(node.left.val);
			pathSumIIMethod(node.left, currentSum + node.left.val, sum, result, listLeft);
		}
		if (node.right != null) {
			List<Integer> listRight = new ArrayList<Integer>(list);
			listRight.add(node.right.val);
			pathSumIIMethod(node.right, currentSum + node.right.val, sum, result, listRight);
		}
	}

	/** 437. Path Sum III */
	public int pathSumIII(TreeNode root, int sum) {
		int count = 0;
		if (root != null) {
			count = pathSumIIIMethod(root, root.val, sum, true);
		}
		return count;
	}

	private int pathSumIIIMethod(TreeNode node, int currentSum, int sum, boolean openNew) {
		int count = 0;
		if (currentSum == sum) {
			count++;
		}
		if (node != null && node.left != null) {
			count += pathSumIIIMethod(node.left, currentSum + node.left.val, sum, false);
		}
		if (node != null && node.right != null) {
			count += pathSumIIIMethod(node.right, currentSum + node.right.val, sum, false);
		}
		if (openNew && node != null && node.left != null) {
			count += pathSumIII(node.left, sum);
		}
		if (openNew && node != null && node.right != null) {
			count += pathSumIII(node.right, sum);
		}
		return count;
	}

	/** 617. Merge Two Binary Trees */
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) {
			return null;
		} else if (t1 == null) {
			t1 = new TreeNode(t2.val);
		} else {
			t1.val = t1.val + (t2 == null ? 0 : t2.val);
		}
		t1.left = mergeTrees(t1.left, t2 == null ? null : t2.left);
		t1.right = mergeTrees(t1.right, t2 == null ? null : t2.right);
		return t1;
	}

	/** 38. Count and Say */
	public String countAndSay(int n) {
		String start = "1";
		for (int i = 0; i < n - 1; i++) {
			StringBuilder sb = new StringBuilder();
			char[] arr = start.toCharArray();
			int count = 1;
			char tmp = arr[0];
			for (int j = 1; j < arr.length; j++) {
				if (arr[j] == tmp) {
					count++;
				} else {
					sb.append(count).append(tmp);
					tmp = arr[j];
					count = 1;
				}
			}
			sb.append(count).append(tmp);
			start = sb.toString();
		}
		return start;
	}

	/** 328. Odd Even Linked List */
	public ListNode oddEvenList(ListNode head) {
		if (head != null) {
			oddEvenListMethod(2, head, head, head.next);
		}
		return head;
	}

	public void oddEvenListMethod(int current, ListNode lastOdd, ListNode preNode, ListNode currNode) {
		if (currNode != null) {
			if (current % 2 == 1) {
				ListNode tmp = lastOdd.next;
				preNode.next = currNode.next;
				lastOdd.next = currNode;
				currNode.next = tmp;

				lastOdd = currNode;
				currNode = preNode.next;
			} else {
				ListNode tmp = currNode.next;
				preNode = currNode;
				currNode = tmp;
			}
			oddEvenListMethod(current + 1, lastOdd, preNode, currNode);
		}
	}

	/** 540. Single Element in a Sorted Array */
	public int singleNonDuplicate(int[] nums) {
		if (nums.length == 1) {
			return nums[0];
		} else if (nums.length > 1) {
			for (int i = 0; i < nums.length; i = i + 2) {
				if ((i + 1 < nums.length && nums[i] != nums[i + 1]) || i + 1 == nums.length) {
					return nums[i];
				}
			}
		}
		return 0;
	}

	/** 453. Minimum Moves to Equal Array Elements (add one to all the other elements is equivalent to subtract one from current element) */
	public int minMoves(int[] nums) {
		int count = 0;
		int min = nums[0];
		for (int i = 0; i < nums.length; i++) {
			min = nums[i] < min ? nums[i] : min;
			count = count + nums[i];
		}
		return count - nums.length * min;
	}

	/** 504. Base 7 */
	public String convertToBase7(int num) {
		if (num == 0) {
			return "0";
		}
		StringBuilder sb = new StringBuilder();
		boolean less0 = num < 0;
		num = Math.abs(num);
		while (num > 0) {
			int less = num % 7;
			num = num / 7;
			sb.insert(0, less);
		}
		String result = (less0 ? "-" : "") + sb.toString();
		return result;
	}

	public int[] intersect(int[] nums1, int[] nums2) {
		int[] longer = nums1.length > nums2.length ? nums1 : nums2;
		int[] shorter = nums1.length <= nums2.length ? nums1 : nums2;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < shorter.length; i++) {
			Integer curr = map.get(shorter[i]);
			map.put(shorter[i], curr == null ? 1 : (curr + 1));
		}
		int count = 0;
		for (int i = 0; i < longer.length; i++) {
			Integer curr = map.get(longer[i]);
			if (curr != null && curr > 0) {
				shorter[count++] = longer[i];
				map.put(longer[i], curr - 1);
			}
		}
		int[] res = new int[count];
		System.arraycopy(shorter, 0, res, 0, count);
		return res;
	}

	/** 268. Missing Number */
	public int missingNumber(int[] nums) {
		long result = nums.length;
		for (int i = 0; i < nums.length; i++) {
			result = result + i - nums[i];
		}
		return (int) result;
	}

	/** 371. Sum of Two Integers */
	public int getSum(int a, int b) {
		int ino = (a & b) << 1;
		int result = a ^ b;
		while (ino != 0) {
			int tmp = ino & result;
			result = ino ^ result;
			ino = tmp << 1;
		}
		return result;
	}

	private String toBinaryString(int n, int len) {
		StringBuilder result = new StringBuilder(len);
		result.append(Integer.toBinaryString(n));
		while (result.length() < len) {
			result.insert(0, '0');
		}
		return result.toString();
	}

	/** 169. Majority Element */
	public int majorityElement(int[] nums) {
		if (nums.length == 1) {
			return nums[0];
		}
		Arrays.sort(nums);
		int count = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			count = nums[i] == nums[i + 1] ? count + 1 : 0;
			if (count + 1 > nums.length / 2) {
				return nums[i];
			}
		}
		return 0;
	}

	/** 171. Excel Sheet Column Number */
	public int titleToNumber(String s) {
		char[] chars = s.toCharArray();
		int result = 0;
		for (int i = 0; i < chars.length; i++) {
			result *= 26;
			result += (chars[i] + 1 - 'A');
		}
		return result;
	}

	/** 60. Permutation Sequence */
	public String getPermutation(int n, int k) {
		String s = "";
		k = k - 1;
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++) {
			list.add(i);
		}
		for (int i = 1; i <= n; i++) {
			int curr = getPermutationMethod(n - i);
			int poi = k / curr;
			k = k % curr;
			s += list.remove(poi);
		}
		return s;
	}

	private int getPermutationMethod(int n) {
		int res = 1;
		for (int i = 1; i <= n; i++) {
			res *= i;
		}
		return res;
	}

	/** 387. First Unique Character in a String */
	public int firstUniqChar(String s) {
		int index = -1;
		char[] chars = s.toCharArray();
		int[] arr = new int['z' + 200];
		for (int i = 0; i < chars.length; i++) {
			arr[chars[i]]++;
		}
		for (int i = 0; i < chars.length; i++) {
			if (arr[chars[i]] == 1) {
				index = i;
				break;
			}
		}
		return index;
	}

	/** 386. Lexicographical Numbers */
	public List<Integer> lexicalOrder(int n) {
		int num = 0;
		for (int i = 10; i > 0; i--) {
			if (n > Math.pow(10, i)) {
				num = i;
				break;
			}
		}
		Integer min = (int) Math.pow(10, num);
		Integer[] arr = new Integer[n];
		for (int i = 1; i <= n; i++) {
			Integer dou = i;
			while (dou < min) {
				dou = dou * 10;
			}
			arr[i - 1] = dou;
		}
		Arrays.sort(arr);
		boolean skip = false;
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] == n && !skip) {
				skip = true;
				continue;
			}
			if (arr[i] % 10 == 0 && (arr[i] > n || arr[i] >= arr[i + 1])) {
				arr[i] = arr[i] / 10;
				i = i + 1;
			}
		}
		return Arrays.asList(arr);
	}

	/** 263. Ugly Number */
	public boolean isUgly(int num) {
		for (int i = 2; i < 6 && num > 0; i++)
			while (num % i == 0)
				num /= i;
		return num == 1;
	}

	/** 判断n是否是质数 */
	private boolean isPrime(int n) {
		boolean isPrime = true;
		int s = (int) Math.sqrt(n);
		for (int i = s; i > 1; i--) {
			if (n % i == 0) {
				isPrime = false;
			}
		}
		return isPrime;
	}

	/** 202. Happy Number */
	public boolean isHappy(int n) {
		Set<Integer> set = new HashSet<Integer>();
		while (n != 1 && !set.contains(n)) {
			set.add(n);
			int result = 0;
			while (n > 9) {
				result += Math.pow(n % 10, 2);
				n = n / 10;
			}
			result += Math.pow(n, 2);
			n = result;
		}
		return n == 1;
	}

	/** 92. Reverse Linked List II */
	public ListNode reverseBetween(ListNode head, int m, int n) {
		ListNode tmp = head;
		ListNode begin = null;
		int[] arr = new int[n + 1 - m];
		for (int i = 1; i < n + 1; i++) {
			if (i >= m) {
				arr[i - m] = tmp.val;
				begin = i == m ? tmp : begin;
			}
			tmp = tmp.next;
		}
		for (int i = 0; i < arr.length; i++) {
			begin.val = arr[arr.length - i - 1];
			begin = begin.next;
		}
		return head;
	}

	/** 217. Contains Duplicate */
	public boolean containsDuplicate(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (set.contains(nums[i])) {
				return true;
			} else {
				set.add(nums[i]);
			}
		}
		return false;
	}

	/** 234. Palindrome Linked List */
	public boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null) {
			return true;
		}
		Stack<ListNode> stack = new Stack<ListNode>();
		ListNode tmp = head;
		int count = 0;
		while (tmp != null) {
			count++;
			stack.push(tmp);
			tmp = tmp.next;
		}
		tmp = head;
		int curr = 0;
		while (tmp != null) {
			ListNode cc = stack.pop();
			if (cc.val != tmp.val) {
				return false;
			}
			curr++;
			if (curr > count / 2 + 1) {
				break;
			}
			tmp = tmp.next;
		}
		return true;
	}

	/** 108. Convert Sorted Array to Binary Search Tree */
	public TreeNode sortedArrayToBST(int[] nums) {
		TreeNode root = null;
		if (nums.length > 0) {
			root = new TreeNode(nums[0]);
			List<TreeNode> list = new ArrayList<TreeNode>();
			int count = 1;
			list.add(root);
			for (int i = 0; i < nums.length; i++) {
				TreeNode node = list.remove(0);
				node.val = nums[i];
				if (count++ < nums.length) {
					node.left = new TreeNode(0);
					list.add(node.left);
				}
				if (count++ < nums.length) {
					node.right = new TreeNode(0);
					list.add(node.right);
				}
			}
		}
		return root;
	}

	/** 222. Count Complete Tree Nodes **/
	public int countNodes(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int depthLeft = getDepth(root.left);
		int depthRight = getDepth(root.right);
		if (depthLeft == depthRight) {
			return (1 << depthLeft) + countNodes(root.right);
		} else {
			return (1 << depthRight) + countNodes(root.left);
		}
	}

	private int getDepth(TreeNode node) {
		int depth = 0;
		while (node != null) {
			depth++;
			node = node.left;
		}
		return depth;
	}

	/** 66. Plus One */
	public int[] plusOne(int[] digits) {
		boolean add = true;
		for (int i = digits.length - 1; i >= 0; i--) {
			add = add && digits[i] == 9;
			if (add) {
				digits[i] = 0;
			} else {
				digits[i] = digits[i] + 1;
				break;
			}
		}
		if (add) {
			digits = new int[digits.length + 1];
			digits[0] = 1;
		}
		return digits;
	}

	/** 373. Find K Pairs with Smallest Sums */
	public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < nums1.length; i++) {
			for (int j = 0; j < nums2.length; j++) {
				list.add(new int[] { nums1[i], nums2[j] });
			}
		}
		Collections.sort(list, new kSmallestPairsComparable());
		if (k < list.size()) {
			list = list.subList(0, k);
		}
		return list;
	}

	private class kSmallestPairsComparable implements Comparator<int[]> {

		public int compare(int[] o1, int[] o2) {
			return o1[0] + o1[1] - (o2[0] + o2[1]);
		}

	}

	/** 17. Letter Combinations of a Phone Number */
	public List<String> letterCombinations(String digits) {
		List<String> listResult = new ArrayList<String>();
		String[] init = new String[] { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
		int count = 1;
		String all = "";
		char[] chars = digits.toCharArray();
		for (char c : chars) {
			String ss = init[Character.getNumericValue(c)];
			count = count * ss.length();
			all += ss + ",";
		}
		if (count > 1) {
			String[] arr = all.split(",");
			for (int i = 0; i < count; i++) {
				String letters = letterCombinationsGet(i, arr);
				if (!listResult.contains(letters)) {
					listResult.add(letters);
				}
			}
		}
		return listResult;
	}

	public String letterCombinationsGet(int index, String[] arr) {
		StringBuilder sb = new StringBuilder(arr.length);
		for (int i = 0; i < arr.length; i++) {
			String tmp = arr[i];
			sb.append(tmp.charAt(index % tmp.length()));
			index = index / tmp.length();
		}
		return sb.toString();
	}

	public boolean canConstruct(String ransomNote, String magazine) {
		StringBuffer sb = new StringBuffer(magazine);
		char[] chars = ransomNote.toCharArray();
		for (char ch : chars) {
			int index = sb.indexOf("" + ch);
			if (index < 0) {
				return false;
			} else {
				sb.deleteCharAt(index);
			}
		}
		return true;
	}

	/** 119. Pascal's Triangle II */
	public List<Integer> getRow(int rowIndex) {
		int len = rowIndex + 1;
		Integer[] arr = new Integer[len];
		for (int i = 0; i < len; i++) {
			arr[i] = 1;
			for (int j = i - 1; j > 0; j--) {
				arr[j] = arr[j] + arr[j - 1];
			}
		}
		List<Integer> listResult = Arrays.asList(arr);
		return listResult;
	}

	/** 118. Pascal's Triangle */
	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		Integer[] arr = new Integer[numRows];
		for (int i = 0; i < numRows; i++) {
			arr[i] = 1;
			for (int j = i - 1; j > 0; j--) {
				arr[j] = arr[j] + arr[j - 1];
			}
			// 复制出列表
			List<Integer> listTmp = new ArrayList<Integer>();
			for (int j = 0; j < i + 1; j++) {
				listTmp.add(arr[j]);
			}
			result.add(listTmp);
		}
		return result;
	}

	/** 591. Tag Validator */
	public boolean isValid(String code) {
		if (!code.startsWith("<")) {
			return false;
		}
		int cdataBegin = -1;
		while ((cdataBegin = code.indexOf("<![CDATA[")) > 0) {
			int cdataEnd = code.indexOf("]]>");
			if (cdataEnd < 0 || cdataEnd + 3 == code.length()) {
				return false;
			}
			code = code.substring(0, cdataBegin) + code.substring(cdataEnd + 3);
		}
		Stack<String> stack = new Stack<String>();
		for (int i = 0; i < code.length();) {
			if (i > 0 && stack.isEmpty()) {
				return false;
			}
			if (code.startsWith("</", i)) {
				int j = i + 2;
				i = code.indexOf('>', j);
				if (i < 0 || i == j || i - j > 9)
					return false;
				for (int k = j; k < i; k++) {
					if (!Character.isUpperCase(code.charAt(k)))
						return false;
				}
				String s = code.substring(j, i++);
				if (stack.isEmpty() || !stack.pop().equals(s))
					return false;
				if (stack.isEmpty() && i != code.length()) {
					return false;
				}
			} else if (code.startsWith("<", i)) {
				int j = i + 1;
				i = code.indexOf('>', j);
				if (i < 0 || i == j || i - j > 9)
					return false;
				for (int k = j; k < i; k++) {
					if (!Character.isUpperCase(code.charAt(k)))
						return false;
				}
				String s = code.substring(j, i++);
				stack.push(s);
			} else {
				i++;
			}
		}
		return stack.isEmpty();
	}

	/** 609. Find Duplicate File in System */
	public List<List<String>> findDuplicate(String[] paths) {
		List<List<String>> listResult = new ArrayList<List<String>>();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String files : paths) {
			String[] fileArr = files.split(" ");
			String filePath = fileArr[0];
			for (int i = 1; i < fileArr.length; i++) {
				String file = fileArr[i];
				int con = file.indexOf("(");
				String fileName = file.substring(0, con);
				String fileContent = file.substring(con + 1);
				List<String> list = map.get(fileContent);
				list = list == null ? new ArrayList<String>() : list;
				list.add(filePath + "/" + fileName);
				map.put(fileContent, list);
			}
		}
		Collection<List<String>> values = map.values();
		for (List<String> list : values) {
			if (list.size() > 1) {
				listResult.add(list);
			}
		}
		return listResult;
	}

	public String tree2str(TreeNode t) {
		String s = "";
		if (t != null) {
			if (t.left != null || t.right != null) {
				s += t.left == null ? "()" : "(" + tree2str(t.left) + ")";
				s += t.right == null ? "" : "(" + tree2str(t.right) + ")";
			}
			s = t.val + s;
		}
		return s;
	}

	/** 605. Can Place Flowers */
	public boolean canPlaceFlowers(int[] flowerbed, int n) {
		if (flowerbed.length == 0) {
			return false;
		}
		if (n == 0) {
			return true;
		}
		if (flowerbed.length == 1) {
			return flowerbed[0] == 0 && n <= 1;
		}
		if (flowerbed.length == 2) {
			return flowerbed[0] == 0 && flowerbed[1] == 0 && n <= 1;
		}
		int count = 0;
		for (int i = 0; i < flowerbed.length; i++) {
			if (i == 0) {
				if (flowerbed[0] == 0 && flowerbed[1] == 0) {
					flowerbed[0] = 1;
					count++;
				}
				continue;
			}
			if (i == flowerbed.length - 1) {
				if (flowerbed[flowerbed.length - 1] == 0 && flowerbed[flowerbed.length - 2] == 0) {
					count++;
				}
				break;
			}
			if (flowerbed[i] == 0 && flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
				flowerbed[i] = 1;
				count++;
			}
		}
		return n <= count;
	}

	/** 538. Convert BST to Greater Tree */
	public TreeNode convertBST(TreeNode root) {
		Set<Integer> set = new HashSet<Integer>();
		convertBSTMethod1(set, root);
		Integer[] nums = set.toArray(new Integer[] {});
		Arrays.sort(nums);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int count = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			count += nums[i];
			map.put(nums[i], count);
		}
		convertBSTMethod2(map, root);
		return root;
	}

	private void convertBSTMethod1(Set<Integer> set, TreeNode root) {
		if (root != null) {
			set.add(root.val);
			convertBSTMethod1(set, root.left);
			convertBSTMethod1(set, root.right);
		}
	}

	private void convertBSTMethod2(Map<Integer, Integer> map, TreeNode root) {
		if (root != null) {
			root.val = map.get(root.val);
			convertBSTMethod2(map, root.left);
			convertBSTMethod2(map, root.right);
		}
	}

	/** 442. Find All Duplicates in an Array */
	public List<Integer> findDuplicates(int[] nums) {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			int n = Math.abs(nums[i]);
			int tmp = nums[n - 1];
			if (tmp < 0) {
				result.add(n);
			}
			nums[n - 1] = -nums[n - 1];
		}
		return result;
	}

	/** 515. Find Largest Value in Each Tree Row */
	public List<Integer> largestValues(TreeNode root) {
		List<Integer> result = new ArrayList<Integer>();
		largestValuesMethod(result, 0, root);
		return result;
	}

	public void largestValuesMethod(List<Integer> result, int curr, TreeNode node) {
		if (node != null) {
			if (curr >= result.size()) {
				result.add(node.val);
			} else {
				if (result.get(curr) < node.val) {
					result.set(curr, node.val);
				}
			}

			largestValuesMethod(result, curr + 1, node.left);
			largestValuesMethod(result, curr + 1, node.right);
		}
	}

	/** 406. Queue Reconstruction by Height */
	public int[][] reconstructQueue(int[][] people) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < people.length; i++) {
			for (int j = 0; j < people.length; j++) {
				int[] pj = people[j];
				if (pj[1] == i) {
					int count = 0;
					for (int k = 0; k < list.size(); k++) {
						int[] tmp = list.get(k);
						if (pj[0] <= tmp[0]) {
							if (count++ >= i) {
								list.add(k, pj);
								count = -1;
								break;
							}
						}
					}
					if (count >= 0) {
						list.add(pj);
					}
				}
			}
		}
		return list.toArray(new int[][] {});
	}

	/** 526. Beautiful Arrangement */
	public int countArrangement(int N) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i % j == 0 || j % i == 0) {
					List<Integer> list = map.get(i) == null ? new ArrayList<Integer>() : map.get(i);
					list.add(j);
					map.put(i, list);
				}
			}
		}
		int count = countArrangementMethod(0, new int[N + 1], map);
		return count;
	}

	public int countArrangementMethod(int currPosi, int[] flag, Map<Integer, List<Integer>> map) {
		int count = 0;
		if (currPosi == flag.length - 1) {
			return 1;
		}
		List<Integer> list = map.get(currPosi + 1);
		for (int i = 0; i < list.size(); i++) {
			int use = list.get(i);
			if (flag[use] == 0) {
				flag[use] = 1;
				count += countArrangementMethod(currPosi + 1, flag, map);
				flag[use] = 0;
			}
		}
		return count;
	}

	/** 553. Optimal Division */
	public String optimalDivision(int[] nums) {
		if (nums.length == 1) {
			return "" + nums[0];
		}
		if (nums.length == 2) {
			return "" + nums[0] + "/" + nums[1];
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nums.length; i++) {
			sb.append(nums[i] + (i == nums.length - 1 ? "" : "/"));
		}
		sb.insert(sb.indexOf("/") + 1, "(").append(")");
		return sb.toString();
	}

	/** 513. Find Bottom Left Tree Value */
	public int findBottomLeftValue(TreeNode root) {
		int[] res = new int[2];
		findBottomLeftValueMethod(res, 1, root);
		return res[1];
	}

	public void findBottomLeftValueMethod(int[] res, int curr, TreeNode node) {
		if (node != null) {
			if (curr > res[0]) {
				res[0] = curr;
				res[1] = node.val;
			}
			findBottomLeftValueMethod(res, curr + 1, node.left);
			findBottomLeftValueMethod(res, curr + 1, node.right);
		}
	}

	/** 338. Counting Bits */
	public int[] countBits(int num) {
		int[] res = new int[num + 1];
		for (int i = 0; i <= num; i++) {
			res[i] = Integer.bitCount(i);
		}
		return res;
	}

	/** 537. Complex Number Multiplication */
	public String complexNumberMultiply(String a, String b) {
		String[] aArr = a.replaceAll("i", "").split("\\+");
		String[] bArr = b.replaceAll("i", "").split("\\+");
		int aa = Integer.valueOf(aArr[0]);
		int ab = Integer.valueOf(aArr[1]);
		int ba = Integer.valueOf(bArr[0]);
		int bb = Integer.valueOf(bArr[1]);

		int resA = aa * ba + (-1 * ab * bb);
		int resb = aa * bb + ba * ab;
		String res = resA + "+" + resb + "i";
		return res;
	}

	/** 189. Rotate Array */
	public void rotate(int[] nums, int k) {
		if (nums == null || nums.length == 0 || k == 0) {
			return;
		}
		int kt = k % nums.length;
		int[] tmp = new int[kt];
		System.arraycopy(nums, nums.length - kt, tmp, 0, kt);
		System.arraycopy(nums, 0, nums, kt, nums.length - kt);
		System.arraycopy(tmp, 0, nums, 0, tmp.length);
	}

	/** 530. Minimum Absolute Difference in BST */
	public int getMinimumDifference(TreeNode root) {
		int res = Integer.MAX_VALUE;
		List<Integer> list = new ArrayList<Integer>();
		turnToList(list, root);
		Integer[] arr = list.toArray(new Integer[] {});
		Arrays.sort(arr);
		for (int i = 0; i < arr.length - 1; i++) {
			res = Math.min(res, Math.abs(arr[i] - arr[i + 1]));
		}
		return res;
	}

	public void turnToList(List<Integer> list, TreeNode root) {
		if (root != null) {
			list.add(root.val);
			turnToList(list, root.left);
			turnToList(list, root.right);
		}
	}

	/** 167. Two Sum II - Input array is sorted */
	public int[] twoSum(int[] numbers, int target) {
		int[] res = null;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			Integer first = map.get(target - numbers[i]);
			map.put(numbers[i], i);
			if (first != null && first != i) {
				res = new int[] { first + 1, i + 1 };
				break;
			}
		}
		return res;
	}

	/** 492. Construct the Rectangle */
	public int[] constructRectangle(int area) {
		int[] res = new int[2];
		int sqrt = (int) Math.sqrt(area);
		for (int i = sqrt; i > 0; i--) {
			if (area % i == 0) {
				int l = area / i;
				res[0] = l;
				res[1] = i;
				break;
			}
		}
		return res;
	}

	/** 283. Move Zeroes */
	public void moveZeroes(int[] nums) {
		int curr = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != 0) {
				nums[curr++] = nums[i];
			}
		}
		for (int i = curr; i < nums.length; i++) {
			nums[i] = 0;
		}
	}

	/** 258. Add Digits */
	public int addDigits(int num) {
		int res = 0;
		while (num > 0) {
			res += num % 10;
			num = num / 10;
		}
		return res >= 10 ? addDigits(res) : res;
	}

	/** 389. Find the Difference */
	public char findTheDifference(String s, String t) {
		if (s != null && s.length() > 0) {
			char[] chars = s.toCharArray();
			for (char c : chars) {
				t = t.replaceFirst("" + c, "");
			}
		}
		return t.length() > 0 ? t.charAt(0) : 0;
	}

	public TreeNode invertTree(TreeNode root) {
		if (root != null) {
			TreeNode tmp = root.left;
			root.left = root.right;
			root.right = tmp;
			invertTree(root.left);
			invertTree(root.right);
		}
		return root;
	}

	/** 104. Maximum Depth of Binary Tree */
	public int maxDepth(TreeNode root) {
		int res = depthCount(0, root);
		return res;
	}

	public int depthCount(int curr, TreeNode node) {
		int resl = 0, resr = 0;
		if (node != null) {
			resl = depthCount(curr + 1, node.left);
			resr = depthCount(curr + 1, node.right);
		} else {
			return curr;
		}
		return resl > resr ? resl : resr;
	}

	/** 448. Find All Numbers Disappeared in an Array<a href='https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/#/description'>Link</a> */
	public List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> listResult = new ArrayList<Integer>();
		if (nums == null || nums.length == 0) {
			return listResult;
		}
		Arrays.sort(nums);
		int currPosi = 0;
		for (int i = 1; i <= nums.length; i++) {
			while (currPosi < nums.length) {
				int curr = nums[currPosi];
				if (curr == i) {
					currPosi++;
					break;
				}
				if (curr > i) {
					listResult.add(new Integer(i));
					break;
				}
				currPosi++;
			}
			if (currPosi == nums.length && nums[nums.length - 1] != i) {
				listResult.add(new Integer(i));
			}
		}
		return listResult;
	}

	public List<Integer> findDisappearedNumbers1(int[] nums) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			int index = Math.abs(nums[i]) - 1;
			if (nums[index] > 0)
				nums[index] = -nums[index];
		}
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > 0)
				list.add(i + 1);
		}
		return list;
	}

	/** 520. Detect Capital<a href='https://leetcode.com/problems/detect-capital/#/description'>Link</a> */
	public boolean detectCapitalUse(String word) {
		int charZ = 'Z' + 1;
		if (word == null || word.length() == 0) {
			return false;
		}
		char[] chars = word.toCharArray();
		int count = 0;
		for (int i = 0; i < chars.length; i++) {
			count += chars[i] < charZ ? 1 : 0;
		}
		if (chars[0] < charZ) {
			return count == chars.length || count == 1;
		} else {
			return count == 0;
		}
	}

	/** 599. Minimum Index Sum of Two Lists<a href='https://leetcode.com/problems/minimum-index-sum-of-two-lists/#/description'>Link</a> */
	public String[] findRestaurant(String[] list1, String[] list2) {
		int leastIndex = Integer.MAX_VALUE;
		List<String> listResult = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < list2.length; i++) {
			map.put(list2[i], i);
		}
		for (int i = 0; i < list1.length; i++) {
			String curr = list1[i];
			Integer index2 = map.remove(curr);
			if (index2 != null) {
				int currLeastIndex = index2 + i;
				if (currLeastIndex < leastIndex) {
					listResult = new ArrayList<String>();
					listResult.add(curr);
					leastIndex = currLeastIndex;
				} else if (currLeastIndex == leastIndex) {
					listResult.add(curr);
				}
			}
		}
		return listResult.toArray(new String[] {});
	}

	/** 136. Single Number <a href='https://leetcode.com/problems/single-number/#/description'>Link</a> */
	public int singleNumber(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < nums.length; i++) {
			boolean bool = set.remove(nums[i]);
			if (!bool) {
				set.add(nums[i]);
			}
		}
		int res = set.iterator().next();
		return res;
	}

	/** 485. Max Consecutive Ones<a href='https://leetcode.com/problems/max-consecutive-ones/#/description'>Link</a> */
	public int findMaxConsecutiveOnes(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int result = 0;
		int tmp = 0;
		for (int i = 0; i < nums.length; i++) {
			tmp = nums[i] == 1 ? tmp + 1 : 0;
			result = tmp > result ? tmp : result;
		}
		return result;
	}

	/** 292. Nim Game <a href='https://leetcode.com/problems/nim-game/#/description'>Link</a> */
	public boolean canWinNim(int n) {
		return false;
	}

	/** 463. Island Perimeter <a href='https://leetcode.com/problems/island-perimeter/#/description'>Link</a> */
	public int islandPerimeter(int[][] grid) {
		if (grid.length == 0 || grid[0].length == 0) {
			return 0;
		}
		int row = grid.length;
		int col = grid[0].length;
		int sideLen = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (grid[i][j] != 0) {
					sideLen += (i == 0 || grid[i - 1][j] == 0 ? 1 : 0) + (i + 1 == row || grid[i + 1][j] == 0 ? 1 : 0) + (j == 0 || grid[i][j - 1] == 0 ? 1 : 0)
							+ (j + 1 == col || grid[i][j + 1] == 0 ? 1 : 0);
				}
			}
		}
		return sideLen;
	}

	/** 496. Next Greater Element I <a href='https://leetcode.com/problems/next-greater-element-i/#/description'>Link</a> */
	public int[] nextGreaterElement(int[] findNums, int[] nums) {
		if (findNums == null || nums == null || findNums.length == 0 || nums.length <= 1) {
			return new int[] {};
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int findGreater = -1;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] > nums[i]) {
					findGreater = nums[j];
					break;
				}
			}
			map.put(nums[i], findGreater);
		}
		int[] res = new int[findNums.length];
		for (int i = 0; i < findNums.length; i++) {
			res[i] = map.get(findNums[i]) == null ? -1 : map.get(findNums[i]);
		}
		return res;
	}

	/** 344. Reverse String */
	public String reverseString(String s) {
		return s == null ? null : new StringBuffer(s).reverse().toString();
	}

	/** 412. Fizz Buzz */
	public List<String> fizzBuzz(int n) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= n; i++) {
			String res = "";
			if (i % 3 == 0) {
				res += "Fizz";
			}
			if (i % 5 == 0) {
				res += "Buzz";
			}
			if (res.length() == 0) {
				res += i;
			}
			list.add(res);
		}
		return list;
	}

	/** 500. Keyboard Row */
	public String[] findWords(String[] words) {
		List<String> listResult = new ArrayList<String>();
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		String[] boards = new String[] { "qwertyuiopQWERTYUIOP", "asdfghjklASDFGHJKL", "zxcvbnmZXCVBNM" };
		for (int i = 0; i < boards.length; i++) {
			char[] boardStr = boards[i].toCharArray();
			for (char c : boardStr) {
				map.put(c, i);
			}
		}
		for (int i = 0; i < words.length; i++) {
			int res = -1;
			char[] str = words[i].toCharArray();
			for (char c : str) {
				int strB = map.get(c);
				if (res == -1) {
					res = strB;
				} else if (res != strB) {
					res = -1;
					break;
				}
			}
			if (res != -1) {
				listResult.add(words[i]);
			}
		}
		return listResult.toArray(new String[] {});
	}

	/** 557. Reverse Words in a String III */
	public String reverseWords(String s) {
		StringBuilder sb = new StringBuilder(s).reverse();
		int len = sb.length();
		String[] sArr = sb.toString().split(" ");
		sb = new StringBuilder(len);
		for (int i = sArr.length - 1; i >= 0; i--) {
			sb.append(sArr[i]).append(i == 0 ? "" : " ");
		}
		return sb.toString();
	}

	/** 566. Reshape the Matrix */
	public int[][] matrixReshape(int[][] nums, int r, int c) {
		int oldr = nums.length;
		if (oldr == 0) {
			return nums;
		}
		int oldc = nums[0].length;
		if (r * c != oldr * oldc) {
			return nums;
		}
		int[][] result = new int[r][c];
		for (int i = 0; i < r * c; i++) {
			result[i / c][i % c] = nums[i / oldc][i % oldc];
		}
		return result;
	}

	/** 476. Number Complement */
	public int findComplement(int num) {
		if (num == 0) {
			return 1;
		}
		int or = (Integer.highestOneBit(num) << 1) - 1;
		int result = or ^ num;
		return result;
	}

	/** 561. Array Partition I */
	public int arrayPairSum(int[] nums) {
		Arrays.sort(nums);
		int result = 0;
		for (int i = 0; i < nums.length / 2; i++) {
			result += nums[i * 2];
		}
		return result;
	}

	/** 461. Hamming Distance */
	public int hammingDistance(int x, int y) {
		int result = 0;
		int z = x ^ y;
		// result = Integer.bitCount(z);
		char[] s = Integer.toBinaryString(z).toCharArray();
		for (char c : s) {
			if (c == '1') {
				result = result + 1;
			}
		}
		return result;
	}

	/** 7. Reverse Integer */
	public int reverse(int x) {
		StringBuilder sb = new StringBuilder(("" + x).replaceAll("-", "")).reverse();
		String resTmp = sb.toString();
		while (resTmp.startsWith("0")) {
			resTmp = resTmp.substring(1);
		}
		String result = (x < 0 ? "-" : "") + resTmp;
		int ire = 0;
		try {
			ire = Integer.valueOf(result);
			if (!result.equals("" + ire)) {
				ire = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ire;
	}

	/** 45. jump game II */
	public int jump(int[] nums) {
		nums = new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		if (nums.length <= 1) {
			return 0;
		}
		Map<String, Integer> mapRoot = new HashMap<String, Integer>();
		int lessPoi = nums.length - 1;
		for (int i = nums.length - 1; i >= 0; i--) {
			int num = nums[i];
			int currLessPoi = 0;
			int less = i == nums.length - 1 ? -1 : nums.length;
			for (int j = 1; j <= num; j++) {
				int posi = i + j;
				if (posi <= lessPoi) {
					int lessT = mapRoot.get("P" + posi);
					if (lessT < less) {
						less = lessT;
						if (posi != nums.length - 1) {
							currLessPoi = posi;
						}
					}
				}
			}
			if (currLessPoi < lessPoi && currLessPoi != 0) {
				lessPoi = currLessPoi;
			}
			mapRoot.put("P" + i, less + 1);
		}
		int res = mapRoot.get("P0");
		return res;
	}

	public int jump1(int[] nums) {
		if (nums.length <= 1) {
			return 0;
		}
		Map<String, Object> mapRoot = new HashMap<String, Object>();
		for (int i = nums.length - 1; i >= 0; i--) {
			int num = nums[i];
			Map<String, Object> map = new HashMap<String, Object>();
			int less = i == nums.length - 1 ? -1 : nums.length;
			for (int j = 1; j <= num; j++) {
				int posi = i + j;
				if (posi < nums.length) {
					Map<String, Object> tmp = (Map<String, Object>) mapRoot.get("P" + posi);
					int lessT = Integer.valueOf(tmp.get("L").toString());
					less = lessT < less ? lessT : less;
				}
			}
			map.put("L", less + 1);
			mapRoot.put("P" + i, map);
		}
		int res = Integer.valueOf(((Map<String, Object>) mapRoot.get("P0")).get("L").toString());
		return res;
	}

	private int findLast(int curr, int last, Map<String, Object> map) {
		if (map.containsKey("Step" + last)) {
			return curr;
		}
		Collection<Object> tmp = map.values();
		int res = Integer.MAX_VALUE;
		for (Object object : tmp) {
			int tmpRes = findLast(curr + 1, last, (Map<String, Object>) object);
			if (tmpRes < res) {
				res = tmpRes;
			}
		}
		return res;
	}

	/** 4. Median of Two Sorted Arrays */
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int[] total = new int[nums1.length + nums2.length];
		int i = 0;
		int j = 0;
		int curr = 0;
		int end = total.length / 2 + 1;
		while (i < nums1.length || j < nums2.length) {
			int currNum = 0;
			if (i != nums1.length && j != nums2.length && (nums1[i] < nums2[j] || j == nums2.length)) {
				currNum = nums1[i];
				i++;
			} else if (j != nums2.length) {
				currNum = nums2[j];
				j++;
			} else {
				currNum = nums1[i];
				i++;
			}
			total[curr++] = currNum;
			if (curr == end) {
				break;
			}
		}
		return total.length % 2 == 1 ? (double) total[curr - 1] : (total[curr - 1] + total[curr - 2]) / 2.0;
	}

	/** 3. Longest Substring Without Repeating Characters */
	public int lengthOfLongestSubstring(String s) {
		Set<Character> mapKeys = new HashSet<Character>();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			Character ct = s.charAt(i);
			mapKeys.add(ct);
		}
		StringBuilder res = new StringBuilder();
		StringBuilder tmp = new StringBuilder();
		Set<Character> map = new HashSet<Character>();
		for (int i = 0; i < len; i++) {
			tmp = new StringBuilder();
			map = new HashSet<Character>();
			for (int j = i; j < len; j++) {
				Character ne = s.charAt(j);
				if (map.contains(ne)) {
					break;
				} else {
					map.add(ne);
					tmp.append(ne);
				}
			}
			if (tmp.length() > res.length()) {
				res = tmp;
				if (res.length() == mapKeys.size()) {
					break;
				}
			}
		}
		return res.length();
	}

	/** 2. Add Two Numbers */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		boolean add = false;
		ListNode node = null;
		ListNode last = null;
		while (l1 != null || l2 != null) {
			int value = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + (add ? 1 : 0);
			l1 = l1 != null ? l1.next : null;
			l2 = l2 != null ? l2.next : null;
			int tmp = value % 10;
			if (node != null) {
				last.next = new ListNode(tmp);
				last = last.next;
			} else {
				node = new ListNode(tmp);
				last = node;
			}
			add = (value / 10) > 0;
		}
		if (add) {
			last.next = new ListNode(1);
		}
		return node;
	}

	/** 1. Two Sum */
	public int[] twoSum1(int[] nums, int target) {
		int[] result = null;
		for (int i = 0; i < nums.length; i++) {
			int left = target - nums[i];
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] == left) {
					result = new int[] { i, j };
					break;
				}
			}
		}
		return result;
	}
}
