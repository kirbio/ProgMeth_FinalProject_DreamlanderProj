package logic;

import data.GameData;
import graphic.DrawingUtility;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Entity{
	
	public Enemy(String name, int hp, int attack) {
		super(name, hp, attack);
		x = 0;
		y = 0;
		z = 1;
		
	}
	
	public Enemy(int type) {
		super(GameData.getEnemyName(type), GameData.getEnemyHp(type), GameData.getEnemyAtk(type));
		x = 0;
		y = 0;
		z = 1;
	}
	
	@Override
	void attack(Entity entity) {
		entity.decreaseHP(attack);
		System.out.println(name+" attacks!");
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (isBeingAttacked()) {
			DrawingUtility.drawHurtEnemy(gc, this);
		} else {
			DrawingUtility.drawEnemy(gc, this);
		}
		
		
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
		this.z = y;
	}
	
		

}
