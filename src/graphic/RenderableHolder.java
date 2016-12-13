/**
 * @author Phakawat and Nitit
 *
 */

package graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RenderableHolder {
	private List<IRenderable> entities;
	private List<IRenderable> menuEntities;
	private Comparator<IRenderable> comparator;

	private static final RenderableHolder instance = new RenderableHolder();

	static {
		loadResource();
	}

	public RenderableHolder() {
		entities = new ArrayList<IRenderable>();
		menuEntities = new ArrayList<IRenderable>();
		comparator = (IRenderable o1, IRenderable o2) -> {
			return o1.getZ() - o2.getZ();
		};

	}

	static {
	}

	public synchronized void add(IRenderable entity) {
		entities.add(entity);

		Collections.sort(entities, comparator);
	}

	public synchronized void addMenu(IRenderable entity) {
		menuEntities.add(entity);

		Collections.sort(menuEntities, comparator);
	}

	public synchronized void remove(IRenderable entity) {
		entities.remove(entity);

		Collections.sort(entities, comparator);
	}

	public synchronized void removeMenu(IRenderable entity) {
		menuEntities.remove(entity);

		Collections.sort(menuEntities, comparator);
	}

	private static void loadResource() {

	}

	public synchronized void remove(int index) {
		entities.remove(index);
	}

	public synchronized static RenderableHolder getInstance() {

		return instance;
	}

	public synchronized List<IRenderable> getEntities() {

		return entities;
	}

	public synchronized void clearMenuEntities() {
		menuEntities.clear();
	}

}
