package exception;

public class SpriteParsingException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	public SpriteParsingException(String name){
		this.name = name;
		System.out.println("Cannot find the enemy image: "+name);
	}
	
	@Override
	public String getMessage() {
		return "sprite not found";	
	}
}
