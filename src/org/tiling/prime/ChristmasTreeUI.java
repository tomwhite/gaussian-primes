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

import java.awt.geom.GeneralPath;import java.util.Iterator;import java.util.TreeMap;import java.awt.geom.AffineTransform;import java.awt.geom.Arc2D;import java.awt.geom.Point2D;/**
 * A graphical representation of a Shape.
 */
public class ChristmasTreeUI implements UI {

	public static final float LINE_WIDTH = 0.5f;

	protected Color backgroundColor = Color.white;


	private TreeMap primes;

	protected Rectangle2D bounds;

	protected RenderingHints qualityHints;
	protected Stroke stroke;

	public ChristmasTreeUI(int endRadius) {
		this(1, endRadius);
	}

	private void calculate() {
		Sieve sieve = new Sieve(2 * (endRadius + 1) * (endRadius + 1));

		primes = new TreeMap();

		int startRadius2 = startRadius * startRadius;
		int endRadius2 = endRadius * endRadius;

		for (int x = 1; x < endRadius; x++) {
			for (int y = 1; y <= x; y++) {
				int p = x * x + y * y;
				if (sieve.isPrime(p) && p >= startRadius2 && p <= endRadius2) {
					primes.put(new Integer(p), new Point2D.Double(x, y));
				}
			}
		}

	}
	private void initialiseShapes() {
		path = new GeneralPath();

		Iterator i = primes.keySet().iterator();
		Integer prime = (Integer) i.next();
		
		Point2D previous = (Point2D) primes.get(prime);
		double previousR = transformR(Math.sqrt(prime.intValue()));
		double previousTheta = Math.atan2(previous.getY(), previous.getX());

		path.moveTo((float) (previousR * Math.cos(previousTheta)), (float) (previousR * Math.sin(previousTheta)));
		while (i.hasNext()) {
			prime = (Integer) i.next();
			
			Point2D next = (Point2D) primes.get(prime);
			double nextR = transformR(Math.sqrt(prime.intValue()));
			double nextTheta = Math.atan2(next.getY(), next.getX());

			path.lineTo((float) (nextR * Math.cos(previousTheta)), (float) (nextR * Math.sin(previousTheta)));

			Arc2D arc = new Arc2D.Double();
			arc.setArcByCenter(0, 0, nextR, -Math.toDegrees(previousTheta), -Math.toDegrees(nextTheta - previousTheta), Arc2D.OPEN);
			path.append(arc, true);

			previous = next;
			previousTheta = nextTheta;
		}
		// Rotate to be upright...
		path.transform(AffineTransform.getRotateInstance(-5 * Math.PI / 8));
		bounds = path.getBounds2D();
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

		g2.setColor(Color.black);
		g2.draw(path);
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
	private int endRadius;	protected GeneralPath path;	private int startRadius;	public ChristmasTreeUI(int startRadius, int endRadius) {
		this.startRadius = startRadius;
		this.endRadius = endRadius;
		calculate();
		initialiseShapes();
		initialiseGraphics();
	}	public Object clone() {
		try {
			GaussianPrimesUI gaussianPrimesUI = (GaussianPrimesUI) super.clone();
			return gaussianPrimesUI;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}		
	}	private void initialiseShapes2() {
		path = new GeneralPath();

		Iterator i = primes.keySet().iterator();
		Integer prime = (Integer) i.next();
		
		Point2D previous = (Point2D) primes.get(prime);
		double previousR = transformR(Math.sqrt(prime.intValue()));
		double previousTheta = Math.atan2(previous.getY(), previous.getX());

		path.moveTo((float) previousR, (float) previousTheta * 100);
		while (i.hasNext()) {
			prime = (Integer) i.next();
			
			Point2D next = (Point2D) primes.get(prime);
			double nextR = transformR(Math.sqrt(prime.intValue()));
			double nextTheta = Math.atan2(next.getY(), next.getX());

			path.lineTo((float) nextR, (float) previousTheta * 100);
			path.lineTo((float) nextR, (float) nextTheta * 100);

			previous = next;
			previousTheta = nextTheta;
		}
		bounds = path.getBounds2D();
	}	private double transformR(double r) {
		return r * r / Math.log(r);
	}}