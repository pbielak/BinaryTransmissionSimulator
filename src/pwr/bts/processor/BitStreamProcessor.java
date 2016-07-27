package pwr.bts.processor;

import pwr.bts.stream.BitInputStream;

public interface BitStreamProcessor {

	public BitInputStream process(BitInputStream stream);
}
