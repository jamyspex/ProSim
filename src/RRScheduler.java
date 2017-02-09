import java.util.*;

/**
 * Created by james on 03/02/17.
 */
public class RRScheduler extends Scheduler {

    private int quantum;


    public RRScheduler(String filename, int q) {
        super("RR Q = " + q, filename);
        quantum = q;
        waiting = new LinkedList<>();
    }

    @Override
    public void run() {

        int currentTicks = 0;

        do {

            if(beforeStart.containsKey(ticks)) {
                beforeStart.get(ticks).forEach(p -> waiting.add(p));
                beforeStart.remove(ticks);
            }

            if (current == null) {
                current = waiting.remove();
                current.run();
            }

            waiting.forEach(Process::tick);

            if (current.tick()) {
                complete.add(current);
                current = null;
                currentTicks = 0;
            }

            if(currentTicks == quantum && current != null){
                current.pause();
                waiting.add(current);
                current = null;
                currentTicks = 0;
            }

            ticks++;
            currentTicks++;

        } while (!waiting.isEmpty() || !beforeStart.keySet().isEmpty() || current != null);

        printStats();
    }
}
