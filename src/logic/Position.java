/**
 * @author Phakawat and Nitit
 *
 */
package logic;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import graphic.DrawingUtility;

public class Position {
	public static final int PLAYER_POSITION_X = DrawingUtility.PLAY_SCREEN_WIDTH * 75 / 100;
	public static final int PLAYER_POSITION_Y = DrawingUtility.STATUS_BAR_HEIGHT
			+ (DrawingUtility.PLAY_SCREEN_HEIGHT / 2);
	private static int amount;

	public static void set(CopyOnWriteArrayList<Enemy> enemyList) {
		amount = enemyList.size();
		PlaceEnemy(enemyList);
	}

	public static void set(CopyOnWriteArrayList<Enemy> enemyList, String format) {
		amount = enemyList.size();
		if (format.toLowerCase().equals(".......")) {
			/* insert high quality code here */
		} else {
			LineForm(enemyList);
		}

	}

	private static void PlaceEnemy(CopyOnWriteArrayList<Enemy> enemyList) {
		final int ENEMY_ORIGIN_X = DrawingUtility.PLAY_SCREEN_WIDTH / 4 + 30;
		final int ENEMY_ORIGIN_Y = PLAYER_POSITION_Y - 30;

		if (enemyList.size() == 1) {
			Enemy enemy = enemyList.get(0);
			enemy.setX(ENEMY_ORIGIN_X);
			enemy.setY(ENEMY_ORIGIN_Y);
		} else if (enemyList.size() == 2) {
			for (int i = 0; i < 2; i++) {
				Enemy enemy = enemyList.get(i);
				enemy.setX(ENEMY_ORIGIN_X);
				enemy.setY((int) (ENEMY_ORIGIN_Y + 60 * (Math.pow(-1, i))));
			}
		} else {
			int totalEnemy = enemyList.size();
			for (int i = 0; i < totalEnemy; i++) {
				Enemy enemy = enemyList.get(i);
				enemy.setX((int) (ENEMY_ORIGIN_X + 80 * Math.cos(Math.toRadians(i * 360 / totalEnemy))));
				enemy.setY((int) (ENEMY_ORIGIN_Y + 80 * Math.sin(Math.toRadians(i * 360 / totalEnemy))));
			}
		}
	}

	private static void LineForm(CopyOnWriteArrayList<Enemy> enemyList) {
		int offset;
		int number = 0; // number in line, 0 is first in line
		int line_number = 0; // 0 is center, 1 will be bottom, 2 will be top,
								// and so on
		for (int i = 0; i < amount; i++) {

			Enemy enemy = enemyList.get(i);

			// first, check if x is valid
			int x = (int) (DrawingUtility.PLAY_SCREEN_WIDTH / 2
					- ((number) * (1.1 * enemy.getAnimation().getFrameWidth())));

			if (x < 5) { // if out of screen, go to new line
				number = 0;
				line_number++;
				x = (int) (DrawingUtility.PLAY_SCREEN_WIDTH / 2
						- ((number) * (1.1 * enemy.getAnimation().getFrameWidth())));
			}
			number++;

			switch (line_number) { // determining offset for each line
			default:
				offset = 0;
				break;
			case 1:
				offset = DrawingUtility.PLAY_SCREEN_HEIGHT / 3;
				break;
			case 2:
				offset = -DrawingUtility.PLAY_SCREEN_HEIGHT / 3;
				break;

			}

			enemy.setX(x);
			enemy.setY(DrawingUtility.STATUS_BAR_HEIGHT + offset
					+ (DrawingUtility.PLAY_SCREEN_HEIGHT / 2 - (enemy.getAnimation().getFrameHeight() / 2)));

		}
	}

}
