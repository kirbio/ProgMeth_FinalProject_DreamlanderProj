/**
 * @author Phakawat and Nitit
 *
 */

package data;

import java.util.ArrayList;

public class LevelData {
	private ArrayList<EnemyEncounterPair> enemylist;

	private int[] attackguage;
	private int attackspeed;

	public LevelData() {
		enemylist = new ArrayList<EnemyEncounterPair>();
		setAttackGuage(new int[] { -1 });
	}

	public ArrayList<EnemyEncounterPair> getEnemyList() {
		return enemylist;
	}

	public void addEncounter(int enemytype, int amount) {
		enemylist.add(new EnemyEncounterPair(enemytype, amount));
	}

	public ArrayList<EnemyEncounterPair> getEncounterList() {
		return enemylist;
	}

	public int[] getAttackGuage() {
		return attackguage;
	}

	public void setAttackGuage(int[] attackguage) {
		this.attackguage = attackguage;
	}

	public int getAttackSpeed() {
		return attackspeed;
	}

	public void setAttackSpeed(int attackspeed) {
		this.attackspeed = attackspeed;
	}

}