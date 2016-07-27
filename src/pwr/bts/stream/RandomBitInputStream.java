package pwr.bts.stream;

import java.util.Random;

/**
 * Klasa bedaca strumieniem bitowym, ktora dziedziczy po ArrayBitInputStream. Sluzy
 * do przechowywania bitow randomowego ciagu bitow o dlugosci zadanej jako parametr konstruktora.
 */
public class RandomBitInputStream extends ArrayBitInputStream {

	/**
	 * Konstruktor randomowego strumienia bitow.
	 * @param size rozmiar strumienia, ktory jest tworzony
	 */
	public RandomBitInputStream(int size) {
		super(new Random().ints(size, 0, 256).mapToObj(i -> (byte) i).toArray(Byte[]::new));
	}

}
