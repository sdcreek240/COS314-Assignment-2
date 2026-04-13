import java.util.*;

public class GeneticAlgorithm {

    private static final int    POP_SIZE        = 100;
    private static final int    MAX_GENERATIONS = 500;
    private static final double CROSSOVER_RATE  = 0.8;
    private static final double MUTATION_RATE   = 0.02;
    private static final int    TOURNAMENT_SIZE = 5;
    private static final int    ELITE_COUNT     = 2;

    public Individual solve(KnapsackFile kf) {
 
        Population pop  = new Population(POP_SIZE, kf);
        Individual best = new Individual(pop.getBest());
 
        for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
 
            Individual[] next = new Individual[POP_SIZE];
 
            // Elitism
            Individual[] elites = getElites(pop, ELITE_COUNT);
            for (int e = 0; e < ELITE_COUNT; e++) next[e] = elites[e];
 
            // New generation
            for (int i = ELITE_COUNT; i < POP_SIZE; i++) {
 
                Individual parentA = tournamentSelect(pop);
                Individual parentB = tournamentSelect(pop);
 
                Individual child = (Main.rand.nextDouble() < CROSSOVER_RATE)
                    ? singlePointCrossover(parentA, parentB, kf)
                    : new Individual(parentA.getFitness() >= parentB.getFitness() ? parentA : parentB);
 
                if (Main.rand.nextDouble() < MUTATION_RATE) {
                    child.mutate();
                    child.eval();
                }
 
                next[i] = child;
            }
 
            for (int i = 0; i < POP_SIZE; i++) pop.set(i, next[i]);
 
            Individual genBest = pop.getBest();
            if (genBest.getFitness() > best.getFitness()) best = new Individual(genBest);
 
        }//END_generations
 
        return best;
    }
 
    // Tournament selection
    private Individual tournamentSelect(Population pop) {
        Individual best = null;
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            Individual c = pop.get(Main.rand.nextInt(pop.getSize()));
            if (best == null || c.getFitness() > best.getFitness()) best = c;
        }
        return best;
    }
 
    // Single-point crossover
    private Individual singlePointCrossover(Individual a, Individual b, KnapsackFile kf) {
        int len   = a.getLength();
        int point = Main.rand.nextInt(len);
 
        Individual child = new Individual(kf);
        for (int i = 0; i < len; i++) {
            child.setGene(i, (i <= point) ? a.getGene(i) : b.getGene(i));
        }
        child.eval();
        return child;
    }
 
    // Elitism — top N copies
    private Individual[] getElites(Population pop, int n) {
        Individual[] elites = new Individual[n];
        boolean[]    used   = new boolean[pop.getSize()];
        for (int e = 0; e < n; e++) {
            double bestFit = -1; int bestIdx = 0;
            for (int i = 0; i < pop.getSize(); i++) {
                if (!used[i] && pop.get(i).getFitness() > bestFit) {
                    bestFit = pop.get(i).getFitness(); bestIdx = i;
                }
            }
            elites[e] = new Individual(pop.get(bestIdx));
            used[bestIdx] = true;
        }
        return elites;
    }

}//END_GeneticAlgorithm