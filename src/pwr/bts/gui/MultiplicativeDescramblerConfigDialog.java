package pwr.bts.gui;

import pwr.bts.processor.BitStreamProcessor;
import pwr.bts.processor.MultiplicativeDescrambler;

public class MultiplicativeDescramblerConfigDialog extends AbstractScramblerConfigDialog {

	@Override
	public BitStreamProcessor createProcessor() {
		long seed = Long.parseLong(seedTextField.getText());
		int xorBit = Integer.parseInt(xorBitTextField.getText());
		return new MultiplicativeDescrambler(seed, xorBit);
	}
}
