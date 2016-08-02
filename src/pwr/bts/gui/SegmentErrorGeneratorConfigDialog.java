package pwr.bts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pwr.bts.processor.BitStreamProcessor;
import pwr.bts.processor.SegmentErrorGenerator;

public class SegmentErrorGeneratorConfigDialog implements BitProcessorConfigDialog {

	private JTextField meanFrequency = new JTextField();
	private JTextField deviationFrequency = new JTextField();
	private JTextField meanSegmentLength = new JTextField();
	private JTextField deviationSegmentLength = new JTextField();
	
	@Override
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		panel.add(new JLabel("Frequency"), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.1;
		panel.add(new JLabel("Mean: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		meanFrequency.setPreferredSize(new Dimension(100,20));
		panel.add(meanFrequency, c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Deviation: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		panel.add(deviationFrequency, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		panel.add(new JLabel("Segment length"), c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.1;
		panel.add(new JLabel("Mean: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		meanFrequency.setPreferredSize(new Dimension(100,20));
		panel.add(meanSegmentLength, c);
		
		c.gridy = 5;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Deviation: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		panel.add(deviationSegmentLength, c);
		
		return panel;
	}

	@Override
	public BitStreamProcessor createProcessor() {
		double meanFreq = Double.parseDouble(this.meanFrequency.getText());
		double stddevFreq = Double.parseDouble(this.deviationFrequency.getText());
		double meanSegLen = Double.parseDouble(this.meanSegmentLength.getText());
		double stddevSegLen = Double.parseDouble(this.deviationSegmentLength.getText());
		return new SegmentErrorGenerator(meanFreq, stddevFreq, meanSegLen, stddevSegLen);
	}

	@Override
	public String getDescription() {
		String description = String.format("(Frequency - m: %s d: %s | Segment length - m: %s d: %s)", meanFrequency.getText(), deviationFrequency.getText(), meanSegmentLength.getText(), deviationSegmentLength.getText());
		return (meanFrequency.getText().isEmpty() && deviationFrequency.getText().isEmpty() && meanSegmentLength.getText().isEmpty() && deviationSegmentLength.getText().isEmpty()) ? "" : description;
	}
}
