package graphic;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logic.AttackGuage;
import logic.Enemy;
import logic.Entity;

public class DrawingUtility {
	
	public static void drawTextArea(GraphicsContext gc, String text) {
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 380, 680, 100);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.font("Comic Sans MS", 40));
		
		gc.fillText(text, 340, 420);
		
	}
	
	public static void drawStatusBar(GraphicsContext gc, int level, int hp) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, 680, 30);
		
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.TOP);
		gc.setFill(Color.WHITE);
		
		gc.setFont(Font.font("Comic Sans MS", 20));
		gc.fillText("Level : "+level, 40, 5);
		gc.fillText("HP : "+hp, 340, 5);
	}
	
	/*=================================================
	 * Drawing entity methods
	 ==================================================*/
	
	//Use to draw HPBar on entity's spot
	private static void drawHPBar(GraphicsContext gc, Entity entity) {
		int HPBarWidth = 50;
		int HPBarHeight = 10;
		gc.setFill(Color.BLACK);
		gc.fillRect(entity.getX(), entity.getY(), HPBarWidth, HPBarHeight);
	}
	
	//Use to draw sprite
	//animation --> each entity have animationFrame field?
	public static void drawEnemy(GraphicsContext gc, Enemy enemy) {
		
		//enemy.getsprite
		//draw enemy sprite
		drawHPBar(gc,enemy);
	}
	
	/*=================================================
	 * Drawing attack gauge methods
	 ==================================================*/
	
	/*public static void drawAttackGauge(GraphicsContext gc, int[] attackpower, int currentAtkPower) {
		gc.setFill(Color.BLACK);
		gc.fillRect(600, 160, 80, 160);
		gc.setFill(Color.RED);
		
	}*/
	

}
