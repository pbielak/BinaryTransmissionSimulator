package pwr.bts.stream;

import java.io.InputStream;

/**
 * Interfejs, ktory implementuja wszystkie klasy bedace strumieniami bitowymi. Strumienie
 * sa wysylane z nadajnika przez kanal transmisyjny do odbiornika. Zaimplementowalismy 
 * strumien tablicowy, obrazowy, randomowy oraz dzwiekowy. Interfejs ten ma wszystkie potrzebne
 * metody, dzieki ktorym dana klasa staje sie strumieniem.
 *
 */
public interface BitInputStream  {

	/**
	 * Sprawdzenie, czy dany strumien bitow ma jeszcze jakis element.
	 * @return odpowiedz, czy jest jeszcze chociaz jeden bit
	 */
	public boolean hasNext();
	
	/**
	 * Pobranie nastepnego bitu, ale bez przesuniecia wskaznika (jezeli pobierzemy element 5, to
	 * po pobraniu wskaznik dalej pokazuje na element 5).
	 * @return bit, na ktory aktualnie wskazuje wskaznik
	 */
	public long peek();
	
	/**
	 * Pobranie kilku bitow ze strumienia bez przesuniecia.
	 * @param bits ilosc bitow, ktore nalezy pobrac
	 * @return tyle bitow, ile jest w wyslanym parametrze, rozpoczynajac od tego, na ktory aktualnie
	 * wskazuje wskaznik 
	 */
	public long peek(int bits);
	
	/**
	 * Pobranie nastepnego bitu z przesunieciem wskaznika na kolejny bit.
	 * @return bit, na ktory aktualnie wskazuje wskaznik
	 */
	public long next();
	
	/**
	 * Pobranie kilku bitow ze strumienia z przesunieciem.
	 * @param bits ilosc bitow, ktore nalezy pobrac
	 * @return tyle bitow, ile jest w wyslanym parametrze, rozpoczynajac od tego, na ktory aktualnie
	 * wskazuje wskaznik 
	 */
	public long next(int bits);
	
	/**
	 * Cofniecie wskaznika na 1 element.
	 */
	public void reset();
	
	/**
	 * Zwraca wskaznik.
	 * @return wskaznik na kolejny bit
	 */
	public long getPointer();
	
	/**
	 * Konwersja tablicy byte do InputStreama.
	 * @return strumien jako InputStream
	 */
	public InputStream asInputStream();
}
