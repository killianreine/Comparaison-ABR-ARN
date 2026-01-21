/**
 * @author Killian REINE
 * @date Dimanche 30 novembre
 */
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class BinaryTree<T> extends AbstractCollection<T> {
    protected Node root_;                       /** Racine de l'arbre                   */
    protected int nbNode_;                      /** Hauteur de l'arbre                  */
    protected Comparator<? super T> cmp_;       /** Comparateur pour définir l'ordre    */
    /**
     * Classe interne Noeud, commune à tous les arbres
     * Toutes les classes qui implémentent Arbre<T> auront accès a la classe Noeud.
     */
    public class Node{
        private T value_;                                           /** La valeur encapsulée dans le noeud  */
        private Node father_;                                       /** Le noeud père                       */
        private Node left_;                                         /** Le noeud gauche                     */
        private Node right_;                                        /** Le noeud droit                      */
        
        // ---------------------------------------------------------------------------------
        // ------------------------------ Constructeurs ------------------------------------
        // ---------------------------------- de la classe Node ----------------------------
        // ---------------------------------------------------------------------------------
        /**
         * Constructeur d'un noeud
         * @param val la valeur encapsulée par le noeud
         */
        public Node(T val){
            this.value_ = val;
            this.father_ = null;
            this.left_ = null;
            this.right_ = null;
        }

        // ---------------------------------------------------------------------------------
        // ------------------------------ Getters et setters -------------------------------
        // ---------------------------------- de la classe Node ----------------------------
        // ---------------------------------------------------------------------------------
        
        // #1 La valeur
        public T getValue(){ return value_; }
        public void setValue(T v_){ value_ = v_; }

        // #2 Le noeud père
        public Node getFather(){ return father_; }
        public void setFather(Node f_){ father_ = f_; }

        // #3 Le noeud gauche
        public Node getLeft(){ return left_; }
        public void setLeft(Node l_){ left_ = l_; }

        // #4 Le noeud droit
        public Node getRight(){ return right_; }
        public void setRight(Node r_){ right_ = r_; }

        /**
         * Permet de retourner le noeud minimal de l'arbre
         * @return le noeud contenant la valeur la plus petite
         */
        Node minimum() {
            Node min = this;
            while (min.getLeft() != null) {
                min = min.getLeft();
            }
            return min;
        }

        /**
         * Permet de retourner le noeud suivant si il existe (pour le parcours)
         * @return
         */
        Node suivant() {
            if (right_ != null) {
                return right_.minimum();
            } else {
                Node p = father_;
                Node current = this;  
                while (p != null && current == p.getRight()) {
                    current = p;
                    p = p.getFather();
                }
                return p;
            }
        }
    }

    // ---------------------------------------------------------------------------------
    // ------------------------------ Constructeurs ------------------------------------
    // ---------------------------------- de la classe BinaryTree ----------------------
    // ---------------------------------------------------------------------------------
    /**
     * Constructeur par défaut de l'arbre
     * Initialisation de root_ et nbNode_ à null et 0 respectivement
     */
    public BinaryTree(){
        this.root_ = null;
        this.nbNode_ = 0;
        this.cmp_ = null;
    }

    /**
     * Constructeur d'un arbre vide en précisant le comparateur
     * @param cmp Comparateur utilisé pour définir l'ordre des éléments
     */
    public BinaryTree(Comparator<? super T> cmp){
        this.root_ = null;
        this.nbNode_ = 0;
        this.cmp_ = cmp;
    }

    public BinaryTree(Collection<? extends T> collect){ addAll(collect); }
    // ---------------------------------------------------------------------------------
    // ------------------------------ Méthodes abstraite -------------------------------
    // ---------------------------------- de la classe BinaryTree ----------------------
    // ---------------------------------------------------------------------------------
    
    // Méthodes d'ajout d'un noeud ou d'une liste de noeuds
    public abstract boolean add(T n_);
    public abstract boolean addAll(Collection<? extends T> collect_);

    // Méthodes de suppression d'un noeud ou d'une liste de noeuds
    public abstract boolean removeValue(T n_);
    public abstract boolean removeAllValue(Collection<? extends T> collect_);

    // Méthode de recherche
    public abstract Node research(Object n_);

    // ---------------------------------------------------------------------------------
    // ------------------------------ Méthodes concrète --------------------------------
    // ---------------------------------- de la classe BinaryTree ----------------------
    // ---------------------------------------------------------------------------------

    // Surcharge de la méthode size() de Collection<T> permettant de retourner le nombre de noeuds
    @Override
    public int size(){ return nbNode_; }

    /**
     * Surcharge de la méthode clear() de Collection<T>
     * Permet de vider l'arbre (le remettre à 0)
     */
    @Override
    public void clear(){ 
        root_ = null;
        nbNode_ = 0;
    }

    /**
     * Surcharge de la méthode remove(Object o) de Collection<T>
     * Elle permet de supprimer un objet
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        try {
            @SuppressWarnings("unchecked")
            T val = (T) o;
            return removeValue(val);
        } catch (ClassCastException e) {
            return false;
        }
    }

    /**
     * Surcharge de removeAll de Collection<T>
     * Permet de supprimer une liste d'objets
     */
    public boolean removeAll(Collection<?> collect){
        boolean modified = false;
        for(Object obj : collect){
            if(removeValue((T) obj)) modified = true;  // unchecked cast
        }
        return modified;
    }

    /**
     * Permet de vérifier si une valeur est dans l'ABR
     */
    public boolean contains(Object o) {
        return research(o) != null;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new TreeIterator();
    }
    
    private class TreeIterator implements java.util.Iterator<T> {
        private BinaryTree<T>.Node current;
        private BinaryTree<T>.Node lastReturned;

        TreeIterator() {
            current = (root_ == null) ? null : root_.minimum();
            lastReturned = null;
        }

        // Parcours infixe : successeur d'un noeud
        private BinaryTree<T>.Node successor(BinaryTree<T>.Node n) {
            if (n == null) return null;
            return n.suivant(); // utilise votre méthode Node.suivant()
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = current;
            current = successor(current);
            return lastReturned.getValue();
        }

        @Override
        public void remove() {
            if (lastReturned == null) {
                throw new IllegalStateException();
            }
            // supprime le dernier élément retourné
            removeValue(lastReturned.getValue());
            lastReturned = null;
        }
    }

    /**
     * La méthode d'affichage
     * Méthode reprise du TP ABR d'Algo avancée
     * @author Stefan Balev
     */
    @Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(root_, buf, "", maxStrLen(root_));
		return buf.toString();
	}

	private void toString(Node x, StringBuffer buf, String path, int len) {
		if (x == null)
			return;
		toString(x.getRight(), buf, path + "D", len);
		for (int i = 0; i < path.length(); i++) {
			for (int j = 0; j < len + 6; j++)
				buf.append(' ');
			char c = ' ';
			if (i == path.length() - 1)
				c = '+';
			else if (path.charAt(i) != path.charAt(i + 1))
				c = '|';
			buf.append(c);
		}
		buf.append("-- " + x.getValue().toString());
		if (x.getLeft() != null || x.getRight() != null) {
			buf.append(" --");
			for (int j = x.getValue().toString().length(); j < len; j++)
				buf.append('-');
			buf.append('|');
		}
		buf.append("\n");
		toString(x.getLeft(), buf, path + "G", len);
	}

	private int maxStrLen(Node x) {
		return x == null ? 0 : Math.max(x.getValue().toString().length(),
				Math.max(maxStrLen(x.getLeft()), maxStrLen(x.getRight())));
	}

    public boolean isEmpty() {
        return root_ == null;
    }
}
