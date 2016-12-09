package logic;

import graphic.DrawingUtility;
import graphic.GameScreen;
import javafx.scene.canvas.GraphicsContext;

public class Player extends Entity {
	
	private static final int TOTAL_HP = 50;
	
	public Player() {
		super("Kirby", TOTAL_HP, 1);
		x = Position.PLAYER_POSITION_X;
		y = Position.PLAYER_POSITION_Y - getAnimation().getFrameHeight()/2;
		z = 2;
	}
	
	@Override
	void attack(Entity entity) {

		entity.decreaseHP(attack);
		System.out.println("Kirby attacks "+entity.getName()+"!");

		
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (isBeingAttacked()) {
			DrawingUtility.drawHurtPlayer(gc, this);
		} else {
			DrawingUtility.drawPlayer(gc, this);
		}
		
	}




}
