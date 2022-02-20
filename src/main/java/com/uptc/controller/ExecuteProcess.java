package com.uptc.controller;

import com.uptc.models.Process;
import com.uptc.models.States;
import com.uptc.reports.Report;

import static com.uptc.models.States.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExecuteProcess {

    private final Queue<Process> processes;
    private final List<Process> allProcess;
    private final List<Process> comunicateProcess; //Lista de procesos comunicados

    private int timeProcess;   // cambia --
    private int timeCPU;       // lo que atiende la cpu
    private int totalTime;     // total de atencion de los procesos
    private Report report;

    public ExecuteProcess() {
        this.processes = new LinkedList<>();
        this.allProcess = new LinkedList<>();
        this.comunicateProcess= new LinkedList<>();
        timeProcess = 0;
        totalTime = 0;
    }

    public void addProcessToQueue(Process p) {
        this.allProcess.add(p);
        totalTime += p.getTime();
        p.states(0, 0, READY, INIT);
    }

    	
	public void communicateProcess() {
		for(int i = 0; i < allProcess.size(); i++) {
			if(allProcess.get(i).getConnectProcess() != "No") {
				comunicateProcess.add(allProcess.get(i));
			}
		}
	}

    public void init() {
        this.timeCPU = 5;
        allProcess.sort( new Comparator<Process>() {
			public int compare(Process p1, Process p2) {
				return p1.getNewPriority() - p2.getNewPriority();
			}
		});
        orderProcess();
		communicateProcess();
        while (!processes.isEmpty()) {
            Process p = processes.poll();
            if(p.getIsExecute()){
            attendProcessCPU(p);
            }
        }
    }

    private void orderProcess() {
        for (int i = 0; i < allProcess.size(); i++) {
            this.processes.add(allProcess.get(i));   
        }
    }

    private void attendProcessCPU(Process p) {
        System.out.println("ATENDIENDO PROCESO" + p.getName());
        System.out.println(p.getName()+"tiempo"+p.getTime());
        if (p.getTime() > timeCPU) { // 500 - 100
            p.setTime(timeCPU);
            p.states(timeProcess, timeProcess += timeCPU, EXECUTE, READY);
             if (p.isLocked()) {
                p.states(timeProcess, timeProcess, LOCKED, EXECUTE);
                layOffProcess(p,LOCKED);
                destroyProcess(p,READY,LOCKED);
            } else {
                layOffProcess(p,EXECUTE);
                destroyProcess(p,READY,EXECUTE);
            }
            processes.add(p);
        } else { // 50 100
            int timePi = p.getTime();
            p.setTime(timePi);
            p.states(timeProcess, timeProcess += timePi, EXECUTE, READY);
            if (p.isLocked()) {
                p.states(timeProcess, timeProcess, LOCKED, EXECUTE);
                layOffProcess(p,LOCKED);
                destroyProcess(p,EXIT,EXECUTE);
            } else {
                layOffProcess(p,EXECUTE);
                destroyProcess(p,EXIT,EXECUTE);
            }
            
        }
    }

    private void layOffProcess(Process p,States lastState) {
        if(p.getisLayoff()){
            p.states(timeProcess, timeProcess, LAYOFF, lastState);
            p.states(timeProcess, timeProcess, RESUME, LAYOFF);
        }
    }

    private void destroyProcess(Process p,States actualState, States lastState) {
        if(p.getIsDestroy()){
            p.states(timeProcess, timeProcess, DESTROY, lastState);
            for (int i = 0; i < allProcess.size(); i++) {
                if (allProcess.get(i).getName()==p.getName()) {
                    this.allProcess.remove(i);
                }
            }
        } else {
            p.states(timeProcess, timeProcess, actualState, lastState);
        }
    }

    public void reports() {
        report = new Report(allProcess, totalTime, timeCPU);
    }

    public ArrayList<Object[]> reportMissingTimeProcess(){
        return report.getReportMissingTimeProcess();
    }

    public ArrayList<Object[]> reportStatusChangeProcess() {
        return report.getReportForStatusChangeProcess();
    }

    public ArrayList<Object[]> reportByReadyStates(){
        return report.getReportByReadyStates();
    }

    public ArrayList<Object[]> reportByExitState(){
        return report.getReportByExitState();
    }

    public ArrayList<Object[]> reportByLockedStates(){
        return report.getReportByLockedStates();
    }

    public ArrayList<Object[]> reportForStatusChange(){
        return report.getReportForStatusChange();
    }

    public ArrayList<Object[]> reportByCpuExecuteOrder() {
        return report.reportByCpuExecuteOrder();
    }

    public ArrayList<Object[]> reportDestroyProcess() {
        return report.getReportByDestroyProcess();
    }

    public ArrayList<Object[]> reportLayOffProcess() {
        return report.getReportByLayOffProcess();
    }

    public ArrayList<Object[]> reportResumeProcess() {
        return report.getReportByResumeProcess();
    }

    public String[] reportHeadersTable() {
        return report.headerTable();
    }
    

    public ArrayList<Object[]> reportComunicateProcess() {
        return report.getReportByComunicateProcess(comunicateProcess);
    }
 


 
   
}
