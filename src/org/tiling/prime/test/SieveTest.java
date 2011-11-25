package org.tiling.prime.test;

import java.math.BigInteger;

import junit.framework.*;

import org.tiling.prime.*;

public class SieveTest extends TestCase {

	public SieveTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(SieveTest.class);
	}

	public void testSieve() {
		Sieve sieve = new Sieve(10000);
		assertTrue("1 not prime", !sieve.isPrime(1));
		assertTrue("2 prime", sieve.isPrime(2));
		assertTrue("3 prime", sieve.isPrime(3));
		assertTrue("4 not prime", !sieve.isPrime(4));
		assertTrue("5 prime", sieve.isPrime(5));
		assertTrue("6 not prime", !sieve.isPrime(6));
		assertTrue("7 prime", sieve.isPrime(7));
		assertTrue("8 not prime", !sieve.isPrime(8));
		assertTrue("9 not prime", !sieve.isPrime(9));
		assertTrue("10 not prime", !sieve.isPrime(10));
		assertTrue("163 prime", sieve.isPrime(163));
		assertTrue("1117 prime", sieve.isPrime(1117));
		assertTrue("4111 prime", sieve.isPrime(4111));
		assertTrue("9973 prime", sieve.isPrime(9973));
		assertTrue("10000 not prime", !sieve.isPrime(10000));
		try {
			sieve.isPrime(10001);
			fail("10001 out of range");
		} catch (IllegalArgumentException e) {
			// should throw
		}
		System.out.println(sieve);
	}

	public void testBigSieve() {
		// See how reasonable it is to create a big list of primes.
		// 10 million seems to be OK on average machines (~5 seconds).
		// Storage is the limiting factor.
		Sieve sieve = new Sieve(10000000);
	}

}