package main;

import java.util.Scanner;

import javafx.application.Application;
import logic.Enemy;
import logic.GameLogic;
import logic.Player;
import logic.Stage;

public class Main extends Application {

	static GameLogic logic = new GameLogic();
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(javafx.stage.Stage primaryStage) throws Exception {
		//code here
		
		logic.startGame();
		
	}

}
