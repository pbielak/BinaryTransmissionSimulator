package pwr.bts.processor;

import pwr.bts.stream.BitInputStream;

/**
 * Interfejs, z ktorego tworzone sa wszystkie Processory strumieni bitowych.
 * Kazdy Scrambler/Descrambler/Generator bledow jest swojego rodzaju "przetwarzaczem"
 * strumieni bitowych, wiec wydzielilismy interfejs, ktory implementuje wszystkie te
 * "przetwarzacze".
 * @author jakub
 *
 */
public interface BitStreamProcessor {

	/**
	 * Metoda, w ktorej po prostu przetwarzane sa bity. Kazdy processor ma swoj wlasny
	 * sposob przetwarzania bitow strumienia.
	 * @param stream strumien wejsciowy
	 * @return
	 */
	public BitInputStream process(BitInputStream stream);
}
