package pwr.bts.processor;

import pwr.bts.stream.BitInputStream;

public interface BitStreamProcessor {

	BitInputStream process(BitInputStream stream);
}
