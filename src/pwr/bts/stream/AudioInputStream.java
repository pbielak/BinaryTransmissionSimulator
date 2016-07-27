package pwr.bts.stream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioInputStream extends ArrayBitInputStream {

	public AudioInputStream(String filename) throws UnsupportedAudioFileException, IOException {
		super(getBytes(filename));
	}
	
	private static byte[] getBytes(String filename) throws UnsupportedAudioFileException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = AudioSystem.getAudioInputStream(new File(filename));
		int read;
		byte[] buff = new byte[1024];
		
		while ((read = in.read(buff)) > 0)
			out.write(buff, 0, read);
		
		out.flush();
		
		return out.toByteArray();
	}

}
