public class Process {
    private int psNumber;
    private int waitingTime;
    private int requiredCpuTime;
    private int turnaroundTime;
    private int responseTime;
    private int arriveTime;
    private int priority;

    public Process(int psNumber, int requiredCpuTime, int arriveTime, int priority){
        this.psNumber = psNumber;
        this.requiredCpuTime = requiredCpuTime;
        this.arriveTime = arriveTime;
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
    public int getArriveTime() {
        return arriveTime;
    }
    public int getPriority() {
        return priority;
    }

    public void setTime(int turnaroundTime, int waitingTime){
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }
}