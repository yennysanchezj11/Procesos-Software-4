package com.uptc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.uptc.models.Process;
import com.uptc.reports.Report;
import com.uptc.viewer.Constants;
import com.uptc.viewer.JFramePrincipal;
import com.uptc.viewer.reports.ReportDialog;

public class ControllerApp implements ActionListener {
	ExecuteProcess executeProcess;
	JFramePrincipal jPrincipal;
	Report reportClass;
	String [] headersReports;
	ReportDialog reportTable;

	public ControllerApp() {
		executeProcess = new ExecuteProcess();
		jPrincipal = new JFramePrincipal(this,  headersReports);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try{
		switch (Commands.valueOf(e.getActionCommand())) {
		case C_ADD_PROCESS:
			// agregar proceso a la tabla de procesos
			addProcessTable(this);
			break;
		case C_EXECUTE_PROCESS:
			// Ejecutar lista de procesos
			System.out.println("ENTRO A EJECUTAR");
			executeProcess();
			break;

		case C_CLOSE_APP:
			// Cerrar la app
			jPrincipal.close();
			break;

		case C_REPORT_FOR_STATUS_CHANGE_PROCESS:
			// reporte por cambio de estado de los proceso
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT2);
			reportTable.assignHeaders(this, getHeadersTable(),Constants.TOP_T_MENUITEM_REPORT2);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportStatusChangeProcess());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_REPORT_BY_EXIT_STATE:
			// reporte por orden en el estado en salida
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT3);
			reportTable.assignHeaders(this,Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT3);
			reportTable.cleanRowsTable();
			System.out.println("controler"+Constants.headersEstados[0]);
			reportTable.addElementToTable(reportByExitState());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_REPORT_BY_READY_STATES:
			// reporte por orden en el estado en listo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT4);
			reportTable.assignHeaders(this, Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT4);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByReadyStates());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

			case C_REPORT_BY_LOCKED_STATES:
			// reporte por orden en el estado en bloqueo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT5);
			reportTable.assignHeaders(this, Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT5);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByLockedStates());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;
		
			case C_REPORT_BY_DESTROY_PROCESS:
			// reporte por orden en el estado en bloqueo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT6);
			reportTable.assignHeaders(this, Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT6);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByDestroyProcess());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

			case C_REPORT_BY_LAYOFF_PROCESS:
			// reporte por orden en el estado en bloqueo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT7);
			reportTable.assignHeaders(this, Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT7);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByLayOffProcess());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;
		
			case C_REPORT_BY_RESUME_PROCESS:
			// reporte por orden en el estado en bloqueo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT8);
			reportTable.assignHeaders(this, Constants.headersEstados,Constants.TOP_T_MENUITEM_REPORT8);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByResumeProcess());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_REPORT_BY_CONNECT_PROCESS:
			// reporte por orden en el estado en bloqueo
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT9);
			reportTable.assignHeaders(this, Constants.headersR8,Constants.TOP_T_MENUITEM_REPORT9);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByComunicateProcess());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_REPORT_BY_EXECUTE_STATES:
			// reporte por orden en el estado de en ejecución
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT10);
			reportTable.assignHeaders(this,Constants.headersR6,Constants.TOP_T_MENUITEM_REPORT10);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportByCpuExecuteOrder());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_REPORT_FOR_STATUS_CHANGE:
			// reporte por cambios de estado de cada proceso
			reportTable= new ReportDialog(jPrincipal,Constants.TOP_T_MENUITEM_REPORT11);
			reportTable.assignHeaders(this,Constants.headersR7,Constants.TOP_T_MENUITEM_REPORT11);
			reportTable.cleanRowsTable();
			reportTable.addElementToTable(reportForStatusChange());
			jPrincipal.reportTableVisibility(true,reportTable);
			break;

		case C_CLOSE_DIALOG:
			reportTable.setVisible(false);
			break;

		case NEW_PRIORITY:
			jPrincipal.changeStatusJtextfieldPriority();
			break;

		case CONNECT_PROCESS:
			jPrincipal.changeStatusJtextfieldConnect();
			break;

		default:

			break;
		}
	}catch (Exception ex) {
		deleteProcess(Integer.valueOf(e.getActionCommand()));
	}
	}



	public void addProcessTable(ActionListener actionListener) {
		jPrincipal.setInformationProcessTable(actionListener);
	}

	public void executeProcess() {
		// set lista de procesos
		executeListProcess(jPrincipal.getInformation());

	}

	public void executeListProcess(ArrayList<Object[]> listProcess) {
		for (int i = 0; i < listProcess.size(); i++) {
			Object[] vector = (Object[]) listProcess.get(i);
			Process temp=new Process("" + vector[0], Integer.parseInt("" + vector[1]),
			Boolean.parseBoolean("" + vector[2]),Integer.parseInt("" + vector[3]),Boolean.parseBoolean("" + vector[4]),
			Boolean.parseBoolean("" + vector[5]),Boolean.parseBoolean("" + vector[6]),
			("" + vector[7]));
			executeProcess.addProcessToQueue(temp);
		}
		executeProcess.init();
		executeProcess.reports();
		JOptionPane.showMessageDialog(null, "Ejecucion realizada correctamente");
	}


	public String[] getHeadersTable() {
		return executeProcess.reportHeadersTable();
	}

	public ArrayList<Object[]> reportMissingTimeProcess() {
		return executeProcess.reportMissingTimeProcess();
	}

	public ArrayList<Object[]> reportStatusChangeProcess() {
		return executeProcess.reportStatusChangeProcess();
	}

	private ArrayList<Object[]> reportByDestroyProcess() {
		return executeProcess.reportDestroyProcess();
	}


	private ArrayList<Object[]> reportByLayOffProcess() {
		return executeProcess.reportLayOffProcess();
	}

	private ArrayList<Object[]> reportByResumeProcess() {
		return executeProcess.reportResumeProcess();
	}

	public ArrayList<Object[]> reportByCpuExecuteOrder() {
		return executeProcess.reportByCpuExecuteOrder();
	}

	public ArrayList<Object[]> reportByReadyStates() {
		return executeProcess.reportByReadyStates();
	}

	public ArrayList<Object[]> reportByLockedStates() {
		return executeProcess.reportByLockedStates();
	}

	public ArrayList<Object[]> reportByExitState() {
		return executeProcess.reportByExitState();
	}

	private ArrayList<Object[]> reportByComunicateProcess() {
		return executeProcess.reportComunicateProcess();
	}


	public ArrayList<Object[]> reportForStatusChange() {
		return executeProcess.reportForStatusChange();
	}

	public void deleteProcess(int id) {
		if(JOptionPane.showConfirmDialog(jPrincipal, "¿Seguro que desea borrar el proceso con Id: " + id +"?",
				"Pregunta", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			jPrincipal.deleteProcess(id,this);
		}
	}
}
