public class Process {
    private int psNumber;
    private double waitingTime = 0;
    private int requiredCpuTime;
    private int burstTime;
    private int turnaroundTime;
    private int responseTime = -1;
    private int arrivedTime;
    private int priority;
    private double responseRatio;
    public boolean preempt = false;
    public Process(int psNumber, int requiredCpuTime, int arrivedTime){
        this.psNumber = psNumber;
        this.requiredCpuTime = requiredCpuTime;
        this.arrivedTime = arrivedTime;
        burstTime = requiredCpuTime;
    }
    public Process(int psNumber, int requiredCpuTime, int arrivedTime, int priority){
        this.psNumber = psNumber;
        this.requiredCpuTime = requiredCpuTime;
        this.arrivedTime = arrivedTime;
        this.priority = priority;
        burstTime = requiredCpuTime;
    }

    public int getPsNumber() {
        return psNumber;
    }

    public double getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }
    public int getRequiredCpuTime() { return requiredCpuTime; }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public int getBurstTime(){ return burstTime; }
    public void setTurnaroundTime(int turnaroundTime){ this.turnaroundTime = turnaroundTime; }
    public int getResponseTime(){ return responseTime; }
    public void setResponseTime( int responseTime ){ this.responseTime = responseTime; }
    public int getArriveTime() { return arrivedTime; }
    public int getPriority() { return priority; }
    public void setPriority(int priority){ this.priority = priority; }
    public double getResponseRatio() { return responseRatio; }
    public void setResponseRatio(){ this.responseRatio = (waitingTime + requiredCpuTime) / (double)requiredCpuTime;}
    public void increaseTime(){ waitingTime += 1; }
    public void onJob(){ requiredCpuTime -= 1; }

    public void setTime(int turnaroundTime, int waitingTime, int responseTime){
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
        this.responseTime = responseTime;
    }
}