package graphic;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.AttackGuage;
import logic.Enemy;
import logic.Entity;
import logic.Player;

public class DrawingUtility {
	
	private static final int TEXT_AREA_HEIGHT = 100;
	private static final int STATUS_BAR_HEIGHT = 50;
	private static final int HP_BAR_WIDTH = 50;
	private static final int HP_BAR_HEIGHT = 10;
	private static final int ATK_GAUGE_WIDTH = 100;
	private static final int ATK_GAUGE_HEIGHT = GameScreen.SCREEN_HEIGHT - (TEXT_AREA_HEIGHT + STATUS_BAR_HEIGHT);
	private static final int ATK_GAUGE_X = GameScreen.SCREEN_WIDTH - ATK_GAUGE_WIDTH;
	private static final int ATK_GAUGE_Y = STATUS_BAR_HEIGHT;	//Adjacent to Status bar
	private static final int ATK_GAUGE_PADDING = 10;	//padding on each side
	private static final int ATK_GAUGE_CELL_W = ATK_GAUGE_WIDTH - (2 * ATK_GAUGE_PADDING);
	private static final int ATK_GAUGE_ALL_CELL_H = ATK_GAUGE_HEIGHT - (2 * ATK_GAUGE_PADDING);
	private static final int ATK_GAUGE_CELL_X = ATK_GAUGE_X + ATK_GAUGE_PADDING;
	private static final int ATK_GAUGE_BASE_Y = (ATK_GAUGE_Y + ATK_GAUGE_HEIGHT - ATK_GAUGE_PADDING);
			
	public static void drawTextArea(GraphicsContext gc, String text) {
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT, GameScreen.SCREEN_WIDTH, TEXT_AREA_HEIGHT);
		
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.font("Arial", 40));
		
		gc.fillText(text, 20, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT);
		
	}
	
	public static void drawStatusBar(GraphicsContext gc, int level, int hp) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, GameScreen.SCREEN_WIDTH, STATUS_BAR_HEIGHT);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.font("Arial", 20));
		gc.fillText("Level : "+level, 40, 5);
		gc.fillText("HP : "+hp, 340, 5);
	}
	
	/*=================================================
	 * Drawing entity methods
	 ==================================================*/
	
	//Use to draw HPBar on entity's spot
	private static void drawHPBar(GraphicsContext gc, int x, int y) {
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
	}
	
	//Use to draw sprite
	//animation --> each entity have animationFrame field?
	public static void drawEnemy(GraphicsContext gc, Enemy enemy) {
		Image sprite = enemy.getAnimation().getCurrentIdleSprite();
		gc.drawImage(sprite, enemy.getX(), enemy.getY());
		enemy.getAnimation().updateAnimation();
		//drawHPBar(gc, enemy.getX(), enemy.getY());
	}
	
	public static void drawHurtEnemy(GraphicsContext gc, Enemy enemy) {
		Image sprite = enemy.getAnimation().getHurtSprite();
		gc.drawImage(sprite, enemy.getX(), enemy.getY());
		//drawHPBar(gc, enemy.getX(), enemy.getY());
	}
	
	
	public static void drawPlayer(GraphicsContext gc, Player player) {
		Image sprite = player.getAnimation().getCurrentIdleSprite();
		gc.drawImage(sprite, player.getX(), player.getY());
		player.getAnimation().updateAnimation();
		
	}
	
	public static void drawHurtPlayer(GraphicsContext gc, Player player) {
		Image sprite = player.getAnimation().getHurtSprite();
		gc.drawImage(sprite, player.getX(), player.getY());
		
	}

	
	/*=================================================
	 * Drawing attack gauge methods
	 ==================================================*/
	
	public static void drawAttackGauge(GraphicsContext gc, int[] attackpower, int index, int currentAtkPower) {
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		
		double cellHeight = ((double) ATK_GAUGE_ALL_CELL_H / attackpower.length);
		gc.fillRect(ATK_GAUGE_X, ATK_GAUGE_Y, ATK_GAUGE_WIDTH, ATK_GAUGE_HEIGHT);
		for (int i = 0 ; i < attackpower.length ; i++) {
			gc.setGlobalAlpha(0.3);
			gc.setFill(AttackGuage.colorGauge[attackpower[i]]);
			gc.fillRect(ATK_GAUGE_CELL_X, ATK_GAUGE_BASE_Y - (cellHeight*(i+1)), ATK_GAUGE_CELL_W, cellHeight);
			gc.strokeRect(ATK_GAUGE_CELL_X, ATK_GAUGE_BASE_Y - (cellHeight*(i+1)), ATK_GAUGE_CELL_W, cellHeight);
			gc.setGlobalAlpha(1);
		}
		for (int i = 0 ; i < index+1 ; i++) {
			gc.setGlobalAlpha(0.7);
			gc.setFill(AttackGuage.colorGauge[attackpower[i]]);
			gc.fillRect(ATK_GAUGE_CELL_X, ATK_GAUGE_BASE_Y - (cellHeight*(i+1)), ATK_GAUGE_CELL_W, cellHeight);
			gc.strokeRect(ATK_GAUGE_CELL_X, ATK_GAUGE_BASE_Y - (cellHeight*(i+1)), ATK_GAUGE_CELL_W, cellHeight);
			gc.setGlobalAlpha(1);
		}
	}
	

}
