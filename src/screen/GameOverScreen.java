package screen;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;
import main.Main;

public class GameOverScreen extends Group {

	private Canvas canvas;
	private GraphicsContext gc;
	private Button returnButton;
	
	//call once by GameLogic
	public GameOverScreen() {
		canvas = new Canvas(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		getChildren().add(canvas);
		
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc.setFill(Color.AQUA);
		gc.setFont(Font.font("Papyrus", 60));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("GAME OVER", GameScreen.SCREEN_WIDTH/2, GameScreen.SCREEN_HEIGHT/2);

		createButton();
		
		returnButton.setOnAction(e -> {
			Main.instance.setToMenuScene();
			GameLogic.instance.stopGame();
		});
	}
	
	private void createButton() {
		returnButton = new Button("Return to Title screen");
		returnButton.getStyleClass().add("returnbutton");
		
		VBox vbox = new VBox();
		vbox.setPrefWidth(GameScreen.SCREEN_WIDTH);
		vbox.relocate(0, GameScreen.SCREEN_HEIGHT/2 + 50);
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().add(returnButton);
		getChildren().add(vbox);
		
		

	}
}
