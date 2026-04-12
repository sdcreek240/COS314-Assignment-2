import java.util.Scanner;
import java.util.Random;

public class Main {

    //Global seed, use main.seed in other classes to ensure same seed used
    public static Random rand;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter seed value: ");
        long seed = scanner.nextLong();
        rand = new Random(seed);

        DataProcessor dp = new DataProcessor();
        DataProcessor.loadAllFiles();

        for (KnapsackFile kf : dp.getKnapsackFiles()){
            System.out.println("File: " + kf.getName());
            System.out.println("Items: " + kf.getTotalObjects());
            System.out.println("Capacity: " + kf.getWeightCapacity());
        }//END_kf

        // === SELECT ALGORITHM ===
        // System.out.println("Choose algorithm:");
        // System.out.println("1 - Genetic Algorithm (GA)");
        // System.out.println("2 - Iterated Local Search (ILS)");
        // int choice = scanner.nextInt();

        // long startTime = System.currentTimeMillis();

        // Solution bestSolution = null;

        // if (choice == 1) {//GA
        //     GeneticAlgorithm ga = new GeneticAlgorithm();
        //     bestSolution = ga.solve(instance);

        // } else if (choice == 2) {//ILS
        //     IteratedLocalSearch ils = new IteratedLocalSearch();
        //     bestSolution = ils.solve(instance);

        // } else {
        //     System.out.println("Invalid choice.");
        //     System.exit(0);
        // }

        // long endTime = System.currentTimeMillis();

        // //out
        // System.out.println("\n=== RESULT ===");
        // System.out.println("Best value: " + bestSolution.getValue());
        // System.out.println("Weight: " + bestSolution.getWeight());
        // System.out.println("Runtime: " + (endTime - startTime) / 1000.0 + " seconds");

        // scanner.close();
    }
}