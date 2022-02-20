package com.uptc.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class JTableData extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel dtmElements;
	private String[] headers;
	private JTable jtElements;
	private JScrollPane jsTable;
	private ArrayList<Object[]> listProcess;

	public JTableData(String[] headers) {
		this.headers = headers;
		initComponents();
	}

	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.decode("#30373D"));
		dtmElements = new DefaultTableModel();
		dtmElements.setColumnIdentifiers(headers);
		jtElements = new JTable();
		defaulModel();
		jtElements.getTableHeader().setResizingAllowed(false);
		jtElements.getTableHeader().setReorderingAllowed(false);
		jtElements.getTableHeader().setBackground(Constants.DATA_PANEL_HEADERS_TABLE_COLOR);
		jtElements.getTableHeader().setPreferredSize(new Dimension(0, 60));
		jtElements.getTableHeader().setForeground(Color.BLACK);
		jtElements.getTableHeader().setFont(Constants.DATA_PANEL_HEADERS_TABLE_FONT);
		jtElements.setBackground(Color.WHITE);
		jtElements.setFont(Constants.DATA_PANEL_HEADERS_TABLE_FONT);
		jtElements.setFillsViewportHeight(true);
		jtElements.setRowHeight(50);
		jtElements.setBorder(null);
		jtElements.setDefaultRenderer(Object.class, new RenderTable());
		jsTable = new JScrollPane(jtElements, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsTable.setForeground(Color.RED);
		jsTable.setBorder(null);
		jsTable.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.add(jsTable, BorderLayout.PAGE_END);
		this.setBorder(null);

	}

	public void defaulModel(){
		dtmElements.setColumnIdentifiers(headers);
		jtElements.setModel(dtmElements);
		jtElements.getColumn(Constants.PRICIPAL_HEADERS[9]).setCellEditor(new TableCellEditor() {
			
			@Override
			public boolean stopCellEditing() {
				return true;
			}
			
			@Override
			public boolean shouldSelectCell(EventObject arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void removeCellEditorListener(CellEditorListener arg0) {
			}
			
			@Override
			public boolean isCellEditable(EventObject arg0) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Object getCellEditorValue() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void cancelCellEditing() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addCellEditorListener(CellEditorListener arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				// TODO Auto-generated method stub
				return (JButton)value;
			}

		});
	}
	public DefaultTableModel getDtmElements() {
		return dtmElements;
	}

	public void setDtmElements(DefaultTableModel dtmElements) {
		this.dtmElements = dtmElements;
	}

	public void addElementToTable(ArrayList<Object[]> datasList) {
		for (Object[] datasObject : datasList) {
			dtmElements.addRow(datasObject);
		}
	}

	public void addElementUniqueToTable(Object[] datasList, ActionListener actionListener) {
		Object[] row = new Object[] { datasList[0], datasList[1], datasList[2],datasList[3], datasList[4],
		datasList[5], datasList[6], datasList[7], datasList[8],
		createButton(actionListener,String.valueOf(datasList[0]))};
		dtmElements.addRow(row);
	}

	public ArrayList<Object[]> getProcessInformation() {
		ArrayList<Object[]> infoProcess = new ArrayList<>();
		for (int i = 0; i < dtmElements.getRowCount(); i++) {
			Object[] row = new Object[8];
			row[0] = dtmElements.getValueAt(i, 1);
			row[1] = dtmElements.getValueAt(i, 2);
			row[2] = dtmElements.getValueAt(i, 3);
			row[3] = dtmElements.getValueAt(i, 4);
			row[4] = dtmElements.getValueAt(i, 5);
			row[5] = dtmElements.getValueAt(i, 6);
			row[6] = dtmElements.getValueAt(i, 7);
			row[7] = dtmElements.getValueAt(i, 8);
			infoProcess.add(row);	
		}
		return infoProcess;
	}

	public void cleanRowsTable() {
		//dtmElements.setNumRows(0);
		this.remove(jsTable);
	}

	public JButton createButton(ActionListener actionListener,String id) {
		JButton deletteButton = new JButton("Eliminar");
		deletteButton.addActionListener(actionListener);
		deletteButton.setActionCommand(id);
		System.out.println("Accion boton"+deletteButton.getActionCommand());
		deletteButton.setBackground(Color.decode("#DF3A01"));
		deletteButton.setForeground(Color.WHITE);
		deletteButton.setHorizontalAlignment(JLabel.CENTER);
		deletteButton.setVisible(true);
		return deletteButton;
	}

	public void deleteProcess(int id,ActionListener actionListener) {
		listProcess= new ArrayList<>();
		int rowInitial=dtmElements.getRowCount()+1;
		for (int i = 0; i < rowInitial; i++) {
			int idProcess=Integer.parseInt(""+dtmElements.getValueAt(i, 0));
			if(idProcess==id){
				dtmElements.removeRow(i);
		      }
			else {
				Object[] row = new Object[9];
				row[0] = dtmElements.getValueAt(i, 0);
				row[1] = dtmElements.getValueAt(i, 1);
				row[2] = dtmElements.getValueAt(i, 2);
				row[3] = dtmElements.getValueAt(i, 3);
				row[4] = dtmElements.getValueAt(i, 4);
				row[5] = dtmElements.getValueAt(i, 5);
				row[6] = dtmElements.getValueAt(i, 6);
				row[7] = dtmElements.getValueAt(i, 7);
				row[8] = dtmElements.getValueAt(i, 8);
				listProcess.add(row);
			  }  
	        }
		cleanRowsTable();
		
		//loadProcess(actionListener);
	}

	private void loadProcess(ActionListener actionListener) {
		initComponents();
		defaulModel();
		for (int i = 0; i < listProcess.size(); i++) {
			addElementUniqueToTable(listProcess.get(i), actionListener);
		}
	}
}