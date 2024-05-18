public class Process {
    private int psNumber;
    private int waitingTime;
    private int requiredCpuTime;
    private int turnaroundTime;
    private int responseTime;
    private int arrivedTime;
    private int priority;
    private double responseRatio;

    public Process(int psNumber, int requiredCpuTime, int arrivedTime, int priority){
        this.psNumber = psNumber;
        this.requiredCpuTime = requiredCpuTime;
        this.arrivedTime = arrivedTime;
        this.priority = priority;
    }

    public int getPsNumber() {
        return psNumber;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public int getRequiredCpuTime() {
        return requiredCpuTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public int getResponseTime() {
        return responseTime;
    }
    public int getArriveTime() { return arrivedTime; }
    public int getPriority() { return priority; }
    public double getResponseRatio() { return responseRatio; }
    public void setResponseRatio( double responseRatio ){ this.responseRatio = responseRatio; }

    public void setTime(int turnaroundTime, int waitingTime, int responseTime){
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
        this.responseTime = responseTime;
    }
}