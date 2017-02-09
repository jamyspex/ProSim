import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by james on 03/02/17.
 */
public abstract class Scheduler {

    protected ArrayList<Process> complete;
    protected Queue<Process> waiting;
    protected String workload;
    protected String algorithm;
    protected int ticks;
    protected HashMap<Integer, ArrayList<Process>> beforeStart;

    protected Process current;

    public Scheduler(String algo, String filename){
        complete = new ArrayList<>();
        beforeStart = new HashMap<>();
        current = null;
        algorithm = algo;
        ticks = 0;
        load(filename);
    }

    public ArrayList<Process> getComplete(){
        return complete;
    }

    public double averageWaitTime(){
        return complete.stream().mapToInt(Process::getWaitingTime).average().getAsDouble();
    }

    public double averageTurnAroundTime(){
        return complete.stream().mapToInt(Process::getTurnaroundTime).average().getAsDouble();
    }

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

    abstract public void run();

    public void load(String filename) {
        workload = filename;

        try {
            Scanner sc = new Scanner(new File(filename));
            sc.useDelimiter(", |,|\n");

            while (sc.hasNext()) {

                int id = sc.nextInt();
                int cbt = sc.nextInt();
                int att = sc.nextInt();

                if (!beforeStart.containsKey(att)) {
                    beforeStart.put(att, new ArrayList<>());
                }

                beforeStart.get(att).add(new Process(id, cbt, att));
            }

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
