package graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameBackground implements IRenderable {
	private Image bg;
	private String path;
	
	public GameBackground(String backgroundName) {	
		try {
			path = ClassLoader.getSystemResource("img/bg/"+backgroundName+".jpg").toString();		
		} catch (NullPointerException | IllegalArgumentException e) {
			path = ClassLoader.getSystemResource("img/bg/0.jpg").toString();
		}
		bg = new Image(path, (double) DrawingUtility.PLAY_SCREEN_WIDTH, (double) DrawingUtility.PLAY_SCREEN_HEIGHT,
				false, false);
		RenderableHolder.getInstance().add(this);
		System.out.println("bg added");
	
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

}
