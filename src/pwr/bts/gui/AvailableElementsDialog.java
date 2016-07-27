package pwr.bts.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AvailableElementsDialog {
	private JPanel components;
	private JPanel oldPanel;
	private JComboBox<BitProcessorWrapper> options;
	
	public AvailableElementsDialog() {
		components = new JPanel();
		components.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		components.add(new JLabel("Wybierz jeden z poniższych elementów"), c);
		
		options = new JComboBox<>();
		options.addActionListener(e -> {
            if(oldPanel != null) {
                components.remove(oldPanel);
            }

            JPanel panel = ((BitProcessorWrapper) options.getSelectedItem()).getConfigPanel();
            GridBagConstraints c1 = new GridBagConstraints();
            c1.gridx = 0;
            c1.gridy = 2;
            c1.insets = new Insets(20, 0, 0, 0);
            components.add(panel, c1);

            oldPanel = panel;
            components.revalidate();
            components.repaint();
        });
		
		addOptions();
		c.gridy = 1;
		components.add(options, c);
	}
	
	public int showDialog() {
		return JOptionPane.showConfirmDialog(null, components, "Dostępne elementy", JOptionPane.PLAIN_MESSAGE);
	}
	
	public BitProcessorWrapper getSelectedValue() {
		return (BitProcessorWrapper) options.getSelectedItem();
	}
	
	private void addOptions() {
		options.addItem(new BitProcessorWrapper("Additive Scrambler", new AdditiveScramblerConfigDialog()));
		options.addItem(new BitProcessorWrapper("Multiplicative Scrambler", new MultiplicativeScramblerConfigDialog()));
		options.addItem(new BitProcessorWrapper("Multiplicative Descrambler", new MultiplicativeDescramblerConfigDialog()));
		options.addItem(new BitProcessorWrapper("Bit Error Generator", new BitErrorGeneratorConfigDialog()));
		options.addItem(new BitProcessorWrapper("Segment Error Generator", new SegmentErrorGeneratorConfigDialog()));
		options.addItem(new BitProcessorWrapper("TMDS Encoder", new TMDSEncoderConfigDialog()));
		options.addItem(new BitProcessorWrapper("TMDS Decoder", new TMDSDecoderConfigDialog()));
	}
}
