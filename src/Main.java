import java.util.Scanner;
import java.util.Random;
import java.util.List;

public class Main {

    public static Random rand;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DataProcessor dp = new DataProcessor();
        dp.loadAllFiles();
        List<KnapsackFile> files = dp.getKnapsackFiles();

        if (files.isEmpty()) {
            System.out.println("No data files found. Exiting program.");
            return; 
        }

        boolean running = true;

        while (running) {
            System.out.print("\nEnter seed value: ");
            long seed = scanner.nextLong();
            rand = new Random(seed);

            System.out.println("\nAvailable knapsack instances:");
            for (int i = 0; i < files.size(); i++) {
                System.out.println("  [" + i + "] " + files.get(i).getName());
            }
            System.out.println("  [100] RUN FULL EXPERIMENT (Generate Table 1)");
            System.out.print("Select instance (0-" + (files.size() - 1) + ") or 100 for experiment: ");
            int fileIndex = scanner.nextInt();

            // === AUTOMATED EXPERIMENT MODE ===
            if (fileIndex == 100) {
                System.out.println("\n=== EXPERIMENT RESULTS (TABLE 1) ===");
                System.out.printf("%-22s | %-9s | %-10s | %-15s | %-15s\n", 
                                  "Problem Instance", "Algorithm", "Seed Value", "Best Solution", "Runtime (sec)");
                System.out.println("-".repeat(80));

                for (KnapsackFile instance : files) {
                    // Run ILS
                    rand = new Random(seed); // Reset seed so ILS gets a fair, identical starting state
                    long startILS = System.currentTimeMillis();
                    IteratedLocalSearch ils = new IteratedLocalSearch();
                    Individual bestILS = ils.solve(instance);
                    double timeILS = (System.currentTimeMillis() - startILS) / 1000.0;

                    // Run GA
                    rand = new Random(seed); // Reset seed so GA gets a fair, identical starting state
                    long startGA = System.currentTimeMillis();
                    GeneticAlgorithm ga = new GeneticAlgorithm();
                    Individual bestGA = ga.solve(instance);
                    double timeGA = (System.currentTimeMillis() - startGA) / 1000.0;

                    // Print formatted rows
                    System.out.printf("%-22s | %-9s | %-10d | %-15.1f | %-15.3f\n", 
                                      instance.getName(), "ILS", seed, bestILS.getValue(), timeILS);
                    System.out.printf("%-22s | %-9s | %-10d | %-15.1f | %-15.3f\n", 
                                      instance.getName(), "GA", seed, bestGA.getValue(), timeGA);
                }
                System.out.println("-".repeat(80));
                
            } 
            // === SINGLE INSTANCE MODE ===
            else if (fileIndex >= 0 && fileIndex < files.size()) {
                KnapsackFile instance = files.get(fileIndex);
                System.out.println("Selected: " + instance.getName());

                System.out.println("\nChoose algorithm:");
                System.out.println("  [1] Genetic Algorithm (GA)");
                System.out.println("  [2] Iterated Local Search (ILS)");
                System.out.print("Choice: ");
                int choice = scanner.nextInt();

                long startTime = System.currentTimeMillis();
                Individual bestSolution = null;

                if (choice == 1) {
                    GeneticAlgorithm ga = new GeneticAlgorithm();
                    bestSolution = ga.solve(instance);
                } else if (choice == 2) {
                    IteratedLocalSearch ils = new IteratedLocalSearch();
                    bestSolution = ils.solve(instance);
                } else {
                    System.out.println("Invalid algorithm choice.");
                }

                long endTime = System.currentTimeMillis();

                if (bestSolution != null) {
                    System.out.println("\n=== RESULT ===");
                    System.out.println("Instance : " + instance.getName());
                    System.out.println("Best value: " + bestSolution.getValue());
                    System.out.println("Weight   : " + bestSolution.getWeight() + " / " + instance.getWeightCapacity());
                    System.out.printf("Runtime  : %.3f seconds%n", (endTime - startTime) / 1000.0);
                }
            } else {
                System.out.println("Invalid selection.");
            }

            System.out.print("\nRun again? (y/n): ");
            String again = scanner.next();
            running = again.equalsIgnoreCase("y");
        }

        System.out.println("Goodbye.");
        scanner.close();
    }
}