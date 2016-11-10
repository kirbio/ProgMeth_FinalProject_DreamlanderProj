package data;

import java.util.ArrayList;

public class LevelData{
	private ArrayList<EnemyEncounterPair> enemylist ;
	LevelData(){
		enemylist = new ArrayList<EnemyEncounterPair>();
	}
	public ArrayList<EnemyEncounterPair> getEnemyList() {
		return enemylist;
	}
	public void addEncounter(int enemytype,int amount){
		enemylist.add(new EnemyEncounterPair(enemytype,amount));
	}
	public ArrayList<EnemyEncounterPair> getEncounterList(){
		return enemylist;
	}
	
}