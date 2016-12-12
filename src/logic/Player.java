package logic;

import graphic.DrawingUtility;
import javafx.scene.canvas.GraphicsContext;
import screen.GameScreen;
import sound.AudioHolder;

public class Player extends Entity {
	
	private static final int TOTAL_HP = 50;
	private int level;
	private int exp;
	
	public Player() {
		super("Kirby", TOTAL_HP, 1);
		x = Position.PLAYER_POSITION_X;
		y = Position.PLAYER_POSITION_Y - getAnimation().getFrameHeight()/2;
		z = 2;
		level = 1;
		exp = 0;
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

	public void increaseEXP(int amount){ //increasing EXP, will automanage level up
		int threashold = 350+200*level; //Next Level EXP
		exp+= amount;
		if(exp > threashold){
			exp-=threashold;
			level+=1;
		}
	}
	
	public void setLevel(int lv){ //manually set level, automatically reset EXP
		level = lv;
		exp = 0;
	}
	
	public int getEXP(){
		return exp;
	}
	
	public int getNextEXP(){
		return (int) (500+50*(Math.pow(level, 2)));	//algorithm for exp calculation
	}

	public int getLevel(){
		return level;
	}
	
	@Override
	public void setAttack(int attack) {
		this.attack = ( attack * ( level + 1 ) / 2 ) ;
	}


}
