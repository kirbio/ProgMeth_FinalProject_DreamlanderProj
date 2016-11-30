package logic;

import java.util.ArrayList;

import data.EnemyEncounterPair;
import data.GameData;

public class Round {
	ArrayList<Enemy> enemyList;
	Player player;
	
	public Round(int level) {
		enemyList = new ArrayList<Enemy>();
		setEnemyInLevel(level);
		
	}

	public void setEnemyInLevel(int level) {
		ArrayList<EnemyEncounterPair> data = GameData.getEnemyList(level);
		for(EnemyEncounterPair i:data){
			for(int j=0;j<i.getEnemyAmount();j++){
				addEnemy(new Enemy(i.getEnemyType()));
			}
		}
//		switch (level) {
//			case 0 :	addEnemy(new Enemy("Waddle Dee", 3, 0));
//						break;
//			case 1 :	addEnemy(new Enemy("Waddle Dee", 3, 0));
//						addEnemy(new Enemy("Waddle Doo", 5, 0));
//						break;
//		}		
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
	
	
}
