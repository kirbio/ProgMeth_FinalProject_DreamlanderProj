package logic;

import graphic.EntityAnimation;
import graphic.IRenderable;
import graphic.RenderableHolder;

public abstract class Entity implements IRenderable{
	protected String name;
	protected int hp;
	protected int maxHP;
	protected int attack;
	protected boolean isDead;
	protected EntityAnimation animation;
	protected boolean isBeingAttacked;
	
	protected int x,y,z;
	
	Entity(String name, int hp, int attack) {
		this.name = name;
		this.maxHP = this.hp = hp;
		this.attack = attack;
		isDead = isBeingAttacked =false;
		
		animation = new EntityAnimation(getAnimationFilePath());
		
		RenderableHolder.getInstance().add(this);	
		
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
		if (hp > maxHP) {
			hp = maxHP;
		}
	}
	
	void decreaseHP(int amount) {
		this.hp -= amount;
		if (hp < 0) {
			hp = 0;
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

	public int getMaxHP() {
		return maxHP;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public boolean isBeingAttacked() {
		return isBeingAttacked;
	}

	public void setBeingAttacked(boolean isBeingAttacked) {
		this.isBeingAttacked = isBeingAttacked;
	}
		
	
	/*==================================
	 * Animation related Methods
	 ==================================*/

	public EntityAnimation getAnimation() {
		return animation;
	}
	
	private String getAnimationFilePath() {
		return getName().toLowerCase().replace(' ', '_') + ".png";
	}
	
}
