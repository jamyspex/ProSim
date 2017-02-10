import java.util.*;

/**
 * Created by james on 03/02/17.
 * Round Robin Scheduler
 */
public class RRScheduler extends Scheduler {

    // Properties
    private int quantum;

    // Constructor
    public RRScheduler(String filename, int q) {
        super("RR Q = " + q, filename);
        quantum = q;
        // waiting queue is just a LinkedList for RR
        // processes added to end as they arrive
        waiting = new LinkedList<>();
    }

    @Override
    public void run() {

        // current ticks keeps track of how long currently
        // running process has used so it does't over run the quantum
        int currentTicks = 0;

        // While there are still processes to execute
        while (!waiting.isEmpty() || !beforeStart.keySet().isEmpty() || current != null) {

            // check if there are any processes arriving at this time
            if(beforeStart.containsKey(ticks)) {
                // add them all the the waiting queue and remove them
                // from the has map
                beforeStart.get(ticks).forEach(p -> waiting.add(p));
                beforeStart.remove(ticks);
            }

            // if the current process is null get the next waiting process
            // and run it.
            if (current == null) {
                current = waiting.remove();
                current.run();
            }

            // tick all the waiting processes
            waiting.forEach(Process::tick);

            // tick the current process and if it
            // completes add it to the completed queue
            // set current to null so next process is run
            // also reset currentTicks so next process gets
            // full quantum when it is run
            if (current.tick()) {
                complete.add(current);
                current = null;
                currentTicks = 0;
            }

            // if the process has had its time slice pause it
            // add it to the waiting queue and set current to
            // null so next process is taken from waiting
            // and executed next tick
            if(currentTicks == quantum && current != null){
                current.pause();
                waiting.add(current);
                current = null;
                currentTicks = 0;
            }

            // increment overall time count and
            // current processes ticks
            ticks++;
            currentTicks++;

        }

        // after all the processes are complete print execution stats
        printStats();
    }
}
