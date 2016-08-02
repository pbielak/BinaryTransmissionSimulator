package pwr.bts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class AbstractScramblerConfigDialog implements BitProcessorConfigDialog {

	protected final JTextField seedTextField, xorBitTextField;
	private final JButton randomizeSeedButton;
	
	public AbstractScramblerConfigDialog() {
		seedTextField = new JTextField();
		xorBitTextField = new JTextField();
		randomizeSeedButton = new JButton("\u2685");
		randomizeSeedButton.addActionListener(e -> seedTextField.setText(String.valueOf(new Random().nextLong())));
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
		panel.add(new JLabel("Seed: "), c);
		
		c.gridx = 1;
		c.weightx = 0.8;
		seedTextField.setPreferredSize(new Dimension(100,20));
		panel.add(seedTextField, c);
		
		c.gridx = 2;
		c.weightx = 0.1;
		panel.add(randomizeSeedButton, c);
		
		c.gridy = 1;
		c.gridx = 0;
		c.weightx = 0.1;
		panel.add(new JLabel("Bit: "), c);
		
		c.gridx = 1;
		c.gridwidth = 2;
		c.weightx = 0.9;
		panel.add(xorBitTextField, c);
		
		return panel;
	}

	@Override
	public String getDescription() {
		String description = String.format("(Seed: %s | XorBit: %s)", seedTextField.getText(), xorBitTextField.getText());
		return (seedTextField.getText().isEmpty() && xorBitTextField.getText().isEmpty()) ? "" : description;
	}
}
