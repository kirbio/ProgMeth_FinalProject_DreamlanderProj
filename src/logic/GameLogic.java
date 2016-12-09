package logic;

import java.util.ArrayList;
import java.util.Scanner;

import data.GameData;
import graphic.GameScreen;
import graphic.IRenderable;
import graphic.RenderableHolder;
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
	private static boolean readyToDraw;
	
	public void startGame() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
		newRound = true;
		readyToDraw = false;
	}

	public void stopGame() {

	}

	// This method loops until game over
	public void update() {
		if (newRound) {
			// initialize new round, run once per round
			startNewRound();		
			
		} else {
			ArrayList<Enemy> enemies = round.getEnemyList();
			RPGTextArea.text = "Press Any Key to Attack";
			
			if (waitforInput && InputUtility.getKeyTriggered(KeyCode.ENTER) ) {
				waitforInput = false;
				int attackpower = atkGuage.getCurrentAtkPower();
				if (attackpower == 0) {
					enemyAttack(enemies);
				} else {
					player.setAttack(attackpower);				
					if (!checkPlayerDead()) {
						System.out.println("Attack Success, Power: " + attackpower);	
						playerAttack(enemies);	
				}
				}	
				if (isAllEnemyDead(enemies)) {
					win();
				}
				waitforInput = true;
				player.setBeingAttacked(false);
				for (Enemy e : enemies) {
					e.setBeingAttacked(false);
				}
			}	

		}
		
		removeDead();
		
	}
	
	private void startNewRound() {
		System.out.println("----------LEVEL " + (level + 1) + "----------");
		round = new Round(level);
		round.addPlayer(player);
		
		waitforInput = true;
		newRound = false;
		
		int[] atkguage = GameData.getAttackGuageType(level);
		int atkguageseed = GameData.getAttackGuageSpeed(level);
		if(atkguage.length>1){
			atkGuage = new AttackGuage(atkguage,atkguageseed);
		}else{
			atkGuage = new AttackGuage(atkguageseed);
		}
		atkGuage.start();
		readyToDraw = true;
	}

	private void playerAttack(ArrayList<Enemy> enemies) {	
			for (Enemy e : enemies) {
				if (!e.isDead()) {
					RPGTextArea.text = "Kirby Attacks!";
					player.attack(e);
					e.setBeingAttacked(true);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}	
	}
	
	private void enemyAttack(ArrayList<Enemy> enemies) {
		player.setBeingAttacked(true);
		for (Enemy e : enemies) {
			if (!e.isDead()) {
				 RPGTextArea.text = "Enemies attack!";
				 e.attack(player);
				 if (checkPlayerDead()) {
					 break;
				 }
				 try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
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
		atkGuage.interrupt();
		System.out.println("You win");
		RPGTextArea.text = "You win!";
		newRound = true;
		level++;
		
	}
	
	private void removeDead() {
		for (int i = RenderableHolder.getInstance().getEntities().size()-1 ; i >=0 ; i--) {			
			if (RenderableHolder.getInstance().getEntities().get(i).isDead()) {
				RenderableHolder.getInstance().remove(i);
			}
		}
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

	public boolean isNewRound() {
		return newRound;
	}

	public boolean isReadyToDraw() {
		return readyToDraw;
	}

	public void setReadyToDraw(boolean readyToDraw) {
		GameLogic.readyToDraw = readyToDraw;
	}
	
	
	
	
}