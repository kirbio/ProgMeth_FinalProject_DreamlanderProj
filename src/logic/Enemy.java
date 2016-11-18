package logic;

import data.GameData;
import javafx.scene.canvas.GraphicsContext;

public class Enemy extends Entity{
	
	public Enemy(String name, int hp, int attack) {
		super(name, hp, attack);
		
	}
	
	public Enemy(int type) {
		super(GameData.getEnemyName(type), GameData.getEnemyHp(type), GameData.getEnemyAtk(type));
		
	}
	
	@Override
	void attack(Entity entity) {
		entity.decreaseHP(attack);
		System.out.println(name+" attacks!");
		System.out.println("Kirby HP Left: "+ entity.getHp());
		
	}

	@Override
	public void draw(GraphicsContext g) {
		// TODO Auto-generated method stub
		
	}
	
		

}
