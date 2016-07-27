package pwr.bts.processor;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class SimpleAdditiveScrambler implements BitStreamProcessor {

	private final long seed;
	final int xorBit;
	
	long randomBuffer;
	
	public SimpleAdditiveScrambler(long seed, int xorBit) {
		this.seed = seed;
		this.xorBit = xorBit;
		
		if (xorBit < 0 || xorBit >= 64)
			throw new IllegalArgumentException("Invalid xorBit value. has to be between 0 and 64");
	}
	
	@Override
	public BitInputStream process(BitInputStream stream) {
		randomBuffer = seed;
		BitOutputStream bos = new ArrayBitOutputStream();
		while(stream.hasNext()) {
			bos.offer(xorOneBit(stream.next()), 1);
		}
		return bos.convert();
	}

	protected long xorOneBit(long next) {
		long bit = ((randomBuffer >> xorBit) & 1) ^ (randomBuffer & 1);
		randomBuffer = (randomBuffer >> 1) & 0x7FFFFFFFFFFFFFFFL | (bit << 63);
		return bit ^ next;
	}
}
