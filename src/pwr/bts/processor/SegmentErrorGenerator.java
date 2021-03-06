package pwr.bts.processor;

import java.util.Random;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class SegmentErrorGenerator extends BitErrorGenerator {

	private final Random rand = new Random();
	private final double meanSegmentLength, deviationSegmentLength;
	
	public SegmentErrorGenerator(double meanFrequency, double deviationFrequency, double meanSegmentLength, double deviationSegmentLength) {
		super(meanFrequency, deviationFrequency);
		this.meanSegmentLength = meanSegmentLength;
		this.deviationSegmentLength = deviationSegmentLength;
	}
	
	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		int bitsToError = bitsToError(), segmentLength = getSegmentLength();
		
		while (stream.hasNext())
			if (bitsToError > 0) {
				bos.offer(stream.next(), 1);
				bitsToError--;
			} else {
				segmentLength = getSegmentLength();
				bitsToError = -segmentLength;
				
				for (; segmentLength > 0 && stream.hasNext(); segmentLength--)
					bos.offer(stream.next() ^ 1, 1);
				
				bitsToError += bitsToError();
			}
		
		return bos.convert();
	}
	
	private int getSegmentLength() {
		return (int) Math.round(rand.nextGaussian() * deviationSegmentLength + meanSegmentLength);
	}
}
