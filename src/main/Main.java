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
		
		gameScreen = new GameScreen();
		gameScreen.requestFocusForCanvas();
		gameScene = new Scene(gameScreen,680,480);
		addEventListener(gameScene);
		
		this.primaryStage.setScene(gameScene);
		this.primaryStage.show();
		
		gameLogic.startGame();
		Thread update = new Thread(() -> {
			while(!GameLogic.instance.isGameOver()) {
				gameLogic.update();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		update.start();
		
		new AnimationTimer() {
			Long start=0l;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(start==0l)start=now;
				long diff = now-start;
				if(diff>=100000000l){ //100000000l = 100ms.
					gameScreen.paintComponents();
					start=now;

				}
			}
		}.start();
		

		
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
