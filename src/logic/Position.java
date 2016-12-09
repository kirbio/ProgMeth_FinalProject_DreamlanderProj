package logic;

import java.util.ArrayList;
import java.util.Random;

import graphic.DrawingUtility;
import graphic.GameScreen;
import graphic.IRenderable;
import graphic.RenderableHolder;

public class Position {
	public static final int PLAYER_POSITION_X = DrawingUtility.PLAY_SCREEN_WIDTH*75/100;
	public static final int PLAYER_POSITION_Y = DrawingUtility.STATUS_BAR_HEIGHT + (DrawingUtility.PLAY_SCREEN_HEIGHT/2);
	private static int amount;
	
	public static void set(ArrayList<Enemy> enemyList) {
		amount = enemyList.size();
		LineForm(enemyList);
	}
	
	public static void set(ArrayList<Enemy> enemyList, String format) {
		amount = enemyList.size();
		if (format.toLowerCase().equals("..")) {
			
		} else {
			LineForm(enemyList);
		}
	
	}
	
	private static void LineForm(ArrayList<Enemy> enemyList) {
		int offset = 0;
		for (int i = 0 ; i < amount ; i++) {
			if (i >= 4) {
				offset = DrawingUtility.PLAY_SCREEN_HEIGHT/4;
			} else if (i >= 8) {
				offset = -DrawingUtility.PLAY_SCREEN_HEIGHT/4;
			}
			Enemy enemy = enemyList.get(i);
			enemy.setX(DrawingUtility.PLAY_SCREEN_WIDTH/2 - ((( i % 4 ) + 1 ) * (2 * enemy.getAnimation().getFrameWidth())));
			enemy.setY(DrawingUtility.STATUS_BAR_HEIGHT + offset +(DrawingUtility.PLAY_SCREEN_HEIGHT/2 - (enemy.getAnimation().getFrameHeight()/2)));
		}	
	}	

}
