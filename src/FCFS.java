import java.util.*;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    private HashMap<Integer, Integer> hs = new HashMap<Integer, Integer>();
    private ArrayList<Integer> sKey;
    private int allTime = 0;
    private boolean checkFirst = false;
    private double totalWaitTime = 0;

    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
        setArrive();
    }

    private void setArrive(){
        for(int i = 0; i < processes.size(); i++){
            hs.put(processes.get(i).getPsNumber(), processes.get(i).getArriveTime());
        }
        sKey = new ArrayList<>(hs.keySet());

        sKey.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return hs.get(o1).compareTo(hs.get(o2));
            }
        });
    }

    public void run(){
        for(int key : sKey){
            if(!checkFirst){
                processes.get(key).setTime(processes.get(key).getRequiredCpuTime(), 0, 0);
                allTime += processes.get(key).getRequiredCpuTime();
                checkFirst = true;
                continue;
            }
            processes.get(key).setTime(processes.get(key).getRequiredCpuTime(), allTime - hs.get(key), processes.get(key).getRequiredCpuTime());
            allTime += processes.get(key).getRequiredCpuTime();
        }

        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 대기시간 : " + p.getWaitingTime());
            totalWaitTime += p.getWaitingTime();
        }

        System.out.println("총 실행 시간 : " + allTime);
        System.out.println("평균 대기시간 : " + totalWaitTime / processes.size());
        allTime = 0;
        totalWaitTime = 0;
        checkFirst = false;
    }
}
