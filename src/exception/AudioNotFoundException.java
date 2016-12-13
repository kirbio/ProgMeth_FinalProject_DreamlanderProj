/**
 * @author Phakawat and Nitit
 *
 */

package exception;

public class AudioNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AudioNotFoundException(String name) {
		System.out.println("Cannot find the sound: " + name);
	}

	@Override
	public String getMessage() {
		return "Audio file not found";
	}
}
