package logic;

import data.GameData;
import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import javafx.scene.canvas.GraphicsContext;

public class StatusBar implements IRenderable {
	
	public StatusBar() {
		RenderableHolder.getInstance().add(this);
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawStatusBar(gc, GameLogic.instance.getLevel(), GameLogic.instance.getPlayer().getHp()
				, GameLogic.instance.getPlayer().getMaxHP(), GameLogic.instance.getPlayer().getLevel(), GameLogic.instance.getPlayer().getEXP(), GameLogic.instance.getPlayer().getNextEXP());
		
	}

	@Override
	public boolean isDead() {
		return false;
	}

}
