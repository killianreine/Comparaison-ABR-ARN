import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class main_add_defavo {
    // Ajout d'éléments dans un ordre défavorable (décroissant) dans un ABR et un ARN
    // + enregistrement des temps d'ajout dans deux fichiers csv : "AjoutPireABR.csv" et "AjoutPireARN.csv"
    public static void main(String[] args) {
        ABR<Integer> abr = new ABR<>();
        ARN<Integer> arn = new ARN<>();
        for(int i = 10000; i >= 1; i--) {
            long startTime, endTime;

            // Ajout dans l'ABR
            startTime = System.nanoTime();
            abr.add(i);
            endTime = System.nanoTime();
            long abrTime = endTime - startTime;

            // Ajout dans l'ARN
            startTime = System.nanoTime();
            arn.add(i);
            endTime = System.nanoTime();
            long arnTime = endTime - startTime;

            // Écriture des résultats dans les fichiers CSV
            try (FileWriter abrWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/AjoutPireABR.csv", true);
                    FileWriter arnWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/AjoutPireARN.csv", true)) {
                abrWriter.append((10001 - i) + "," + abrTime + "\n");
                arnWriter.append((10001 - i) + "," + arnTime + "\n");
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }
}
