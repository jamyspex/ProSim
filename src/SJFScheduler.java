import java.util.PriorityQueue;

/**
 * Created by james on 03/02/17.
 */
public class SJFScheduler extends Scheduler {

    private int ticks;

    public SJFScheduler(String filename) {
        super("SJF", filename);
        waiting = new PriorityQueue<>((process, t1) -> process.getRemainingTime() - t1.getRemainingTime());
        ticks = 0;
    }

    @Override
    public void run() {

        do {

            if(beforeStart.containsKey(ticks)) {
                beforeStart.get(ticks).forEach(p -> waiting.add(p));
                beforeStart.remove(ticks);
            }

            ticks++;

            if (current == null) {
                current = waiting.remove();
                current.run();
            }

            waiting.forEach(Process::tick);

            if (current.tick()) {
                complete.add(current);
                current = null;
            }

        } while (!waiting.isEmpty() || !beforeStart.keySet().isEmpty() || current != null);

        printStats();
    }
}
