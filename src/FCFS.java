import java.util.*;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    ArrayList<Process> readyQueue = new ArrayList<>();
    private int allTime = 0;
    private int waitTime = 0;
    private int totalWaitTime = 0;

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

        /*while(!readyQueue.isEmpty()){

        }*/






















        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 대기시간 : " + p.getWaitingTime());
            totalWaitTime += p.getWaitingTime();
        }
        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 실행시간 : " + p.getRequiredCpuTime());
        }
        System.out.println("============================");
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 응답시간 : " + p.getResponseTime());
        }
        System.out.println("============================");
        System.out.println("총 실행 시간 : " + allTime);
        System.out.println("평균 대기시간 : " + totalWaitTime / processes.size());
        System.out.println("============================");
    }
}
