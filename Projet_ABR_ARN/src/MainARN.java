import java.util.Arrays;
import java.util.List;

/**
 * @author Killian Reine
 * @date 1er décembre 2025
 * Programme de test pour la classe ARN (Arbre Rouge-Noir)
 */
public class MainARN {
    private static final String SEPARATOR = "═══════════════════════════════════════════════════════════════";

    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("       TESTS DE LA CLASSE ARN (Arbre Rouge-Noir)");
        System.out.println(SEPARATOR);

        // Test 1 : Ajout simple
        testAjoutSimple();

        // Test 2 : Ajout valeur individuelle
        testAjoutValeurIndividuelle();

        // Test 3 : Suppression simple
        testSuppressionSimple();

        // Test 4 : Suppression valeur inexistante
        testSuppressionInexistante();

        // Test 5 : Suppressions multiples
        testSuppressionsMultiples();

        // Test 6 : Suppression éléments critiques
        testSuppressionsCritiques();

        System.out.println(SEPARATOR);
        System.out.println("                     TESTS TERMINÉS");
        System.out.println(SEPARATOR);
    }

    /**
     * Test 1 : Construction et ajout simple d'éléments
     */
    private static void testAjoutSimple() {
        System.out.println("\n[TEST 1] Construction et ajout simple");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        System.out.println("ARN créé (vide)");
        System.out.println("Taille: " + arn.size());

        List<Integer> valeurs = Arrays.asList(10, 5, 20, 15, 30, 25, 2, 8, 12);
        System.out.println("\nAjout des valeurs: " + valeurs);

        for (Integer v : valeurs) {
            arn.add(v);
        }

        System.out.println("Taille après ajouts: " + arn.size());

        System.out.println("\nStructure de l'ARN (nœuds rouges en rouge, sentinelles ☒) :");
        System.out.println(arn.toString());
    }

    /**
     * Test 2 : Ajout d'une valeur individuelle
     */
    private static void testAjoutValeurIndividuelle() {
        System.out.println("\n[TEST 2] Ajout d'une valeur individuelle");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        List<Integer> valeursInitiales = Arrays.asList(10, 5, 20);
        arn.addAll(valeursInitiales);

        System.out.println("ARN initial:");
        System.out.println(arn);

        // Ajout d'une nouvelle valeur
        int nouvelleValeur = 15;
        testAjoutValeur(arn, nouvelleValeur);

        // Ajout d'un doublon
        int doublon = 10;
        testAjoutValeur(arn, doublon);

        // Ajout d'autres valeurs
        testAjoutValeur(arn, 14);
        testAjoutValeur(arn, 21);
        testAjoutValeur(arn, 22);
    }

    /**
     * Fonction utilitaire pour ajouter une valeur à l'ARN et afficher le résultat
     */
    private static void testAjoutValeur(ARN<Integer> arn, Integer valeur) {
        System.out.println("\nAjout de la valeur " + valeur + " :");
        boolean added = arn.add(valeur);
        System.out.println("Ajout effectué? " + added);
        System.out.println("Taille actuelle: " + arn.size());
        System.out.println("Structure de l'ARN après ajout:");
        System.out.println(arn);
    }

    /**
     * Test 3 : Suppression simple
     */
    private static void testSuppressionSimple() {
        System.out.println("\n[TEST 3] Suppression simple");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        List<Integer> valeurs = Arrays.asList(10, 5, 20, 15, 30);
        arn.addAll(valeurs);

        System.out.println("ARN avant suppression:");
        System.out.println(arn);

        int valeurSupprimee = 20;
        System.out.println("\nSuppression de la valeur " + valeurSupprimee + " :");
        boolean removed = arn.removeValue(valeurSupprimee);

        System.out.println("Suppression effectuée? " + removed);
        System.out.println("Taille actuelle: " + arn.size());
        System.out.println("Structure de l'ARN après suppression:");
        System.out.println(arn);
    }

    /**
     * Test 4 : Suppression d'une valeur inexistante
     */
    private static void testSuppressionInexistante() {
        System.out.println("\n[TEST 4] Suppression d'une valeur inexistante");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        arn.addAll(Arrays.asList(10, 5, 20));

        System.out.println("ARN initial:");
        System.out.println(arn);

        int valeur = 42;
        System.out.println("\nTentative de suppression de " + valeur + " :");
        boolean removed = arn.removeValue(valeur);

        System.out.println("Suppression effectuée? " + removed);
        System.out.println("Structure de l'ARN:");
        System.out.println(arn);
    }

    /**
     * Test 5 : Suppressions multiples
     */
    private static void testSuppressionsMultiples() {
        System.out.println("\n[TEST 5] Suppressions multiples");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        List<Integer> valeurs = Arrays.asList(15, 6, 3, 7, 20, 18, 23, 17, 19);
        arn.addAll(valeurs);

        System.out.println("ARN initial:");
        System.out.println(arn);

        List<Integer> aSupprimer = Arrays.asList(7, 6, 15, 3, 23);

        for (Integer v : aSupprimer) {
            System.out.println("\nSuppression de la valeur " + v + " :");
            boolean removed = arn.removeValue(v);
            System.out.println("Suppression effectuée? " + removed);
            System.out.println("Structure après suppression:");
            System.out.println(arn);
        }
    }

    /**
     * Test 6 : Suppression de valeurs critiques
     */
    private static void testSuppressionsCritiques() {
        System.out.println("\n[TEST 6] Suppression d'éléments critiques");
        System.out.println("________________________________________________________________");

        ARN<Integer> arn = new ARN<>();
        arn.addAll(Arrays.asList(10, 5, 1, 7, 40, 50, 30));

        System.out.println("ARN initial:");
        System.out.println(arn);

        // Suppression racine
        testSuppressionValeur(arn, 10);

        // Suppression feuille rouge
        testSuppressionValeur(arn, 1);

        // Suppression feuille noire
        testSuppressionValeur(arn, 7);

        // Suppression nœud avec deux enfants
        testSuppressionValeur(arn, 40);

        // Suppression du dernier élément
        testSuppressionValeur(arn, 50);
    }

    /**
     * Fonction utilitaire pour supprimer une valeur et afficher le résultat
     */
    private static void testSuppressionValeur(ARN<Integer> arn, Integer valeur) {
        System.out.println("\nSuppression de la valeur " + valeur + " :");
        boolean removed = arn.removeValue(valeur);
        System.out.println("Suppression effectuée? " + removed);
        System.out.println("Taille actuelle: " + arn.size());
        System.out.println("Structure de l'ARN après suppression:");
        System.out.println(arn);
    }
}
