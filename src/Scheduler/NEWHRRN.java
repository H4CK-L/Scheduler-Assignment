package Scheduler;
import java.util.*;

public class NEWHRRN {
    private ArrayList<Process> ps = new ArrayList<Process>();
    private HashMap<Integer, Double> hs =new HashMap<>();
    private ArrayList<Integer> keyset = new ArrayList<>();
    private int allTime = 0;
    private int size = 0;
    private double allBurst = 0;
    private double totalwaitingtime = 0;
    private double totalturnaroundtime = 0;
    public NEWHRRN(ArrayList<Process> pss){
        for(Process p : pss){
            ps.add(new Process(p.getPsNumber(),p.getRequiredCpuTime(), p.getArriveTime()));
            size++;
            p.setPriority(1000);
            allBurst += p.getRequiredCpuTime();
        }
    }

    public void setPriority(){
        if(!ps.isEmpty()){
            for(int i = 0 ; i < ps.size() ; i++){
                if(ps.get(i).preempt){
                    if(ps.get(i).getRequiredCpuTime() == 0){
                        ps.get(i).setTurnaroundTime(allTime-ps.get(i).getArriveTime());
                        totalwaitingtime += ps.get(i).getWaitingTime();
                        totalturnaroundtime += ps.get(i).getTurnaroundTime();
                        CPU.result.put("newwaitingtime"+ps.get(i).getPsNumber(),ps.get(i).getWaitingTime());
                        CPU.result.put("newturnaroundtime"+ps.get(i).getPsNumber(),ps.get(i).getTurnaroundTime());
                        CPU.result.put("newresponsetime"+ps.get(i).getPsNumber(),ps.get(i).getResponseTime());
                        ps.remove(i);
                        i--;
                    }
                    else {
                        ps.get(i).setResponseRatio();
                        hs.put(ps.get(i).getPsNumber(), ps.get(i).getResponseRatio());
                    }
                }
            }
            if(!hs.isEmpty()) {
                keyset = new ArrayList<>(hs.keySet());
                keyset.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return hs.get(o2).compareTo(hs.get(o1));
                    }
                });
                for(int i = 0 ; i < ps.size() ; i++){
                    ps.get(i).setPriority(keyset.indexOf(ps.get(i).getPsNumber()));

                }
            }
        }

    }

    public void running(){

        if(!keyset.isEmpty()){
            for (int i = 0 ; i < ps.size() ; i++) {
                if(ps.get(i).getPriority() != 0 && ps.get(i).preempt){
                    ps.get(i).increaseTime();
                    hs.remove(ps.get(i).getPsNumber());
                }
                else if (ps.get(i).preempt && ps.get(i).getPriority() == 0){
                    if(ps.get(i).getResponseTime() < 0){
                        ps.get(i).setResponseTime(allTime);
                    }
                    ps.get(i).onJob();
                    hs.remove(ps.get(i).getPsNumber());
                }

            }
        }
        allTime++;
    }
    //arrived time 0이 안왔을때 종료가 안되게 하는 법
    public void run(){
        while(true){
            for(Process p : ps) {
                if (p.getArriveTime() <= allTime) {
                    p.preempt = true;
                }
            }
            setPriority();
            running();
            if(ps.size() == 0) {
                allTime -= 1;
                CPU.result.put("newexecutiontime",(double)allTime);
                CPU.result.put("newavgwaitingtime", totalwaitingtime/size);
                CPU.result.put("newavgturnaroundtime", totalturnaroundtime/size);
                CPU.result.put("newthroughput", size/(double)allTime);
                CPU.result.put("newutil", allBurst/allTime*100);
                break;
            }




        }
    }
}