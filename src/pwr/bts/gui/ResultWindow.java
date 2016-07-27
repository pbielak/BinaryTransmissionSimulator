package pwr.bts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pwr.bts.Simulator;
import pwr.bts.stream.BitInputStream;

public class ResultWindow extends JFrame {
	private Simulator simulator;
	private ChartPanel zerosBefore, zerosAfter, onesBefore, onesAfter;
	
	public ResultWindow(Simulator simulator) {
		this.simulator = simulator;
		
		setTitle("Wyniki symulacji");
		setSize(700, 700);
		setResizable(false);	
		setLayout(new GridBagLayout());
		
		makePanels();
		addPanels();
	}
	
	private void makePanels() {
		zerosBefore = getPanel("Zera (wejście)", simulator.getSent(), 0);
		zerosAfter = getPanel("Zera (wyjście nadajnika)", simulator.getTransOut(), 0);
		onesBefore = getPanel("Jedynki (wejście)", simulator.getSent(), 1);
		onesAfter = getPanel("Jedynki (wyjście nadajnika)", simulator.getTransOut(), 1);
	}

	private ChartPanel getPanel(String title, BitInputStream stream, int lookForValue) {
		XYSeriesCollection collection = getCollection(stream, lookForValue);
		JFreeChart chart = ChartFactory.createXYBarChart(title, "Długość ciągu", false, "Liczba wystąpień", collection,
				PlotOrientation.VERTICAL, false, true, false);
		
		ChartPanel panel = new ChartPanel(chart);
		panel.setDomainZoomable(false);
		panel.setRangeZoomable(false);
		return panel;
	}
	
	private XYSeriesCollection getCollection(BitInputStream stream, int lookForValue) {
		XYSeries series = new XYSeries("");
		
		Map<Integer, Integer> values = new HashMap<>();
		int blockLength = 0;
		stream.reset();
		
		while(stream.hasNext()) {
			if(stream.next() == lookForValue)
				blockLength++;
			else {
				int count = values.containsKey(blockLength) ? values.get(blockLength) : 0; 
				values.put(blockLength, count + 1);
				blockLength = 0;
			}
		}
		
		for(int key : values.keySet()) {
			series.add(key, values.get(key));
		}
		
		return new XYSeriesCollection(series);
	}

	private void addPanels() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		JPanel stats = getStatsPanel();
		stats.setPreferredSize(new Dimension(600, 100));
		add(stats, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		zerosBefore.setMinimumSize(new Dimension(300, 200));
		add(zerosBefore, c);
		
		c.gridx = 1;
		zerosAfter.setMinimumSize(new Dimension(300, 200));
		add(zerosAfter, c);
		
		c.gridx = 0;
		c.gridy = 2;
		onesBefore.setMinimumSize(new Dimension(300, 200));
		add(onesBefore, c);
		
		c.gridx = 1;
		onesAfter.setMinimumSize(new Dimension(300, 200));
		onesAfter.setEnabled(false);
		add(onesAfter, c);
	}

	private JPanel getStatsPanel() {
		JPanel stats = new JPanel();
		stats.setLayout(new GridBagLayout());
		stats.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		JLabel BER = new JLabel("Bit Error Rate: " + simulator.getBitErrorRate());
		BER.setFont(new Font("Serif", Font.BOLD, 14));
		stats.add(BER, c);
		
		c.gridy = 1;
		JLabel efficiency = new JLabel("Efficiency: " + simulator.getDataSizeEfficiency());
		efficiency.setFont(new Font("Serif", Font.BOLD, 14));
		stats.add(efficiency, c);
		
		c.gridy = 2;
		JLabel desync = new JLabel("DesyncsCount: " + simulator.getDesyncsCount());
		desync.setFont(new Font("Serif", Font.BOLD, 14));
		stats.add(desync, c);
		
		return stats;
	}
}
