package graphic;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {
	//Fill in here
	
	public int getZ();
	public void draw(GraphicsContext gc);
	//public boolean isDestroy();
}
