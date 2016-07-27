package pwr.bts.stream;

import java.util.Random;

public class RandomBitInputStream extends ArrayBitInputStream {

	public RandomBitInputStream(int size) {
		super(new Random().ints(size, 0, 256).mapToObj(i -> (byte) i).toArray(Byte[]::new));
	}

}
