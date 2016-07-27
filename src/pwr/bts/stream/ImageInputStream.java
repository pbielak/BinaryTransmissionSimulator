package pwr.bts.stream;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Klasa bedaca strumieniem bitowym, ktora dziedziczy po ArrayBitInputStream. Sluzy
 * do przechowywania bitow podanego w konstruktorze pliku bedacego obrazkiem.
 * Nie uzylismy do tego klasy FileBitInputStream, poniewaz konwertuje ona rowniez bity
 * naglowka, podczas gdy w tej klasie naglowek jest omijany.
 */
public class ImageInputStream extends ArrayBitInputStream {

	/**
	 * Konstruktor, ktory wywoluje konstruktor nadrzedny
	 * @param filename
	 * @param format
	 * @throws IOException
	 */
	public ImageInputStream(String filename, String format) throws IOException {
		super(getBytesFromIMG(filename, format));
	}

	/**
	 * Zamiana obrazka (np. JPG) na tablicy byte zawierajaca bity, z ktorych obrazek jest zbudowany.
	 * @param filename nazwa pliku obrazka
	 * @param format format obrazka (np. JPG)
	 * @return tablica byte zawierajaca bity obrazka
	 * @throws IOException
	 */
	private static byte[] getBytesFromIMG(String filename, String format) throws IOException {
		BufferedImage image = ImageIO.read(new File(filename));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, format, baos);
		baos.flush();
		return baos.toByteArray();
	}
	
	/**
	 * Zamiana bitow z powrotem na obrazek.
	 * @return
	 * @throws IOException
	 */
	public BufferedImage getIMG() throws IOException {
		InputStream input = asInputStream();
		return ImageIO.read(input);
	}
}
