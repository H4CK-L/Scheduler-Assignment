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

            while (!readyQueue.isEmpty()) {
                if (readyQueue.get(0).getArriveTime() <= allTime) {
                    System.out.println("rq 0 : arr" + readyQueue.get(0).getArriveTime() + "alltime : " + allTime);
                    waitTime = allTime - readyQueue.get(0).getArriveTime();
                    allTime += readyQueue.get(0).getRequiredCpuTime();
                    readyQueue.get(0).setTime(allTime - readyQueue.get(0).getArriveTime(), waitTime, waitTime);
                    processCount++;
                    totalWaitTime += waitTime;
                    readyQueue.remove(0);
                } else {
                    allTime++;
                }
            }

            if(jobQueue.size() <= 0) {
                break;
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
