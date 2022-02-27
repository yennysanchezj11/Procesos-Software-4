package com.uptc.viewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.uptc.controller.Commands;

public class MenuBarReports extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private JMenu report;
	private JMenuItem report2, report3, report4, report5, report10, report11;
	private JButton executeButton, closeApppButton;

	public MenuBarReports(ActionListener actionListener) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.setBackground(Color.WHITE);
		this.initComponents(actionListener);
		this.setVisible(true);
	}
	

	private void initComponents(ActionListener actionListener) {
		
		executeButton = new JButton();
		executeButton.addActionListener(actionListener);
		executeButton.setActionCommand(Commands.C_EXECUTE_PROCESS.toString());
		this.add(Utilities.button(executeButton, new Dimension(100, 30), "Execute"));
		
		report = new JMenu(Constants.TOP_PANEL_MENU_PRINCIPAL_REPORT);
		report.setFont(Constants.FONT_MENUBAR);
		report.setForeground(Color.BLACK);

		report2 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT2);
		report2.setFont(Constants.FONT_MENUBAR);
		report2.addActionListener(actionListener);
		report2.setActionCommand(Commands.C_REPORT_FOR_STATUS_CHANGE_PROCESS.toString());
		report.add(report2);
		report.addSeparator();

		report3 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT3);
		report3.setFont(Constants.FONT_MENUBAR);
		report3.addActionListener(actionListener);
		report3.setActionCommand(Commands.C_REPORT_BY_EXIT_STATE.toString());
		report.add(report3);
		report.addSeparator();

		report4 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT4);
		report4.setFont(Constants.FONT_MENUBAR);
		report4.addActionListener(actionListener);
		report4.setActionCommand(Commands.C_REPORT_BY_READY_STATES.toString());
		report.add(report4);
		report.addSeparator();

		report5 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT5);
		report5.setFont(Constants.FONT_MENUBAR);
		report5.addActionListener(actionListener);
		report5.setActionCommand(Commands.C_REPORT_BY_LOCKED_STATES.toString());
		report.add(report5);
		report.addSeparator();


		report10 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT10);
		report10.setFont(Constants.FONT_MENUBAR);
		report10.addActionListener(actionListener);
		report10.setActionCommand(Commands.C_REPORT_BY_EXECUTE_STATES.toString());
		report.add(report10);
		report.addSeparator();

		report11 = new JMenuItem(Constants.TOP_T_MENUITEM_REPORT11);
		report11.setFont(Constants.FONT_MENUBAR);
		report11.addActionListener(actionListener);
		report11.setActionCommand(Commands.C_REPORT_FOR_STATUS_CHANGE.toString());
		report.add(report11);
		report.addSeparator();
		
		this.add(report);
		
		closeApppButton = new JButton();
		closeApppButton.addActionListener(actionListener);
		closeApppButton.setActionCommand(Commands.C_CLOSE_APP.toString());
		this.add(Utilities.button(closeApppButton, new Dimension(100, 30), "Close"));
	}
}