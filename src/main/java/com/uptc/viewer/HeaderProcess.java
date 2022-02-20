package com.uptc.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.uptc.controller.Commands;
import com.uptc.controller.ControllerApp;
import java.awt.*;

public class HeaderProcess extends JPanel  {

	private static final long serialVersionUID = 1L;
	
	private JTextField processTime, nameProcess, priorityProcess, nameProcessConection;
	private JLabel tittle, lProcessTime, lNameProcess, lPriorityProcess;
	private JPanel tittlePanel, dataProcess, checkProcess, labelsData; 
	private JButton saveButton;
	private JCheckBox blockedProcess, isRun, isDestroyed, isSuspended, newPriority, isConnect;
	private int numProcess;
	private ActionListener actionListener;

	public HeaderProcess(ActionListener actionListener) {
		super();
		this.tittlePanel = new JPanel();
		new JPanel();
		this.actionListener = actionListener;
		this.dataProcess = new JPanel();
		this.checkProcess = new JPanel();
		this.labelsData = new JPanel();
		this.initComponents(actionListener);
		numProcess=0;
	}
	
	private void initComponents(ActionListener actionListener) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		tittlePanel.setBackground(Constants.COLOR_TITTLE_PANEL);
		tittlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		tittle = new JLabel();
		tittlePanel.add(Utilities.text(tittle, Constants.FONT_TITTLE, Constants.TITTLE_APP, Color.BLACK));
		this.add(tittlePanel);

		labelsData.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		labelsData.setBackground(Constants.COLOR_SET_DATA_PANEL);

		lNameProcess = new JLabel();
		labelsData.add(Utilities.text(lNameProcess, new Font("arial", Font.ITALIC, 15), "Ingresa el nombre del proceso", Color.BLACK));

		lProcessTime = new JLabel();
		labelsData.add(Utilities.text(lProcessTime, new Font("arial", Font.ITALIC, 15), "Ingresa el tiempo que el proceso requiere", Color.BLACK));

		lPriorityProcess = new JLabel();
		labelsData.add(Utilities.text(lPriorityProcess, new Font("arial", Font.ITALIC, 15), "Ingresa la prioridad del proceso", Color.BLACK));
		this.add(labelsData);
		
		dataProcess.setLayout(new FlowLayout(FlowLayout.LEFT, 10,5));
		dataProcess.setBackground(Constants.COLOR_SET_DATA_PANEL);

		checkProcess.setLayout(new FlowLayout(FlowLayout.LEFT));
		checkProcess.setBackground(Constants.COLOR_SET_DATA_PANEL);

		nameProcess = new JTextField();
		dataProcess.add(Utilities.textField(nameProcess, new Font("arial", Font.ITALIC, 15), "		", Color.GRAY, 150, 70));
		
		processTime= new JTextField();
		dataProcess.add(Utilities.textField(processTime, new Font("arial", Font.ITALIC, 15), "		              ", Color.GRAY, 150, 70));
		
		newPriority = new JCheckBox();
		newPriority.addActionListener(actionListener);
		newPriority.setActionCommand(Commands.NEW_PRIORITY.toString());
		newPriority.setText("Agregar una nueva prioridad");
		dataProcess.add((Utilities.checkBox(newPriority, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false)));

		
		
		priorityProcess = new JTextField();
		this.changeStatusJtextfieldPriority();
		dataProcess.add(Utilities.textField(priorityProcess, new Font("arial", Font.ITALIC, 15), "	5	", Color.GRAY, 150, 70));
		
		blockedProcess = new JCheckBox();
		blockedProcess.setText("¿El proceso se bloqueara?");
		blockedProcess.setBackground(Constants.COLOR_SET_DATA_PANEL);
		dataProcess.add(Utilities.checkBox(blockedProcess, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false));
	
		this.add(dataProcess);

		isRun = new JCheckBox();
		isRun.setText("¿El proceso se ejecuta?");
		checkProcess.add(Utilities.checkBox(isRun, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false));
		
		isDestroyed = new JCheckBox();
		isDestroyed.setText("¿El proceso se destruye?");
		checkProcess.add(Utilities.checkBox(isDestroyed, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false));
		
		isSuspended= new JCheckBox();
		isSuspended.setText("¿El proceso se suspende?");
		checkProcess.add(Utilities.checkBox(isSuspended, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false));
		
		isConnect = new JCheckBox();
		isConnect.setText("¿El proceso se conecta?");
		isConnect.addActionListener(actionListener);
		isConnect.setActionCommand(Commands.CONNECT_PROCESS.toString());
		checkProcess.add(Utilities.checkBox(isConnect, new Font("arial", Font.ITALIC, 15), Color.BLACK, Constants.COLOR_SET_DATA_PANEL, false));

		nameProcessConection = new JTextField();
		this.changeStatusJtextfieldConnect();
		checkProcess.add(Utilities.textField(nameProcessConection, new Font("arial", Font.ITALIC, 15), "		", Color.GRAY, 150, 70));
		

		saveButton = new JButton();
		saveButton.addActionListener(actionListener);
		saveButton.setActionCommand(Commands.C_ADD_PROCESS.toString());
		checkProcess.add(Utilities.button(saveButton, new Dimension(100, 30), "AGREGAR"));
		
		this.add(checkProcess);
	}

     public String getNameProcess(){
		 return nameProcess.getText();
	 }

	 public String getProcessTime(){
		return processTime.getText();
	 }

	 public Boolean getBlockedProcess(){
		return blockedProcess.isSelected();
	 }

    public int getId() {
        return numProcess;
    }

	public int incrementId() {
        return numProcess++;
    }

	public String getPriorityProcess() {
		if(priorityProcess.getText()!=""){
			return priorityProcess.getText();
		} else {
			return ""+5;
		}
	}

	public boolean getIsRun() {
		return isRun.isSelected();
	}

	public boolean getIsDestroyed() {
		return isDestroyed.isSelected();
	}

	public boolean getIsSuspended() {
		return isSuspended.isSelected();
	}

	public String getIsConnects() {
	String connect="No";
		if(nameProcessConection.getText()!="		") {
			connect=nameProcessConection.getText();
		}
		return connect;
	}


	public void changeStatusJtextfieldPriority(){
		boolean isEditable = false;
		if(newPriority.isSelected()){
			isEditable = true;
			priorityProcess.setEditable(true);	
		}
		else{
			priorityProcess.setEditable(false);
			isEditable=false;
		}
	}

	public void changeStatusJtextfieldConnect(){
		boolean isEditable = false;
		if(isConnect.isSelected()){
			isEditable = true;
			nameProcessConection.setEditable(true);	
		}
		else{
			nameProcessConection.setEditable(false);
			isEditable=false;
		}
	}

}