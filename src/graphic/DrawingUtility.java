package graphic;

import java.io.InputStream;
import java.util.List;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.FontMetrics;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import logic.AttackGuage;
import logic.Enemy;
import logic.Position;
import screen.GameScreen;
import logic.Entity;
import logic.Player;

public class DrawingUtility {
	
	public static final int TEXT_AREA_HEIGHT = 100;
	public static final int STATUS_BAR_HEIGHT = 50;
	public static final int HP_BAR_WIDTH = 50;
	public static final int HP_BAR_HEIGHT = 10;
	public static final int PLAYER_HP_BAR_WIDTH = 280;
	public static final int PLAYER_HP_BAR_HEIGHT = 40;
	public static final int ATK_GAUGE_WIDTH = 100;
	public static final int ATK_GAUGE_HEIGHT = GameScreen.SCREEN_HEIGHT - (TEXT_AREA_HEIGHT + STATUS_BAR_HEIGHT);
	public static final int ATK_GAUGE_X = GameScreen.SCREEN_WIDTH - ATK_GAUGE_WIDTH;
	public static final int ATK_GAUGE_Y = STATUS_BAR_HEIGHT;	//Adjacent to Status bar
	public static final int ATK_GAUGE_PADDING = 10;	//padding on each side
	public static final int ATK_GAUGE_CELL_W = ATK_GAUGE_WIDTH - (2 * ATK_GAUGE_PADDING);
	public static final int ATK_GAUGE_ALL_CELL_H = ATK_GAUGE_HEIGHT - (2 * ATK_GAUGE_PADDING);
	public static final int ATK_GAUGE_CELL_X = ATK_GAUGE_X + ATK_GAUGE_PADDING;
	public static final int ATK_GAUGE_BASE_Y = (ATK_GAUGE_Y + ATK_GAUGE_HEIGHT - ATK_GAUGE_PADDING);
	public static final int PLAY_SCREEN_WIDTH = GameScreen.SCREEN_WIDTH - DrawingUtility.ATK_GAUGE_WIDTH;
	public static final int PLAY_SCREEN_HEIGHT = DrawingUtility.ATK_GAUGE_HEIGHT;
	public static final int ATK_GAUGE_TEXT_X = ATK_GAUGE_CELL_X + ATK_GAUGE_CELL_W/2;
	
	
	/*=================================================
	 * Drawing GUI - TextArea, StatusBar, Attack Gauge
	 ==================================================*/
	
	public static void drawTextArea(GraphicsContext gc, String text) {
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT, GameScreen.SCREEN_WIDTH, TEXT_AREA_HEIGHT);
		
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.font("Arial", 40));
		
		gc.fillText(text, 20, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT);
		
	}
	
	public static void drawStatusBar(GraphicsContext gc, int level, int HP, int maxHP) {
		//draw Status bar
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, GameScreen.SCREEN_WIDTH, STATUS_BAR_HEIGHT);
		
		//set text properties
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		//draw text
		gc.setFont(Font.font("Arial", 20));
		gc.fillText("Level : "+(level+1), 40, 5);
		gc.fillText("HP : "+HP, 340, 5);
		
		drawPlayerHPBar(gc, 380, 5, HP, maxHP);
	}
	
	public static void drawAttackGauge(GraphicsContext gc, int[] attackpower, int index, int currentAtkPower, boolean showAttackDescription) {
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
		if (showAttackDescription) {
			
			gc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, cellHeight-5));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.TOP);
			
			gc.setFill(Color.WHITESMOKE);
			gc.fillText(AttackGuage.attackDescription[currentAtkPower], ATK_GAUGE_TEXT_X, ATK_GAUGE_BASE_Y - (cellHeight*(index+1)));	
			
//			gc.setStroke(Color.BLACK);
//			gc.setLineWidth(1);	
//			gc.strokeText(AttackGuage.attackDescription[currentAtkPower], ATK_GAUGE_TEXT_X, ATK_GAUGE_BASE_Y - (cellHeight*(index+1)));
		
		}

	}
	
	/*=================================================
	 * Drawing entity methods
	 ==================================================*/
	
	//draw player hp bar on StatusBar
	private static void drawPlayerHPBar(GraphicsContext gc, int x, int y, int HP, int maxHP) {       
		//HP bar background color
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, PLAYER_HP_BAR_WIDTH, PLAYER_HP_BAR_HEIGHT);
		
		//HP bar gauge, varying color depend on HP
		double t = (double)HP/(double)maxHP;
        
        try {
			if (0.5 <= t) {
				gc.setFill(Color.rgb((int) (510 * (1 - t)), 255, 0));
			} else {
				gc.setFill(Color.rgb(255, (int) (t * 510), 0));
			}
			gc.fillRect(x, y, ((double) HP / maxHP) * PLAYER_HP_BAR_WIDTH, PLAYER_HP_BAR_HEIGHT);
		} catch (IllegalArgumentException e) {
			//System.out.println("HP Out of range");
		}
        
		//HP bar border
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect(x, y, PLAYER_HP_BAR_WIDTH, PLAYER_HP_BAR_HEIGHT);
	}
	
	//Use to draw HPBar on entity's spot
	private static void drawEnemyHPBar(GraphicsContext gc, int x, int y, int HP, int maxHP, String name) {   
		
		//HP bar background color
		gc.setFill(Color.BLACK);
		gc.fillRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
		
		//Enemy's name
		gc.setFont(Font.font("Arial", 10));
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.BASELINE);
		gc.fillText(name, x, y-5);
		
		//HP bar gauge, varying color depend on HP
		double t = (double)HP/(double)maxHP;
        
        try {
			if (0.5 <= t) {
				gc.setFill(Color.rgb((int) (510 * (1 - t)), 255, 0));
			} else {
				gc.setFill(Color.rgb(255, (int) (t * 510), 0));
			}
			gc.fillRect(x, y, ((double) HP / maxHP) * HP_BAR_WIDTH, HP_BAR_HEIGHT);
		} catch (IllegalArgumentException e) {
			//System.out.println("HP Out of range");
		}
        
		//HP bar border
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT);
	}
	
	//Use to draw sprite
	//animation --> each entity have animationFrame field?
	public static void drawEnemy(GraphicsContext gc, Enemy enemy) {
		Image sprite = enemy.getAnimation().getCurrentIdleSprite();
		gc.drawImage(sprite, enemy.getX(), enemy.getY());
		enemy.getAnimation().updateAnimation();
		int x = (enemy.getX() + enemy.getAnimation().getFrameWidth()/2 - HP_BAR_WIDTH/2);
		int y = enemy.getY() - HP_BAR_HEIGHT;
		drawEnemyHPBar(gc, x, y, enemy.getHp(), enemy.getMaxHP(), enemy.getName());
	}
	
	public static void drawHurtEnemy(GraphicsContext gc, Enemy enemy) {
		Image sprite = enemy.getAnimation().getHurtSprite();
		gc.drawImage(sprite, enemy.getX(), enemy.getY());
		int x = (enemy.getX() + enemy.getAnimation().getFrameWidth()/2 - HP_BAR_WIDTH/2);
		int y = enemy.getY() - HP_BAR_HEIGHT;
		drawEnemyHPBar(gc, x, y, enemy.getHp(), enemy.getMaxHP(), enemy.getName());
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
	 * Drawing background
	 ==================================================*/
	
	public static void drawBG(GraphicsContext gc, Image bg) {
		gc.drawImage(bg, 0, STATUS_BAR_HEIGHT);
	}
	
	

}
