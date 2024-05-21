import java.util.*;

public class NEWHRRN {
    private ArrayList<Process> ps;
    private HashMap<Integer, Double> hs =new HashMap<>();
    private ArrayList<Integer> keyset;
    private int[] waitingTimePerProcess;
    private int allTime = 0;
    public HRRN(ArrayList<Process> ps){
        this.ps=ps;
        for(Process p : ps){
            p.setPriority(1000);
        }
        waitingTimePerProcess=new int[ps.size()];
    }

    public void setPriority(){
        if(!ps.isEmpty()){
            for(int i = 0 ; i < ps.size() ; i++){
                if(ps.get(i).preempt){
                    if(ps.get(i).getRequiredCpuTime() == 0){
                        //System.out.println("psNum[" + ps.get(i).getPsNumber() +"] is successed on " + allTime + " ratio : " + ps.get(i).getResponseRatio());
                        ps.get(i).setTurnaroundTime(allTime-ps.get(i).getArriveTime());
                        waitingTimePerProcess[i] = ps.get(i).getWaitingTime();
                        System.out.println("PS["+ps.get(i).getPsNumber()+"] 의 Turnaround Time = " + ps.get(i).getTurnaroundTime() + " Response Time = " + ps.get(i).getResponseTime() + " Waiting Time = " + ps.get(i).getWaitingTime());

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
                this.keyset = new ArrayList<>(hs.keySet());
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

            if(ps.size() == 0) {
                double avg = Arrays.stream(waitingTimePerProcess).average().orElse(0);
                System.out.println("전체 실행 시간 : " + allTime);
                System.out.println("평균 대기 시간 : " + avg );
                break;
            }
            for(Process p : ps) {
                if (p.getArriveTime() <= allTime) {
                    p.preempt = true;
                }
            }
            setPriority();
            running();



        }
    }
}
