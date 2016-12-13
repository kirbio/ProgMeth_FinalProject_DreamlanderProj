/**
 * @author Phakawat and Nitit
 *
 */

package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import data.GameData;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.scene.input.KeyCode;
import main.Main;
import sound.AudioHolder;

public class GameLogic {
	public static final GameLogic instance = new GameLogic();
	private int mode;
	private boolean isGameOver;
	private int stage;
	private Player player;
	private Round round;
	private boolean isWaitforInput; // using to determine if it's wait for
									// input or not - using mainly for
									// update graphics and gauges
	private boolean isNewRound;
	private RPGTextArea textArea;
	private AttackGuage atkGuage;
	private Counter counter;
	private boolean isReadyToDraw;
	private List<Enemy> defeatedEnemies;
	public static final int MID_BOSS = 4;
	public static final int KAWASAKI = 6;
	public static final int BOSS = 9;
	private boolean isPause;

	public void startGame(int mode) {
		isGameOver = false;
		stage = 0;
		player = new Player();
		new GameData();
		textArea = new RPGTextArea();
		new StatusBar();
		textArea.start();
		isNewRound = true;
		isReadyToDraw = false;
		isPause = false;
		defeatedEnemies = new ArrayList<>();
		instance.mode = mode;
	}

	public void stopGame() {
		isReadyToDraw = false;
		defeatedEnemies.clear();
		textArea.interrupt();
		counter.interrupt();
		atkGuage.interrupt();
		RenderableHolder.getInstance().getEntities().clear();

	}

