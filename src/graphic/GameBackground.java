package graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;
import screen.GameScreen;

public class GameBackground implements IRenderable {
	private Image bg;
	private String path;
	
	public GameBackground(String backgroundName) {	
		
		
	}
	
	public GameBackground(String backgroundName, double width, double height) {	
		try {
			path = ClassLoader.getSystemResource("img/bg/"+backgroundName+".png").toString();		
			bg = new Image(path, width, height, false, false);
		} catch (NullPointerException | IllegalArgumentException e) {
			bg = null;
		}
		
	}
	
	public GameBackground(int mode, int index) {
		String backgroundName = "";
		switch (index) {
		default : 	backgroundName = "plain"	; break;
		//both case 0,1,2 use the same result
		case 0	:   
		case 1	:   
		case 2  :  backgroundName = "plain"	; break;
		//both case 3,4 use the same result
		case 3  :
		case 4  :  backgroundName = "plain"	; break;
		
		case GameLogic.BOSS : 	switch (mode) {
									case 0 : backgroundName = "slave" ; break;
								}
								break;
		
			 
		}
		
		try {
			path = ClassLoader.getSystemResource("img/bg/"+backgroundName+".png").toString();		
			bg = new Image(path, (double) DrawingUtility.PLAY_SCREEN_WIDTH, (double) DrawingUtility.PLAY_SCREEN_HEIGHT,
					false, false);
		} catch (NullPointerException | IllegalArgumentException e) {
			bg = null;
		}
	}
	

	@Override
	public int getZ() {
		return 0;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawBG(gc, bg);
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public Image getBg() {
		return bg;
	}
	
}
