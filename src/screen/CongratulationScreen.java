/**
 * @author Phakawat and Nitit
 *
 */

package screen;

import graphic.DrawingUtility;
import graphic.GameBackground;
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
import main.Main;

public class CongratulationScreen extends Group {

	private Canvas canvas;
	private GraphicsContext gc;
	private Button returnButton;
	private GameBackground bg;

	// call once by GameLogic
	public CongratulationScreen() {
		canvas = new Canvas(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		getChildren().add(canvas);

		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		bg = new GameBackground("congrats", GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gc.drawImage(bg.getBg(), 0, 0);

		gc.setFill(Color.CRIMSON);
		gc.setFont(Font.loadFont(DrawingUtility.FONT_8BIT, 90));
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.fillText("CONGRATULATIONS!", GameScreen.SCREEN_WIDTH / 2, GameScreen.SCREEN_HEIGHT / 6);

		createButton();

		returnButton.setOnAction(e -> {
			Main.instance.stopMainGame();
			Main.instance.setToMenuScene();

		});
	}

	private void createButton() {
		returnButton = new Button("Return to Title screen");
		returnButton.getStyleClass().add("congratsreturnbutton");

		VBox vbox = new VBox();
		vbox.setPrefWidth(GameScreen.SCREEN_WIDTH);
		vbox.relocate(0, GameScreen.SCREEN_HEIGHT - 70);
		vbox.setAlignment(Pos.BOTTOM_RIGHT);
		vbox.getChildren().add(returnButton);
		getChildren().add(vbox);

	}
}
