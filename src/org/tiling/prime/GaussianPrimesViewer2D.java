package org.tiling.prime;

import java.awt.BorderLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.geom.Point2D;

import java.io.Serializable;

import javax.swing.JLabel;

import org.tiling.gui.Canvas2D;
import org.tiling.gui.Viewer2D;

public class GaussianPrimesViewer2D extends Viewer2D {
	protected class MouseClickListener extends MouseAdapter implements Serializable {
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() != getCanvas2D()) {
				return;
			}
			Canvas2D canvas = (Canvas2D) e.getSource();
			if ((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0) { // Right button
				Point2D clickPoint = canvas.transformToLogicalSpace(e.getX(), e.getY());
				GaussianPrimesUI ui = (GaussianPrimesUI) canvas.getUI();
				GaussianInteger prime = ui.getGaussianPrimes().findPrime(clickPoint);
				if (prime == null) {
					setMessage("Cannot find closest prime - click nearer!");
					ui.getGaussianPrimes().setSelected(null);
				} else {
					ui.getGaussianPrimes().setSelected(prime);
					if (prime.isReal()) { // 4n + 3
						setMessage(prime.toString());
					} else {
						setMessage(prime.toString() + " (" + (prime.a * prime.a + prime.b * prime.b) + ")");
					}
				}
				repaint();
			}
		}
	}
	
	/**
	 * @serial area for messages
	 */
	private JLabel messageArea;
	public GaussianPrimesViewer2D() {
		super("Gaussian Primes", true, false, true);
		messageArea = new JLabel();
		getContentPane().add(messageArea, BorderLayout.SOUTH);

		GaussianPrimesUI ui = new GaussianPrimesUI(new GaussianPrimes(50, 100));
		Canvas2D canvas = new Canvas2D(ui);
		setCanvas2D(canvas);
		
		addMouseListener(new MouseClickListener());
	}
	private void setMessage(String message) {
		messageArea.setText(message);
	}
}
