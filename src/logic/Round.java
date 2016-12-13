/**
 * @author Phakawat and Nitit
 *
 */
package logic;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import data.EnemyEncounterPair;
import data.GameData;
import graphic.GameBackground;
import graphic.RenderableHolder;

public class Round {
	CopyOnWriteArrayList<Enemy> enemyList;
	Player player;

	public Round(int level) {
		enemyList = new CopyOnWriteArrayList<Enemy>();
		setEnemyInLevel(level);
		Position.set(enemyList);
		RenderableHolder.getInstance().add(new GameBackground(GameLogic.instance.getMode(), level));
	}

	public void setEnemyInLevel(int level) {
		ArrayList<EnemyEncounterPair> data = GameData.getEnemyList(level);
		for (EnemyEncounterPair i : data) {
			for (int j = 0; j < i.getEnemyAmount(); j++) {
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

	public CopyOnWriteArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public Enemy getEnemy(int index) {
		return enemyList.get(index);
	}

	public void removeEnemy(int index) {
		enemyList.remove(index);
	}

	public void removeDeadEnemy() {
		for (int i = enemyList.size() - 1; i >= 0; i--) {
			if (enemyList.get(i).isDead()) {
				removeEnemy(i);
			}
		}
	}

}
