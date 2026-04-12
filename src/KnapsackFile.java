//Each file with respective name | total objects | weight capacity
import java.util.*;

public class KnapsackFile {

    //Wieght first then value
    
    public class Thingies {

        private final double weight;
        private final double value;

        public Thingies(double w, double v){
            this.weight=w;
            this.value=v; 
        }//END_constr

        //Accessors
        public double getWeight() {return this.weight;}
        public double getValue() {return this.value;}
        
    }//END_Thingies
    //==============================

    String name;
    int totalObjects;
    int weightCapacity;

    Thingies[] objects; int currItems=0;

    KnapsackFile(String n, int tO, int wC) {

        this.name = n;
        this.totalObjects = tO;
        this.weightCapacity = wC;
        this.objects = new Thingies[this.totalObjects];
    }//END_constructor


    //Accessors
    public String getName() {return name;}
    public int getTotalObjects() {return totalObjects;}
    public int getWeightCapacity() {return weightCapacity;}
    public Thingies[] getObjects() {return objects;}
    public Thingies getObject(int i) { return i<totalObjects? objects[i] : null; }

    //Mutators
    public boolean appendObject(double weight, double value){

        if (currItems<totalObjects) {
            
            objects[currItems++] = new Thingies(weight, value); 
            return true;
        }
        return false;
    }//END_appendObject

}//END_KnapsackFile

