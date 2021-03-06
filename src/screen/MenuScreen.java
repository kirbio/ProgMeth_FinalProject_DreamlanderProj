/**
 * @author Phakawat and Nitit
 *
 */

package screen;

import java.util.Optional;

import graphic.GameBackground;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import main.Main;
import sound.AudioHolder;

public class MenuScreen extends Group {
	private Button startButton;
	private Button exitButton;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameBackground gameBG;

	public MenuScreen() {
		canvas = new Canvas(GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gameBG = new GameBackground("menu", GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT - 100);
		gc.drawImage(gameBG.getBg(), 0, 0);
		getChildren().add(canvas);

		addButtonPane();
	}

	private void addButtonPane() {

		startButton = new Button("Start");
		startButton.getStyleClass().add("button");
		startButton.relocate(200, 400);

		exitButton = new Button("Exit");
		exitButton.getStyleClass().add("button");
		exitButton.relocate(400, 400);

		addButtonListener();

		getChildren().addAll(startButton, exitButton);

	}

	private void addButtonListener() {
		startButton.setOnAction(e -> {
			AudioHolder.getInstance().playSFX("select");

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText(null);
			alert.setHeaderText("Choose Chapter");
			alert.getButtonTypes().set(0, new ButtonType("Chapter 1"));
			alert.getButtonTypes().set(1, new ButtonType("Chapter 2"));
			alert.getButtonTypes().add(new ButtonType("Chapter 3"));

			String leveldata;
			int mode;
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == alert.getButtonTypes().get(0)) {
				leveldata = "leveldata1.csv";
				mode = 0;
			} else if (result.get() == alert.getButtonTypes().get(1)) {
				leveldata = "leveldata2.csv";
				mode = 1;
			} else {
				leveldata = "leveldata3.csv";
				mode = 2;
			}
			Main.instance.setLeveldatafile(leveldata);
			Main.instance.startMainGame(mode);
			AudioHolder.getInstance().playSFX("select");
			AudioHolder.getInstance().stopBGM();
			Main.instance.setToRoundScene();
		});
		exitButton.setOnAction(e -> {
			AudioHolder.getInstance().playSFX("select");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Are you sure you want to quit?");
			alert.setHeaderText(null);
			alert.getButtonTypes().set(0, new ButtonType("Yes, sure"));

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == alert.getButtonTypes().get(0)) {
				AudioHolder.getInstance().playSFX("select");
				// ... user chose OK
				System.exit(0);
			} else {
				AudioHolder.getInstance().playSFX("select2");
				// ... user chose CANCEL or closed the dialog
			}

		});
	}

}
