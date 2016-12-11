package sound;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import exception.AudioNotFoundException;
import javafx.scene.media.AudioClip;

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
		File folder = new File(ClassLoader.getSystemResource("bgm").getFile());
		for (File file : folder.listFiles() ) {		
			try {
				bgm.add(new AudioClip(file.toURI().toURL().toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			bgm_name.add(file.getName());
		}
		folder = new File(ClassLoader.getSystemResource("sfx").getFile());
		for (File file : folder.listFiles() ) {
			try {
				sfx.add(new AudioClip(file.toURI().toURL().toString()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			sfx_name.add(file.getName());
		}
	}
	
	public synchronized static AudioHolder getInstance() {
		return instance;
	}
	
	public void playBGM(String name, boolean looping, double volume) {
		try {
			if (!bgm_name.contains(name+".mp3")) {
				throw new AudioNotFoundException();
			}
			AudioClip audio = bgm.get(bgm_name.indexOf(name+".mp3"));
			audio.setVolume(volume);
			if (looping) {
				audio.setCycleCount(AudioClip.INDEFINITE);
			}    
		    audio.play();
		    currentBGM = audio;
		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());;
		}	
	}
	
	public void playLevelBGM(int level) {
		switch (level) {
		default :	playBGM("peppermint", true, 0.5); break;
		case 4  :   playBGM("slave", true, 0.3); break;
		//case 1  :   playBGM(".."); break;
		}
	}
	
	public void playSFX(String name) {
		try {
			if (!sfx_name.contains(name+".wav")) {
				throw new AudioNotFoundException();
			}
			sfx.get(sfx_name.indexOf(name+".wav")).play();
		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());;
		}	
	}
	
	public void playSFX(String name, double volume) {	
		try {
			if (!sfx_name.contains(name+".wav")) {
				throw new AudioNotFoundException();
			}
			AudioClip audio;
			audio = sfx.get(sfx_name.indexOf(name + ".wav"));
			audio.setVolume(volume);
			audio.play();
		} catch (AudioNotFoundException e) {
			System.out.println(e.getMessage());;
		}
		
	}
	
	public AudioClip getSFX(String name) {
		return sfx.get(sfx_name.indexOf(name+".wav"));
	}
	
	public void stopBGM() {
		if (currentBGM.isPlaying()) {
			currentBGM.stop();
		}
	}

}
