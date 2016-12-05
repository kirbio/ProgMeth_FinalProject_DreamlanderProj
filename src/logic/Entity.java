package logic;

import graphic.IRenderable;

public abstract class Entity implements IRenderable{
	protected String name;
	protected int hp;
	protected final int maxHP;
	protected int attack;
	protected boolean isDead;
	
	protected int x,y,z;
	
	Entity(String name, int hp, int attack) {
		this.name = name;
		this.maxHP = this.hp = hp;
		this.attack = attack;
		isDead = false;
		
		x = 0;
		y = 0;
		z = 1;
	}
	
	/*==================================
	 * Position/Render-related methods
	================================== */
	
	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}
	
	//draw() is implemented on subclass
	
	public void setZ(int z) {
		// TODO Auto-generated method stub
		this.z=z;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	/*==================================
	 * RPG Game Logic Methods
	 ==================================*/
	
	
	abstract void attack(Entity entity);
	
	
	void increaseHP(int amount) { 
		this.hp += amount;
	}
	
	void decreaseHP(int amount) {
		this.hp -= amount;
		if (hp <= 0) {
			isDead = true;
			System.out.println(getName()+" is Dead!");
		}
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	
}
