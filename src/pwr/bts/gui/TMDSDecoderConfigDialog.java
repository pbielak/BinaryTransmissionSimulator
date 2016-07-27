package pwr.bts.gui;

import javax.swing.JPanel;

import pwr.bts.processor.BitStreamProcessor;
import pwr.bts.processor.TMDSDecoder;

public class TMDSDecoderConfigDialog implements BitProcessorConfigDialog {

	@Override
	public JPanel getPanel() {
		return new JPanel();
	}

	@Override
	public BitStreamProcessor createProcessor() {
		return new TMDSDecoder();
	}

	@Override
	public String getDescription() {
		return "";
	}

}
