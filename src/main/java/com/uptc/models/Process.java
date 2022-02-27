package com.uptc.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Process {

    private final String name;
    private int time;
    private final int size;
    private final String asingPartition;

    private final Map<Integer, List<Register>> register;

    public Process(String name, int time, int size, String asingPartition) {
        this.name = name;
        this.time = time;
        this.size = size;
        this.asingPartition= asingPartition;

        this.register = new HashMap<>();
    }

    public void states(int timeI, int timeF, States status, States previous) {
        //this.registers.add(new Register(timeI,timeF,status,this.time,this));
        Register r = new Register(timeI, timeF, status, this.time, this, previous);
        addRegister(timeI, r);
        addRegister(timeF, r);
    }

    private void addRegister(int time, Register status) {
        if (!register.containsKey(time)) register.put(time, new ArrayList<>());
        if (!register.get(time).contains(status)) register.get(time).add(status);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time -= time;
    }

    public String getName() {
        return name;
    }


	public int getsize() {
		return size;
	}


	public String getAsingPartition() {
		return asingPartition;
	}

    public Object[] getTableByState(int totalTime, int timeCPU) {
        int size= Math.round(totalTime/timeCPU)+1;
        Object aux[] = new Object[size];
        int pos=0;
        aux[pos]=name;
        pos++;
        for (int i = 1; i <= totalTime; i += timeCPU) {
            String status = getLastByState(i, timeCPU);
            aux[pos] = status;
            pos++;
        }
       return aux;
    }

    public Object[] getTableByTime(int totalTime, int timeCPU) {
        int size= Math.round(totalTime/timeCPU)+1;
        Object aux[] = new Object[size];
        int pos=0;
        aux[pos]=name;
        pos++;
        for (int i = 1; i <= totalTime; i += timeCPU) {
            int lastTime = getLastByTime(i, timeCPU);
            System.out.println("tiempo faltante"+lastTime);
            aux[pos] = lastTime!=-1 ? lastTime : 0;
            pos++;
        }
        return aux;
    }

    private int getLastByTime(int initKey, int timeCPU) {
        for (int i = initKey; i > initKey - timeCPU; i--) {
            int key = i;
            Optional<Integer> actualTime = register.getOrDefault(key, List.of())
                    .stream().filter(x -> x.timeEnd == key)
                    .map(x -> x.actualTime)
                    .findFirst();
                   // System.out.println("tiempo actual segundo metodo"+actualTime.get());
            if (actualTime.isPresent()){
                System.out.println("existe tiempo faltante"+actualTime.get());
                return actualTime.get();
            } 
        }
        return -1;
    }

    private String getLastByState(int initKey, int timeCPU) {
        StringBuilder status = new StringBuilder();
        for (int i = initKey; i > initKey - timeCPU; i--) {
            register.getOrDefault(i, List.of())
                    .stream().map(x-> x.status.toString())
                    .forEach(status::append);
        }
        System.out.println("estado aca "+status.toString());
        return status.toString();
    }

    public List<Register> getAllRegisters(){
        List<Register> registers = new ArrayList<>();
        register.forEach((k,v)-> registers.addAll(v));
        return registers;
    }
}
