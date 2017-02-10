/**
 * Created by james on 02/02/17.
 * Class represents a process
 */
public class Process {

    // Properties
    private int id;
    private int cpuBurstTime;
    private int absArrivalTime;

    private int waitingTime;
    private int runningTime;

    private boolean running;

    // Constructor

    public Process(int id, int cbt, int aat) {
        this.id = id;
        this.cpuBurstTime = cbt;
        this.absArrivalTime = aat;
        this.waitingTime = 0;
        this.runningTime = 0;
        this.running = false;
    }

    // Methods

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

    // Tick called by scheduler
    public boolean tick() {

        // depending on whether the process is
        // running or not increment runningTime or waiting time.
        if (running) {
            runningTime++;
        } else {
            waitingTime++;
        }

        // return true when process completed so
        // scheduler can add it to the complete queue
        return runningTime == cpuBurstTime;
    }

    @Override
    public String toString() {
        return id + ", " + cpuBurstTime + ", " + absArrivalTime;
    }
}
