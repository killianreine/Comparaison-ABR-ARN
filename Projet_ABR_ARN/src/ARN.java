/**
 * @author Killian REINE
 * @date 1er décembre 2025
 */

import java.util.Collection;

public class ARN<T> extends ABR<T>{ 

    /**
     * Classe interne Noeud spécifique aux ARN
     * Toutes les classes qui implémentent BinaryTree<T> auront accès a la classe Noeud.
     */
    class NodeRN extends Node {
        boolean isRed;

        public NodeRN(T v_) {
            super(v_);
            this.isRed = true;

            // Initialisation des liens via les setters hérités
            this.setLeft(sentinel_);
            this.setRight(sentinel_);
            this.setFather(sentinel_);
        }

        // Redéfinitions typées pour ARN
        @Override public NodeRN getLeft(){ return (NodeRN) super.getLeft(); }
        @Override public NodeRN getRight(){ return (NodeRN) super.getRight(); }
        @Override public NodeRN getFather(){ return (NodeRN) super.getFather(); }

        public void setLeft(NodeRN n){ super.setLeft(n); }
        public void setRight(NodeRN n){ super.setRight(n); }
        public void setFather(NodeRN n){ super.setFather(n); }

        NodeRN minimum() {
            NodeRN min = this;
            while (min.getLeft() != sentinel_) {
                min = min.getLeft();
            }
            return min;
        }

        NodeRN suivant() {
            if (this.getRight() != sentinel_) {
                return this.getRight().minimum();
            } else {
                NodeRN p = this.getFather();
                NodeRN current = this; 
                while (p != sentinel_ && current == p.getRight()) {
                    current = p;
                    p = p.getFather();
                }
                return p;
            }
        }
    }

    protected NodeRN root_;
    protected final NodeRN sentinel_ = new NodeRN(null);
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    /**
     * Constructeur de la classe ARN
     * Initialise un arbre ARN vide avec une sentinelle
     */
    public ARN() {
        sentinel_.isRed = false; // La sentinelle est noire
        root_ = sentinel_;
    }

    /**
     *  Méthode permettant d'ajouter un élément à notre ARN
     * @param n_ la valeur à encapsuler dans le Noeud
     * @return un booléen qui permet de savoir si la valeur a été ajoutée
     */
    public boolean add(T n_) {  
        NodeRN n = new NodeRN(n_);
        NodeRN y = sentinel_;
        NodeRN x = root_;

        // Recherche de la position d'insertion
        while (x != sentinel_) {
            y = x;
            int cmp = compare(n.getValue(), x.getValue());
            if (cmp < 0) {
                x = x.getLeft();
            } else if (cmp > 0) {
                x = x.getRight();
            } else {
                // La valeur existe déjà - on n'ajoute pas de doublon
                return false;
            }
        }

        // Insertion
        n.setFather(y);
        if (y == sentinel_) {
            root_ = n; // l'arbre était vide
        } else if (compare(n.getValue(), y.getValue()) < 0) {
            y.setLeft(n);
        } else {
            y.setRight(n);
        }

        // Initialisation des fils et couleur
        n.setLeft(sentinel_);
        n.setRight(sentinel_);
        n.isRed = true;

        // Correction des propriétés de l'ARN
        ajouterCorrection(n);

        nbNode_++;
        return true;
    }

    private void ajouterCorrection(NodeRN z) {
        while (z.getFather().isRed) {   // parent rouge >>> violation

            // parent est enfant gauche du grand-parent
            if (z.getFather() == z.getFather().getFather().getLeft()) {

                NodeRN y = (NodeRN) z.getFather().getFather().getRight();  // oncle

                // ----- CAS 1 : l'oncle est rouge -----
                if (y.isRed) {
                    z.getFather().isRed = false;
                    y.isRed = false;
                    z.getFather().getFather().isRed = true;
                    z = z.getFather().getFather();
                } else {

                    // ----- CAS 2 : triangle -----
                    if (z == z.getFather().getRight()) {
                        z = z.getFather();
                        leftRotate(z);
                    }

                    // ----- CAS 3 : ligne -----
                    z.getFather().isRed = false;
                    z.getFather().getFather().isRed = true;
                    rightRotate(z.getFather().getFather());
                }

            } else { 
                // ----- PARTIE MIROIR -----
                NodeRN y = (NodeRN) z.getFather().getFather().getLeft(); // oncle (miroir)

                // ----- CAS 1' : oncle rouge -----
                if (y.isRed) {
                    z.getFather().isRed = false;
                    y.isRed = false;
                    z.getFather().getFather().isRed = true;
                    z = z.getFather().getFather();
                } else {

                    // ----- CAS 2' : triangle -----
                    if (z == z.getFather().getLeft()) {
                        z = z.getFather();
                        rightRotate(z);
                    }

                    // ----- CAS 3' : ligne -----
                    z.getFather().isRed = false;
                    z.getFather().getFather().isRed = true;
                    leftRotate(z.getFather().getFather());
                }
            }
        }

        // racine doit être noire
        root_.isRed = false;
    }

