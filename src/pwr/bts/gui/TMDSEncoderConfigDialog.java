package pwr.bts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pwr.bts.processor.BitStreamProcessor;
import pwr.bts.processor.TMDSEncoder;

public class TMDSEncoderConfigDialog implements BitProcessorConfigDialog {
	
	public TMDSEncoderConfigDialog() {}
	
	@Override
	public JPanel getPanel() {
		return new JPanel();
	}

	@Override
	public BitStreamProcessor createProcessor() {
		return new TMDSEncoder();
	}

	@Override
	public String getDescription() {
		return "";
	}

}
