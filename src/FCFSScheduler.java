import java.util.LinkedList;

/**
 * Created by james on 03/02/17.
 */
public class FCFSScheduler extends Scheduler {

    public FCFSScheduler(String filename) {
        super("FCFS", filename);
        waiting = new LinkedList<>();
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
