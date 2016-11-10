package logic;

public class Player extends Entity {
	
	public Player() {
		super("Kirby", 10, 1);
	}
	
	@Override
	void attack(Entity entity) {

		entity.decreaseHP(attack);
		System.out.println("Kirby attacks "+entity.getName()+"!");

		
	}



}
