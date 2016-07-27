package pwr.bts.processor;

import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class TMDSDecoder implements BitStreamProcessor {


	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		
		while (stream.hasNext())
			bos.offer(decode(stream.next(10)), 8);
		
		return bos.convert();
	}

	private long decode(long word) {
		if ((word & 1) == 1)
			word ^= 0b1111111100;
		word = word >> 1;

		if ((word & 1) == 1)
			for (int i = 8; i > 1; i--)
				word ^= ((word >> (i - 1)) & 1 ^ 1) << i;
		else
			for (int i = 8; i > 1; i--)
				word ^= ((word >> (i - 1)) & 1) << i;
		
		return word >> 1;
	}
}
