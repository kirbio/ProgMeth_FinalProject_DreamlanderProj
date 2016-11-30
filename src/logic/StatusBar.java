package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import javafx.scene.canvas.GraphicsContext;

public class StatusBar implements IRenderable {
	
	public StatusBar() {
		super();
		
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE-1;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawStatusBar(gc, GameLogic.instance.getLevel(), GameLogic.instance.getPlayer().getHp());
		
	}

}
