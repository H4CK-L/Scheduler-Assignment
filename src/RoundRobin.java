import java.util.*;

public class RoundRobin {
    private ArrayList<Process> ps;
    private int timeSlice;
    private int allTime;
    private ArrayList<Process> readyqueue = new ArrayList<>();
    private ArrayList<Process> jobqueue = new ArrayList<>();
    public RoundRobin(ArrayList<Process> ps){
        this.ps = ps;
    }

    public void setTimeSlice(int timeSlice){
        this.timeSlice = timeSlice;
    }
    public void running(){
        while(true){
            if(ps.isEmpty()){
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
                System.out.println("PS["+jobqueue.getFirst().getPsNumber()+"] 의 turnarountime : "+ jobqueue.getFirst().getTurnaroundTime()+ " waitingtime : "+ jobqueue.getFirst().getWaitingTime());
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
                System.out.println("Round Robin 종료");
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
