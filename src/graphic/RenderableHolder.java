package graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.scene.image.Image;

public class RenderableHolder {
	//Fill in here
	private List<IRenderable> entities;
	private Comparator<IRenderable> comparator;

	public static Image star;
	public static Image[] arrow;
	public final static RenderableHolder instance = new RenderableHolder();
	
	static {
		loadResource();
	}
	
	public RenderableHolder(){
		//Fill in here
		entities = new ArrayList<IRenderable>();
		//arrow = new Image[4];
		comparator = (IRenderable o1, IRenderable o2) -> {
			return o1.getZ() - o2.getZ();
		};
		
	}
	static{
		//Fill in here
	}
	
	public synchronized void add(IRenderable entity){
		//Fill in here
		entities.add(entity);
		
		Collections.sort(entities, comparator);
	}
	private static void loadResource() {
		// TODO Auto-generated method stub
		//Fill in here
//		arrow[0] = new Image(ClassLoader.getSystemResource("arrow_down.png").toString());
//		arrow[1] = new Image(ClassLoader.getSystemResource("arrow_left.png").toString());
//		arrow[2] = new Image(ClassLoader.getSystemResource("arrow_right.png").toString());
//		arrow[3] = new Image(ClassLoader.getSystemResource("arrow_up.png").toString());
//		
//		star = new Image(ClassLoader.getSystemResource("star.png").toString());
//		
	}
	public synchronized void remove(int index){
		//Fill in here
		entities.remove(index);
	}

	public synchronized static RenderableHolder getInstance() {
		// TODO Auto-generated method stub
		//Fill in here
		return instance;
	}

	public synchronized List<IRenderable> getEntities() {
		// TODO Auto-generated method stub
		//Fill in here
		return entities;
	}
}
