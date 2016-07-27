package pwr.bts.processor;

import java.util.Random;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

/**
 * Klasa, ktora tworzy generator bledow, ktory generuje bledy na pojedynczych bitach (bardzo
 * rzadko jest taka sytuacja, ze kilka bitow pod rzad jest zmienionych ze wzgledu na szumy).
 */
public class BitErrorGenerator implements BitStreamProcessor {

	/**
	 * generator liczb pseudolosowych
	 */
	private final Random rand = new Random();
	/**
	 * srednia oraz odchylenie standardowe
	 */
	private final double mean, stddev;
	
	/**
	 * @param mean srednia
	 * @param stddev odchylenie standardowe
	 */
	public BitErrorGenerator(double mean, double stddev) {
		this.mean = mean;
		this.stddev = stddev;
	}
	
	/**
	 * Nakladanie szumu na strumien bitowy.
	 */
	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		int bitsToError = bitsToError();
		
		while (stream.hasNext())
			if (bitsToError > 0) {
				bos.offer(stream.next(), 1);
				bitsToError--;
			} else {
				bos.offer(stream.next() ^ 1, 1);
				bitsToError = bitsToError() - 1;
			}
		
		return bos.convert();
	}
	
	/**
	 * Metoda zwracajaca wartosc mowiaca, co ktory bit ma zostac zaszumiony.
	 * @return wartosc mowiaca, co ktory bit ma zostac zaszumiony
	 */
	protected int bitsToError() {
		return (int) Math.round(rand.nextGaussian() * stddev + mean);
	}
}
