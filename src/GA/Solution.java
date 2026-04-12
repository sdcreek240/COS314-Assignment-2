public class Solution {

    private boolean[] genes;        // genes[i] = true means item i is selected
    private int       n;            // number of items
    private double    totalValue;
    private double    totalWeight;
    private boolean   feasible;     // true if totalWeight <= capacity

//Constr
    //Build solution from gene array and eval
    public Solution(boolean[] genes, KnapsackFile kf) {
        this.n     = genes.length;
        this.genes = new boolean[n];
        System.arraycopy(genes, 0, this.genes, 0, n);
        evaluate(kf);
    }

   //cc constr
    public Solution(Solution other) {
        this.n           = other.n;
        this.genes       = new boolean[n];
        this.totalValue  = other.totalValue;
        this.totalWeight = other.totalWeight;
        this.feasible    = other.feasible;
        System.arraycopy(other.genes, 0, this.genes, 0, n);
    }

//Evaluation
    //eval from gene array
    public void evaluate(KnapsackFile kf) {
        totalValue  = 0;
        totalWeight = 0;
        for (int i = 0; i < n; i++) {
            if (genes[i]) {
                totalValue  += kf.getObject(i).getValue();
                totalWeight += kf.getObject(i).getWeight();
            }
        }
        feasible = (totalWeight <= kf.getWeightCapacity());
    }

    //Fitness function
    public double getFitness() {
        return feasible ? totalValue : 0;
    }

//Accessors
    public boolean[] getGenes()          { return genes; }
    public boolean   getGene(int i)      { return genes[i]; }
    public int       size()              { return n; }
    public double    getValue()          { return totalValue; }
    public double    getWeight()         { return totalWeight; }
    public boolean   isFeasible()        { return feasible; }

//mutators
    //flip a bit and evaluate
    public void flipGene(int i, KnapsackFile kf) {
        genes[i] = !genes[i];
        evaluate(kf);
    }

    //overwrite genes
    public void setGenes(boolean[] newGenes, KnapsackFile kf) {
        System.arraycopy(newGenes, 0, this.genes, 0, n);
        evaluate(kf);
    }

//utils
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Value=").append(totalValue)
          .append(" | Weight=").append(totalWeight)
          .append(" | Feasible=").append(feasible)
          .append(" | Genes=[");
        for (int i = 0; i < n; i++) sb.append(genes[i] ? "1" : "0");
        sb.append("]");
        return sb.toString();
    }

}//END_Solution