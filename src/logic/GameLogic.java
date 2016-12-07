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
	private static boolean waitforInput; // using to determine if it's wait for
											// input or not - using mainly for
											// AttackGuage
	private static boolean newRound;
	private static AttackGuage atkGuage;

	public void startGame() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
		newRound = true;
	}

	public void stopGame() {

	}

	// This method loops until game over
	public void update() {
		//initialize new round, run once per round
		if (newRound) {
			System.out.println("----------LEVEL " + (level + 1) + "----------");
			round = new Round(level);
			round.addPlayer(player);
			ArrayList<Enemy> enemies = round.getEnemyList();		
			atkGuage = new AttackGuage(2);
			atkGuage.start();
			waitforInput = true;
			newRound = false;			
		} else {
			// System.out.println(isAllEnemyDead());
			int attackpower = -1;
			RPGTextArea.text = "Press Any Key to Attack";
			/*if (attackpower < 0) {
				waitforInput = true;
			}*/
			// System.out.println(InputUtility.getKeyTriggered(KeyCode.ENTER));
			if (InputUtility.getKeyTriggered(KeyCode.ENTER)) {
				attackpower = AttackGuage.currentAtkPower;
				System.out.println("Attack Success, Power: " + attackpower);
				waitforInput = false;
				attackpower = -1;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				waitforInput = true;
				System.out.println(waitforInput);
			}
			
			// if (InputUtility.getKeyTriggered(KeyCode.ENTER)) {
			// System.out.println("PRESSSSSSSSSSSSSSSS");
			// for (Enemy e : enemies) {
			// if (!e.isDead()) {
			// player.attack(e);
			// RPGTextArea.text = "Kirby Attacks "+e.getName();
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// }
			// }
			//
			// for (Enemy e : enemies) {
			// if(player.isDead()){
			// setGameOver(true);
			// System.out.println("Game Over");
			// RPGTextArea.text = "GAME OVER";
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// break;
			// }
			// System.out.println(e.getName()+" "+e.getHp()+" dead :
			// "+e.isDead());
			//
			// if (!e.isDead()) {
			// e.attack(player);
			// RPGTextArea.text = e.getName()+" attacks!";
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// }
			//
			// }
			//
			// if(isGameOver()){
			// break;
			// }
			//
			// if(isAllEnemyDead()){
			// System.out.println("You won!");
			// RPGTextArea.text = "You won";
			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e1) {
			// // TODO Auto-generated catch block
			// e1.printStackTrace();
			// }
			// level++;
			// roundEnds = true;
			// }
			//
			// }

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		GameLogic.player = player;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		GameLogic.round = round;
	}

	public boolean isWaitForInput() {
		return waitforInput;
	}

	public void setWaitForInput(boolean waitforInput) {
		GameLogic.waitforInput = waitforInput;
	}
}