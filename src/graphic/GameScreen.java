package graphic;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import input.InputUtility;

public class GameScreen extends BorderPane {
	public static final GameScreen instance = new GameScreen();
	public Canvas canvas;
	public GraphicsContext gc;
	
	public GameScreen() {
		canvas = new Canvas(680,480);
		gc = canvas.getGraphicsContext2D();
		setCenter(canvas);
		gc.setFill(Color.LIGHTGREEN);
		gc.fillRect(0, 0, 680, 480);
		System.out.println("HIIIIIIIIIIIIIIIIII");
		initializeEventListeners();
	}
	
	public void updateGameScreen() {
		//draw all enemies in level
	}
	
	public void print(String text) {
		gc.setFill(Color.LIGHTGREEN);
		gc.fillRect(0, 0, 680, 480);
		gc.setFont(Font.font("Comis Sans MS", 100));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFill(Color.BLACK);
		gc.fillText(text, 340, 400);
	}
	
	public void initializeEventListeners() {
		
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
}
