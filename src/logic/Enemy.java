package logic;

import data.GameData;
import graphic.AttackAnimation;
import graphic.DrawingUtility;
import javafx.scene.canvas.GraphicsContext;
import screen.GameScreen;

public class Enemy extends Entity{
	
	public Enemy(String name, int hp, int attack) {
		super(name, hp, attack);
		x = GameScreen.SCREEN_WIDTH;
		y = GameScreen.SCREEN_HEIGHT;
		z = 1;
		
	}
	
	public Enemy(int type) {
		super(GameData.getEnemyName(type), GameData.getEnemyHp(type), GameData.getEnemyAtk(type));
		x = GameScreen.SCREEN_WIDTH;
		y = GameScreen.SCREEN_HEIGHT;
		z = 1;
	}
	
	@Override
	void attack(Entity entity) {
		new AttackAnimation(entity.getX(),entity.getY());
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
		this.z = y;		//prevent lower enemy to block upper enemy hp bar
	}
	
		

}
