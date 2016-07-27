package pwr.bts.processor;

public class MultiplicativeDescrambler extends SimpleAdditiveScrambler {

	public MultiplicativeDescrambler(long seed, int xorBit) {
		super(seed, xorBit);
	}

	@Override
	protected long xorOneBit(long next) {
		long bit = ((randomBuffer >> xorBit) & 1) ^ (randomBuffer & 1);
		randomBuffer = (randomBuffer >> 1) & 0x7FFFFFFFFFFFFFFFL | (next << 63);
		return next ^ bit;
	}
}
