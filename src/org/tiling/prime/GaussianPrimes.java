package org.tiling.prime;

import java.awt.geom.Point2D;

public class GaussianPrimes {
	private int startRadius;
	private int endRadius;
	private boolean[][] primes;
	private GaussianInteger selected;
	public GaussianPrimes(int endRadius) {
		this(0, endRadius);
	}
	public GaussianPrimes(int startRadius, int endRadius) {
		this.startRadius = startRadius;
		this.endRadius = endRadius;
		calculate();
	}
	private void calculate() {
		Sieve sieve = new Sieve(2 * (endRadius + 1) * (endRadius + 1));
		primes = new boolean[endRadius + 1][endRadius + 1];
		primes[1][1] = true;
		for (int x = 2; x <= endRadius; x++) {
			int y = 0;
			primes[x][y] = ((x % 4 == 3) && sieve.isPrime(x));
			for (y = 1; y < x; y++) {
				primes[x][y] = sieve.isPrime(x * x + y * y);
			}
		}

	}
	public GaussianInteger findPrime(Point2D point) {
		final int x = (int) Math.round(point.getX());
		final int y = (int) Math.round(point.getY());

		// Try exact match ...
		if (isPrime(x, y)) {
			return new GaussianInteger(x, y);
		}

		// ...then search. (Should really do this as a spiral)
		final int searchRadius = 1;
		for (int i = -searchRadius; i <= searchRadius; i++) {
			for (int j = -searchRadius; j <= searchRadius; j++) {
				if (isPrime(x + i, y + j)) {
					return new GaussianInteger(x + i, y + j);
				}
			}
		}
		return null;
	}
	public int getEndRadius() {
		return endRadius;
	}
	public GaussianInteger getSelected() {
		return selected;
	}
	public int getStartRadius() {
		return startRadius;
	}
	public boolean isPrime(int x, int y) {
		return primes[x][y];
	}
	public void setSelected(GaussianInteger selected) {
		this.selected = selected;
	}
}
