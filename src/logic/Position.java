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
		if (format.toLowerCase().equals(".......")) {
			/*insert high quality code here*/
		} else {
			LineForm(enemyList);
		}
	
	}
	
	private static void LineForm(ArrayList<Enemy> enemyList) {	
		int offset;
		int number = 0;		//number in line, 0 is first in line
		int line_number = 0;	//0 is center, 1 will be bottom, 2 will be top, and so on
		for (int i = 0 ; i < amount ; i++) {
			
			Enemy enemy = enemyList.get(i);
			
			//first, check if x is valid
			int x = (int) (DrawingUtility.PLAY_SCREEN_WIDTH/2 - (( number ) * (1.1 * enemy.getAnimation().getFrameWidth())));
			
			if (x < 5) {	//if out of screen, go to new line
				number = 0;
				line_number++;
				x = (int) (DrawingUtility.PLAY_SCREEN_WIDTH/2 - (( number ) * (1.1 * enemy.getAnimation().getFrameWidth())));
			}
			number++;
			
			
			switch(line_number) {		//determining offset for each line
			default 		:		offset = 0; break;
			case 1			:		offset = DrawingUtility.PLAY_SCREEN_HEIGHT/3; break;
			case 2			:		offset = -DrawingUtility.PLAY_SCREEN_HEIGHT/3; break;
			
			}
			
			enemy.setX(x);
			enemy.setY(DrawingUtility.STATUS_BAR_HEIGHT + offset +(DrawingUtility.PLAY_SCREEN_HEIGHT/2 - (enemy.getAnimation().getFrameHeight()/2)));
			System.out.println(enemy.getX() + " " + enemy.getY());
		}	 
	}	

}
