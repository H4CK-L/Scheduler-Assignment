package Scheduler;
import java.util.*;

public class HRRN {
    private ArrayList<Process> hrrnps = new ArrayList<Process>();
    private HashMap<Integer, Double> hs = new HashMap<>();
    private ArrayList<Integer> keyset;
    private int allTime = 0;
    private double totalwaitingtime = 0;
    private int size = 0;
    private double allBurst = 0;
    private double totalturnaroundtime =0;
    public HRRN(ArrayList<Process> ps) {
        for(Process p : ps) {
            hrrnps.add(new Process(p.getPsNumber(),p.getRequiredCpuTime(), p.getArriveTime()));
            size++;
            allBurst += p.getRequiredCpuTime();
        }
        for (Process p : hrrnps) {
            p.setPriority(1000);
        }
    }
    public boolean setting(){
        for(Process p : hrrnps) {
            if(p.preempt){
                return true;
            }
        }
        return false;
    }
    public void setPriority() {
        if (!hrrnps.isEmpty()) {
            for (int i = 0; i < hrrnps.size(); i++) {
                if (hrrnps.get(i).preempt) {
                    hrrnps.get(i).setWaitingTime(allTime - hrrnps.get(i).getArriveTime());
                    hrrnps.get(i).setResponseRatio();
                    hs.put(hrrnps.get(i).getPsNumber(), hrrnps.get(i).getResponseRatio());
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
            for (int i = 0; i < hrrnps.size(); i++) {
                hrrnps.get(i).setPriority(keyset.indexOf(hrrnps.get(i).getPsNumber()));
            }
        }
    }

    public boolean running() {
        if (!hs.isEmpty()) {
            for (int i = 0; i < hrrnps.size(); i++) {
                if (hrrnps.get(i).getPriority() == 0) {

                    if (hrrnps.get(i).getResponseTime() < 0) {
                        hrrnps.get(i).setResponseTime(allTime);
                    }
                    while (hrrnps.get(i).getRequiredCpuTime() > 0) {
                        hrrnps.get(i).onJob();
                        allTime++;
                    }
                    hrrnps.get(i).setTurnaroundTime(allTime - hrrnps.get(i).getArriveTime());
                    CPU.result.put("hrrnwaitingtime"+hrrnps.get(i).getPsNumber(), hrrnps.get(i).getWaitingTime());
                    CPU.result.put("hrrnturnaroundtime"+hrrnps.get(i).getPsNumber(), hrrnps.get(i).getTurnaroundTime());
                    CPU.result.put("hrrnresponsetime"+hrrnps.get(i).getPsNumber(), hrrnps.get(i).getResponseTime());
                    totalwaitingtime += hrrnps.get(i).getWaitingTime();
                    totalturnaroundtime += hrrnps.get(i).getTurnaroundTime();
                    hrrnps.remove(i);
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
            if (hrrnps.size() == 0) {
                CPU.result.put("hrrnexecutiontime",(double)allTime);
                CPU.result.put("hrrnavgwaitingtime", totalwaitingtime/size);
                CPU.result.put("hrrnavgturnaroundtime", totalturnaroundtime/size);
                CPU.result.put("hrrnthroughput", size/(double)allTime);
                CPU.result.put("hrrnutil", allBurst/allTime*100);
                System.out.println("HRRN종료");
                break;
            }
            for (Process p : hrrnps) {
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