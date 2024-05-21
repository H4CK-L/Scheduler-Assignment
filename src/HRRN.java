import java.util.*;

public class HRRN {
    private ArrayList<Process> ps;
    private HashMap<Double, Integer> hs =new HashMap<>();
    private ArrayList<Double> keyset;
    private int allTime = 0;
    public HRRN(ArrayList<Process> ps){
        this.ps=ps;
    }

    public void setPriority(){
        if(!ps.isEmpty()){
            for(int i = 0 ; i < ps.size() ; i++){
                if(ps.get(i).preempt){
                    if(ps.get(i).getRequiredCpuTime() == 0){
                        hs.remove(ps.get(i).getResponseRatio());
                        ps.remove(i);
                        i--;
                    }
                    else {
                        ps.get(i).setResponseRatio();
                        hs.put(ps.get(i).getResponseRatio(), ps.get(i).getPsNumber());
                    }
                }
            }
            if(!hs.isEmpty()) {
                keyset = new ArrayList<>(hs.keySet());
                keyset.sort((o1, o2) -> o2.compareTo(o1));
                int pri = 0;
                for (Double key : keyset) {
                    ps.get(hs.get(key)).setPriority(pri++);
                }
            }
        }

    }

    public void running(){
        for (Double key : keyset) {
            System.out.println("key :" + key + "value :" + hs.get(key) );
            if(!ps.get(hs.get(key)).preempt){
                ps.get(hs.get(key)).increaseTime();
                hs.remove(key);
            }
            else{
                ps.get(hs.get(key)).onJob();
                hs.remove(key);
            }

        }

        allTime++;
    }
//arrived time 0이 안왔을때 종료가 안되게 하는 법
    public void run(){
        while(true){

            if(ps.size() == 0) {
                System.out.println("HRRN종료");
                break;
            }
            for(Process p : ps) {
                if (p.getArriveTime() <= allTime) {
                    p.preempt = true;
                }
                if (p.getPriority() != 0) {
                    p.preempt = false;
                }
            }
            setPriority();
            running();



        }
    }
}
