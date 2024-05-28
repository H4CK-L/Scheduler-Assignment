import java.util.*;

public class HRRN {
    private ArrayList<Process> ps;
    private HashMap<Integer, Double> hs = new HashMap<>();
    private ArrayList<Integer> keyset;
    private int allTime = 0;
    private int[] waitingTimePerProcess;

    public HRRN(ArrayList<Process> ps) {
        this.ps = ps;
        for (Process p : ps) {
            p.setPriority(1000);
        }
        waitingTimePerProcess = new int[ps.size()];
    }
    public boolean setting(){
        for(Process p : ps) {
            if(p.preempt){
                return true;
            }
        }
        return false;
    }
    public void setPriority() {
        if (!ps.isEmpty()) {
            for (int i = 0; i < ps.size(); i++) {
                if (ps.get(i).preempt) {
                    ps.get(i).setWaitingTime(allTime - ps.get(i).getArriveTime());
                    ps.get(i).setResponseRatio();
                    hs.put(ps.get(i).getPsNumber(), ps.get(i).getResponseRatio());
                }
            }
        }
        if (!hs.isEmpty()) {
            this.keyset = new ArrayList<>(hs.keySet());
            keyset.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return hs.get(o2).compareTo(hs.get(o1));
                }
            });
            for (int i = 0; i < ps.size(); i++) {
                ps.get(i).setPriority(keyset.indexOf(ps.get(i).getPsNumber()));
            }
        }
    }

    public boolean running() {
        if (!hs.isEmpty()) {
            for (int i = 0; i < ps.size(); i++) {
                if (ps.get(i).getPriority() == 0) {

                    if (ps.get(i).getResponseTime() < 0) {
                        ps.get(i).setResponseTime(allTime);
                    }
                    while (ps.get(i).getRequiredCpuTime() > 0) {
                        ps.get(i).onJob();
                        allTime++;
                    }
                    ps.get(i).setTurnaroundTime(allTime - ps.get(i).getArriveTime());
                    waitingTimePerProcess[i] = ps.get(i).getWaitingTime();
                    System.out.println("PS[" + (ps.get(i).getPsNumber()+1) + "] 의 Turnaround Time = " + ps.get(i).getTurnaroundTime() + " Response Time = " + ps.get(i).getResponseTime() + " Waiting Time = " + ps.get(i).getWaitingTime());
                    ps.remove(i);
                    i--;
                }
            }
            hs.clear();
            return true;
        }
        return false;
    }


    public void run() {

        while (true) {
            if (ps.size() == 0) {
                System.out.println("HRRN종료");
                break;
            }
            for (Process p : ps) {
                if (p.getArriveTime() <= allTime) {
                    p.preempt = true;
                }
            }
            if(setting()){
                setPriority();
                running();
            }
            else{
                allTime++;
            }
        }

    }
}
