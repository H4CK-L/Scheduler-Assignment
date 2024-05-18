import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FCFS {
    private ArrayList<Process> processes = new ArrayList<Process>();
    private HashMap<Integer, Integer> hs = new HashMap<Integer, Integer>();
    private ArrayList<Integer> keySet;
    private int allTime = 0;
    private boolean checkFirst = false;

    public FCFS(ArrayList<Process> processes) {
        this.processes = processes;
        setArrive();
    }

    private void setArrive(){
        for(int i = 0; i < processes.size(); i++){
            hs.put(processes.get(i).getPsNumber(), processes.get(i).getArriveTime());
        }
        keySet = new ArrayList<>(hs.keySet());

        keySet.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return hs.get(o1).compareTo(hs.get(o2));
            }
        });
    }

    public void run(){
        for(int key : keySet){
            if(!checkFirst){
                processes.get(keySet.get(key)).setTime(processes.get(key).getRequiredCpuTime(), 0, 0);
                allTime += processes.get(key).getRequiredCpuTime();
                checkFirst = true;
                continue;
            }
            processes.get(keySet.get(key)).setTime(processes.get(key).getRequiredCpuTime(), allTime - hs.get(key), processes.get(key).getRequiredCpuTime());
            allTime += processes.get(key).getRequiredCpuTime();
        }
        for(int key : keySet){
            System.out.println("프로세스 " + key + " 의 대기시간 : " + processes.get(key).getWaitingTime());
        }
        System.out.println("총 실행 : " + allTime);
        System.out.println("평균 대기시간 : ");
        allTime = 0;
        checkFirst = false;
    }
}
