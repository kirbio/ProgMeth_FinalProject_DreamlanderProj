package thread;

import javafx.animation.AnimationTimer;
import main.Main;

public class AnimationStarter {
	
	public void start() {
		new AnimationTimer() {
			Long start=0l;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(start==0l)start=now;
				long diff = now-start;
				if(diff>=10000000l){ //100,000,000l = 100ms.
					Main.instance.getGameScreen().paintComponents();
					
					start=now;

				}
			}
		}.start();
	}

}
