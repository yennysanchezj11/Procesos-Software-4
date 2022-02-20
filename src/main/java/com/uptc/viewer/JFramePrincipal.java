package com.uptc.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.uptc.viewer.reports.ReportDialog;

public class JFramePrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jPanelPrincipal;
	private HeaderProcess headerProcess;
	private JTableData centerTable;
	private MenuBarReports menuBarr;



	public JFramePrincipal(ActionListener actionListener, String [] headers) {
		super(Constants.TITTLE_APP);
		this.setSize(1300, 600);
		Image icon = new ImageIcon(Constants.LOGO_APP).getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
		this.setIconImage(icon);
		this.setUndecorated(true);
		this.jPanelPrincipal = new JPanel();
		this.headerProcess = new HeaderProcess(actionListener);
		this.centerTable = new JTableData(Constants.PRICIPAL_HEADERS);
		this.menuBarr = new MenuBarReports(actionListener);
		this.initComponents(actionListener);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowsListenerOption();
		this.setVisible(true);
	}

	private void initComponents(ActionListener actionListener) {
		jPanelPrincipal.setBackground(Color.WHITE);
		jPanelPrincipal.setLayout(new BorderLayout());
		jPanelPrincipal.add(headerProcess, BorderLayout.NORTH);

		jPanelPrincipal.add(centerTable, BorderLayout.CENTER);

		jPanelPrincipal.add(menuBarr, BorderLayout.SOUTH);

		this.add(jPanelPrincipal);
	}

	public void cleanRowsTable() {
		centerTable.cleanRowsTable();
	}

	private void addWindowsListenerOption() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	public void close() {
		if (JOptionPane.showConfirmDialog(this, "¿Desea realmente salir del sistema?", "Salir del sistema",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public ArrayList<Object[]> getInformation() {
		return centerTable.getProcessInformation( );
	}

	public void addElementToTablePrincipalTable(ActionListener actionListener ) {
		centerTable.addElementToTable(getInformation());
	}

	public void setInformationProcessTable(ActionListener actionListener) {
		if(checkName(getInformation())){
		headerProcess.incrementId();
		System.out.println(headerProcess.getPriorityProcess()+"campo prioridad");
		Object[] data ={headerProcess.getId(),headerProcess.getNameProcess(), headerProcess.getProcessTime(),
				headerProcess.getBlockedProcess(),headerProcess.getPriorityProcess(),headerProcess.getIsRun(),
			    headerProcess.getIsDestroyed(),headerProcess.getIsSuspended(),headerProcess.getIsConnects()};
		centerTable.addElementUniqueToTable(data, actionListener);
		} else {
			JOptionPane.showMessageDialog(this, "Nombre de proceso ya existente");
		}
	}

	private boolean checkName(ArrayList<Object[]> information) {
        for (int i = 0; i < information.size(); i++) {
			Object[] data= information.get(i);
			if(data[0].equals(headerProcess.getNameProcess())) {
				return false;
			}
		}
		return true;
	}

	public void reportTableVisibility(boolean visibility,ReportDialog table) {
		table.setVisible(visibility);
	}

    public void deleteProcess(int id,ActionListener actionListener) {
		centerTable.deleteProcess(id,actionListener);
    }

	public void changeStatusJtextfieldPriority(){
		headerProcess.changeStatusJtextfieldPriority();
	}

	public void changeStatusJtextfieldConnect(){
		headerProcess.changeStatusJtextfieldConnect();
	}

}