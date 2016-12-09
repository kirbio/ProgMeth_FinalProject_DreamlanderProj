package exception;

public class SpriteException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	public SpriteException(String name){
		this.name = name;
		System.out.println("Cannot find the enemy image: "+name);
	}
}
