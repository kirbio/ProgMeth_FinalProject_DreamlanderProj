package thread;

import input.InputUtility;
import logic.GameLogic;

public class UpdateThread extends Thread {

	@Override
	public void run() {
		while(!GameLogic.instance.isGameOver()) {
			GameLogic.instance.update();
			InputUtility.postUpdate();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
		
	}
}
