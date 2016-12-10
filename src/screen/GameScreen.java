package screen;

import graphic.IRenderable;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import logic.GameLogic;
import logic.RPGTextArea;
import logic.StatusBar;

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
		
		
		

		RPGTextArea textArea = new RPGTextArea("");
		textArea.start();

		new StatusBar();

		System.out.println("gameScreen initialized");
	}

	public void paintComponents() {

		if (GameLogic.instance.isReadyToDraw()) {
			// draw all renderable in level
			gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			gc.setFill(Color.PINK);
			gc.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

			for (IRenderable renderable : RenderableHolder.getInstance().getEntities()) {
				renderable.draw(gc);

			}
		}

	}

	public void requestFocusForCanvas() {
		this.requestFocus();
	}
}
