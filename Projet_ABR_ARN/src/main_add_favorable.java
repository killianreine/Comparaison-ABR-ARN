import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class main_add_favorable {
    public static void main(String[] args) {
        // Création d'un arbre binaire de recherche d'entiers et d'un ARN de [1 à 10 000] éléments de pas de 100 aléatoirement
        // dans deux fichiers csv : "ABR.csv" et "ARN.csv" : nb_elements, temps_ajout_n_elem
        ABR<Integer> abr = new ABR<>();
        ARN<Integer> arn = new ARN<>();
        for(int i = 1; i <= 10001; i += 100) {
            long startTime, endTime;

            // Ajout dans l'ABR
            startTime = System.nanoTime();
            for(int j = 0; j < i; j++) {
                int value = (int)(Math.random() * 100000);
                abr.add(value);
            }
            endTime = System.nanoTime();
            long abrTime = endTime - startTime;

            // Ajout dans l'ARN
            startTime = System.nanoTime();
            for(int j = 0; j < i; j++) {
                int value = (int)(Math.random() * 100000);
                arn.add(value);
            }
            endTime = System.nanoTime();
            long arnTime = endTime - startTime;

            // Écriture des résultats dans les fichiers CSV
            try (FileWriter abrWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/ajoutABR.csv", true);
                    FileWriter arnWriter = new FileWriter("comparaison-abr-arn/Projet_ABR_ARN/csv/ajoutARN.csv", true)) {
                abrWriter.append(i + "," + abrTime + "\n");
                arnWriter.append(i + "," + arnTime + "\n");
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }
    }
}
