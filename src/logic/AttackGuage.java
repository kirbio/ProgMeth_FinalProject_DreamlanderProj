package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class AttackGuage extends Thread implements IRenderable {
	private int[] attackpower;
	private int speed;
	private int level;
	private int currentAtkPower = -1;
	private int index;
	public static final Color[] colorGauge = {Color.DARKRED, Color.DARKORANGE, Color.RED, Color.YELLOW, Color.MAGENTA};
	private static final int UP = 1;
	private static final int DOWN = -1;
	public AttackGuage(int speed) { // Constructor indexor using deindexault attack type
		attackpower = new int[] { 0, 0, 0, 0, 1, 2, 2, 4, 2, 2, 1, 0, 0, 0, 0 };
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
	}

	public AttackGuage(int[] type, int speed) { // Constructor indexor Writing Own
												// attack type setting
		attackpower = type;
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
	}
	
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawAttackGauge(gc, attackpower, index, currentAtkPower);
	}

	@Override
	public void run() {
		// TODO KETE SETSUNA SANIWA
		index = 0;
		int way = UP;
		while (!GameLogic.instance.isNewRound()) {
			if (GameLogic.instance.isWaitForInput()) {	
				if (index == attackpower.length - 1) {
					way = DOWN;
				} else if (index == 0) {
					way = UP;
				}
				if (way == UP) {
					index++;
				} else {
					index--;
				}
				currentAtkPower = attackpower[index]; 
			}
			try {
				Thread.sleep(100-(speed*10));
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			}
			
		}
	
	public int[] getAttackpower() {
		return attackpower;
	}

	public void setAttackpower(int[] attackpower) {
		this.attackpower = attackpower;
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
}
