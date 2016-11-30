package main;

import java.util.Scanner;

import graphic.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import logic.Enemy;
import logic.GameLogic;
import logic.Player;
import logic.Round;

public class Main extends Application {

	static GameLogic logic = new GameLogic();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {
		//code here
		BorderPane root = new BorderPane();
		root.setCenter(GameScreen.instance);
		
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Scene scene = new Scene(root,680,480);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/*new AnimationTimer() {
			Long start=0l;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(start==0l)start=now;
				long diff = now-start;
				if(diff>=100000000l){ //100000000l = 100ms.
					//update here
					//GameScreen.paintComponents();
					//GameLogic.update();
					
					
					start=now;

				}
			}
		}.start();*/
		
		logic.startGame();
		
	}

}
