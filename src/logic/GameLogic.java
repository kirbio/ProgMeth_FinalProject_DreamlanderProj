package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import data.GameData;
import graphic.IRenderable;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.scene.input.KeyCode;
import main.Main;
import screen.GameScreen;
import sound.AudioHolder;

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
	private static Counter counter;
	private static boolean readyToDraw;
	private static List<Enemy> defeatedEnemies;
	private static final int MID_BOSS = 4;
	private static final int BOSS = 9;
	
	public void startGame() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
		newRound = true;
		readyToDraw = false;
		defeatedEnemies = new ArrayList<>();
	}

	public void stopGame() {
		readyToDraw = false;
		defeatedEnemies.clear();
	}

	// This method loops until game over
	public void update() {
		if (newRound) {
			// initialize new round, run once per round
			startNewRound();		
			
		} else {
			ArrayList<Enemy> enemies = round.getEnemyList();
			RPGTextArea.text = "Press Any Key to Attack";
			
			if (counter.isTimeOut()) {
				waitforInput = false;
				atkGuage.resetGauge();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				enemyAttack(enemies);
				
				waitforInput = true;
				player.setBeingAttacked(false);
				counter.resetCounter();
				
				
			} else if (waitforInput && InputUtility.getKeyTriggered(KeyCode.ENTER) ) {
				waitforInput = false;
				atkGuage.setShowAttackDescription(true);
				atkGuage.playSound();
				
				try {
					Thread.sleep(800);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				int attackpower = atkGuage.getCurrentAtkPower();
				if (attackpower == 0) {
					enemyAttack(enemies);
				} else {
					player.setAttack(attackpower);				
					if (!player.isDead()) {
						System.out.println("Attack Success, Power: " + attackpower);	
						playerAttack(enemies);
						removeDead();
					} else {
						triggerGameOver();
					}
				}
				
				if (isAllEnemyDead(enemies)) {
					defeatedEnemies.addAll(round.getEnemyList());
					triggerWin();
					Main.instance.getRoundScreen().update();
					Main.instance.setToRoundScene();
				}
				
				atkGuage.resetGauge();		//start at bottom again after attack
				
				waitforInput = true;
				player.setBeingAttacked(false);
				for (Enemy e : enemies) {
					e.setBeingAttacked(false);
				}
				
				counter.resetCounter();
			}	

		}
		
		
		
	}
	
	private void startNewRound() {
		readyToDraw = false;
		waitforInput = true;
		newRound = false;
		
		System.out.println("----------LEVEL " + (level + 1) + "----------");
		round = new Round(level);
		round.addPlayer(player);
		
		int[] atkguage = GameData.getAttackGuageType(level);
		int atkguageseed = GameData.getAttackGuageSpeed(level);
		if(atkguage.length>1){
			atkGuage = new AttackGuage(atkguage,atkguageseed);
		}else{
			atkGuage = new AttackGuage(atkguageseed);
		}
		counter = new Counter();
		
		readyToDraw = true;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		AudioHolder.getInstance().playLevelBGM(level);
		Main.instance.setToGameScene();
		atkGuage.start();
		counter.start();
		
	}

	private void playerAttack(ArrayList<Enemy> enemies) {	
			for (Enemy e : enemies) {
				if (!e.isDead()) {
					RPGTextArea.text = "Kirby Attacks!";
					playAttackSound("player");
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
				 try {
						Thread.sleep(50);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}			 
			}
		}
		playAttackSound("enemy");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		 if (player.isDead()) {
			 triggerGameOver();
		 }
		
	}
	
	private boolean isAllEnemyDead(ArrayList<Enemy> enemies) {
		boolean allEnemyDead = true;
		for (Enemy e : enemies) {
			allEnemyDead = allEnemyDead && e.isDead();
		}
		return allEnemyDead;
	}
	
	private void triggerWin() {
		atkGuage.interrupt();
		AudioHolder.getInstance().stopBGM();
		newRound = true;
		RPGTextArea.text = "You win!";
		System.out.println("You win");
		if (level == MID_BOSS || level == BOSS) {
			AudioHolder.getInstance().playSFX("win2", 0.7);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			AudioHolder.getInstance().playSFX("win1", 0.7);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		level++;
		
	}
	
	private void triggerGameOver() {
		AudioHolder.getInstance().stopBGM();
		setGameOver(true);
		atkGuage.interrupt();	
		System.out.println("Game Over");
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Main.instance.setToGameOverScene();
		AudioHolder.getInstance().playSFX("gameover2", 1);
		
		RPGTextArea.text = "GAME OVER";
	}
	
	private void removeDead() {
		for (int i = RenderableHolder.getInstance().getEntities().size()-1 ; i >=0 ; i--) {			
			if (RenderableHolder.getInstance().getEntities().get(i).isDead()) {
				RenderableHolder.getInstance().remove(i);
			}
		}
	}
	
	private void playAttackSound(String attacker) {
		Random r = new Random();
		if (attacker.equals("player")) {
			if (atkGuage.isPerfect()) {
				AudioHolder.getInstance().playSFX("fightperfect", 1.0);
			} else {
				AudioHolder.getInstance().playSFX("fight" + (r.nextInt(2) + 1), 1.0);
			} 
		} else {
			AudioHolder.getInstance().playSFX("hurt" + (r.nextInt(2) + 1), 0.7);
			//AudioHolder.getInstance().playSFX("hurtvoice", 1.0);
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

	public List<Enemy> getDefeatedEnemies() {
		return defeatedEnemies;
	}
	
	
	
	
}