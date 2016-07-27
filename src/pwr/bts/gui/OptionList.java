package pwr.bts.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import pwr.bts.processor.BitStreamProcessorContainer;

public class OptionList extends JPanel{
	private JList<BitProcessorWrapper> list;
	private DefaultListModel<BitProcessorWrapper> model;
	private JButton addButton;
	private JButton deleteButton;
	private JButton upButton;
	private JButton downButton;
	
	public OptionList(String title) {
		setBorder(BorderFactory.createTitledBorder(title));
		setLayout(new GridBagLayout());
		
		model = new DefaultListModel<>();
		
		list = new JList<>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setPreferredSize(new Dimension(350, 200));
		addListToPanel();
		
		addButton = new JButton("+");
		deleteButton = new JButton("-");
		upButton = new JButton("\u2191");
		downButton = new JButton("\u2193");
		addButtonsToPanel();
		addListeners();
		setInitialStateOfElements();
	}

	private void setInitialStateOfElements() {
		deleteButton.setEnabled(false);
		upButton.setEnabled(false);
		downButton.setEnabled(false);
	}

	private void addListeners() {
		upButton.addActionListener(e -> moveElementUp());
		downButton.addActionListener(e -> moveElementDown());
		addButton.addActionListener(e -> addElement());
		deleteButton.addActionListener(e -> deleteElement());
		list.addListSelectionListener(e -> handleListSelection());
	}

	private void handleListSelection() {
		int selectedIndex = list.getSelectedIndex();
		if(selectedIndex != -1) {
			deleteButton.setEnabled(true);
			upButton.setEnabled(true);
			downButton.setEnabled(true);
		}
	}

	private void addButtonsToPanel() {
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		add(addButton, c);
		
		c.gridx = 1;
		add(deleteButton, c);
		
		c.gridx = 2;
		add(upButton, c);
		
		c.gridx = 3;
		add(downButton, c);
	}

	private void addListToPanel() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(list, c);
	}
	
	private void moveElementUp() {
		int selectedIndex = list.getSelectedIndex();
		
		if(selectedIndex != -1 && selectedIndex > 0) {
			moveElements(selectedIndex, selectedIndex - 1);
		}
		
		list.clearSelection();
		setInitialStateOfElements();
	}
	
	private void moveElementDown() {
		int selectedIndex = list.getSelectedIndex();
		
		if(selectedIndex != -1 && selectedIndex < model.getSize() - 1) {
			moveElements(selectedIndex, selectedIndex + 1);
		}
		
		list.clearSelection();
		setInitialStateOfElements();
	}
	
	private void addElement() {
		AvailableElementsDialog dialog = new AvailableElementsDialog();
		int returnValue = dialog.showDialog();
		
		if(returnValue == JOptionPane.OK_OPTION) {
			model.addElement(dialog.getSelectedValue());
		}
		
		list.clearSelection();
		setInitialStateOfElements();
	}
	
	private void deleteElement() {
		int selectedIndex = list.getSelectedIndex();
		
		if(selectedIndex != -1) {
			model.remove(selectedIndex);
			list.updateUI();
		}
		
		list.clearSelection();
		setInitialStateOfElements();
	}

	private void moveElements(int firstPosition, int secondPosition) {
		BitProcessorWrapper tmp = model.get(firstPosition);
		model.set(firstPosition, model.get(secondPosition));
		model.set(secondPosition, tmp);
		
		list.setSelectedIndex(secondPosition);
		list.updateUI();
	}
	
	public BitStreamProcessorContainer getContainer() {
		return new BitStreamProcessorContainer(model);
	}
	
	public void restart() {
		list.removeAll();
		model.removeAllElements();
		list.updateUI();
	}
}
