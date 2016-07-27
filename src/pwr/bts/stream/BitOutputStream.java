package pwr.bts.stream;

/**
 * Interfejs, z ktorego bedziemy tworzyc strumienie wyjsciowe ze scramblera.
 *
 */
public interface BitOutputStream {

	/**
	 * Metoda, dzieki ktorej dodajemy kolejne bity do naszego strumienia wyjsciowego.
	 * @param bits bity, ktore chcemy dodac do strumienia
	 * @param bitCount ilosc bitow, ktore chcemy dodac do strumienia
	 */
	public void offer(long bits, int bitCount);
	
	/**
	 * Konwersja strumienia wyjsciowego do wejsciowego.
	 * @return strumień wejsciowy (zawierajacy te same bity co strumien wyjsciowy).
	 */
	public BitInputStream convert();
}
