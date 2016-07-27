package pwr.bts.gui;

import javax.swing.JPanel;

import pwr.bts.processor.BitStreamProcessor;

public interface BitProcessorConfigDialog {
	public JPanel getPanel();
	public BitStreamProcessor createProcessor();
	public String getDescription();
}
