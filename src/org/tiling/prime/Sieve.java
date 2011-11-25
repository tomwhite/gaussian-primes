package org.tiling.prime;

import java.util.Arrays;

public class Sieve {

	int limit;
	boolean[] sieve;

	public Sieve(int limit) {
		this.limit = limit;
		sieve = new boolean[limit];
		Arrays.fill(sieve, true);
		sieve();
	}

	private void sieve() {
		sieve[0] = false; // 1 is not prime;
		for (int i = 0; i < limit; i++) {
			if (!sieve[i]) {
				continue;
			}
			int multiple = i + 1;
			if (multiple * multiple > limit) {
				break;
			}
			for (int j = 2; j <= limit / multiple; j++) {
				sieve[j * multiple - 1] = false;
			}
		}		
	}

	public boolean isPrime(int n) {
		if (n < 1 || n > limit) {
			throw new IllegalArgumentException("n must lie in the range [1, " + limit + "]");
		}
		return sieve[n - 1];
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < limit; i++) {
			if (sieve[i]) {
				sb.append(i + 1).append(' ');
			}
		}
		return sb.toString();
	}

}