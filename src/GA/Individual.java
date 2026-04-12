import java.util.*;

public class Individual {

    private int       iLength;
    private int[]     genes;

    private double    totalValue;
    private double    totalWeight;
    
    private boolean   feasible;  

    private KnapsackFile kf;

    public Individual(KnapsackFile kf) {

        this.iLength = kf.getTotalObjects();
        this.kf = kf;

        this.genes = new int[iLength];

        for (int i=0; i<iLength; i++) {

            genes[i] = Main.rand.nextInt(2); //Choose randomly between 1 / 0
        }//END_i

        eval();
    }//END_Constructor

    // CC
    public Individual(Individual other) {
 
        this.iLength      = other.iLength;
        this.genes        = new int[iLength];
        this.totalValue   = other.totalValue;
        this.totalWeight  = other.totalWeight;
        this.feasible     = other.feasible;
        System.arraycopy(other.genes, 0, this.genes, 0, iLength);

        eval();
    }//END_cc

//Accessors
    public int     getLength()   { return iLength; }
    public int[]   getGenes()    { return genes; }
    public int     getGene(int i){ return (i>=0 && i<iLength) ? genes[i] : -1; }
    public double  getValue()    { return totalValue; }
    public double  getWeight()   { return totalWeight; }
    public boolean isFeasible()  { return feasible; }

    public double getFitness() { return feasible ? totalValue : 0; }

//Mutators
    public void    setGene(int i, int val) { if (i>=0 && i<iLength && (val==0 || val==1)) genes[i] = val; }

    public void mutate() {

        int i = Main.rand.nextInt(iLength);
        genes[i] = (genes[i]==1) ? 0 : 1;
    }//END_Mutate

//utils
    public void eval() {

        totalValue=0; totalWeight=0; feasible=false;

        for (int i=0; i<iLength; i++){

            if (genes[i]==1) {

                Thingies t = kf.getObject(i);

                totalWeight += t.getWeight();
                totalValue += t.getValue();
            }//END_if
        }//END_i

        if (totalWeight<=kf.getWeightCapacity()) this.feasible = true;
    }//END_eval

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Value=").append(totalValue)
          .append(" | Weight=").append(totalWeight)
          .append(" | Feasible=").append(feasible)
          .append(" | Genes=[");
        for (int i = 0; i < iLength; i++) sb.append(genes[i]);
        sb.append("]");
        return sb.toString();
    }
}//END_Individuals