package pwr.bts.stream;

public interface BitOutputStream {

	public void offer(long bits, int bitCount);
	public BitInputStream convert();
}
