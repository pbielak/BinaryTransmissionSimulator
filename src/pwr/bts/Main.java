package pwr.bts;

import java.awt.Dimension;

import javax.swing.UIManager;

import pwr.bts.gui.MainWindow;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("OptionPane.minimumSize",new Dimension(300, 225)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new MainWindow().setVisible(true);
	}
}
