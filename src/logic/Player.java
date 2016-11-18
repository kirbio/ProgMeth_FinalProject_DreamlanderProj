package logic;

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
	public void draw(GraphicsContext g) {
		// TODO Auto-generated method stub
		
	}



}
