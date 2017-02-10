import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by james on 03/02/17.
 * Abstract class to be subclassed to create schedulers
 */
public abstract class Scheduler {

    // Properties
    protected ArrayList<Process> complete;
    protected Queue<Process> waiting;
    protected String workload;
    protected String algorithm;
    protected int ticks;
    protected HashMap<Integer, ArrayList<Process>> beforeStart;

    protected Process current;

    // Constructor
    public Scheduler(String algo, String filename){
        complete = new ArrayList<>();
        beforeStart = new HashMap<>();
        current = null;
        algorithm = algo;
        ticks = 0;
        load(filename);
    }

    // Calculate average wait time of all the completed processes
    public double averageWaitTime(){
        return complete.stream().mapToInt(Process::getWaitingTime).average().getAsDouble();
    }

    // Calculate average turnaround time of all the completed processes
    public double averageTurnAroundTime(){
        return complete.stream().mapToInt(Process::getTurnaroundTime).average().getAsDouble();
    }

    // print out the stats
    public void printStats(){

        System.out.println(algorithm + " with workload " + workload);

        complete.forEach(process -> System.out.println(
                "PID: " +  process.getId() +
                        " Turnaround: " + process.getTurnaroundTime() +
                        " Waiting: " + process.getWaitingTime())
        );

        System.out.println("Average turnaround time: " + averageTurnAroundTime());
        System.out.println("Average waiting time: " + averageWaitTime());
        System.out.println();
    }

    // abstract method that concrete classes implement
    abstract public void run();

    // load the CSV file
    public void load(String filename) {
        workload = filename;

        try {

            // open a scanner to read in the file
            Scanner sc = new Scanner(new File(filename));

            // scanner separates on "<space>," or "," or "\n"
            sc.useDelimiter(", |,|\n");

            // loop over whole file
            while (sc.hasNext()) {

                // get the next ints
                int id = sc.nextInt();
                int cbt = sc.nextInt();
                int att = sc.nextInt();

                // Processes are stored in HashMap<ATT, List of process with that ATT>
                // allowing them to be easily retrieved later.

                // Check if list has been initialised yet if it
                // hasn't initialise it
                if (!beforeStart.containsKey(att)) {
                    beforeStart.put(att, new ArrayList<>());
                }

                // add the Process to the list at that ATT key
                beforeStart.get(att).add(new Process(id, cbt, att));
            }

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
