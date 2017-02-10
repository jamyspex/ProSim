import java.util.PriorityQueue;

/**
 * Created by james on 03/02/17.
 * Shortest Job First Scheduler
 */
public class SJFScheduler extends Scheduler {

    // Constructor
    public SJFScheduler(String filename) {
        super("SJF", filename);
        // waiting queue is a PriorityQueue ordered by time remaining for SJF
        // meaning remove() removes the process with the least remaining time
        waiting = new PriorityQueue<>((process, t1) -> process.getRemainingTime() - t1.getRemainingTime());
    }

    @Override
    public void run() {

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
            if (current.tick()) {
                complete.add(current);
                current = null;
            }

            // increment time count
            ticks++;

        }

        // after all the processes are complete print execution stats
        printStats();
    }
}
