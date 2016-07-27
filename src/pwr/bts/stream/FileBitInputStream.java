package pwr.bts.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Klasa bedaca strumieniem bitowym, ktora dziedziczy po ArrayBitInputStream. Sluzy
 * do przechowywania bitow podanego w konstruktorze pliku.
 */
public class FileBitInputStream extends ArrayBitInputStream {
	
	/**
	 * Konstruktor wywolujacy konstruktor nadrzedny (konwertujac w locie
	 * nasz plik na tablice byte).
	 * @param file plik, z ktorego pobieramy bity
	 * @throws IOException
	 */
	public FileBitInputStream(File file) throws IOException {
		super(Files.readAllBytes(file.toPath()));
	}
}
