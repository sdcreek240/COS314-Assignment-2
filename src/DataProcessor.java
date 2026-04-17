//Create File object from each data file provided in data/*
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class DataProcessor {

    private List<KnapsackFile> knapsackFiles = new ArrayList<>();
    private File dataDir;

    public void loadAllFiles() {

        dataDir = new File("./data");
        File[] allF = dataDir.listFiles();

        if (allF == null) {
            System.err.println("Error: data directory not found at: " + dataDir.getAbsolutePath());
            return;
        }

        for (File f:allF){

            if (f.isFile()){

                KnapsackFile kf = readKnapsackFile(f);
                if (kf!=null) knapsackFiles.add(kf);
            }
        }//END_f
        
    }//END_loadAllFiles

    private KnapsackFile readKnapsackFile(File f) {

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {

            String sLine = br.readLine().trim();
            String[] p = sLine.split(" ");

            int tO = Integer.parseInt(p[0]);
            int wC = Integer.parseInt(p[1]);

            KnapsackFile kf = new KnapsackFile(f.getName(), tO, wC);

            while ((sLine=br.readLine())!=null){

                sLine = sLine.trim();

                if (!sLine.isEmpty()){

                    p = sLine.split(" ");
                    double v = Double.parseDouble(p[0]);
                    double w = Double.parseDouble(p[1]);
                    kf.appendObject(w, v);
                }//END_Empty check
            }//END_while

            return kf;
        } catch (IOException | NumberFormatException e) {

            System.err.println("Error reading "+f.getName()+": "+e.getMessage());
            return null;
        }
    }//END_readKnapsackFile


    //Accessors
    public List<KnapsackFile> getKnapsackFiles() { return knapsackFiles; }
}//ENDDataProcessor