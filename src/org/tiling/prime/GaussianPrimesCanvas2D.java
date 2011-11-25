package org.tiling.prime;

import java.awt.geom.Point2D;

import org.tiling.UI;
import org.tiling.gui.Canvas2D;

public class GaussianPrimesCanvas2D extends Canvas2D {
	public GaussianPrimesCanvas2D(UI ui) {
		super(ui);
		setToolTipsEnabled(true);
	}
	protected String getToolTipText(Point2D point) {
		return Math.round(point.getX()) + " + " + Math.round(point.getY()) + "i";
	}
}
