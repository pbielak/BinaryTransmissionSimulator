package pwr.bts.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import pwr.bts.ComplexResultsModule;
import pwr.bts.Simulator;

public class MainWindow extends JFrame{
	
	private Simulator simulator;
	private JMenuItem fileOpenItem;
	private JMenuItem startSimulationItem;
	private JMenuItem restartSimulationItem;
	private JMenuItem stepSimulationItem;
	private OptionList encoders, channel, decoders;
	
	public MainWindow() {
		initFrame();
		addMenu();
		addComponents();
		setInitialStateOfElements();
		
		simulator = new Simulator();
		simulator.setTransmitter(encoders.getContainer());
		simulator.setChannel(channel.getContainer());
		simulator.setReceiver(decoders.getContainer());
	}

	private void setInitialStateOfElements() {
		fileOpenItem.setEnabled(true);
		startSimulationItem.setEnabled(false);
		restartSimulationItem.setEnabled(false);
		stepSimulationItem.setEnabled(false);
	}

	private void addComponents() {
		GridBagConstraints c = new GridBagConstraints();
				
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		add(encoders = new OptionList("Encoders List"), c);
		
		c.gridx = 1;
		c.gridy = 1;
		add(channel = new OptionList("Channel List"), c);
		
		c.gridx = 2;
		c.gridy = 1;
		add(decoders = new OptionList("Decoders List"), c);
		
	}
	
	private void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileOpenItem = new JMenuItem("Open");
		fileOpenItem.setIcon(UIManager.getIcon("FileView.fileIcon"));
		fileOpenItem.addActionListener(e -> openFile());
		fileMenu.add(fileOpenItem);

		JMenuItem resultsExportItem = new JMenuItem("Export results");
		resultsExportItem.addActionListener(e -> exportResults());
		fileMenu.add(resultsExportItem);

		JMenuItem closeItem = new JMenuItem("Exit");
		closeItem.addActionListener(e -> System.exit(0));
		fileMenu.add(closeItem);
		
		JMenu simulationMenu = new JMenu("Simulation");
		menuBar.add(simulationMenu);
		
		startSimulationItem = new JMenuItem("Start");
		startSimulationItem.addActionListener(e -> startSimulation());
		simulationMenu.add(startSimulationItem);
		
		stepSimulationItem = new JMenuItem("Step simulation");
		stepSimulationItem.addActionListener(e -> stepSimulation());
		simulationMenu.add(stepSimulationItem);
		
		restartSimulationItem = new JMenuItem("Restart simulator");
		restartSimulationItem.addActionListener(e -> restartSimulation());
		simulationMenu.add(restartSimulationItem);
		
		setJMenuBar(menuBar);
	}

	private void initFrame() {
		setTitle("NiDUC Scrambling");
		setSize(1200, 400);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void openFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(this);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
		
			try {
				simulator.readFile(file);
				
				fileOpenItem.setEnabled(false);
				startSimulationItem.setEnabled(true);
				stepSimulationItem.setEnabled(true);
				restartSimulationItem.setEnabled(true);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, "Blad przy wczytywaniu pliku");
			}
		}
	}
	
	private void exportResults() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showSaveDialog(this);
		
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			
			try {
				new ComplexResultsModule().generateResults(file);
				JOptionPane.showMessageDialog(this, "Wyniki pomyslnie wyeksportowane");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Blad przy eksportowaniu wynikow");
			}
		}
	}
	
	private void startSimulation() {
		String desyncBitCount = JOptionPane.showInputDialog("Podaj liczbę takich samych kolejno występujących bitów powodujących desynchronizację: ");
		
		if(desyncBitCount != null && !desyncBitCount.isEmpty()) {
			simulator.simulate(Integer.parseInt(desyncBitCount));
			
			ResultWindow window = new ResultWindow(simulator);
			window.setVisible(true);
		}
	}

	private void stepSimulation() {
		simulator.simulate();
		StepSimulationWindow window = new StepSimulationWindow(simulator);
		window.setVisible(true);
	}
	
	private void restartSimulation() {
		encoders.restart();
		channel.restart();
		decoders.restart();
		simulator.reset();
		setInitialStateOfElements();
	}
}
