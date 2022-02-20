package com.uptc.reports;

import com.uptc.models.Process;
import com.uptc.models.Register;
import com.uptc.models.States;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.uptc.models.States.*;

public class Report {

    public static final ArrayList<Object[]> getReportByExecuteStates = null;
    private final List<Process> processes;
    private final Map<States, List<Register>> registers;
    private final int totalTime;
    private final int timeCPU;
    private final int timeApprox;

    public Report(List<Process> processList, int totalTime, int timeCPU) {
        this.processes = processList;
        this.timeCPU = timeCPU;
        this.totalTime = totalTime;
        this.timeApprox = getFinal();
        this.registers = getTotalRegisters().stream()
                .collect(Collectors.groupingBy(Register::getStatus));
    }

    public ArrayList<Object[]> getReportMissingTimeProcess(){
        ArrayList<Object[]> aux= new ArrayList<>();
        processes.stream()
        .sorted(Comparator.comparing(Process::getName))
        .forEach(x -> aux.add(x.getTableByTime(timeApprox, timeCPU)));
        return aux;
    }

    public ArrayList<Object[]> getReportForStatusChangeProcess() {
        ArrayList<Object[]> aux= new ArrayList<>();
        processes.stream()
                .sorted(Comparator.comparing(Process::getName))
                .forEach(x -> aux.add(x.getTableByState(timeApprox, timeCPU)));
        return aux;
    }

    public ArrayList<Object[]> getReportByReadyStates() {
        ArrayList<Object[]> aux= new ArrayList<>();
            registers.get(READY).stream()
                    .sorted(Comparator.comparingInt(Register::getTimeEnd))
                    .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                    .forEach(System.out::print);
    return aux;
    }

    public ArrayList<Object[]> getReportByLockedStates() {
        ArrayList<Object[]> aux= new ArrayList<>();
        registers.get(LOCKED).stream()
                .sorted(Comparator.comparingInt(Register::getTimeEnd))
                .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                .forEach(System.out::print);
    return aux;
    }

    public ArrayList<Object[]> getReportByDestroyProcess() {
        ArrayList<Object[]> aux= new ArrayList<>();
            registers.get(DESTROY).stream()
                    .sorted(Comparator.comparingInt(Register::getTimeEnd))
                    .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                    .forEach(System.out::print);
    return aux;
    }

    public ArrayList<Object[]> getReportByLayOffProcess() {
        ArrayList<Object[]> aux= new ArrayList<>();
            registers.get(LAYOFF).stream()
                    .sorted(Comparator.comparingInt(Register::getTimeEnd))
                    .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                    .forEach(System.out::print);
    return aux;
    }

    public ArrayList<Object[]> getReportByResumeProcess() {
        ArrayList<Object[]> aux= new ArrayList<>();
            registers.get(RESUME).stream()
                    .sorted(Comparator.comparingInt(Register::getTimeEnd))
                    .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                    .forEach(System.out::print);
    return aux;
    }
    
    public ArrayList<Object[]> getReportByExitState() {
        ArrayList<Object[]> aux= new ArrayList<>();
        registers.get(EXIT).stream()
                .sorted(Comparator.comparingInt(Register::getTimeEnd))
                .map(x -> (aux.add(new String[]{ ""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                .forEach(System.out::print);
    return aux;
    }

    public ArrayList<Object[]> reportByCpuExecuteOrder() {
        ArrayList<Object[]> aux= new ArrayList<>();
        registers.get(EXECUTE).stream()
                .distinct()
                .sorted(Comparator.comparingInt(Register::getTimeInit))
                .map(x -> (aux.add(new String[]{ ""+x.getTimeInit(),""+x.getTimeEnd(),x.getProcess().getName(),""+x.getProcess().getNewPriority()})))
                .forEach(System.out::print);
        return aux;
    }
  
    public String[] headerTable() {
        int size=Math.round(timeApprox/timeCPU)+2;
        int pos=0;
        String[] headers= new String[size];
        headers[pos]="Proceso / Tiempo";
        pos++;
        for (int i = 0; i <= timeApprox; i += timeCPU) {
                headers[pos]=""+i;
                pos++;
        }
       
        return headers;
    }


    private List<Register> getTotalRegisters() {
        List<Register> totalRegisters = new ArrayList<>();
        processes.forEach(x -> totalRegisters.addAll(x.getAllRegisters()));
        return totalRegisters;
    }

    public ArrayList<Object[]> getReportForStatusChange() {
        ArrayList<Object[]> aux= new ArrayList<>();
        getTotalRegisters().stream().distinct()
                .sorted((x, y) -> {
                    if (x.getTimeEnd() == y.getTimeEnd())
                        return x.getProcess().getName().compareTo(y.getProcess().getName());
                    else
                        return x.getTimeEnd() - y.getTimeEnd();
                }).forEach(x -> aux.add(printRegister(x))); 
        return aux;
    }

    private Object[] printRegister(Register x) {
        States state = x.getPreviousState();
        String nameProcess = x.getProcess().getName();
        Object[] exit=new Object[1];
        switch (state) {
            case READY: // despachar
                 exit[0]="despachar("+(nameProcess)+"): listo -> en_ejecucion";
                break;
            case INIT: // nuevo
                 exit[0]="insertar ("+(nameProcess)+"):  nuevo -> listo";
               //  out.println("insertar(" + nameProcess + "): nuevo -> listo");
                break;
            case LOCKED:  // despertar
                 exit[0]="despertar ("+(nameProcess)+"): bloqueado ->  listo";
               // out.println("despertar(" + nameProcess + "): bloqueado-> listo");
                break;
            case EXECUTE: // salio- bloqueado-listos
                exit=evaluateStatus(x, nameProcess);
                break;
        }
        return exit;
    }

    private Object[] evaluateStatus(Register x, String name) {
        Object[] exit=new Object[1];
        switch (x.getStatus()) {
            case READY:
                 exit[0]="tiempo_expirado ("+(name)+"): en_ejecucion -> listo";
              // out.println("tiempo_expirado(" + name + "): en_ejecucion -> listo");
                break;
            case LOCKED:
                exit[0]="bloquear ("+(name)+"): en_ejecucion -> bloqueado";
               // out.println("bloquear(" + name + "): en_ejecucion-> bloqueado");
                break;
            case EXIT:
                exit[0]="finalizar ("+(name)+"): en_ejecucion -> salida";
               // out.println("finalizar(" + name + "): en_ejecucion-> salida");
                break;
        }
        return exit;
    }

    private int getFinal() {
        int aux = totalTime;
        while (aux % timeCPU != 0) aux++;
        return aux;
    }

    public ArrayList<Object[]> getReportByComunicateProcess(List<Process> comunicateProcess) {
            ArrayList<Object[]> aux= new ArrayList<>();
            processes.stream()
            .sorted(Comparator.comparing(Process::getName))
            .filter(x->x.getConnectProcess()!="")
            .map(x -> (aux.add(new String[]{ ""+x.getName(),""+x.getConnectProcess()})))
            .forEach(System.out::print);
            return aux;
    }

}
