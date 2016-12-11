package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import sound.AudioHolder;
import javafx.scene.media.AudioClip;

public class AttackGuage extends Thread implements IRenderable {
	private int[] attackgauge;
	private int speed;
	private int level;
	private int currentAtkPower = -1;
	private int index;
	private boolean showAttackDescription;
	private static final int UP = 1;
	private static final int DOWN = -1;
	public static final Color[] colorGauge = 			{Color.GRAY	, Color.DARKRED	, Color.RED	, Color.YELLOW	, Color.MAGENTA};
	public static final String[] attackDescription = 	{"MISSED!"		, "GOOD!"	, "GREAT!"		, "UNUSED!"		, "PERFECT!"};
	public static final AudioClip[] sound = {	AudioHolder.getInstance().getSFX("missed"), 
												AudioHolder.getInstance().getSFX("good"), 
												AudioHolder.getInstance().getSFX("great"),
												null,
												AudioHolder.getInstance().getSFX("perfect")};
	
	public AttackGuage(int speed) { // Constructor for using default attack array
		attackgauge = new int[] { 0, 0, 0, 0, 1, 2, 2, 4, 2, 2, 1, 0, 0, 0, 0 };
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
		showAttackDescription = false;
	}

	public AttackGuage(int[] type, int speed) { // Constructor for import attack array
		attackgauge = type;
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
		showAttackDescription = false;
	}
	
	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawAttackGauge(gc, attackgauge, index, currentAtkPower, showAttackDescription);
	}

	@Override
	public void run() {
		// TODO KETE SETSUNA SANIWA
		index = 0;
		int way = UP;
		//System.out.println("Attack gauge started " + GameLogic.instance.isNewRound() + " " + GameLogic.instance.isWaitForInput());
		while (!GameLogic.instance.isNewRound() && !GameLogic.instance.isGameOver()) {
			showAttackDescription = false;
			if (GameLogic.instance.isWaitForInput()) {		
				if (index == attackgauge.length - 1) {
					way = DOWN;
				} else if (index == 0) {
					way = UP;
				}
				if (way == UP) {
					index++;
				} else {
					index--;
				}
				currentAtkPower = attackgauge[index]; 
			} else {
				showAttackDescription = true;
			}
			try {
				Thread.sleep(100-(speed*10));		//affect gauge speed
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
			
	}
	
	public void resetGauge() {
		index = 0;
		showAttackDescription = false;
	}
	public int[] getAttackpower() {
		return attackgauge;
	}

	public void setAttackpower(int[] attackpower) {
		this.attackgauge = attackpower;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCurrentAtkPower() {
		return currentAtkPower;
	}

	public void setCurrentAtkPower(int currentAtkPower) {
		this.currentAtkPower = currentAtkPower;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isPerfect() {
		return (currentAtkPower == 4);
	}

	@Override
	public boolean isDead() {
		return false;
	}
	
	public void playSound() {
		sound[currentAtkPower].setVolume(1.0);
		sound[currentAtkPower].play();
	}
}
