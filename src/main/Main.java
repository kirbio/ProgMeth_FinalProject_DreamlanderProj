package main;

import java.util.Scanner;

import graphic.RenderableHolder;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jdk.management.resource.internal.inst.ThreadRMHooks;
import logic.Enemy;
import logic.GameLogic;
import logic.Player;
import logic.Round;
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
	private GameLogic gameLogic = GameLogic.instance;
	private String leveldatafile;
	
	
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
			
		roundScreen = new RoundStartScreen();
		roundScene = new Scene(roundScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);	
		
		gameOverScreen = new GameOverScreen();
		gameOverScene = new Scene(gameOverScreen, GameScreen.SCREEN_WIDTH, GameScreen.SCREEN_HEIGHT);
		gameOverScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	}
	
	public void setToMenuScene() {	
		primaryStage.setScene(menuScene);
		//Play Titlescreen bgm
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
		new UpdateThread().start();	
		new AnimationStarter().start();
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
