/**
 * Created by james on 02/02/17.
 */
public class Process {

    private int id;
    private int cpuBurstTime;
    private int absArrivalTime;

    private int waitingTime;
    private int runningTime;

    private boolean running;

    public Process(int id, int cbt, int aat) {
        this.id = id;
        this.cpuBurstTime = cbt;
        this.absArrivalTime = aat;
        this.waitingTime = 0;
        this.runningTime = 0;
        this.running = false;
    }

    public int getRemainingTime() {
        return cpuBurstTime - runningTime;
    }

    public int getId() {
        return id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return runningTime + waitingTime;
    }

    public int getAbsArrivalTime() {
        return absArrivalTime;
    }

    public void pause() {
        running = false;
    }

    public void run() {
        running = true;
    }

    public boolean tick() {

        if (running) {
            runningTime++;
        } else {
            waitingTime++;
        }

        return runningTime == cpuBurstTime;
    }

    @Override
    public String toString() {
        return id + ", " + cpuBurstTime + ", " + absArrivalTime;
    }
}
