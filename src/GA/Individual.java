import java.util.*;

public class Individual {

    private int       iLength;
    private int[]     genes;

    private double    totalValue;
    private double    totalWeight;
    
    private boolean   feasible;  

    public Individual(int iLength) {

        this.iLength = iLength;

        genes = new int[iLength];

        for (int i=0; i<iLength; i++) {

            genes[i] = Main.rand.nextInt(2); //Choose randomly between 1 / 0
        }//END_i


    }//END_Constructor

//Accessors
    public int getLength() { return iLength; }
    public int[] getGenes() { return genes; }
    public int getGene(int i) { return i<iLength? genes[i] : null }

//Mutators
    public boolean mutate() {
        //randomly mutate individual
        return false;
    }

//utils
    private void eval() {

        totalValue=0; totalWeight=0; feasible=false;

        for (int i=0; i<iLength; i++){

            if (genes[i]==1) {

                Thingies t = kf.getObject(i);

                totalWeight += Thingies.getWeight();
                totalValue += Thingies.getValue();
            }//END_if
        }//END_i

        if (totalWeight<=kf.getWeightCapacity()) this.feasible = true;
    }//END_eval
}//END_Individuals