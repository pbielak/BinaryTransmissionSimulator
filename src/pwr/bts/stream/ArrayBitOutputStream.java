package pwr.bts.stream;

import java.util.ArrayList;
import java.util.List;

/**
 *	Klasa ArrayBitOutputStream implementuje BitOutputStream, czyli jest strumieniem wyjsciowym
 * bitow, ktore sa wysylane do odbiornika. Jest to strumien, ktory przechowuje bity w array liscie.
 *
 */
public class ArrayBitOutputStream implements BitOutputStream {

	/**
	 * Lista bitow, ktore przechowuje aktualnie nasz strumien wyjsciowy.
	 */
	private final List<Byte> data = new ArrayList<>();
	private int bitCount;
	
	/**
	 * Konstruktor domyslny.
	 */
	public ArrayBitOutputStream() {
		data.add((byte) 0);
	}
	
	@Override
	public void offer(long bits, int bitCount) {
		while (bitCount > 0) {
			int minBitCount = Math.min(8 - this.bitCount, bitCount);
			
			data.set(data.size() - 1, (byte) (((bits >> (bitCount - minBitCount)) << (8 - this.bitCount - minBitCount))
					| data.get(data.size() - 1)));
			this.bitCount = (minBitCount + this.bitCount) % 8;
			
			if (this.bitCount == 0)
				data.add((byte) 0);
			
			bitCount -= minBitCount;
		}
	}

	@Override
	public BitInputStream convert() {
		long length = ((long) data.size() << 3) + bitCount - 8;
		
		if (bitCount == 0)
			data.remove(data.size() - 1);
		
		return new ArrayBitInputStream(data.toArray(new Byte[data.size()]), length);
	}
}
