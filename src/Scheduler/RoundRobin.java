package Scheduler;
import java.util.*;

public class RoundRobin {
    private ArrayList<Process> ps = new ArrayList<Process>();
    private int timeSlice;
    private int allTime;
    private ArrayList<Process> readyqueue = new ArrayList<>();
    private ArrayList<Process> jobqueue = new ArrayList<>();
    private double totalwaitingtime=0;
    private int size=0;
    private double allBurst = 0;
    private double totalturnaroundtime =0;
    public RoundRobin(ArrayList<Process> pss ,int timeSlice){
        for(Process p : pss){
            ps.add(new Process(p.getPsNumber(), p.getRequiredCpuTime(),p.getArriveTime()));
            size++;
            allBurst += p.getRequiredCpuTime();

        }
        this.timeSlice = timeSlice;
    }
    public void running(){
        while(true){
            if(ps.isEmpty()){
                break;
            }
            if(readyqueue.isEmpty()){
                allTime++;
                break;
            }
            jobqueue.add(readyqueue.getFirst());
            readyqueue.removeFirst();
            if(jobqueue.getFirst().getResponseTime() == -1){
                jobqueue.getFirst().setResponseTime(allTime);
            }
            for(int j = 0 ; j < timeSlice ; j++){
                if(jobqueue.getFirst().getRequiredCpuTime() == 0){
                    break;
                }
                jobqueue.getFirst().onJob();
                allTime++;
                for(Process p : ps){
                    if(!readyqueue.contains(p) && !jobqueue.contains(p) && p.getArriveTime() <= allTime){
                        readyqueue.add(p);
                    }
                }
            }
            if(jobqueue.getFirst().getRequiredCpuTime() == 0){
                jobqueue.getFirst().setWaitingTime(allTime-jobqueue.getFirst().getArriveTime()-jobqueue.getFirst().getBurstTime());
                jobqueue.getFirst().setTurnaroundTime(allTime - jobqueue.getFirst().getArriveTime());
                CPU.result.put("rrwaitingtime"+jobqueue.getFirst().getPsNumber(), jobqueue.getFirst().getWaitingTime());
                CPU.result.put("rrturnaroundtime"+jobqueue.getFirst().getPsNumber(), jobqueue.getFirst().getTurnaroundTime());
                CPU.result.put("rrresponsetime"+jobqueue.getFirst().getPsNumber(), jobqueue.getFirst().getResponseTime());
                totalwaitingtime += jobqueue.getFirst().getWaitingTime();
                totalturnaroundtime += jobqueue.getFirst().getTurnaroundTime();
                ps.remove(jobqueue.getFirst());
                jobqueue.removeFirst();
            }
            else{
                readyqueue.add(jobqueue.getFirst());
                jobqueue.removeFirst();
            }
        }
    }
    public void run(){
        while(true){
            if(ps.isEmpty()){
                CPU.result.put("rrexecutiontime", (double)allTime);
                CPU.result.put("rravgwaitingtime" , totalwaitingtime/size);
                CPU.result.put("rrthroughput", size/(double)allTime);
                CPU.result.put("rrutil", allBurst/allTime*100);
                CPU.result.put("rravgturnaroundtime", totalturnaroundtime/size);
                break;
            }
            for(Process p : ps){
                if(p.getArriveTime() <= allTime){
                    readyqueue.add(p);
                }
            }
            if(!readyqueue.isEmpty()){
                running();
            }
            else{
                allTime++;
            }

        }


    }
}