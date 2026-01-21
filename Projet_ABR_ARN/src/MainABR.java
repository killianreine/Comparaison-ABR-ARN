import java.util.Arrays;
import java.util.List;

/**
 * @author Killian Reine
 * @date Dimanche 30 novembre
 * Programme de test pour la classe ABR (Arbre Binaire de Recherche)
 */
public class MainABR {
    private static final String SEPARATOR = "═══════════════════════════════════════════════════════════════";
    
    public static void main(String[] args) {
        System.out.println(SEPARATOR);
        System.out.println("     TESTS DE LA CLASSE ABR (Arbre Binaire de Recherche)");
        System.out.println(SEPARATOR);
        
        // Test 1: Construction et ajout simple
        testAjoutSimple();
        
        // Test 2: Suppression
        testSuppression();
        
        // Test 3: Ajout de collection
        testAjoutCollection();
        
        // Test 4: Recherche
        testRecherche();
        
        // Test 5: Itération
        testIteration();
        
        // Test 6: Opérations sur collection
        testOperationsCollection();
        
        System.out.println(SEPARATOR);
        System.out.println("                    TESTS TERMINÉS");
        System.out.println(SEPARATOR);
    }
    
    /**
     * Test 1: Construction et ajout simple d'éléments
     */
    private static void testAjoutSimple() {
        System.out.println("\n[TEST 1] Construction et ajout simple");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        System.out.println("ABR créé (vide)");
        System.out.println("Taille: " + abr.size());
        
        System.out.println("\nAjout des valeurs: 14, 7, 21, 3, 10, 18, 25");
        abr.add(14);
        abr.add(7);
        abr.add(21);
        abr.add(3);
        abr.add(10);
        abr.add(18);
        abr.add(25);
        
        System.out.println("Taille après ajouts: " + abr.size());
        System.out.println("\nStructure de l'ABR:");
        System.out.println(abr);
        
        // Test d'ajout d'un doublon
        System.out.println("Tentative d'ajout d'un doublon (14): " + abr.add(14));
        System.out.println("Taille (inchangée): " + abr.size());
    }
    
    /**
     * Test 2: Suppression d'éléments
     */
    private static void testSuppression() {
        System.out.println("\n[TEST 2] Suppression d'éléments");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        abr.add(14);
        abr.add(7);
        abr.add(21);
        abr.add(3);
        abr.add(10);
        abr.add(18);
        abr.add(25);
        
        System.out.println("ABR initial:");
        System.out.println(abr);
        
        // Suppression d'une feuille
        System.out.println("Suppression de 3 (feuille): " + abr.remove(3));
        System.out.println("Taille: " + abr.size());
        System.out.println(abr);
        
        // Suppression d'un nœud avec un enfant
        System.out.println("Suppression de 7 (un enfant): " + abr.remove(7));
        System.out.println("Taille: " + abr.size());
        System.out.println(abr);
        
        // Suppression d'un nœud avec deux enfants
        System.out.println("Suppression de 21 (deux enfants): " + abr.remove(21));
        System.out.println("Taille: " + abr.size());
        System.out.println(abr);
        
        // Tentative de suppression d'un élément inexistant
        System.out.println("Tentative de suppression de 99 (inexistant): " + abr.remove(99));
        System.out.println("Taille (inchangée): " + abr.size());
    }
    
    /**
     * Test 3: Ajout d'une collection
     */
    private static void testAjoutCollection() {
        System.out.println("\n[TEST 3] Ajout d'une collection");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        List<Integer> nombres = Arrays.asList(50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45);
        
        System.out.println("Ajout de la collection: " + nombres);
        abr.addAll(nombres);
        
        System.out.println("Taille: " + abr.size());
        System.out.println("\nStructure de l'ABR:");
        System.out.println(abr);
    }
    
    /**
     * Test 4: Recherche d'éléments
     */
    private static void testRecherche() {
        System.out.println("\n[TEST 4] Recherche d'éléments");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        abr.addAll(Arrays.asList(14, 7, 21, 3, 10, 18, 25));
        
        System.out.println("ABR de test:");
        System.out.println(abr);
        
        System.out.println("Recherche de 10: " + (abr.contains(10) ? "✓ Trouvé" : "✗ Non trouvé"));
        System.out.println("Recherche de 21: " + (abr.contains(21) ? "✓ Trouvé" : "✗ Non trouvé"));
        System.out.println("Recherche de 99: " + (abr.contains(99) ? "✓ Trouvé" : "✗ Non trouvé"));
        System.out.println("Recherche de 3: " + (abr.contains(3) ? "✓ Trouvé" : "✗ Non trouvé"));
    }
    
    /**
     * Test 5: Itération (parcours infixe)
     */
    private static void testIteration() {
        System.out.println("\n[TEST 5] Itération (parcours infixe)");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        abr.addAll(Arrays.asList(14, 7, 21, 3, 10, 18, 25, 1, 5, 9, 12));
        
        System.out.println("ABR de test:");
        System.out.println(abr);
        
        System.out.print("Parcours infixe (ordre croissant): ");
        for (Integer valeur : abr) {
            System.out.print(valeur + " ");
        }
        System.out.println();
        
        // Test de l'itérateur avec suppression
        System.out.println("\nSuppression des valeurs paires via l'itérateur:");
        java.util.Iterator<Integer> it = abr.iterator();
        while (it.hasNext()) {
            Integer val = it.next();
            if (val % 2 == 0) {
                System.out.println("  Suppression de " + val);
                it.remove();
            }
        }
        
        System.out.println("\nABR après suppression des pairs:");
        System.out.println(abr);
    }
    
    /**
     * Test 6: Opérations sur collection (clear, removeAll, etc.)
     */
    private static void testOperationsCollection() {
        System.out.println("\n[TEST 6] Opérations sur collection");
        System.out.println("________________________________________________________________");
        
        ABR<Integer> abr = new ABR<>();
        abr.addAll(Arrays.asList(50, 30, 70, 20, 40, 60, 80));
        
        System.out.println("ABR initial:");
        System.out.println(abr);
        System.out.println("Taille: " + abr.size());
        System.out.println("Est vide? " + abr.isEmpty());
        
        // Test removeAll
        List<Integer> aSupprimer = Arrays.asList(20, 40, 60);
        System.out.println("\nSuppression de la collection: " + aSupprimer);
        abr.removeAll(aSupprimer);
        System.out.println("Taille: " + abr.size());
        System.out.println(abr);
        
        // Test clear
        System.out.println("\nClear de l'ABR:");
        abr.clear();
        System.out.println("Taille: " + abr.size());
        System.out.println("Est vide? " + abr.isEmpty());
    }
}