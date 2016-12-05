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
	private static boolean waitforInput; //using to determine if it's wait for input or not - using mainly for AttackGuage
	
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
	
	public static boolean isWaitForInput() {
		return waitforInput;
	}
	
	public static void setWaitForInput(boolean waitforInput) {
		GameLogic.waitforInput = waitforInput;
	}

	public void startGame() {
		
		while(!isGameOver()) {
			round = new Round(level);
			round.addPlayer(player);
			ArrayList<Enemy> enemies = round.getEnemyList();	
			int attackpower = -1;

			System.out.println("----------LEVEL "+(level+1)+"----------");
			boolean roundEnds = false;
			AttackGuage atkguage = new AttackGuage(2);
			atkguage.start();
			while(!roundEnds) {	
				//System.out.println(isAllEnemyDead());
				RPGTextArea.text = "Press Any Key to Attack";
				if(attackpower<0){
					waitforInput = true;
				}
//				System.out.println(InputUtility.getKeyTriggered(KeyCode.ENTER));
				if(InputUtility.getKeyTriggered(KeyCode.ENTER)){
					attackpower = AttackGuage.currentAtkPower;
					System.out.println("Attack Success, Power: "+attackpower);
					waitforInput = false;
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					attackpower = -1;
				}
//				if (InputUtility.getKeyTriggered(KeyCode.ENTER)) {
//					System.out.println("PRESSSSSSSSSSSSSSSS");
//					for (Enemy e : enemies) {
//						if (!e.isDead()) {
//							player.attack(e);
//							RPGTextArea.text = "Kirby Attacks "+e.getName();
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//						}
//					}
//					
//					for (Enemy e : enemies) {
//						if(player.isDead()){
//							setGameOver(true);
//							System.out.println("Game Over");
//							RPGTextArea.text = "GAME OVER";
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//							break;
//						}
//						System.out.println(e.getName()+" "+e.getHp()+" dead : "+e.isDead());
//
//						if (!e.isDead()) {
//							e.attack(player);
//							RPGTextArea.text = e.getName()+" attacks!";
//							try {
//								Thread.sleep(500);
//							} catch (InterruptedException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//						}
//						
//					}
//					
//					if(isGameOver()){
//						break;
//					}
//					
//					if(isAllEnemyDead()){
//						System.out.println("You won!");
//						RPGTextArea.text = "You won";
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						level++;
//						roundEnds = true;
//					}	
//					
//				}
				
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
	
	private boolean isAllEnemyDead() {
		ArrayList<Enemy> enemies = round.getEnemyList();
		boolean allEnemyDead = true;
		for (Enemy e : enemies) {
			allEnemyDead = allEnemyDead && e.isDead();
		}
		return allEnemyDead;
	}
}