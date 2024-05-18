import java.util.*;

public class SJF {
    private ArrayList<Process> processes = new ArrayList<Process>();
    private HashMap<Integer, Integer> hs = new HashMap<>();
    private ArrayList<Integer> sKey;
    private int allTime = 0;
    private int totalWaitTime = 0;

    public SJF(ArrayList<Process> processes) {
        this.processes = processes;
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
        setArrive();
        Process nextProcess = processes.get(sKey.get(0));
        processes.get(0).setTime(processes.get(0).getRequiredCpuTime(), 0, 0);
        Process checkProcess = null;
        allTime += nextProcess.getRequiredCpuTime();
        int checkIndex = 0;
        sKey.remove(0);
        while(true){
            for(int key : sKey){
                if(hs.get(key) > allTime){
                    break;
                }
                if(checkProcess == null){
                    checkProcess = processes.get(key);
                    checkIndex = sKey.indexOf(key);
                    continue;
                }

                if(checkProcess.getRequiredCpuTime() > processes.get(key).getRequiredCpuTime()){
                    checkProcess = processes.get(key);
                    checkIndex = sKey.indexOf(key);
                    continue;
                }
            }
            checkProcess.setTime(checkProcess.getRequiredCpuTime(), allTime - checkProcess.getArriveTime(), checkProcess.getRequiredCpuTime());
            allTime += checkProcess.getRequiredCpuTime();
            sKey.remove(checkIndex);
            checkIndex = 0;
            checkProcess = null;
            if(sKey.size() == 0){
                break;
            }
        }

        setArrive();
        for(Process p : processes){
            System.out.println("프로세스 " + (p.getPsNumber() + 1) + " 의 대기시간 : " + p.getWaitingTime());
            totalWaitTime += p.getWaitingTime();
        }

        System.out.println("총 실행 시간 : " + allTime);
        System.out.println("평균 대기시간 : " + totalWaitTime / processes.size());
        allTime = 0;
        totalWaitTime = 0;
    }

}
