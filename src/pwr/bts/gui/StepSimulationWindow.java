package pwr.bts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pwr.bts.Simulator;
import pwr.bts.stream.BitInputStream;

public class StepSimulationWindow extends JFrame{ 
	
	private final VisualBitStreamPanel dataInput, encoderOutput, channelOutput, decoderOutput;
	private final JButton singleStepButton, byteStepButton;

	public StepSimulationWindow(Simulator simulator) {
		this.dataInput = new VisualBitStreamPanel(simulator.getSent(), "Sygna³ wejœciowy: ");
		this.encoderOutput = new VisualBitStreamPanel(simulator.getTransOut(), "Wyjœcie nadajnika: ");
		this.channelOutput = new VisualBitStreamPanel(simulator.getChanOut(), "Wyjœcie kana³u: ");
		this.decoderOutput = new VisualBitStreamPanel(simulator.getReceived(), "Sygna³ odebrany: ");
		
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