    /**
     * Méthode ajouter
     * Surcharge de addAll pour Collection<T>
     * On ajoute toutes les valeurs contenues dans la collection à notre arbre
     */
    public boolean addAll(Collection<? extends T> collect_){
        boolean modified = false;
        for(T elem : collect_){
            if(add(elem)) modified = true;;
        }
        return modified;
    }

    public boolean removeValue(T n_) {
        NodeRN z = research(n_);
        if (z == sentinel_ || z == null) return false;

        NodeRN y = z;        // noeud à détacher
        NodeRN x;

        // Si z a deux enfants réels, on prend son successeur
        if (z.getLeft() != sentinel_ && z.getRight() != sentinel_) {
            y = z.suivant();  // successeur
        }

        // x = unique fils de y (ou sentinelle)
        x = (y.getLeft() != sentinel_) ? y.getLeft() : y.getRight();

        // rattacher le père de y à x
        x.setFather(y.getFather());

        if (y.getFather() == sentinel_) {
            root_ = x;  // y était racine
        } else if (y == y.getFather().getLeft()) {
            y.getFather().setLeft(x);
        } else {
            y.getFather().setRight(x);
        }

        // copier la valeur si y != z
        if (y != z) z.setValue(y.getValue());

        // correction si y était noir
        if (!y.isRed) supprimerCorrection(x);

        nbNode_--;
        return true;
    }

