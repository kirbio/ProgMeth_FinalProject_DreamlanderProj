package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import javafx.scene.canvas.GraphicsContext;

public class RPGTextArea implements IRenderable {
	
	public static String text;

	public RPGTextArea(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawTextArea(gc, text);
	}

}
