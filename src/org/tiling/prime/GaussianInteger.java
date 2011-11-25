package org.tiling.prime;

public class GaussianInteger {

	long a, b;

	public GaussianInteger(long a, long b) {
		this.a = a;
		this.b = b;
	}

	public static GaussianInteger add(GaussianInteger w, GaussianInteger z) {
		return new GaussianInteger(w.a + z.a, w.b + z.b);
	}

	public static GaussianInteger multiply(GaussianInteger w, GaussianInteger z) {
		return new GaussianInteger(w.a * z.a - w.b * z.b, w.a * z.b + w.b * z.a);
	}

	public static GaussianInteger power(GaussianInteger w, int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n cannot be less than 0");
		} else if (n == 0) {
			return new GaussianInteger(1, 0);
		} else {
			GaussianInteger z = w;
			for (int i = 1; i < n; i++) {
				z = GaussianInteger.multiply(z, w);
			}
			return z;
		}
	}

	public String toString() {
		if (isImaginary()) {
			return b + "i";
		} else if (isReal()) {
			return a + "";
		} else {
			return a + " + " + b + "i";
		}
	}

	public static void main(String[] args) {
		if (args.length == 0 || (args.length % 3) != 0) {
			System.err.println("Usage: GaussianInteger a_0 b_0 n_0 [a_1 b_1 n_1] [a_2 b_2 n_2] ...");
			System.exit(1);
		}
		GaussianInteger g = new GaussianInteger(1, 0);
		for (int i = 0; i < args.length / 3; i++) {
			long a = Long.parseLong(args[3 * i]);
			long b = Long.parseLong(args[3 * i + 1]);
			int n = Integer.parseInt(args[3 * i + 2]);
			GaussianInteger z = new GaussianInteger(a, b);
			z = GaussianInteger.power(z, n);
			g = GaussianInteger.multiply(g, z);
		}
		System.out.println(g);
	}

	public boolean isImaginary() {
		return a == 0;
	}	public boolean isReal() {
		return b == 0;
	}}