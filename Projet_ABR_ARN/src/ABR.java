import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author Killian Reine
 * @date Dimanche 30 novembre
 * Classe Arbre Binaire de Recherche - ABR
 * - Noeud du SAG de n plus petit que n
 * - Noeud du SAD de n plus grand que n
 */
public class ABR<T> extends BinaryTree<T>{
    /**
     * Méthode permettant la comparaison de deux éléments
     * @param a premier élément
     * @param b second élément
     * @return Le résultat de la comparaison des éléments
     */
    protected int compare(T a, T b) {
        if (cmp_ != null) {
            return cmp_.compare(a, b);
        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super T> comp = (Comparable<? super T>) a;
            return comp.compareTo(b);
        }
    }

    /**
     * Méthode pour attacher un noeud à l'arbre à partir d'un parent
     * @param parent Le parent du noeud ajouté
     * @param child Le noeud à ajouter
     */
    protected void attachNode(BinaryTree<T>.Node parent, BinaryTree<T>.Node child) {
        if (compare(child.getValue(), parent.getValue()) < 0) {
            parent.setLeft(child);
        } else {
            parent.setRight(child);
        }
        child.setFather(parent);
    }

    /**
     * Méthode permettant d'ajouter un élément à l'arbre binaire de recherche
     * @param v_ la valeur à ajouter dans l'arbre
     * @return un booléen qui permet de savoir si la valeur a été ajoutée
     */
    @Override
    public boolean add(T v_) {
        Node n = new Node(v_);
        if (root_ == null) {
            root_ = n;
            nbNode_++;
            return true;
        }

        BinaryTree<T>.Node parent = null;
        BinaryTree<T>.Node current = root_;

        while (current != null) {
            parent = current;
            int cmpResult = compare(v_, current.getValue());

            if (cmpResult < 0) {
                // v_ < current
                current = current.getLeft();
            } else if (cmpResult > 0) {
                // v_ > current
                current = current.getRight();
            } else {
                return false; // valeur déjà présente dans l'ABR
            }
        }

        attachNode(parent, n);
        nbNode_++;
        return true;
    }

    /**
     * Méthode permettant d'ajouter une liste de valeur dans l'ABR
     * Surcharge de la méthode addAll de Collection<T>
     * @param collect La collection des valeurs à ajouter dans l'arbre
     */
    @Override
    public boolean addAll(Collection<? extends T> collect){
        boolean modified = false;
        for(T val : collect){
            if(add(val)) modified = true;
        }
        return modified;
    }

    /**
     * Méthode permettant de supprimer une valeur de l'arbre binaire de recherche
     * @param v_ La valeur à supprimer
     * @return true si la valeur a été supprimée, false sinon
     */
    @Override
    public boolean removeValue(T v_) {
        BinaryTree<T>.Node z = research(v_);
        if (z == null) return false; // pas trouvé

        // y est le noeud à supprimer
        BinaryTree<T>.Node y;
        if (z.getLeft() == null || z.getRight() == null) {
            y = z;
        } else {
            // si deux enfants, y = minimum du sous-arbre droit (successeur)
            y = z.getRight().minimum();
        }

        // x est le fils de y
        BinaryTree<T>.Node x = (y.getLeft() != null) ? y.getLeft() : y.getRight();

        // rattacher x au père de y
        if (x != null) {
            x.setFather(y.getFather());
        }

        if (y.getFather() == null) {
            // y est la racine
            root_ = x;
        } else if (y == y.getFather().getLeft()) {
            y.getFather().setLeft(x);
        } else {
            y.getFather().setRight(x);
        }

        if (y != z) {
            z.setValue(y.getValue());
        }

        nbNode_--; 
        return true;
    }

    /**
     * Méthode permettant de supprimer une liste de valeurs dans l'ABR
     * Surcharge de la méthode removeAllValue de Collection<T>
     * @param collect La collection des valeurs à supprimer dans l'arbre
     */
    @Override
    public boolean removeAllValue(Collection<? extends T> collect){
        boolean modified = false;
        for(T val : collect){
            if(removeValue(val)) modified = true;
        }
        return modified;
    }

    /**
     * Méthode de recherche dans l'arbre ABR
     * @param o La valeur à rechercher
     * @return Le noeud contenant la valeur recherchée, ou null si non trouvé
     */
    @Override
    public Node research(Object o){
        Node current = root_;
        while (current != null) {
            @SuppressWarnings("unchecked")
            int comparison = (cmp_ == null ? 
                ((Comparable<? super T>) o).compareTo(current.getValue()) : 
                cmp_.compare((T) o, current.getValue()));
            if (comparison < 0) {
                current = current.getLeft();
            } else if (comparison > 0) {
                current = current.getRight();
            } else {
                return current;  // Trouvé
            }
        }
        return null;  // Pas trouvé
    }
}