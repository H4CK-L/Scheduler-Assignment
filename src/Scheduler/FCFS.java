package Scheduler;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    private int allTime = 0;
    private int waitTime = 0;
    private int totalWaitTime = 0;
    private double totalturnaroundtime = 0;
    private double allBurst = 0;
    private int start = 0;
    private int end = 0;
    private HashMap <Integer, GanttChart> gantths = new HashMap<>();

    public FCFS(ArrayList<Process> pss) {
        for(Process p : pss){
            Random rand = new Random();
            processes.add(new Process(p.getPsNumber(), p.getRequiredCpuTime(),p.getArriveTime()));
            gantths.put(p.getPsNumber(),new GanttChart("p"+(p.getPsNumber()+1), new Color(rand.nextInt(100,256),rand.nextInt(100,256),rand.nextInt(100,256))));
        }
        CPU.Gantts.clear();
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
                    start = allTime;
                    allTime += readyQueue.get(0).getRequiredCpuTime();
                    end = allTime;
                    readyQueue.get(0).setTime(allTime - readyQueue.get(0).getArriveTime(), waitTime, waitTime);
                    totalWaitTime += waitTime;
                    totalturnaroundtime += allTime - readyQueue.get(0).getArriveTime();
                    gantths.get(readyQueue.get(0).getPsNumber()).setStartEnd(start,end);
                    if(end <= allTime){
                        CPU.Gantts.add(gantths.get(readyQueue.get(0).getPsNumber()));
                    }
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
        CPU.result.put("fcfsavgturnaroundtime", totalturnaroundtime/processes.size());
        CPU.result.put("fcfsexecutiontime",(double)allTime);
        CPU.result.put("fcfsthroughput", processes.size()/(double)allTime);
        CPU.result.put("fcfsutil", allBurst/allTime*100);
        CPU.result.put("fcfsavgwaitingtime", (double)totalWaitTime/ processes.size());
        CPU.ganttchart = new GanttChartPanel(CPU.Gantts);

    }
}