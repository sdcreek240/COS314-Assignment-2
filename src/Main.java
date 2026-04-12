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

        boolean running = true;

        while (running) {

            // seed
            System.out.print("\nEnter seed value: ");
            long seed = scanner.nextLong();
            //Add some to make seed 0 when seed isnt entered
            rand = new Random(seed);

            // select file
            System.out.println("\nAvailable knapsack instances:");
            for (int i = 0; i < files.size(); i++) {
                System.out.println("  [" + i + "] " + files.get(i).getName());
            }
            System.out.print("Select instance (0-" + (files.size() - 1) + "): ");
            int fileIndex = scanner.nextInt();

            if (fileIndex < 0 || fileIndex >= files.size()) {
                System.out.println("Invalid selection. Please try again.");
                continue;
            }

            KnapsackFile instance = files.get(fileIndex);
            System.out.println("Selected: " + instance.getName());

            // algo selection
            System.out.println("\nChoose algorithm:");
            System.out.println("  [1] Genetic Algorithm (GA)");
            System.out.println("  [2] Iterated Local Search (ILS)");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();

            // === RUN ===
            long startTime = System.currentTimeMillis();
            Solution bestSolution = null;

            if (choice == 1) {
                GeneticAlgorithm ga = new GeneticAlgorithm();
                bestSolution = ga.solve(instance);
            } else if (choice == 2) {
                IteratedLocalSearch ils = new IteratedLocalSearch();
                bestSolution = ils.solve(instance);
            } else {
                System.out.println("Invalid algorithm choice.");
                // fall through to "run again?" prompt
            }

            long endTime = System.currentTimeMillis();

            //output
            if (bestSolution != null) {
                System.out.println("\n=== RESULT ===");
                System.out.println("Instance : " + instance.getName());
                System.out.println("Best value: " + bestSolution.getValue());
                System.out.println("Weight   : " + bestSolution.getWeight() + " / " + instance.getWeightCapacity());
                System.out.printf("Runtime  : %.3f seconds%n", (endTime - startTime) / 1000.0);
            }

            // === RUN AGAIN? ===
            System.out.print("\nRun again with different seed/instance? (y/n): ");
            String again = scanner.next();
            running = again.equalsIgnoreCase("y");
        }

        System.out.println("Goodbye.");
        scanner.close();
    }
}