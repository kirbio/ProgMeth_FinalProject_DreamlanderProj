package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import input.InputUtility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class AttackGuage extends Thread implements IRenderable {
	private int[] attackpower;
	private int speed;
	private int level;
	public static int currentAtkPower = -1;

	public AttackGuage(int speed) { // Constructor for using default attack type
		attackpower = new int[] { 0, 0, 0, 0, 1, 2, 2, 4, 2, 2, 1, 0, 0, 0, 0 };
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
	}

	public AttackGuage(int[] type, int speed) { // Constructor for Writing Own
												// attack type setting
		attackpower = type;
		this.speed = speed;
		level = GameLogic.instance.getLevel();
		RenderableHolder.getInstance().add(this);
	}

	@Override
	public void run() {
		// TODO KETE SETSUNA SANIWA
		int f = 0; // guage position
		int way = 0;
		while (GameLogic.instance.isWaitForInput()) {
			if (f > 13) {
				f--;
				way = 1;
			} else if (f < 1) {
				f++;
				way = 0;
			} else {
				if (way == 1) {
					f--;
				} else {
					f++;
				}
			}
			currentAtkPower = attackpower[f];
//			System.out.println("Guage: "+ currentAtkPower);
			// "+GameLogic.isWaitForInput());
/*			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}

	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void draw(GraphicsContext gc) {
		
	}

}
