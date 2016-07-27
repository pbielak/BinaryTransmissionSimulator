package pwr.bts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pwr.bts.stream.BitInputStream;

public class VisualBitStreamPanel extends JPanel {

	private final int BIT_COUNT = 16;
	private final int PANEL_HEIGHT = 30;
	private final int PANEL_WIDTH = 500;
	
	private final BitInputStream stream;
	private final List<JLabel> bits;
	private final JLabel name;
	
	public VisualBitStreamPanel(BitInputStream stream, String name) {
		this.stream = stream;
		this.bits = new ArrayList<>();
		this.name = new JLabel(name, SwingConstants.CENTER);
		initComponents();
	}
	
	private void initComponents() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.3;
		name.setMinimumSize(new Dimension((int)(PANEL_WIDTH * 0.3), PANEL_HEIGHT));
		add(name, c);
		
		c.weightx = 0.7 / BIT_COUNT;
		for(int i = 0; i < BIT_COUNT; i++) {
			c.gridx = i + 1;
			
			JLabel bit = new JLabel("-", SwingConstants.CENTER);
			bit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			bit.setPreferredSize(new Dimension((int)((PANEL_WIDTH * 0.7) / BIT_COUNT), PANEL_HEIGHT));
			bit.setOpaque(true);
			bits.add(bit);
			add(bit, c);
		}
	}
	
	public boolean shiftRight() {
		boolean canShift = stream.hasNext();
		
		if(canShift) {
			for(int i = BIT_COUNT - 1; i > 0; i--) {
				bits.get(i).setText(bits.get(i - 1).getText());
				bits.get(i).setBackground(bits.get(i - 1).getBackground());
			}
		
			bits.get(0).setText("" + stream.next());
		}
		
		return canShift;
	}

	public long getBit(int index) {
		return Long.parseLong(bits.get(index).getText());
	}
	
	public void colorBit(int index, Color color) {
		bits.get(index).setBackground(color);
	}
}
