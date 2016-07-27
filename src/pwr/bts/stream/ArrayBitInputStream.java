package pwr.bts.stream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ArrayBitInputStream implements BitInputStream {
	
	private final byte[] data;
	
	private long pointer;
	private long length;
	
	public ArrayBitInputStream(byte[] data, long length) {
		this.data = data.clone();
		this.length = length;
	}
	
	public ArrayBitInputStream(byte[] data) {
		this.data = data.clone();
		this.length = (long) this.data.length << 3;
	}
	
	public ArrayBitInputStream(Byte[] data, long length) {
		this.data = new byte[data.length];
		this.length = length;
		
		for (int i = 0; i < data.length; i++)
			this.data[i] = data[i];
	}
	
	public ArrayBitInputStream(Byte[] data) {
		this.data = new byte[data.length];
		this.length = (long) this.data.length << 3;
		
		for (int i = 0; i < data.length; i++)
			this.data[i] = data[i];
	}
	
	@Override
	public boolean hasNext() {
		return pointer < length;
	}

	@Override
	public long peek() {
		return getBits(1, false);
	}

	@Override
	public long peek(int bits) {
		return getBits(bits, false);
	}

	@Override
	public long next() {
		return getBits(1, true);
	}

	@Override
	public long next(int bits) {
		return getBits(bits, true);
	}
	
	@Override
	public long getPointer() {
		return pointer;
	}
	
	@Override
	public void reset() {
		pointer = 0;
	}
	
	@Override
	public InputStream asInputStream() {
		return new ByteArrayInputStream(data);
	}
	
	private long getBits(int bits, boolean shiftPointer) {
		if (bits > 64 || bits <= 0)
			throw new IllegalArgumentException("The number of bits has to be a positive number smaller than 65!");
		
		long result = 0, pointer = this.pointer;
		
		for (; bits > 0;) {
			long bitOffset = pointer & 0b111, bitOffsetInvert = 8 - bitOffset, shift = Math.min(bitOffsetInvert, bits), maskOffset = bitOffsetInvert - shift;
			byte data = (pointer >> 3) < this.data.length ? this.data[(int) (pointer >> 3)] : 0;
			
			result = (result << shift) | ((data & (~(-1 << shift) << maskOffset)) >> maskOffset);
			
			bits -= shift;
			pointer += shift;
		}
		
		if (shiftPointer)
			this.pointer = pointer;
		
		return result;
	}
}
