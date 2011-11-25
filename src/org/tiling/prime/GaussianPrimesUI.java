package org.tiling.prime;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.tiling.UI;

import java.awt.geom.Ellipse2D;/**
 * I plot a disc (a circle or a square) for each Gaussian integer that is prime.
 */
public class GaussianPrimesUI implements UI {

	public static final float LINE_WIDTH = 0.1f;

	protected Color backgroundColor = Color.white;


	private GaussianPrimes primes;
	protected Shape[] shapes;
	protected Rectangle2D bounds;

	protected RenderingHints qualityHints;
	protected Stroke stroke;




	private void initialiseShapes() {
		ArrayList discs = new ArrayList();
		bounds = new Rectangle2D.Double();

		int startRadius2 = primes.getStartRadius() * primes.getStartRadius();
		int endRadius2 = primes.getEndRadius() * primes.getEndRadius();
		for (int x = 0; x <= primes.getEndRadius(); x++) {
			for (int y = 0; y <= x; y++) {
				int distance2 = x * x + y * y;
				if (primes.isPrime(x, y) && distance2 >= startRadius2 && distance2 <= endRadius2) {
					Shape disc1 = buildDisc(x, y);
					discs.add(disc1);
					bounds.add(disc1.getBounds2D());
					if (!sectorOnly) {
						Shape disc2 = buildDisc(x, -y);
						discs.add(disc2);
						bounds.add(disc2.getBounds2D());
						Shape disc3 = buildDisc(y, x);
						discs.add(disc3);
						bounds.add(disc3.getBounds2D());
						Shape disc4 = buildDisc(y, -x);
						discs.add(disc4);
						bounds.add(disc4.getBounds2D());
						Shape disc5 = buildDisc(-x, -y);
						discs.add(disc5);
						bounds.add(disc5.getBounds2D());
						Shape disc6 = buildDisc(-x, y);
						discs.add(disc6);
						bounds.add(disc6.getBounds2D());
						Shape disc7 = buildDisc(-y, -x);
						discs.add(disc7);
						bounds.add(disc7.getBounds2D());
						Shape disc8 = buildDisc(-y, x);
						discs.add(disc8);
						bounds.add(disc8.getBounds2D());
					}
				}
			}
		}
		shapes = (Shape[]) discs.toArray(new Shape[discs.size()]);
	}

	protected void initialiseGraphics() {
		qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
											RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		stroke = new BasicStroke(LINE_WIDTH);
	}

	public void paint(Graphics2D g2) {
		g2.setRenderingHints(qualityHints);
		g2.setStroke(stroke);

		if (clip != null) {
			g2.clip(clip);
		}

		g2.setColor(Color.red);
		for (int i = 0; i < shapes.length; i++) {
			g2.fill(shapes[i]);
		}

		// Should make this more efficient
		if (getGaussianPrimes().getSelected() != null) {
			g2.setColor(Color.blue);
			g2.fill(buildDisc(getGaussianPrimes().getSelected().a, getGaussianPrimes().getSelected().b));
		}

	}

	public void setBackground(Color c) {
		backgroundColor = c;
	}

	public Color getBackground() {
		return backgroundColor;
	}

	public Rectangle2D getBounds2D() {
		return bounds;
	}
	public Shape clip;	private boolean disc = true;	private double discRadius = 1;	private boolean sectorOnly = true;	public GaussianPrimesUI(GaussianPrimes primes) {
		this.primes = primes;
		initialiseShapes();
		initialiseGraphics();
	}	private Shape buildDisc(double x, double y) {
		if (disc) {
			return new Ellipse2D.Double(x - discRadius, y - discRadius, discRadius * 2, discRadius * 2);
		} else {
			return new Rectangle2D.Double(x - discRadius, y - discRadius, discRadius * 2, discRadius * 2);
		}
	}	public Object clone() {
		try {
			GaussianPrimesUI gaussianPrimesUI = (GaussianPrimesUI) super.clone();
			return gaussianPrimesUI;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}	public GaussianPrimes getGaussianPrimes() {
		return primes;
	}}