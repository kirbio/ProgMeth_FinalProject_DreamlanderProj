package graphic;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

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
import logic.GameLogic;
import logic.Player;

public class DrawingUtility {
	
	public static final int TEXT_AREA_HEIGHT = 100;
	public static final int STATUS_BAR_HEIGHT = 50;
	public static final int HP_BAR_WIDTH = 60;
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
	public static final int ATK_GAUGE_TEXT_X = ATK_GAUGE_CELL_X + ATK_GAUGE_CELL_W/2;
	public static final int COUNTER_WIDTH = 50;
	public static final int PLAY_SCREEN_WIDTH = GameScreen.SCREEN_WIDTH - DrawingUtility.ATK_GAUGE_WIDTH - COUNTER_WIDTH;
	public static final int PLAY_SCREEN_HEIGHT = DrawingUtility.ATK_GAUGE_HEIGHT;
	
	
	public static final String FONT_8BIT = ClassLoader.getSystemResource("8bit.ttf").toString();
	
	/*=================================================
	 * Drawing GUI - TextArea, StatusBar, Attack Gauge, counter
	 ==================================================*/
	
	public static void drawTextArea(GraphicsContext gc, String text) {
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT, GameScreen.SCREEN_WIDTH, TEXT_AREA_HEIGHT);
		
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.loadFont(FONT_8BIT, 40));
		
		gc.fillText(text, 20, GameScreen.SCREEN_HEIGHT-TEXT_AREA_HEIGHT);
		
	}
	
	public static void drawStatusBar(GraphicsContext gc, int level, int HP, int maxHP,ArrayList<Enemy> enemyList) {
		//draw Status bar
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, GameScreen.SCREEN_WIDTH, STATUS_BAR_HEIGHT);
		
		//set text properties
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		//draw text
		gc.setFont(Font.loadFont(FONT_8BIT, 20));
		gc.fillText("Level : "+(level+1), 40, 5);
		gc.fillText("HP : "+HP, 340, 5);
		
		drawPlayerHPBar(gc, 380, 5, HP, maxHP);
		
		/*for(int i=0;i<enemyList.size();i++){
			drawEnemyName(gc,enemyList.get(i).getName(),i);
		}*/
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

			gc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, cellHeight/1.5));
			
			FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
			double font_width= fontLoader.computeStringWidth("PERFECT!", gc.getFont());
			
			if (font_width > ATK_GAUGE_CELL_W - 4) {
				gc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
			}
			
			
			
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			
			gc.setFill(Color.WHITESMOKE);
			gc.fillText(AttackGuage.attackDescription[currentAtkPower], ATK_GAUGE_TEXT_X, ATK_GAUGE_BASE_Y - (cellHeight*(index+1)) + cellHeight/2);	
			
//			gc.setStroke(Color.BLACK);
//			gc.setLineWidth(1);	
//			gc.strokeText(AttackGuage.attackDescription[currentAtkPower], ATK_GAUGE_TEXT_X, ATK_GAUGE_BASE_Y - (cellHeight*(index+1)));
		
		}

	}
	
	public static void drawCounter(GraphicsContext gc, int startTime, int currentTime) {
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.DARKRED);
		gc.setLineWidth(1);
		
		gc.fillRect(0, ATK_GAUGE_Y, COUNTER_WIDTH, ATK_GAUGE_HEIGHT);
		gc.setFill(Color.DARKRED);
		gc.fillRect(5, ATK_GAUGE_Y + 5, COUNTER_WIDTH - 10, ATK_GAUGE_HEIGHT - 10);
		
		double cellHeight = ((double) (ATK_GAUGE_HEIGHT - 20)) / startTime;
		if (currentTime > 5) {
			gc.setFill(Color.CHOCOLATE);
		} else {
			gc.setFill(Color.YELLOW);
		}
		
		for (int i = 0 ; i < currentTime ; i++) {
			gc.fillRoundRect(10, (ATK_GAUGE_Y + ATK_GAUGE_HEIGHT - 10) - (cellHeight*(i+1)), COUNTER_WIDTH - 20, cellHeight, 10, 10);
			gc.strokeRoundRect(10, (ATK_GAUGE_Y + ATK_GAUGE_HEIGHT - 10 ) - (cellHeight*(i+1)), COUNTER_WIDTH - 20, cellHeight, 10, 10);
			//gc.strokeRect(ATK_GAUGE_CELL_X, ATK_GAUGE_BASE_Y - (cellHeight*(i+1)), ATK_GAUGE_CELL_W, cellHeight);
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
		gc.setLineWidth(5);
		gc.strokeRect(x, y, PLAYER_HP_BAR_WIDTH, PLAYER_HP_BAR_HEIGHT);
	}
	
	//Use to draw HPBar on entity's spot
	private static void drawEnemyHPBar(GraphicsContext gc, int x, int y, int HP, int maxHP, String name) {   
		
		gc.setFont(Font.font("Arial Narrow", 10));
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		double font_height= fontLoader.getFontMetrics(gc.getFont()).getLineHeight();
				
		//HP bar panel
		gc.setFill(Color.BLACK);
		gc.fillRoundRect(x - 5, y - (5 + font_height), HP_BAR_WIDTH + 10, font_height + (10 + HP_BAR_HEIGHT), 8, 8);
		
		//Enemy's name	
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
			gc.fillRoundRect(x, y, ((double) HP / maxHP) * HP_BAR_WIDTH, HP_BAR_HEIGHT, 5, 5);
		} catch (IllegalArgumentException e) {
			//System.out.println("HP Out of range");
		}
        
		//HP bar border
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeRoundRect(x, y, HP_BAR_WIDTH, HP_BAR_HEIGHT, 1, 1);
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
	
	public static void drawEnemyName(GraphicsContext gc,String name,int number){
		gc.setFont(Font.font("Arial", 15));
		gc.setFill(Color.WHITE);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.setTextBaseline(VPos.TOP);
		gc.fillText(name, 5, STATUS_BAR_HEIGHT+5+15*number);
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
		gc.drawImage(bg, COUNTER_WIDTH, STATUS_BAR_HEIGHT);
	}
	
	public static void drawPauseText(GraphicsContext gc, boolean pause) {
		if (pause) {
			gc.setFill(Color.WHITE);
			gc.setFont(Font.font("Arial Black", 50));
			gc.setTextAlign(TextAlignment.CENTER);
			gc.setTextBaseline(VPos.CENTER);
			
			gc.fillText("PAUSE", COUNTER_WIDTH + PLAY_SCREEN_WIDTH/2, STATUS_BAR_HEIGHT + PLAY_SCREEN_HEIGHT/2);
		}
	}
	

}
