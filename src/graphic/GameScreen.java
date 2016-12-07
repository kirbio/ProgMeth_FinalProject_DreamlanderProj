package graphic;

import input.InputUtility;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import logic.RPGTextArea;
import logic.StatusBar;

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
//			Platform.runLater(() -> {
				renderable.draw(gc);
//			});
			
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
