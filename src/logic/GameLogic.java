package logic;

import java.util.ArrayList;
import java.util.Scanner;

import data.GameData;
import graphic.GameScreen;
import input.InputUtility;
import javafx.scene.input.KeyCode;

public class GameLogic {
	public static final GameLogic instance = new GameLogic();
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
	
	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		GameLogic.player = player;
	}

	public static Round getRound() {
		return round;
	}

	public static void setRound(Round round) {
		GameLogic.round = round;
	}

	public void startGame() {
		
		while(!isGameOver()) {
			round = new Round(level);
			round.addPlayer(player);
			ArrayList<Enemy> enemies = round.getEnemyList();
			
			
			boolean won = false;
			System.out.println("----------LEVEL "+(level+1)+"----------");	
			while(!won) {			
				System.out.println(won);
				RPGTextArea.text = "Press Any Key to Attack";
				if (InputUtility.getKeyTriggered(KeyCode.ENTER)) {
					System.out.println("PRESSSSSSSSSSSSSSSS");
					for (Enemy e : enemies) {
						if (!e.isDead()) {
							player.attack(e);
							RPGTextArea.text = "Kirby Attacks "+e.getName();
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
					
					won = enemies.get(0).isDead();
					
					for (Enemy e : enemies) {
						if(player.isDead()){
							setGameOver(true);
							System.out.println("Game Over");
							RPGTextArea.text = "GAME OVER";
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							break;
						}
						System.out.println(e.getName()+" "+e.getHp()+" dead : "+e.isDead());

						if (!e.isDead()) {
							e.attack(player);
							RPGTextArea.text = e.getName()+" attacks!";
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						won = won && e.isDead();
					}
					if(isGameOver()){
						break;
					}
					if(!isGameOver()){
						System.out.println("You won!");
						RPGTextArea.text = "You won";
						try {
							Thread.sleep(500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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