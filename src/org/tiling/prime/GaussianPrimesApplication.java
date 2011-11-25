package org.tiling.prime;





import org.tiling.gui.ViewerApplication;import org.tiling.gui.Canvas2D;import java.awt.event.MouseAdapter;import java.io.Serializable;import org.tiling.gui.Viewer2D;import java.awt.event.MouseEvent;import java.awt.event.InputEvent;import java.awt.geom.Point2D;public class GaussianPrimesApplication extends ViewerApplication {




	public GaussianPrimesApplication() {
		super("Gaussian Primes");
		viewer.fitToCanvas();

		addJInternalFrame(buildChristmasTreeViewer());
	}

	public static void main(String[] args) {
		ViewerApplication app = new GaussianPrimesApplication();
	}

	public Viewer2D buildChristmasTreeViewer() {
		Viewer2D viewer = new Viewer2D("Gaussian Primes Christmas Tree", true, false, true);

		ChristmasTreeUI ui = new ChristmasTreeUI(50);
		Canvas2D canvas = new Canvas2D(ui);
		viewer.setCanvas2D(canvas);
		return viewer;
	}	public Viewer2D buildViewer() {
		return new GaussianPrimesViewer2D();
	}}