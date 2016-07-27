package pwr.bts;

import pwr.bts.processor.TMDSDecoder;
import pwr.bts.processor.TMDSEncoder;
import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

public class Test {

	public static void main(String[] args) {
		BitOutputStream stream = new ArrayBitOutputStream();
		
		stream.offer(0b0110101100101101, 16);
		
		BitInputStream bs = stream.convert();
		
		while (bs.hasNext())
			System.out.print(bs.next(1) + " ");
		
		System.out.println();
		
		bs.reset();
		bs = new TMDSEncoder().process(bs);
		while (bs.hasNext())
			System.out.print(bs.next(1) + " ");
		
		System.out.println();
		bs.reset();
		bs = new TMDSDecoder().process(bs);
		
		while (bs.hasNext())
			System.out.print(bs.next(1) + " ");
		
		System.out.println();
	}
}
