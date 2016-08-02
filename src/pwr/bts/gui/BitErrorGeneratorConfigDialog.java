package pwr.bts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pwr.bts.processor.BitStreamProcessor;
import pwr.bts.processor.BitErrorGenerator;

public class BitErrorGeneratorConfigDialog implements BitProcessorConfigDialog {

	private JTextField meanTextField;
	private JTextField deviationTextField;
	
	public BitErrorGeneratorConfigDialog() {
		meanTextField = new JTextField();
		deviationTextField = new JTextField();
	}
	
	@Override
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		panel.add(new JLabel("Mean: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		meanTextField.setPreferredSize(new Dimension(100,20));
		panel.add(meanTextField, c);
		
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Deviation: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		panel.add(deviationTextField, c);
		
		return panel;
	}

	@Override
	public BitStreamProcessor createProcessor() {
		double mean = Double.parseDouble(meanTextField.getText());
		double deviation = Double.parseDouble(deviationTextField.getText());
		return new BitErrorGenerator(mean, deviation);
	}

	@Override
	public String getDescription() {
		String description = String.format("(Mean: %s | Deviation: %s)", meanTextField.getText(), deviationTextField.getText());
		return (meanTextField.getText().isEmpty() && deviationTextField.getText().isEmpty()) ? "" : description;
	}

}
