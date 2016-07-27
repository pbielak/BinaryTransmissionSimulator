package pwr.bts.gui;

import javax.swing.JPanel;

import pwr.bts.processor.BitStreamProcessor;

public class BitProcessorWrapper {

	private String name;
	private BitStreamProcessor processor;
	private BitProcessorConfigDialog configDialog;
	
	
	public BitProcessorWrapper(String name, BitProcessorConfigDialog configDialog) {
		this.name = name;
		this.configDialog = configDialog;
		this.processor = null;
	}

	public BitStreamProcessor getProcessor() {
		if(processor == null) {
			processor = configDialog.createProcessor();
		}
		
		return processor;
	}
	
	public JPanel getConfigPanel() {
		return configDialog.getPanel();
	}
	
	@Override
	public String toString() {
		return name + configDialog.getDescription();
	}
}
