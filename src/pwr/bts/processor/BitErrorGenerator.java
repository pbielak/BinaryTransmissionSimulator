package pwr.bts.processor;

import java.util.Random;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class BitErrorGenerator implements BitStreamProcessor {

	private final Random rand = new Random();
	private final double mean, deviation;
	
	public BitErrorGenerator(double mean, double deviation) {
		this.mean = mean;
		this.deviation = deviation;
	}
	
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
	
	protected int bitsToError() {
		return (int) Math.round(rand.nextGaussian() * deviation + mean);
	}
}
