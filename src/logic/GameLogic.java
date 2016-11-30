package logic;

import java.util.ArrayList;
import java.util.Scanner;

import data.GameData;
import graphic.GameScreen;
import input.InputUtility;
import javafx.scene.input.KeyCode;

public class GameLogic {
	private static boolean isGameOver;
	private static int level;
	private static Player player;
	private static Round round;
	
	public GameLogic() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		GameLogic.isGameOver = isGameOver;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		GameLogic.level = level;
	}
	
	public void startGame() {
		
		while(!isGameOver()) {
			round = new Round(level);
			round.addPlayer(player);
			ArrayList<Enemy> enemies = round.getEnemyList();
			
			
			boolean won = false;
			System.out.println("----------LEVEL "+(level+1)+"----------");
			while(!won) {			
				System.out.println("Press Any Key to Attack");
				GameScreen.instance.print("Press Any Key to Attack");
				if (InputUtility.getKeyTriggered(KeyCode.ENTER)) {
					for (Enemy e : enemies) {
						if (!e.isDead()) {
							player.attack(e);
							GameScreen.instance.print("Kirby Attacks "+e.getName());
						}
					}
					
					won = enemies.get(0).isDead();
					
					for (Enemy e : enemies) {
						if(player.isDead()){
							setGameOver(true);
							System.out.println("Game Over");
							break;
						}
						System.out.println(e.getName()+" "+e.getHp()+" dead : "+e.isDead());
						if (!e.isDead()) {
							e.attack(player);
						}
						won = won && e.isDead();
					}
					if(isGameOver()){
						break;
					}
					if(!isGameOver()){
						System.out.println("You won!");
						GameScreen.instance.print("You won");
						level++;
					}	
					
				}
				
				}
				
			
			
				/*int counter = 0;
				int count = 1;
				while(true) {
					System.out.println(""+counter);
					if (sc.next() != null) {
						player.setAttack(counter);
						break;
					}
					counter += count;
					if (counter == 10 || counter == 0) {
						count *= -1;
					}
				}*/
						
				
		}	
		
	}
}