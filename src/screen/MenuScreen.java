package screen;

import java.util.Optional;

import graphic.GameBackground;
import graphic.RenderableHolder;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;
import main.Main;
import thread.AnimationThread;
import thread.UpdateThread;

public class MenuScreen extends BorderPane {
	private Button startButton;
	private Button exitButton;
	private VBox menu;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameBackground gameBG;

	public MenuScreen() {
		canvas =  new Canvas(GameScreen.SCREEN_WIDTH*3/4, GameScreen.SCREEN_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		setCenter(canvas);
		gameBG = new GameBackground("menu", canvas.getWidth(), canvas.getHeight());
		gc.drawImage(gameBG.getBg(), 0, 0);
		
		getStyleClass().add("menuScreen");
		
		menu = new VBox(10);
		menu.setPrefWidth(GameScreen.SCREEN_WIDTH/4);
		menu.getStyleClass().add("right");
		
		startButton = new Button("Start");
		startButton.getStyleClass().add("button");
		
		exitButton = new Button("Exit");
		exitButton.getStyleClass().add("button");
		
		menu.getChildren().addAll(startButton, exitButton);
		
		addButtonListener();
		setRight(menu);
	}
	
	private void addButtonListener() {
		startButton.setOnAction(e -> {		
			Main.instance.setToRoundScene();
			GameLogic.instance.startGame();	
			new UpdateThread().start();	
			new AnimationThread().start();
		});
		exitButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Are you sure you want to quit?");
			alert.setHeaderText(null);
			alert.getButtonTypes().set(0, new ButtonType("Yes, sure"));
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == alert.getButtonTypes().get(0)){
			    // ... user chose OK
				System.exit(0);
			} else {
			    // ... user chose CANCEL or closed the dialog
			}
			
		});
	}

}
