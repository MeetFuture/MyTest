package com.tangqiang.test;

public class TestClass {
	static int i = 0;

	public static void main(String[] args) {
		TestClass test = new TestClass();
		test.aMethod();
		test.getClass();
		int j = test.aMethod();
		System.out.println(j);

		System.out.println(System.nanoTime());
		System.out.println(System.currentTimeMillis());

		int[] a[] = new int[10][10];
		a[2][2] = 1;
	}

	public int aMethod() {
		i++;
		return i;
	}

}
