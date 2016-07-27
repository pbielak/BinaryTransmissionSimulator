package pwr.bts.stream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Klasa bedaca strumieniem bitowym, ktora dziedziczy po ArrayBitInputStream. Zawiera metody
 * pozwalajace na konwersje dzwieku z rozszerzeniem .wav na bity.
 */
public class AudioInputStream extends ArrayBitInputStream {

	/**
	 * Konstruktor wywolujacy konstruktor nadrzedny (konwertujac w locie
	 * nasz plik na tablice byte).
	 * @param filename nazwa pliku 
	 * @throws IOException
	 */
	public AudioInputStream(String filename) throws UnsupportedAudioFileException, IOException {
		super(getBytes(filename));
	}
	
	/**
	 * Metoda zamieniajaca plik z rozszerzeniem .wav na bity.
	 * @param filename nazwa pliku, ktory ma byc zamieniony na bity
	 * @return tablica byte
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
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
