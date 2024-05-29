import java.util.*;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    private int allTime = 0;
    private int waitTime = 0;
    private int totalWaitTime = 0;
    private int processCount = 0;

    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public void run(){
        Process ps;
        for(int i = 0; i < processes.size(); i++){
            ps = processes.get(i);
            for(int j = i + 1; j < processes.size(); j++){
                if(ps.getArriveTime() > processes.get(j).getArriveTime()){
                    ps = processes.get(j);
                }
            }
            readyQueue.add(ps);
        }

        while(!readyQueue.isEmpty()){
            if(readyQueue.get(0).getArriveTime() <= allTime){
                waitTime = allTime - readyQueue.get(0).getArriveTime();
                allTime += readyQueue.get(0).getRequiredCpuTime();
                readyQueue.get(0).setTime(allTime - readyQueue.get(0).getArriveTime(), waitTime, waitTime);
                processCount++;
                totalWaitTime += waitTime;
                readyQueue.remove(0);
            }
            else{
                allTime++;
            }
        }

        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Waiting Time : " + p.getWaitingTime());
        }
        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Turnaround Time : " + p.getTurnaroundTime());
        }
        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 Response Time : " + p.getResponseTime());
        }
        System.out.println("============================");
        System.out.println("Total CPU Burst : " + allTime);
        System.out.println("Average Waiting Time : " + totalWaitTime / processes.size());
        System.out.println("============================");
    }
}
