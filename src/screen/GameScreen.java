/**
 * @author Phakawat and Nitit
 *
 */

package screen;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.GameLogic;

public class GameScreen extends Group {
	// public static final GameScreen instance = new GameScreen();
	private Canvas canvas;
	public GraphicsContext gc;
	public static final int SCREEN_WIDTH = 680;
	public static final int SCREEN_HEIGHT = 480;

	public GameScreen() {
		canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		getChildren().add(canvas);

		System.out.println("gameScreen initialized");
	}

	public void paintComponents() {

		if (GameLogic.instance.isReadyToDraw()) {
			// draw all renderable in level
			gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

			for (IRenderable renderable : RenderableHolder.getInstance().getEntities()) {
				renderable.draw(gc);
			}
		} else {
			DrawingUtility.drawPauseText(gc, GameLogic.instance.isPause());
		}

	}

	public void requestFocusForCanvas() {
		this.requestFocus();
	}

	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}
