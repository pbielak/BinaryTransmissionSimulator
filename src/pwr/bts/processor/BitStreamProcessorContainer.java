package pwr.bts.processor;

import java.util.Arrays;
import java.util.List;

import javax.swing.ListModel;

import pwr.bts.gui.BitProcessorWrapper;
import pwr.bts.stream.ArrayBitOutputStream;
import pwr.bts.stream.BitInputStream;
import pwr.bts.stream.BitOutputStream;

/**
 * Container, ktory zawiera wszystkie "przetwarzacze" i może wykonac przetwarzanie
 * wszystkich tych "przetwarzaczy" po kolei.
 * @author jakub
 *
 */
public class BitStreamProcessorContainer implements BitStreamProcessor {
	
	private final ListModel<BitProcessorWrapper> processors;
	private final List<BitStreamProcessor> rawProcessors;
	
	/**
	 * Konstruktor kopiujacy.
	 * @param processors
	 */
	public BitStreamProcessorContainer(ListModel<BitProcessorWrapper> processors) {
		this.processors = processors;
		this.rawProcessors = null;
	}
	
	public BitStreamProcessorContainer(BitStreamProcessor... rawProcessors) {
		this.rawProcessors = Arrays.asList(rawProcessors);
		this.processors = null;
	}
	
	/**
	 * Wywolanie metod przetwarzajacych wszystkich skladnikow, ktore zawiera ten container.
	 */
	@Override
	public BitInputStream process(BitInputStream stream) {
		BitOutputStream bos = new ArrayBitOutputStream();
		
		while (stream.hasNext())
			bos.offer(stream.next(), 1);
		
		stream = bos.convert();
		
		if (rawProcessors == null)
			for (int i = 0; i < processors.getSize(); i++)
				stream = processors.getElementAt(i).getProcessor().process(stream);
		else
			for (BitStreamProcessor processor : rawProcessors)
				stream = processor.process(stream);
		
		return stream;
	}
}
