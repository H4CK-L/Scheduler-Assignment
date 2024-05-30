package Scheduler;
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
    public SJF(ArrayList<Process> pss) {
        for(Process p : pss){
            processes.add(new Process(p.getPsNumber(), p.getRequiredCpuTime(),p.getArriveTime()));
        }
        this.totalPs = processes.size();
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
                allTime += readyQueue.peek().getRequiredCpuTime();
                readyQueue.peek().setTime(allTime - readyQueue.peek().getArriveTime(), waitTime, waitTime);
                processCount++;
                totalWaitTime += waitTime;
                readyQueue.poll();
            }
            else{
                allTime++;
            }

            if(processCount == totalPs){
                break;
            }
        }

        System.out.println("============================");
        for(Process p : processes){
            CPU.result.put("sjfwaitingtime"+p.getPsNumber(), p.getWaitingTime());
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Waiting Time : " + p.getWaitingTime());
        }
        System.out.println("============================");
        for(Process p : processes){
            CPU.result.put("sjfturnaroundtime"+p.getPsNumber() , p.getTurnaroundTime());
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Turnaround Time : " + p.getTurnaroundTime());
        }
        System.out.println("============================");
        for(Process p : processes){
            CPU.result.put("sjfresponsetime"+p.getPsNumber(), p.getResponseTime());
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Response Time : " + p.getResponseTime());
        }
        System.out.println("============================");
        System.out.println("Total CPU Burst : " + allTime);
        CPU.result.put("sjfexecutiontime", (double)allTime);
        System.out.println("Average Waiting Time : " + (double)totalWaitTime / processes.size());
        CPU.result.put("sjfavgwaitingtime",(double)totalWaitTime / processes.size());
        System.out.println("============================");
    }
}