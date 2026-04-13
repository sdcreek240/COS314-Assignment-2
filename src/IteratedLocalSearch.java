import java.util.*;
public class IteratedLocalSearch {

    private static final int MAX_ITERATIONS = 500;
    private static final int PERTURBATION_STRENGTH = 3;
    public Individual solve(KnapsackFile kf){

        //random 
        Individual curSolution = new Individual(kf);

        //Initial search to the first local optimum
        curSolution = localSearch(curSolution,kf);

        Individual globalBest = new Individual(curSolution);

        for (int i = 0; i < MAX_ITERATIONS; i++) {

            //Perturbation
            Individual perturbedSol = perturb(curSolution,kf);
            //Local search
            Individual candidateSol = localSearch(perturbedSol,kf);
            //Acceptance Criterion
            if (candidateSol.getFitness()>curSolution.getFitness()){
                curSolution = new Individual(candidateSol);

                if (curSolution.getFitness()> globalBest.getFitness()) {
                    
                    globalBest = new Individual(curSolution);

                }
            }
            
        }

        return globalBest;

    }

    private Individual localSearch(Individual ind, KnapsackFile kf){
        Individual bestLocal= new Individual(ind);
        boolean imp = true;
        while (imp) {
            imp = false;

            for (int i = 0; i < bestLocal.getLength(); i++) {

                Individual neighbour = new Individual(bestLocal);
                int curGene = neighbour.getGene(i);
                neighbour.setGene(i,curGene == 1? 0 : 1);
                neighbour.eval();

                if (neighbour.getFitness()>bestLocal.getFitness()){
                    bestLocal = neighbour;
                    imp = true;
                    break;
                }
                
            }
        }

        return bestLocal;
    }

    private Individual perturb(Individual ind, KnapsackFile kf){

        Individual perturbed = new Individual(ind);
        for (int i = 0; i < PERTURBATION_STRENGTH; i++) {

            int randGeneIdx = Main.rand.nextInt(perturbed.getLength());
            int curGene = perturbed.getGene(randGeneIdx);
            perturbed.setGene(randGeneIdx, curGene ==1 ? 0 : 1);
            
        }

        perturbed.eval();
        return perturbed;
    }

}//END_IteratedLocalSearch