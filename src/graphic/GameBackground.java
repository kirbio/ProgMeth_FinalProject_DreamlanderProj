/**
 * @author Phakawat and Nitit
 *
 */

package graphic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.GameLogic;

public class GameBackground implements IRenderable {
	private Image bg;
	private String path;

	public GameBackground(String backgroundName) {

	}

	public GameBackground(String backgroundName, double width, double height) {
		try {
			path = ClassLoader.getSystemResource("img/bg/" + backgroundName + ".png").toString();
			bg = new Image(path, width, height, false, false);
		} catch (NullPointerException | IllegalArgumentException e) {
			bg = null;
		}

	}

	public GameBackground(int mode, int index) {
		String backgroundName = "";
		switch (index) {
		default:
			backgroundName = "plain";
			break;
		case 0:
		case 1:
		case 2:
		case 3:
			switch (mode) {
			case 0:
			case 2:
				backgroundName = "plain";
				break;
			case 1:
				backgroundName = "haltmann";
				break;
			}
			break;
		case GameLogic.MID_BOSS:
			backgroundName = "itemroom";
			break;
		case GameLogic.BOSS:
			switch (mode) {
			case 0:
				backgroundName = "slave";
				break;
			case 1:
				backgroundName = "tank";
				break;
			}
			break;
		case 5:
		case 6:
		case 7:
		case 8:
			switch (mode) {
			case 0:
			case 2:
				backgroundName = "plain";
				break;
			case 1:
				backgroundName = "shoot";
				break;
			}
			break;
			
		}
		

		try {
			path = ClassLoader.getSystemResource("img/bg/" + backgroundName + ".png").toString();
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
