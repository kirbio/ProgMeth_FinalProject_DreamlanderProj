/**
 * @author Phakawat and Nitit
 *
 */

package data;

public class EnemyEncounterPair {
	private int[] enemypair = { 0, 0 };

	EnemyEncounterPair(int enemytype, int amount) {
		enemypair[0] = enemytype;
		enemypair[1] = amount;
	}

	public int[] getEnemyPair() {
		return enemypair;
	}

	public int getEnemyType() {
		return enemypair[0];
	}

	public int getEnemyAmount() {
		return enemypair[1];
	}

	@Override
	public String toString() {
		return GameData.getEnemyName(enemypair[0]) + ":" + enemypair[1];
	}
}