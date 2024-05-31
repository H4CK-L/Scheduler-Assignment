package Scheduler;
import java.awt.*;
import java.util.*;

public class SJF{

    Comparator<Process> SJFComparator = new Comparator<Process>() {
        @Override
        public int compare(Process p1, Process p2) {
            return Integer.compare(p1.getRequiredCpuTime(), p2.getRequiredCpuTime());
        }
    };

    private ArrayList<Process> processes = new ArrayList<Process>();
    private PriorityQueue<Process> readyQueue = new PriorityQueue<>(SJFComparator);
    private int totalPs = 0;
    private int allTime = 0;
    private int waitTime = 0;
    private int processCount = 0;
    private int totalWaitTime = 0;
    private double allBurst=0;
    private double totalturnaroundtime =0;
    private int start = 0;
    private int end = 0;
    private HashMap <Integer, GanttChart> gantths = new HashMap<>();

    public SJF(ArrayList<Process> pss) {
        for(Process p : pss){
            Random rand = new Random();
            processes.add(new Process(p.getPsNumber(), p.getRequiredCpuTime(),p.getArriveTime()));
            gantths.put(p.getPsNumber(),new GanttChart("p"+(p.getPsNumber()+1), new Color(rand.nextInt(100,256),rand.nextInt(100,256),rand.nextInt(100,256))));
        }
        this.totalPs = processes.size();
        CPU.Gantts.clear();

    }

    public void run(){
        ArrayList <Process> jobQueue = new ArrayList<Process>(processes);
        while(true){
            for(Iterator<Process> it = jobQueue.iterator(); it.hasNext();){
                Process p = it.next();
                if(p.getArriveTime() <= allTime){
                    readyQueue.add(p);
                    it.remove();
                }
            }

            if(!readyQueue.isEmpty()){
                waitTime = allTime - readyQueue.peek().getArriveTime();
                start = allTime;
                allTime += readyQueue.peek().getRequiredCpuTime();
                end = allTime;
                readyQueue.peek().setTime(allTime - readyQueue.peek().getArriveTime(), waitTime, waitTime);
                processCount++;
                totalturnaroundtime += allTime - readyQueue.peek().getArriveTime();
                totalWaitTime += waitTime;
                gantths.get(readyQueue.peek().getPsNumber()).setStartEnd(start,end);
                if(end <= allTime){
                    CPU.Gantts.add(gantths.get(readyQueue.peek().getPsNumber()));
                }
                readyQueue.poll();
            }
            else{
                allTime++;
            }

            if(processCount == totalPs){
                break;
            }
        }

        for(Process p : processes){
            CPU.result.put("sjfwaitingtime"+p.getPsNumber(), p.getWaitingTime());
            CPU.result.put("sjfturnaroundtime"+p.getPsNumber() , p.getTurnaroundTime());
            CPU.result.put("sjfresponsetime"+p.getPsNumber(), p.getResponseTime());
            allBurst += p.getRequiredCpuTime();
        }
        CPU.result.put("sjfavgturnaroundtime", totalturnaroundtime/ processes.size());
        CPU.result.put("sjfexecutiontime", (double)allTime);
        CPU.result.put("sjfthroughput", processes.size()/(double)allTime);
        CPU.result.put("sjfutil", allBurst/allTime*100);
        CPU.result.put("sjfavgwaitingtime",(double)totalWaitTime / processes.size());
        CPU.ganttchart = new GanttChartPanel(CPU.Gantts);
    }
}