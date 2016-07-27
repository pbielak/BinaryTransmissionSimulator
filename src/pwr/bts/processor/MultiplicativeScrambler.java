package pwr.bts.processor;


public class MultiplicativeScrambler extends SimpleAdditiveScrambler {

	public MultiplicativeScrambler(long seed, int xorBit) {
		super(seed, xorBit);
	}
	
	@Override
	protected long xorOneBit(long next) {
		long bit = ((randomBuffer >> xorBit) & 1) ^ (randomBuffer & 1);
		long result = next ^ bit;
		randomBuffer = (randomBuffer >> 1) & 0x7FFFFFFFFFFFFFFFl | (result << 63);
		return result;
	}
}
