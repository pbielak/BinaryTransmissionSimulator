package pwr.bts.processor;

import java.util.Random;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

/**
 * Klasa, ktora tworzy generator bledow, ktory generuje bledy na grupach bitow -
 * jezeli juz wystapi blad to kilka kolejnych bitow bedzie zaszumionych.
 */
public class SegmentErrorGenerator extends BitErrorGenerator {

	/**
	 * generator liczb pseudolosowych
	 */
	private final Random rand = new Random();
	/**
	 * srednia oraz odchylenie dlugosci segmentu
	 */
	private final double meanSegLen, stddevSegLen;
	
	public SegmentErrorGenerator(double meanFreq, double stddevFreq, double meanSegLen, double stddevSegLen) {
		super(meanFreq, stddevFreq);
		this.meanSegLen = meanSegLen;
		this.stddevSegLen = stddevSegLen;
	}
	
	/**
	 * Nakladanie szumu na strumien bitowy.
	 */
	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		int bitsToError = bitsToError(), segmentLength = getSegmentLenght();
		
		while (stream.hasNext())
			if (bitsToError > 0) {
				bos.offer(stream.next(), 1);
				bitsToError--;
			} else {
				segmentLength = getSegmentLenght();
				bitsToError = -segmentLength;
				
				for (; segmentLength > 0 && stream.hasNext(); segmentLength--)
					bos.offer(stream.next() ^ 1, 1);
				
				bitsToError += bitsToError();
			}
		
		return bos.convert();
	}
	
	/**
	 * Metoda zwracajaca wartosc mowiaca, ile bitow w bloku ma byc zaszumionych.
	 * @return wartosc mowiaca, ile bitow w bloku ma byc zaszumionych
	 */
	private int getSegmentLenght() {
		return (int) Math.round(rand.nextGaussian() * stddevSegLen + meanSegLen);
	}
}
