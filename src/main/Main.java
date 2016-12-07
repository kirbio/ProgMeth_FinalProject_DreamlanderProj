package main;

import java.util.Scanner;

import graphic.GameScreen;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.Enemy;
import logic.GameLogic;
import logic.Player;
import logic.Round;

public class Main extends Application {

	
	private GameScreen gameScreen;
	private GameLogic gameLogic = GameLogic.instance;
	private Scene gameScene;
	private Stage primaryStage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//code here
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KIRBY QUEST");
		this.primaryStage.setResizable(false);
		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		
		//BorderPane root = new BorderPane();
		gameScreen = new GameScreen();
		gameScreen.requestFocusForCanvas();
		
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		gameScene = new Scene(gameScreen,680,480);
		this.primaryStage.setScene(gameScene);
		addEventListener(this.primaryStage.getScene());
		
		this.primaryStage.show();
		
		Thread t = new Thread(() -> {
			gameLogic.startGame();
		});
		t.start();
		
		Thread update = new Thread(() -> {
			while (!GameLogic.instance.isGameOver()) {
				gameScreen.paintComponents();
				gameLogic.update();
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		});
		
		update.start();
		
		
	}
	
	private void addEventListener(Scene s) {
		s.setOnKeyPressed((event) -> {
			System.out.println("KeyPressed : " + event.getCode().toString());
			if (!InputUtility.getKeyPressed(event.getCode()))
				InputUtility.setKeyTriggered(event.getCode(), true);
			InputUtility.setKeyPressed(event.getCode(), true);		
		});

		s.setOnKeyReleased((event) -> {
			System.out.println("KeyReleased : " + event.getCode().toString());
			InputUtility.setKeyPressed(event.getCode(), false);
		});
	}

}
