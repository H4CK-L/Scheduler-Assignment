package Scheduler;
import java.util.*;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    private int allTime = 0;
    private int waitTime = 0;
    private int totalWaitTime = 0;
    private int processCount = 0;
    private double allBurst = 0;
    public FCFS(ArrayList<Process> pss) {
        for(Process p : pss){
            processes.add(new Process(p.getPsNumber(), p.getRequiredCpuTime(),p.getArriveTime()));
        }
    }

    public void run(){
        ArrayList<Process> jobQueue = new ArrayList<>(processes);
        Collections.sort(jobQueue, Comparator.comparing(Process::getArriveTime));
        while(true) {
            for (Iterator<Process> it = jobQueue.iterator(); it.hasNext(); ) {
                Process p = it.next();
                if (p.getArriveTime() <= allTime) {
                    readyQueue.add(p);
                    it.remove();
                }
            }
            if(readyQueue.isEmpty()) {
                allTime++;
                continue;
            }

            while (!readyQueue.isEmpty()) {
                if (readyQueue.get(0).getArriveTime() <= allTime) {
                    waitTime = allTime - readyQueue.get(0).getArriveTime();
                    allTime += readyQueue.get(0).getRequiredCpuTime();
                    readyQueue.get(0).setTime(allTime - readyQueue.get(0).getArriveTime(), waitTime, waitTime);
                    processCount++;
                    totalWaitTime += waitTime;
                    readyQueue.remove(0);
                }
            }


            if(jobQueue.size() <= 0) {
                break;
            }
        }

        for(Process p : processes){
            CPU.result.put("fcfswaitingtime"+p.getPsNumber(), p.getWaitingTime());
            CPU.result.put("fcfsresponsetime"+p.getPsNumber(), p.getResponseTime());
            CPU.result.put("fcfsturnaroundtime"+p.getPsNumber(), p.getTurnaroundTime());
            allBurst += p.getRequiredCpuTime();
        }
        CPU.result.put("fcfsexecutiontime",(double)allTime);
        CPU.result.put("fcfsthroughput", processes.size()/(double)allTime);
        CPU.result.put("fcfsutil", allBurst/allTime*100);
        CPU.result.put("fcfsavgwaitingtime", (double)totalWaitTime/ processes.size());

    }
}