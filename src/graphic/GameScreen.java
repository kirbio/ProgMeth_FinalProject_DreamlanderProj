package graphic;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;
import logic.RPGTextArea;
import logic.StatusBar;
import input.InputUtility;

public class GameScreen extends BorderPane {
	//public static final GameScreen instance = new GameScreen();
	public Canvas canvas;
	public GraphicsContext gc;
	
	public GameScreen() {
		canvas = new Canvas(680,480);
		gc = canvas.getGraphicsContext2D();
		setCenter(canvas);
		gc.setFill(Color.PINK);
		gc.fillRect(0, 0, 680, 480);
		RenderableHolder.getInstance().add(new RPGTextArea("Initializing..."));
		RenderableHolder.getInstance().add(new StatusBar());
		System.out.println("gameScreen initialized");
		initializeEventListeners();
	}
	
	public void paintComponents() {
		//draw all enemies in level
		gc.clearRect(0, 0, 680, 480);
		gc.setFill(Color.PINK);
		gc.fillRect(0, 0, 680, 480);
		for (IRenderable renderable : RenderableHolder.getInstance().getEntities()) {
			renderable.draw(gc);
		}
	}
	
	
	public void initializeEventListeners() {
		
		this.setOnMouseClicked(event -> {
			System.out.println("MOUSE CLICKED");
		});
		
		this.setOnKeyPressed((event) -> {
			System.out.println("KeyPressed : " + event.getCode().toString());
			if (!InputUtility.getKeyPressed(event.getCode()))
				InputUtility.setKeyTriggered(event.getCode(), true);
			InputUtility.setKeyPressed(event.getCode(), true);		
		});

		this.setOnKeyReleased((event) -> {
			System.out.println("KeyReleased : " + event.getCode().toString());
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}
	
	public void requestFocusForCanvas(){
		this.requestFocus();
	}
}
