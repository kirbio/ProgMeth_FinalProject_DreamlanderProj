/**
 * @author Phakawat and Nitit
 *
 */

package graphic;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderable {

	public int getZ();

	public void draw(GraphicsContext gc);

	public boolean isDead();
}
