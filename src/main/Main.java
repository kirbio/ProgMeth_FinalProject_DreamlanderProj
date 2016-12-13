/**
 * @author Phakawat and Nitit
 *
 */

package main;

import input.InputUtility;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;
import screen.CongratulationScreen;
import screen.GameOverScreen;
import screen.GameScreen;
import screen.MenuScreen;
import screen.RoundStartScreen;
import sound.AudioHolder;
import thread.AnimationStarter;
import thread.UpdateThread;

public class Main extends Application {

	public static Main instance;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private RoundStartScreen roundScreen;
	private GameOverScreen gameOverScreen;
	private Scene gameScene;
	private Scene menuScene;
	private Scene roundScene;
	private Scene gameOverScene;
	private Stage primaryStage;
	private String leveldatafile;
	private CongratulationScreen congratsScreen;
	private Scene congratsScene;
	private AnimationStarter animator;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KIRBY QUEST");
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(WindowEvent -> System.exit(0));

		initializeScene();
		setToMenuScene();

		AudioHolder.getInstance();

		this.primaryStage.show();

	}

	private void initializeScene() {
		gameScreen = new GameScreen();
		gameScreen.requestFocusForCanvas();
		gameScene = new Scene(gameScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		addEventListener(gameScene);

		menuScreen = new MenuScreen();
		menuScene = new Scene(menuScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		menuScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		gameOverScreen = new GameOverScreen();
		gameOverScene = new Scene(gameOverScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gameOverScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	public void setToMenuScene() {
		primaryStage.setScene(menuScene);
		// Play Titlescreen bgm
		AudioHolder.getInstance().playBGM("titlescreen", true, 0.9);

	}

	public void setToGameScene() {
		Platform.runLater(() -> {
			primaryStage.setScene(gameScene);
		});
	}

	public void setToRoundScene() {
		Platform.runLater(() -> {
			primaryStage.setScene(roundScene);
		});
	}

	public void setToGameOverScene() {
		Platform.runLater(() -> {
			primaryStage.setScene(gameOverScene);
		});
	}

	public void setToCongratsScene() {
		Platform.runLater(() -> {
			primaryStage.setScene(congratsScene);
		});
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public RoundStartScreen getRoundScreen() {
		return roundScreen;
	}

	public void startMainGame(int mode) {
		GameLogic.instance.startGame(mode);
		roundScreen = new RoundStartScreen();
		roundScene = new Scene(roundScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		new UpdateThread().start();
		animator = new AnimationStarter();
	}

	public void stopMainGame() {
		GameLogic.instance.stopGame();
		animator.checkStop();
	}

	public void initializeCongratsScreen() {
		congratsScreen = new CongratulationScreen();
		congratsScene = new Scene(congratsScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		congratsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	}

	private void addEventListener(Scene s) {
		s.setOnKeyPressed((event) -> {
			System.out.println("KeyPressed : " + event.getCode().toString());
			if (!InputUtility.getKeyPressed(event.getCode())) {
				InputUtility.setKeyTriggered(event.getCode(), true);
			}
			InputUtility.setKeyPressed(event.getCode(), true);
		});

		s.setOnKeyReleased((event) -> {
			System.out.println("KeyReleased : " + event.getCode().toString());
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}

	public String getLeveldatafile() {
		return leveldatafile;
	}

	public void setLeveldatafile(String leveldatafile) {
		this.leveldatafile = leveldatafile;
	}

}
