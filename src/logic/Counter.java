package logic;

import graphic.DrawingUtility;
import graphic.IRenderable;
import graphic.RenderableHolder;
import javafx.scene.canvas.GraphicsContext;
import sound.AudioHolder;

public class Counter extends Thread implements IRenderable {
	
	private int startTime;
	private int currentTime;
	
	public Counter() {
		startTime = 10;
		currentTime = 10;
		RenderableHolder.getInstance().add(this);
	}
	
	@Override
	public void run() {
		while (!GameLogic.instance.isNewRound() && !GameLogic.instance.isGameOver()) {
			try {
				Thread.sleep(1000);		
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			if (GameLogic.instance.isWaitForInput()) {
				decreaseTime();
				AudioHolder.getInstance().playSFX("select2");
			}
			
		}
			
	}
	
	private void decreaseTime() {
		currentTime--;
		if (currentTime <= 0) currentTime = 0;
	}

	@Override
	public int getZ() {
		return Integer.MAX_VALUE;
	}

	@Override
	public void draw(GraphicsContext gc) {
		DrawingUtility.drawCounter(gc, startTime, currentTime);
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public int getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	
	public boolean isTimeOut() {
		return (currentTime == 0);
	}
	
	public void resetCounter() {
		currentTime = startTime;
	}
	
	

}
