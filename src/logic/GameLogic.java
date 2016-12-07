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
		// initialize new round, run once per round
		if (newRound) {
			System.out.println("----------LEVEL " + (level + 1) + "----------");
			round = new Round(level);
			round.addPlayer(player);
			
			atkGuage = new AttackGuage(2);
			atkGuage.start();
			waitforInput = true;
			newRound = false;
			
		} else {
			ArrayList<Enemy> enemies = round.getEnemyList();
			int attackpower = -1;
			RPGTextArea.text = "Press Any Key to Attack";
			
			/*
			 * if (attackpower < 0) { waitforInput = true; }
			 */
			// System.out.println(InputUtility.getKeyTriggered(KeyCode.ENTER));
			
			if (waitforInput && InputUtility.getKeyTriggered(KeyCode.ENTER) ) {
				waitforInput = false;
				attackpower = AttackGuage.currentAtkPower;
				player.setAttack(attackpower);				
				if (!checkPlayerDead()) {
					playerAttack(enemies);
					System.out.println("Attack Success, Power: " + attackpower);				
				}
				if (isAllEnemyDead(enemies)) {
					win();
				} else {
					enemyAttack(enemies);
				}
				
				waitforInput = true;
				System.out.println(waitforInput);
			}

		}
	}

	private void playerAttack(ArrayList<Enemy> enemies) {	
			for (Enemy e : enemies) {
				if (!e.isDead()) {
					player.attack(e);
					RPGTextArea.text = "Kirby Attacks " + e.getName();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}	
	}
	
	private void enemyAttack(ArrayList<Enemy> enemies) {
		for (Enemy e : enemies) {
			if (!e.isDead()) {
				 e.attack(player);
				 RPGTextArea.text = e.getName()+" attacks!";
				 if (checkPlayerDead()) {
					 break;
				 }
			}
		}		
	}

	private boolean checkPlayerDead() {
		if (player.isDead()) {
			setGameOver(true);
			System.out.println("Game Over");
			RPGTextArea.text = "GAME OVER";
			return true;
		}
		return false;
	}

	private boolean isAllEnemyDead(ArrayList<Enemy> enemies) {
		boolean allEnemyDead = true;
		for (Enemy e : enemies) {
			allEnemyDead = allEnemyDead && e.isDead();
		}
		return allEnemyDead;
	}
	
	private void win() {
		System.out.println("You win");
		RPGTextArea.text = "You win";
		newRound = true;
		level++;
		
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