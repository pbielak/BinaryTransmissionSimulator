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

	private JTextField meanFreq = new JTextField();
	private JTextField stddevFreq = new JTextField();
	private JTextField meanSegLen = new JTextField();
	private JTextField stddevSegLen = new JTextField();
	
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
		panel.add(new JLabel("Czestotliwosc"), c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.1;
		panel.add(new JLabel("Srednia: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		meanFreq.setPreferredSize(new Dimension(100,20));
		panel.add(meanFreq, c);
		
		c.gridy = 2;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Odchylenie: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		panel.add(stddevFreq, c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		panel.add(new JLabel("Dlugosc segmentu"), c);
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.weightx = 0.1;
		panel.add(new JLabel("Srednia: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		meanFreq.setPreferredSize(new Dimension(100,20));
		panel.add(meanSegLen, c);
		
		c.gridy = 5;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Odchylenie: "), c);
		
		c.gridx = 1;
		c.weightx = 0.9;
		panel.add(stddevSegLen, c);
		
		return panel;
	}

	@Override
	public BitStreamProcessor createProcessor() {
		double meanFreq = Double.parseDouble(this.meanFreq.getText());
		double stddevFreq = Double.parseDouble(this.stddevFreq.getText());
		double meanSegLen = Double.parseDouble(this.meanSegLen.getText());
		double stddevSegLen = Double.parseDouble(this.stddevSegLen.getText());
		return new SegmentErrorGenerator(meanFreq, stddevFreq, meanSegLen, stddevSegLen);
	}

	@Override
	public String getDescription() {
		String description = String.format("(Czestotliwosc - m: %s d: %s | Dlugosc bloku - m: %s d: %s)", meanFreq.getText(), stddevFreq.getText(), meanSegLen.getText(), stddevSegLen.getText()); 
		return (meanFreq.getText().isEmpty() && stddevFreq.getText().isEmpty() && meanSegLen.getText().isEmpty() && stddevSegLen.getText().isEmpty()) ? "" : description;
	}
}
