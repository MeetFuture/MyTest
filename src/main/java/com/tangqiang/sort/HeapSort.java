package com.tangqiang.sort;

import edu.princeton.cs.introcs.StdIn;

/**
 * java实现堆排序
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午4:49:12
 * 
 * @version 1.0 2014-7-25 tqiang create
 * 
 */
public class HeapSort {

	public static void sort(Comparable[] a) {
		int N = a.length;
		for (int k = N / 2; k >= 1; k--) {
			sink(a, k, N);
		}
		while (N > 1) {
			exch(a, 1, N--);
			sink(a, 1, N);
		}
	}

	public static void exch(Comparable[] a, int u, int v) {
		Comparable temp = a[u - 1];
		a[u - 1] = a[v - 1];
		a[v - 1] = temp;
	}

	public static void sink(Comparable[] a, int k, int N) {

		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && (a[j - 1].compareTo(a[j])) < 0) {
				j++;
			}
			if (a[k - 1].compareTo(a[j - 1]) >= 0) {
				break;
			}
			exch(a, k, j);
			k = j;
		}
	}

	public static void main(String[] args) {

		// String[] a = In.readStrings();
		// Comparable[] a = new Comparable[5];
		// int j = 0;
		// while (!StdIn.isEmpty())
		// a[j++] = StdIn.readInt();

		String[] a = StdIn.readAllStrings();

		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + ",");
		HeapSort.sort(a);

		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + ",");
	}
}