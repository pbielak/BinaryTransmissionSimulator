package pwr.bts.stream;

public interface BitOutputStream {

	void offer(long bits, int bitCount);
	BitInputStream convert();
}
