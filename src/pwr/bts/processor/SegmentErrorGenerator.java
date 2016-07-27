package pwr.bts.processor;

import java.util.Random;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class SegmentErrorGenerator extends BitErrorGenerator {

	private final Random rand = new Random();
	private final double meanSegLen, stddevSegLen;
	
	public SegmentErrorGenerator(double meanFreq, double stddevFreq, double meanSegLen, double stddevSegLen) {
		super(meanFreq, stddevFreq);
		this.meanSegLen = meanSegLen;
		this.stddevSegLen = stddevSegLen;
	}
	
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
	
	private int getSegmentLenght() {
		return (int) Math.round(rand.nextGaussian() * stddevSegLen + meanSegLen);
	}
}
