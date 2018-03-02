package com.tangqiang.math;

public class Russian {

	public static void main(String[] args) {
		long m = 643256431655452869l;
		long n = 689132457865123556l;

		long beginTime = System.nanoTime();
		long r = doRussian(m, n);
		long endTime = System.nanoTime();
		System.out.println((endTime - beginTime) + "ns  " + r);

		long beginTime1 = System.nanoTime();
		long r1 = m * n;
		long endTime1 = System.nanoTime();
		System.out.println((endTime1 - beginTime1) + "ns  " + r1);
	}

	public static long doRussian(long m, long n) {
		long s = 0;
		while (m > 0) {
			if (1 == (m & 1)) {
				s += n;
			}
			m = m >> 1;
			n = n << 1;
		}
		return s;
	}
}