	// This method loops until game over
	public void update() {

		if (isNewRound) {
			// initialize new round, run once per round
			startNewRound();

		} else {
			CopyOnWriteArrayList<Enemy> enemies = round.getEnemyList();
			textArea.setText("Press ENTER to Attack");

			// if pause
			if (InputUtility.getKeyTriggered(KeyCode.SPACE)) {
				isPause = !isPause;
				isWaitforInput = !isWaitforInput;
				isReadyToDraw = !isReadyToDraw;
				return;
			}

			// if time out
			if (counter.isTimeOut()) {
				isWaitforInput = false;
				atkGuage.resetGauge();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				enemyAttack(enemies);

				isWaitforInput = true;
				player.setBeingAttacked(false);
				counter.resetCounter();

			} else if (isWaitforInput && InputUtility.getKeyTriggered(KeyCode.ENTER)) { // if
																						// attack
				isWaitforInput = false;
				atkGuage.setShowAttackDescription(true);
				atkGuage.playSound();

				try {
					Thread.sleep(800);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				// if attack missed
				int attackpower = atkGuage.getCurrentAtkPower();
				if (attackpower == 0) {
					enemyAttack(enemies);
				} else {
					player.setAttack(attackpower); // set attack
					if (!player.isDead()) {
						System.out.println("Attack Success, Power: " + attackpower);
						playerAttack(enemies);

						if (spawnSpecialEnemies()) {
							Position.set(enemies);
						}
						removeDeadIrenderable();
						addDeadEnemy();
						round.removeDeadEnemy();

					} else {
						triggerGameOver();
					}
				}

				if (isAllEnemyDead(enemies)) {
					triggerWin();
				}

				isWaitforInput = true;
				player.setBeingAttacked(false);
				for (Enemy e : enemies) {
					e.setBeingAttacked(false);
				}

				atkGuage.resetGauge(); // start at bottom again after attack
				atkGuage.setShowAttackDescription(false);
				counter.resetCounter();
			}

		}

	}

	private void startNewRound() {
		isReadyToDraw = false;
		isWaitforInput = true;
		isNewRound = false;

		System.out.println("----------LEVEL " + (stage + 1) + "----------");
		round = new Round(stage);
		round.addPlayer(player);

		int[] atkguage = GameData.getAttackGuageType(stage);
		int atkguageseed = GameData.getAttackGuageSpeed(stage);
		if (atkguage.length > 1) {
			atkGuage = new AttackGuage(atkguage, atkguageseed);
		} else {
			atkGuage = new AttackGuage(atkguageseed);
		}
		counter = new Counter();

		isReadyToDraw = true;

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		AudioHolder.getInstance().playLevelBGM(mode, stage);
		Main.instance.setToGameScene();
		atkGuage.start();
		counter.start();

	}

	private void playerAttack(CopyOnWriteArrayList<Enemy> enemies) {
		int exptotal = 0;
		int currLevel = player.getLevel();
		for (Enemy e : enemies) {
			if (!e.isDead()) {
				textArea.setText("Kirby Attacks " + e.getName() + "!");
				playAttackSound("player");
				player.attack(e);
				e.setBeingAttacked(true);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (e.isDead()) {
					textArea.setText(e.getName() + " is defeated !");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					int expGet = e.getMaxHP() * 30;
					exptotal += expGet;
				}
			}
		}
		if (exptotal > 0) {
			player.increaseEXP(exptotal);
			textArea.setText("Kirby get total " + exptotal + " EXP!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (currLevel != player.getLevel()) {
				textArea.setText("Kirby's level increased by " + (player.getLevel() - currLevel) + " !");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private void enemyAttack(CopyOnWriteArrayList<Enemy> enemies) {
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

	private boolean isAllEnemyDead(CopyOnWriteArrayList<Enemy> enemies) {
		boolean allEnemyDead = true;
		for (Enemy e : enemies) {
			allEnemyDead = allEnemyDead && e.isDead();
		}
		return allEnemyDead;
	}

	private void triggerWin() {
		atkGuage.interrupt();
		AudioHolder.getInstance().stopBGM();
		isNewRound = true;
		textArea.setText("You win!");
		System.out.println("You win");
		if (stage == BOSS) {
			AudioHolder.getInstance().playSFX("win2", 0.7);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			triggerCongrats();

		} else {
			if (stage == MID_BOSS) {
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
			stage++;
			// recoverHP();
			Main.instance.getRoundScreen().update();
			Main.instance.setToRoundScene();
		}

	}

	private void triggerGameOver() {
		AudioHolder.getInstance().stopBGM();
		setGameOver(true);
		atkGuage.interrupt();
		counter.interrupt();
		System.out.println("Game Over");
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Main.instance.setToGameOverScene();
		AudioHolder.getInstance().playSFX("gameover2", 1);
	}

	private void triggerCongrats() {
		AudioHolder.getInstance().stopBGM();
		setGameOver(true);
		atkGuage.interrupt();
		counter.interrupt();
		System.out.println("Congrats");
		Main.instance.initializeCongratsScreen();
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Main.instance.setToCongratsScene();
		AudioHolder.getInstance().playSFX("congrats", 1);
	}

	/*
	 * private void recoverHP() { // call after win every round - probably
	 * unused // now if (stage == MID_BOSS || stage == BOSS) {
	 * player.increaseHP(25); } else { player.increaseHP(10); } }
	 */

	private void removeDeadIrenderable() {
		for (int i = RenderableHolder.getInstance().getEntities().size() - 1; i >= 0; i--) {
			if (RenderableHolder.getInstance().getEntities().get(i).isDead()) {
				RenderableHolder.getInstance().remove(i);
			}
		}
	}

	private void addDeadEnemy() {
		for (Enemy e : round.getEnemyList()) {
			if (e.isDead())
				defeatedEnemies.add(e);
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
			// AudioHolder.getInstance().playSFX("hurtvoice", 1.0);
		}
	}

	private boolean spawnSpecialEnemies() {
		boolean added = false;
		for (int i = 0; i < round.getEnemyList().size(); i++) {
			Enemy e = round.getEnemy(i);
			if (e.getName().toLowerCase().equals("split doomer") && e.isDead()) {
				textArea.setText("Split Doomer splits apart!");
				double randomseed = Math.random();
				int amount;
				if (randomseed <= 0.4) {
					amount = 2; // 40%
				} else if (randomseed <= 0.9) {
					amount = 3; // 50%
				} else {
					amount = 4; // 10%
				}
				for (int i1 = 0; i1 < amount; i1++) {
					round.addEnemy(new Enemy(11)); // add small doomer
				}
				added = true;
			} else if (e.getName().toLowerCase().equals("chef kawazaki") && e.isDead()) { // If
																							// Chef
																							// Kawazaki,
																							// recover
																							// health
				textArea.setText("Kirby's HP recovered!");
				player.setHp(player.getMaxHP());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (added) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return added;

	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public int getStageLevel() {
		return stage;
	}

	public void setStageLevel(int level) {
		this.stage = level;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public boolean isWaitForInput() {
		return isWaitforInput;
	}

	public void setWaitForInput(boolean waitforInput) {
		this.isWaitforInput = waitforInput;
	}

	public boolean isNewRound() {
		return isNewRound;
	}

	public boolean isReadyToDraw() {
		return isReadyToDraw;
	}

	public void setReadyToDraw(boolean readyToDraw) {
		this.isReadyToDraw = readyToDraw;
	}

	public List<Enemy> getDefeatedEnemies() {
		return defeatedEnemies;
	}

	public boolean isPause() {
		return isPause;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}