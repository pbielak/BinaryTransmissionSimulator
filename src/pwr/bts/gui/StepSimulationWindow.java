package pwr.bts.gui;

import pwr.bts.Simulator;

import javax.swing.*;
import java.awt.*;

public class StepSimulationWindow extends JFrame {
	
	private final VisualBitStreamPanel dataInput, encoderOutput, channelOutput, decoderOutput;
	private final JButton singleStepButton, byteStepButton;

	public StepSimulationWindow(Simulator simulator) {
		this.dataInput = new VisualBitStreamPanel(simulator.getSent(), "Sygnał wejściowy: ");
		this.encoderOutput = new VisualBitStreamPanel(simulator.getTransOut(), "Wyjście nadajnika: ");
		this.channelOutput = new VisualBitStreamPanel(simulator.getChanOut(), "Wyjście kanału: ");
		this.decoderOutput = new VisualBitStreamPanel(simulator.getReceived(), "Sygnał odebrany: ");
		
		this.singleStepButton = new JButton("Shift one bit");
		this.byteStepButton = new JButton("Shift one byte");
		
		initFrame();
	}

	private void initFrame() {
		setTitle("Symulacja krokowa");
		setSize(1000, 200);
		setResizable(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.2;
		c.weightx = 1;
		c.gridwidth = 2;
		add(dataInput, c);
		
		c.gridy = 1;
		add(encoderOutput, c);
		
		c.gridy = 2;
		add(channelOutput, c);
		
		c.gridy = 3;
		add(decoderOutput, c);
		
		c.gridy = 4;
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		singleStepButton.addActionListener(e -> shiftOneBit());
		add(singleStepButton, c);
		
		c.gridx = 1;
		byteStepButton.addActionListener(e -> shiftOneByte());
		add(byteStepButton, c);
	}
	
	private void shiftOneBit() {
		boolean shifted = dataInput.shiftRight() | encoderOutput.shiftRight() | channelOutput.shiftRight() | decoderOutput.shiftRight();
		
		if(!shifted) {
			singleStepButton.setEnabled(false);
			byteStepButton.setEnabled(false);
		}
		else {
			Color color = (dataInput.getBit(0) != decoderOutput.getBit(0) ? Color.RED : Color.GREEN);
			dataInput.colorBit(0, color);
			decoderOutput.colorBit(0, color);
		}
	}
	
	private void shiftOneByte() {
		for(int i = 0; i < 8; i++) {
			shiftOneBit();
		}
	}
}
