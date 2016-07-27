package pwr.bts.stream;

import java.io.InputStream;

public interface BitInputStream  {

	public boolean hasNext();
	public long peek();
	public long peek(int bits);
	public long next();
	public long next(int bits);
	public void reset();
	public long getPointer();
	public InputStream asInputStream();
}
