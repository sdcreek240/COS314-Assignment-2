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
        this.kf          = other.kf; 
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

    public double getFitness() { return totalValue;

    }

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

                totalWeight += kf.getObject(i).getWeight();
                totalValue += kf.getObject(i).getValue();
            }//END_if
        }//END_i

        int cap = kf.getWeightCapacity();
        if (totalWeight>cap) {

            List<Integer> itemsInKnapsack = new ArrayList<>();
            for (int i = 0; i < iLength; i++) {

                if (genes[i]==1) {

                    itemsInKnapsack.add(i);
                    
                }
                
            }

            //Shuffle so we drop random items (prevents bias)
            Collections.shuffle(itemsInKnapsack,Main.rand);

            for (int i = 0; i < itemsInKnapsack.size(); i++) {

                int dropIndex = itemsInKnapsack.get(i);
                //remove items from the sack
                genes[dropIndex] = 0;
                totalWeight -= kf.getObject(dropIndex).getWeight();
                totalValue -= kf.getObject(dropIndex).getValue();

                if (totalWeight <= cap) {

                    break;
                    
                }
                
            }
            
        }
        this.feasible = true;
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