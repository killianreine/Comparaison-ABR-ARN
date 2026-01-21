import java.io.FileWriter;
import java.io.IOException;

public class main_rech {

    public static void main(String[] args) {

        ABR<Integer> abr = new ABR<>();
        ARN<Integer> arn = new ARN<>();

        for (int i = 1; i <= 10001; i += 100) {
            long startTime, endTime;

            // Remplissage des arbres jusqu'à la taille i
            for (int j = 0; j < 100; j++) {
                int value = (int) (Math.random() * 100000);
                abr.add(value);
                arn.add(value);
            }

            // Recherche dans l'ABR
            startTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                int value = (int) (Math.random() * 100000);
                abr.research(value);
            }
            endTime = System.nanoTime();
            long abrTime = endTime - startTime;

            // Recherche dans l'ARN
            startTime = System.nanoTime();
            for (int j = 0; j < i; j++) {
                int value = (int) (Math.random() * 100000);
                arn.research(value);
            }
            endTime = System.nanoTime();
            long arnTime = endTime - startTime;

            // Écriture des résultats dans les fichiers CSV
            try (FileWriter abrWriter = new FileWriter(
                        "src/main/ressources/csv/rechercheABR.csv", true);
                 FileWriter arnWriter = new FileWriter(
                        "src/main/ressources/csv/rechercheARN.csv", true)) {

                abrWriter.append(i + "," + abrTime + "\n");
                arnWriter.append(i + "," + arnTime + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
