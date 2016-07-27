package pwr.bts.gui;

import javax.swing.JPanel;

import pwr.bts.processor.BitStreamProcessor;

public interface BitProcessorConfigDialog {
	JPanel getPanel();
	BitStreamProcessor createProcessor();
	String getDescription();
}
