package graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AttackAnimation implements IRenderable{
	private Image img;
	private int x,y;
	private boolean isEnd;
	
	public AttackAnimation(int x, int y) {
		img = new Image(ClassLoader.getSystemResource("img/attack.png").toString());
		this.x = x;
		this.y = y;
		isEnd = false;
		RenderableHolder.getInstance().add(this);
		
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawAttackAnim(gc, img, x, y);
		
	}

	@Override
	public boolean isDead() {
		return isEnd;
	}
	
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

}
