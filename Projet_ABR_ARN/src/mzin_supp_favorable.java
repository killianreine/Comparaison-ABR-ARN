import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class mzin_supp_favorable {
    public static void main(String[] args) {
        // Suppression d'éléments dans un ordre favorable (croissant) dans un ABR et un ARN
        ABR<Integer> abr = new ABR<>();
        ARN<Integer> arn = new ARN<>();
        // Remplissage des arbres avec des éléments de 1 à 10 000 + temps de suppression
        for(int i = 1; i <= 10000; i++) {
            abr.add(i);
            arn.add(i);
        }
        for(int i = 1; i <= 10000; i++) {
            long startTime, endTime;

            // Suppression dans l'ABR
            startTime = System.nanoTime();
            abr.removeValue(i);
            endTime = System.nanoTime();
            long abrTime = endTime - startTime;

            // Suppression dans l'ARN
            startTime = System.nanoTime();
            arn.removeValue(i);
            endTime = System.nanoTime();
            long arnTime = endTime - startTime;

            // Écriture des résultats dans les fichiers CSV
            try (FileWriter abrWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/SuppFavorableABR.csv", true);
                    FileWriter arnWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/SuppFavorableARN.csv", true)) {
                abrWriter.append(i + "," + abrTime + "\n");
                arnWriter.append(i + "," + arnTime + "\n");
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }       
}
