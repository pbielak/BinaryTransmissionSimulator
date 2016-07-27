package pwr.bts.stream;

import java.io.InputStream;

public interface BitInputStream  {

	boolean hasNext();
	long peek();
	long peek(int bits);
	long next();
	long next(int bits);
	void reset();
	long getPointer();
	InputStream asInputStream();
}
