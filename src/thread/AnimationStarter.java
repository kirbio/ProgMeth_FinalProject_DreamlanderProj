/**
 * @author Phakawat and Nitit
 *
 */

package thread;

import javafx.animation.AnimationTimer;
import logic.GameLogic;
import main.Main;

public class AnimationStarter {

	private AnimationTimer animation;

	public AnimationStarter() {
		animation = new AnimationTimer() {
			Long start = 0l;

			@Override
			public void handle(long now) {
				if (start == 0l)
					start = now;
				long diff = now - start;
				if (diff >= 10000000l) { // 100,000,000l = 100ms.
					Main.instance.getGameScreen().paintComponents();

					start = now;

				}
			}
		};

		animation.start();

	}

	public void checkStop() {
		if (GameLogic.instance.isGameOver()) {
			animation.stop();
		}
	}
}