    private void supprimerCorrection(NodeRN x){
        while (x != root_ && !x.isRed) {
            // (*) est vérifié ici
            if (x == x.getFather().getLeft()) {

                NodeRN w = x.getFather().getRight(); // le frère de x

                if (w.isRed) {
                    // cas 1
                    w.isRed = false;
                    x.getFather().isRed = true;
                    leftRotate(x.getFather());
                    w = x.getFather().getRight();
                }

                if (!w.getLeft().isRed && !w.getRight().isRed) {
                    // cas 2
                    w.isRed = true;
                    x = x.getFather();
                } else {

                    if (!w.getRight().isRed) {
                        // cas 3
                        w.getLeft().isRed = false;
                        w.isRed = true;
                        rightRotate(w);
                        w = x.getFather().getRight();
                    }

                    // cas 4
                    w.isRed = x.getFather().isRed;
                    x.getFather().isRed = false;
                    w.getRight().isRed = false;
                    leftRotate(x.getFather());
                    x = root_;
                }

            } else {
                // idem en miroir, gauche <-> droite

                NodeRN w = x.getFather().getLeft(); // frère de x

                if (w.isRed) {
                    // cas 1'
                    w.isRed = false;
                    x.getFather().isRed = true;
                    rightRotate(x.getFather());
                    w = x.getFather().getLeft();
                }

                if (!w.getLeft().isRed && !w.getRight().isRed) {
                    // cas 2'
                    w.isRed = true;
                    x = x.getFather();
                } else {

                    if (!w.getLeft().isRed) {
                        // cas 3'
                        w.getRight().isRed = false;
                        w.isRed = true;
                        leftRotate(w);
                        w = x.getFather().getLeft();
                    }

                    // cas 4'
                    w.isRed = x.getFather().isRed;
                    x.getFather().isRed = false;
                    w.getLeft().isRed = false;
                    rightRotate(x.getFather());
                    x = root_;
                }
            }
        }

        // (**) est vérifié ici
        x.isRed = false;
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

    // ---------------------------------------------------------------------------------
    // ------------------------------ Les rotations ------------------------------------
    // ---------------------------------- de la classe ARN -----------------------------
    // ---------------------------------------------------------------------------------
    private void leftRotate(NodeRN x) {
        NodeRN y = x.getRight(); // y = fils droit de x
        x.setRight(y.getLeft()); // le sous-arbre gauche de y devient le fils droit de x

        if (y.getLeft() != sentinel_) {
            y.getLeft().setFather(x);
        }

        y.setFather(x.getFather()); // le parent de y devient le parent de x

        if (x.getFather() == sentinel_) {
            root_ = y; // si x était racine → y devient racine
        } else if (x == x.getFather().getLeft()) {
            x.getFather().setLeft(y);
        } else {
            x.getFather().setRight(y);
        }

        y.setLeft(x); // x devient fils gauche de y
        x.setFather(y);
    }


    private void rightRotate(NodeRN y) {
        NodeRN x = y.getLeft(); // x = fils gauche de y
        y.setLeft(x.getRight()); // le sous-arbre droit de x devient le fils gauche de y

        if (x.getRight() != sentinel_) {
            x.getRight().setFather(y);
        }

        x.setFather(y.getFather()); // le parent de x devient le parent de y

        if (y.getFather() == sentinel_) {
            root_ = x; // si y était racine → x devient racine
        } else if (y == y.getFather().getRight()) {
            y.getFather().setRight(x);
        } else {
            y.getFather().setLeft(x);
        }

        x.setRight(y); // y devient fils droit de x
        y.setFather(x);
    }

    // ---------------------------------------------------------------------------------
    // ------------------------------ Méthodes d'affichage -----------------------------
    // ---------------------------------- de la classe ARN -----------------------------
    // ---------------------------------------------------------------------------------

    /**
     * Affichage amélioré de l'arbre rouge-noir
     * Les nœuds rouges sont colorés en rouge
     * Les sentinelles ne sont plus affichées pour plus de clarté
     */

    @Override
    public String toString() {
        if (root_ == sentinel_) {
            return "Arbre vide\n";
        }
        System.out.println("\n");
        StringBuffer buf = new StringBuffer();
        toStringColored(root_, buf, "", maxStrLen(root_));
        return buf.toString();
    }

    /**
     * Affichage récursif de l'arbre avec coloration des nœuds rouges
     * Les sentinelles sont affichées comme des blocs vides ☒
     * 
     * @param x     le nœud courant
     * @param buf   le buffer de construction de la chaîne
     * @param path  la chaîne représentant le chemin pour l'indentation
     * @param len   largeur maximale d'affichage d'une valeur
     */
    private void toStringColored(NodeRN x, StringBuffer buf, String path, int len) {
        if (x == null)
            return;

        // Parcours du fils droit en premier (pour affichage en arbre couché)
        toStringColored(x.getRight(), buf, path + "D", len);

        // Indentation selon le chemin
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

        // Affichage de la valeur ou sentinelle
        String value;
        if (x == sentinel_) {
            value = "☒";  // Bloc vide pour les sentinelles
        } else {
            value = x.getValue().toString();
            if (x.isRed) {
                value = RED + value + RESET;
            }
        }

        buf.append("-- " + value);

        // Affichage des branches uniquement pour les nœuds non-sentinelles
        if (x != sentinel_) {
            buf.append(" --");
            // Ajustement pour la longueur réelle (sans les codes ANSI)
            int realLen = x.getValue().toString().length();
            for (int j = realLen; j < len; j++)
                buf.append('-');
            buf.append('|');
        }
        buf.append("\n");

        // Parcours du fils gauche
        toStringColored(x.getLeft(), buf, path + "G", len);
    }

    /**
     * Calcule la longueur maximale des valeurs dans l'arbre
     * (sans les sentinelles)
     */
    private int maxStrLen(NodeRN x) {
        if (x == null || x == sentinel_) 
            return 0;
        
        int valLen = x.getValue().toString().length();
        int leftLen = maxStrLen(x.getLeft());
        int rightLen = maxStrLen(x.getRight());
        
        return Math.max(valLen, Math.max(leftLen, rightLen));
    }

    /**
     * Méthode de recherche dans l'arbre ARN
     * @param o La valeur à rechercher
     * @return Le noeud contenant la valeur recherchée, ou sentinel_ si non trouvé
     */
    public NodeRN research(Object o){
        NodeRN current = root_;

        while (current != sentinel_) {
            @SuppressWarnings("unchecked")
            int comparison = (cmp_ == null ?
                    ((Comparable<? super T>) o).compareTo(current.getValue()) :
                    cmp_.compare((T) o, current.getValue()));

            if (comparison < 0) {
                current = current.getLeft();
            } else if (comparison > 0) {
                current = current.getRight();
            } else {
                return current;  // trouvé
            }
        }

        return sentinel_; // non trouvé
    }
}
