import java.io.File;

/**
 * Created by james on 02/02/17.
 */
public class Simulator {

    public static void main(String[] args) {

        File w1 = new File("W1.csv");
        File w2 = new File("W2.csv");

        if(!w1.exists() || !w2.exists() || (args.length > 0 && args[0].equals("-r"))) {

            ProcessGenerator.generateProcesses(1000, 20, 3, 5, "W1.csv");
            ProcessGenerator.generateProcesses(1000, 60, 3, 5, "W2.csv");
        }

        FCFSScheduler FCFS1 = new FCFSScheduler("W1.csv");
        SJFScheduler SJF1 = new SJFScheduler("W1.csv");
        RRScheduler RR15 = new RRScheduler("W1.csv", 15);
        RRScheduler RR5 = new RRScheduler("W1.csv", 5);
        RRScheduler RR40 = new RRScheduler("W1.csv", 40);

        FCFS1.run();
        SJF1.run();
        RR15.run();
        RR5.run();
        RR40.run();

    }
}
