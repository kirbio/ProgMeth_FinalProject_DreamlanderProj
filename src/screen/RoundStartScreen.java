package screen;

import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;

public class RoundStartScreen extends Group {
	
	private Canvas canvas;
	private GraphicsContext gc;
	private int level;
	
	//call once by Main
	public RoundStartScreen() {
		canvas = new Canvas(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		getChildren().add(canvas);
		//call once to draw
		update();
		
	}
	
	//initially called once by constructor, then later called by GameLogic every time after win the level
	public void update() {
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setFill(Color.LIGHTPINK);
		gc.setFont(Font.font("IMPACT", 100));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("LEVEL "+( GameLogic.instance.getLevel()+1), GameScreen.SCREEN_WIDTH/2, GameScreen.SCREEN_HEIGHT/2);

		
	}

}
