/**
 * Generates a Process work load and writes them to a file
 * Based on supplied Generator.java
 * Created by james on 02/02/17.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ProcessGenerator {

    public static void generateProcesses(int n, double M1, double STD1, double L, String filename) {

        System.out.println("\nGenerating " + filename + "\n");

        ArrayList<Process> processes = new ArrayList<>();

        Random random = new Random(System.currentTimeMillis());

        int poissonValue = 0;
        int gaussianValue;

        // create processes with sequential id, Gaussian CBT and poisson AAT
        for (int i = 0; i < n; i++) {

            gaussianValue = (int) Math.ceil(random.nextGaussian() * STD1 + M1);

            if(i != 0) {
                poissonValue = ProcessGenerator.myPoisson(L, random, poissonValue);
            }

            processes.add(new Process(i, gaussianValue, poissonValue));

        }

        try {

            // write list of processes to file
            FileWriter file = new FileWriter(filename, false);
            PrintWriter out = new PrintWriter(file);

            for (Process p : processes) {

                String line = p.toString();

                System.out.println(line);
                out.println(line);
            }

            out.close();

        } catch (IOException ex){

        }
    }

    // provided Poisson generator
    public static int myPoisson(double mean, Random random, int previous) {
        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-mean);
        do {
            k++;
            p *= random.nextDouble();
        } while (p >= expLambda);
        return previous + k - 1;
    }
}
