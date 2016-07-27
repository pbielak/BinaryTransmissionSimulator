package pwr.bts.processor;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class TMDSEncoder implements BitStreamProcessor {
	
	private long voltage;

	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		
		while (stream.hasNext())
			bos.offer(encode(stream.next(8)), 10);
		
		return bos.convert();
	}

	private long encode(long _byte) {
		long xor = _byte, xnor = _byte;
		int transX = 0, transXn = 0, voltX = (int) ((_byte & 1) << 1) - 1, voltXn = voltX;
		
		for (int i = 1; i < 8; i++) {
			xor ^= ((xor >> (i - 1)) & 1) << i;
			xnor ^= ((xnor >> (i - 1)) & 1 ^ 1) << i;
			transX += ((xor >> (i - 1)) ^ (xor >> i)) & 1;
			transXn += ((xnor >> (i - 1)) ^ (xnor >> i)) & 1;
			voltX += (((xor >> i) & 1) << 1) - 1;
			voltXn += (((xnor >> i) & 1) << 1) - 1;
		}
		
		if (transX < transXn)
			if ((voltage ^ voltX) < 0) {
				voltage += voltX - 2;
				return (xor << 2);
			} else {
				voltage -= voltX;
				return (xor << 2) ^ 0b1111111101;
			}
		else
			if ((voltage ^ voltXn) < 0) {
				voltage += voltXn;
				return (xnor << 2) ^ 0b10;
			} else {
				voltage -= voltXn - 2;
				return (xnor << 2) ^ 0b1111111111;
			}
	}
}
