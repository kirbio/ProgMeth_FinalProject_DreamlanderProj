package logic;

import graphic.GameScreen;
import javafx.scene.canvas.GraphicsContext;

public class Player extends Entity {
	
	public Player() {
		super("Kirby", 50, 1);
	}
	
	@Override
	void attack(Entity entity) {

		entity.decreaseHP(attack);
		System.out.println("Kirby attacks "+entity.getName()+"!");

		
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}



}
