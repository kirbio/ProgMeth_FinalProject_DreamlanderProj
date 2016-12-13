/**
 * @author Phakawat and Nitit
 *
 */

package graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AttackAnimation implements IRenderable {
	private Image img;
	private int x, y;
	private boolean isEnd;

	private int counter;
	private int animationDelay;
	private int delayCounter;
	private int frameWidth, frameHeight;
	private final int number = 10;
	private Image[] animationFrame;

	public AttackAnimation(int x, int y) {
		img = new Image(ClassLoader.getSystemResource("img/attack.png").toString());
		this.x = x;
		this.y = y;
		isEnd = false;
		RenderableHolder.getInstance().add(this);

		frameWidth = (int) img.getWidth() / number;
		frameHeight = (int) img.getHeight();
		animationFrame = new Image[number];
		for (int i = 0; i < number; i++) {
			animationFrame[i] = new WritableImage(img.getPixelReader(), i * frameWidth, 0, frameWidth, frameHeight);
		}

		counter = 0;
		delayCounter = 0;
		animationDelay = 2;
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		if (!isEnd) {
			DrawingUtility.drawAttackAnim(gc, animationFrame[counter], x, y);
			animationUpdate();
		}

	}

	@Override
	public boolean isDead() {
		return isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public void animationUpdate() {
		if (delayCounter == animationDelay) {
			counter++;
			if (counter == number - 1) { // end of sprite animation
				counter = 0;
				isEnd = true;
			}
			delayCounter = 0;
		} else {
			delayCounter++;
		}

	}

}
