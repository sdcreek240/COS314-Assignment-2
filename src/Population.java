import java.util.*;

public class Population {


    private Individual[] individuals;
    private int          iPopSize;

    public Population(int popSize, KnapsackFile kf) {

        this.iPopSize = popSize;
        this.individuals = new Individual[iPopSize];

        for (int i=0; i<iPopSize; i++){ individuals[i] = new Individual(kf); }//END_i
    }//END_Constr


//Accessors
    public int getSize() {return iPopSize;}
    public Individual get(int i) { return i<iPopSize? individuals[i] : null;}

    public Individual getBest() {
        Individual best = individuals[0];

        for (int i=1; i<iPopSize; i++) if (individuals[i].getFitness()>best.getFitness()) best = individuals[i];

        return best;
    }//END_getBest


//mutators
    public void set(int i, Individual ind) { individuals[i] = ind; }
}//END_Population