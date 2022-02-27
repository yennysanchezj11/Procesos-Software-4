package com.uptc.controller;

import com.uptc.models.Partition;
import com.uptc.models.Process;
import com.uptc.reports.Report;

import static com.uptc.models.States.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExecuteProcess {

    private final Queue<Process> processes;
    private final List<Process> allProcess;
    private final List<Partition> allPartition;


    private int timeProcess;   // cambia --
    private int timeCPU;       // lo que atiende la cpu
    private int totalTime;     // total de atencion de los procesos
    private Report report;

    public ExecuteProcess() {
        this.processes = new LinkedList<>();
        this.allProcess = new LinkedList<>();
        this.allPartition = new LinkedList<>();
        timeProcess = 0;
        totalTime = 0;
    }


    public void addPartitionToList(Partition p) {
        this.allPartition.add(p);
    }

    public void addProcessToQueue(Process p) {
        this.processes.add(p);
        this.allProcess.add(p);
        totalTime += p.getTime();
        p.states(0, 0, READY, INIT);
    }

    	
    public void init() {
        this.timeCPU = 5;
        while (!processes.isEmpty()) {
            Process p = processes.poll();
            Partition pAux=getPartition(p.getAsingPartition());
            if(pAux.getListProcess().size()==0){
                pAux.addProcesses(p);
                    if(pAux.getSize()>= p.getsize()){
                    attendProcessCPU(pAux);
                    }
            } else {
            attendProcessCPU(pAux);
            }  
        }
    }

    private Partition getPartition(String asingPartition) {
        for (Partition partitionIter : allPartition) {
            if(partitionIter.getName()==asingPartition){
                return partitionIter;
            }
        }
        return null;
    }


    private void attendProcessCPU(Partition pAux) {
        Process p=pAux.getListProcess().peek();
        System.out.println("ATENDIENDO PROCESO" + p.getName());
        System.out.println(p.getName()+"tiempo"+p.getTime());
        if (p.getTime() > timeCPU) { // 500 - 100
            p.setTime(timeCPU);
            p.states(timeProcess, timeProcess += timeCPU, EXECUTE, READY);
            p.states(timeProcess, timeProcess += timeCPU, READY, EXECUTE);
        } else { // 50 100
            int timePi = p.getTime();
            p.setTime(timePi);
            p.states(timeProcess, timeProcess += timePi, EXECUTE, READY);
            p.states(timeProcess, timeProcess, EXIT, EXECUTE);
            pAux.getListProcess().poll();
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
    
   
}
