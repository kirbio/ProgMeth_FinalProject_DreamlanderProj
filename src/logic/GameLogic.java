package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import data.GameData;
import graphic.DrawingUtility;
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
	private static RPGTextArea textArea;
	private static AttackGuage atkGuage;
	private static Counter counter;
	private static boolean readyToDraw;
	private static List<Enemy> defeatedEnemies;
	private static final int MID_BOSS = 4;
	private static final int BOSS = 9;
	private static boolean pause;
	
	public void startGame() {
		isGameOver = false;
		level = 0;
		player = new Player();
		new GameData();
		textArea = new RPGTextArea();
		textArea.start();
		newRound = true;
		readyToDraw = false;
		pause = false;
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
			textArea.setText("Press Any Key to Attack");
			
			if( InputUtility.getKeyTriggered(KeyCode.SPACE) ) {
				pause = !pause;
				waitforInput = !waitforInput;
				readyToDraw = !readyToDraw;
				return;
			}
			
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
					player.setAttack(attackpower+100);				
					if (!player.isDead()) {
						System.out.println("Attack Success, Power: " + attackpower);	
						playerAttack(enemies);
						
						spawnSpecialEnemies();
						removeDeadIrenderable();
						addDeadEnemy();
						round.removeDeadEnemy();
						Position.set(enemies);

					} else {
						triggerGameOver();
					}
				}
				
				if (isAllEnemyDead(enemies)) {
					
					triggerWin();
					Main.instance.getRoundScreen().update();
					Main.instance.setToRoundScene();
				}
				
				
				waitforInput = true;
				player.setBeingAttacked(false);
				for (Enemy e : enemies) {
					e.setBeingAttacked(false);
				}
				
				atkGuage.resetGauge();		//start at bottom again after attack
				atkGuage.setShowAttackDescription(false);
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
					textArea.setText("Kirby Attacks "+e.getName()+"!");
					playAttackSound("player");
					player.attack(e);	
					e.setBeingAttacked(true);
					try {
						Thread.sleep(1000);
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
				 textArea.setText("Enemies attack!");
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
		textArea.setText("You win!");
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
		recoverHP();
		
	}
	
	private void triggerGameOver() {
		AudioHolder.getInstance().stopBGM();
		setGameOver(true);
		atkGuage.interrupt();	
		System.out.println("Game Over");
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Main.instance.setToGameOverScene();
		AudioHolder.getInstance().playSFX("gameover2", 1);
		
		textArea.setText("GAME OVER");
	}
	
	private void recoverHP() {		//call after win every round
		if (level == MID_BOSS || level == BOSS) {		
			player.increaseHP(25);
		} else {
			player.increaseHP(10);
		}
	}
	
	private void removeDeadIrenderable() {
		for (int i = RenderableHolder.getInstance().getEntities().size()-1 ; i >=0 ; i--) {			
			if (RenderableHolder.getInstance().getEntities().get(i).isDead()) {
				RenderableHolder.getInstance().remove(i);
			}
		}
	}
	
	private void addDeadEnemy() {
		for (Enemy e : round.getEnemyList()) {
			if (e.isDead()) defeatedEnemies.add(e);
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
	
	private void spawnSpecialEnemies() {
		boolean added = false;
		for (int i = 0 ; i < round.getEnemyList().size() ; i++) {
			Enemy e = round.getEnemy(i);
			if (e.getName().toLowerCase().equals("split doomer") && e.isDead()) {
				textArea.setText("Split Doomer splits apart!");
				double randomseed = Math.random();
				int amount;
				if (randomseed <= 0.4) {
					amount = 2;		//40%
				} else if (randomseed <= 0.9) {
					amount = 3;		//50%
				} else {
					amount = 4;		//10%
				}
				for (int i1 = 0 ; i1 < amount ; i1++ ) {
					round.addEnemy(new Enemy(11));	//add small doomer
				}
				added = true;
			}
		}
		
		if (added) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
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

	public List<Enemy> getDefeatedEnemies() {
		return defeatedEnemies;
	}

	public boolean isPause() {
		return pause;
	}
	
	
	
	
}