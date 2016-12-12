package logic;

import java.util.ArrayList;

import data.EnemyEncounterPair;
import data.GameData;
import graphic.GameBackground;
import graphic.RenderableHolder;
import main.Main;

public class Round {
	ArrayList<Enemy> enemyList;
	Player player;
	
	public Round(int level) {
		enemyList = new ArrayList<Enemy>();
		setEnemyInLevel(level);
		Position.set(enemyList);
		RenderableHolder.getInstance().add(new GameBackground(GameLogic.instance.getMode(), level));
	}

	public void setEnemyInLevel(int level) {
		ArrayList<EnemyEncounterPair> data = GameData.getEnemyList(level);
		for(EnemyEncounterPair i:data){
			for(int j=0;j<i.getEnemyAmount();j++){
				addEnemy(new Enemy(i.getEnemyType()));
				System.out.println(i.getEnemyType());
			}
		}		
	}
	
	public void addEnemy(Enemy enemy) {		
		enemyList.add(enemy);
	}
	
	public void addPlayer(Player player) {
		this.player = player;
	}
	
	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}
	
	public Enemy getEnemy(int index) {
		return enemyList.get(index);
	}
	
	public void removeEnemy(int index ) {
		 enemyList.remove(index);
	}
	
	public void removeDeadEnemy() {
		for (int i = enemyList.size()-1 ; i >=0 ; i--) {			
			if (enemyList.get(i).isDead()) {
				removeEnemy(i);
			}
		}
	}
	
	
}
