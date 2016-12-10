package graphic;

import java.net.URL;

import exception.SpriteParsingException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import logic.Enemy;
import logic.Entity;

public class EntityAnimation {

	private Image img;
	private Entity entity;
	private String path;
	/*
	 * private int delay; private int delayCounter;
	 */
	private int counter;
	private int animationDelay;
	private int delayCounter;
	private int frameWidth, frameHeight;
	private final int number = 5;
	private Image[] animationFrame;

	public EntityAnimation(String path) {
		try {
			this.path = path;
			URL imgpath = ClassLoader.getSystemResource("img/character/" + path);
			if (imgpath == null) {
				throw new SpriteParsingException(path);
			}
			img = new Image(imgpath.toString());

			frameWidth = (int) img.getWidth() / 5;
			frameHeight = (int) img.getHeight();
			animationFrame = new Image[number];
			for (int i = 0; i < number; i++) {
				animationFrame[i] = new WritableImage(img.getPixelReader(), i * frameWidth, 0, frameWidth, frameHeight);
			}
			counter = 0;
			delayCounter = 0;
			animationDelay = 10;
		} catch (SpriteParsingException e) {
			e.printStackTrace();
		}
	}

	public Image getCurrentIdleSprite() {
		return animationFrame[counter];
	}

	public Image getHurtSprite() {
		return animationFrame[number - 1];
	}

	public void updateAnimation() {
		if (delayCounter == animationDelay) {
			counter++;
			if (counter == 4) { // end of sprite animation
				counter = 0;
			}
			delayCounter = 0;
		} else {
			delayCounter++;
		}

	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

}
