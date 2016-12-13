/**
 * @author Phakawat and Nitit
 *
 */

package sound;

import java.io.BufferedReader;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import exception.AudioNotFoundException;
import javafx.scene.media.AudioClip;
import logic.GameLogic;

public class AudioHolder {

	private static List<AudioClip> sfx;
	private static List<String> sfx_name;
	private static List<AudioClip> bgm;
	private static List<String> bgm_name;
	private static AudioClip currentBGM;

	private static final AudioHolder instance = new AudioHolder();

	public AudioHolder() {
		sfx = new ArrayList<>();
		sfx_name = new ArrayList<>();
		bgm = new ArrayList<>();
		bgm_name = new ArrayList<>();
	}

	static {
		loadResource();
	}

	private static void loadResource() {
		sfx_name.add("1");
		sfx_name.add("congrats");
		sfx_name.add("fight1");
		sfx_name.add("fight2");
		sfx_name.add("fightperfect");
		sfx_name.add("gameover1");
		sfx_name.add("gameover2");
		sfx_name.add("good");
		sfx_name.add("great");
		sfx_name.add("hurt1");
		sfx_name.add("hurt2");
		sfx_name.add("hurtvoice");
		sfx_name.add("missed");
		sfx_name.add("missed2");
		sfx_name.add("perfect");
		sfx_name.add("powerup");
		sfx_name.add("select");
		sfx_name.add("select2");
		sfx_name.add("win1");
		sfx_name.add("win2");
		
		bgm_name.add("battle");
		bgm_name.add("kawazaki");
		bgm_name.add("slave");
		bgm_name.add("tankbot");
		bgm_name.add("titlescreen");
		
		for (String name : sfx_name) {
			try {
				sfx.add(new AudioClip(ClassLoader.getSystemResource("sfx/"+name+".wav").toString()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(name);
			}
		}
		
		for (String name : bgm_name) {
			try {
				bgm.add(new AudioClip(ClassLoader.getSystemResource("bgm/"+name+".wav").toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public synchronized static AudioHolder getInstance() {
		return instance;
	}

	public void playBGM(String name, boolean looping, double volume) {
		try {
			if (!bgm_name.contains(name)) {
				throw new AudioNotFoundException(name);
			}
			currentBGM = bgm.get(bgm_name.indexOf(name));
			currentBGM.setVolume(volume);
			if (looping) {
				currentBGM.setCycleCount(AudioClip.INDEFINITE);
			}
			currentBGM.play();

		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void playLevelBGM(int mode, int level) {
		switch (level) { // 0 = easy, 1= normal, 2 = hard
		default:
			playBGM("battle", true, 0.2);
			break;
		case GameLogic.MID_BOSS:
			playBGM("kawazaki", true, 0.2);
			break;
		case GameLogic.BOSS:
			switch (mode) {
			case 0:
				playBGM("slave", true, 0.2);
				break;
			case 1:
				playBGM("tankbot", true, 0.2);
				break;
			case 2:
				playBGM("tankbot", true, 0.2);
				break;
			}
			break;
		case GameLogic.KAWASAKI:
			playBGM("battle", true, 0.2);
			break;
		}
	}

	public void playSFX(String name) {
		try {
			if (!sfx_name.contains(name)) {
				throw new AudioNotFoundException(name);
			}
			sfx.get(sfx_name.indexOf(name)).play();
		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void playSFX(String name, double volume) {
		try {
			if (!sfx_name.contains(name)) {
				throw new AudioNotFoundException(name);
			}
			AudioClip audio;
			audio = sfx.get(sfx_name.indexOf(name));
			audio.setVolume(volume);
			audio.play();
		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());
			;
		}

	}

	public AudioClip getSFX(String name) {
		return sfx.get(sfx_name.indexOf(name));
	}

	public void stopBGM() {
		if (currentBGM.isPlaying()) {
			currentBGM.stop();
		}
	}

}
