package com.uptc.models;

import java.util.Queue;

public class Partition {

    private final String name;
    private final int size;
    private Queue<Process> processes;


    public Partition(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public void addProcesses(Process p){
      processes.add(p);
    }

    public Queue<Process> getListProcess(){
        return processes;
    }

    public String getName() {
        return name;
    }


	public int getSize() {
		return size;
	}

	public Object[] toObject(){
		return new Object[]{getName(), getSize()};
	}
}
