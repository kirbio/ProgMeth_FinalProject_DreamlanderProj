/**
 * @author Phakawat and Nitit
 *
 */

package input;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class InputUtility {

	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	private static ArrayList<KeyCode> keyTriggered = new ArrayList<>();

	public static boolean getKeyPressed(KeyCode keycode) {
		return keyPressed.contains(keycode);
	}

	public static void setKeyPressed(KeyCode keycode, boolean pressed) {
		if (pressed) {
			if (!InputUtility.keyPressed.contains(keycode))
				InputUtility.keyPressed.add(keycode);
		} else {
			InputUtility.keyPressed.remove(keycode);
			InputUtility.keyTriggered.remove(keycode);
		}

	}

	public static boolean getKeyTriggered(KeyCode keycode) {
		return keyTriggered.contains(keycode);
	}

	public static void setKeyTriggered(KeyCode keycode, boolean pressed) {
		if (pressed) {
			if (!InputUtility.keyTriggered.contains(keycode))
				InputUtility.keyTriggered.add(keycode);
		} else {
			InputUtility.keyTriggered.remove(keycode);
		}
	}

	public static void postUpdate() {
		InputUtility.keyTriggered.clear();
		;
	}

}
