/**
 * @author Phakawat and Nitit
 *
 */

package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import javafx.scene.canvas.GraphicsContext;

public class RPGTextArea extends Thread implements IRenderable {

	private String text;
	private String disptext;
	private String prevtext;

	public RPGTextArea() {
		text = "";
		disptext = "";
		RenderableHolder.getInstance().add(this);
	}

	@Override
	public void run() {
		int index = 0;
		while (!GameLogic.instance.isGameOver()) {
			if (prevtext != text) {
				prevtext = text;
				disptext = "";
				index = 0;
			} else {
				if (index < text.length()) {
					disptext += text.charAt(index);
					index += 1;
				}
			}
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		DrawingUtility.drawTextArea(gc, disptext);
	}

	@Override
	public boolean isDead() {
		return false;
	}

}
