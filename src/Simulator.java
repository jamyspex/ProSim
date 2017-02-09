import java.io.File;


/**
 * Main class that kicks everything off
 */
public class Simulator {

    public static void main(String[] args) {

        // check for existing W1.csv and W2.csv
        File W1 = new File("W1.csv");
        File W2 = new File("W2.csv");

        // if they don't exist or the program was run with -r argument generate / regenerate the workloads
        if(!W1.exists() || !W2.exists() || (args.length > 0 && args[0].equals("-r"))) {

            ProcessGenerator.generateProcesses(1000, 20, 3, 5, "W1.csv");
            ProcessGenerator.generateProcesses(1000, 60, 3, 5, "W2.csv");
        }

        // create some schedulers for W1
        FCFSScheduler FCFS = new FCFSScheduler("W1.csv");
        SJFScheduler SJF = new SJFScheduler("W1.csv");
        RRScheduler RR15 = new RRScheduler("W1.csv", 15);
        RRScheduler RR5 = new RRScheduler("W1.csv", 5);
        RRScheduler RR40 = new RRScheduler("W1.csv", 40);

        // run them
        FCFS.run();
        SJF.run();
        RR15.run();
        RR5.run();
        RR40.run();

        // create some schedulers for W2
        FCFS = new FCFSScheduler("W2.csv");
        SJF = new SJFScheduler("W2.csv");
        RR15 = new RRScheduler("W2.csv", 15);
        RR5 = new RRScheduler("W2.csv", 5);
        RR40 = new RRScheduler("W2.csv", 40);

        // run them
        FCFS.run();
        SJF.run();
        RR15.run();
        RR5.run();
        RR40.run();
    }
}
