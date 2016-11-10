package logic;

public abstract class Entity {
	protected String name;
	protected int hp;
	protected int attack;
	protected boolean isDead;
	
	Entity(String name, int hp, int attack) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
		isDead = false;
	}
	
	abstract void attack(Entity entity);
	
	void increaseHP(int amount) { 
		this.hp += amount;
	}
	
	void decreaseHP(int amount) {
		this.hp -= amount;
		if (hp <= 0) {
			isDead = true;
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
