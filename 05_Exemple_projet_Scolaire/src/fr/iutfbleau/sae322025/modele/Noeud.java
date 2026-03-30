package fr.iutfbleau.sae322025.modele;


/**
 * Represente un noeud interne de l'arbre binaire de Huffman.
 *
 * Un noeud stocke une frequence et des references vers ses deux fils.
 * Il peut etre soit un noeud interne (avec fils), soit une feuille (sans fils).
 * La comparaison entre noeuds est basee sur la frequence, ce qui permet
 * leur utilisation dans une {@link java.util.PriorityQueue} lors de la
 * construction de l'arbre de Huffman.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */

public class Noeud implements Comparable<Noeud> {


    /**
     * Frequence associee a ce noeud.
     * Pour une feuille, c'est le nombre d'occurrences de la valeur dans l'image.
     * Pour un noeud interne, c'est la somme des frequences de ses deux fils.
     */

    protected int frequence;


    /**
     * Fils gauche de ce noeud. Vaut null si ce noeud est une feuille.
     * Un deplacement vers le fils gauche correspond au bit '0' dans le code.
     */

    protected Noeud filsGauche;


    /**
     * Fils droit de ce noeud. Vaut null si ce noeud est une feuille.
     * Un deplacement vers le fils droit correspond au bit '1' dans le code.
     */


    protected Noeud filsDroit;


    /**
     * Construit un noeud sans fils avec la frequence donnee.
     * Utilise pour preparer un noeud intermediaire lors de la reconstruction
     * de l'arbre en decompression.
     *
     * @param frequence La frequence associee a ce noeud.
     */


    public Noeud(int frequence) {
        this.frequence = frequence;
        this.filsGauche = null;
        this.filsDroit = null;
    }



    /**
     * Construit un noeud interne par fusion de deux noeuds fils.
     * La frequence du noeud parent est la somme des frequences de ses deux fils.
     * Utilise lors de la construction de l'arbre de Huffman par la file de priorite.
     *
     * @param filsGauche Le fils gauche (noeud de plus faible frequence).
     * @param filsDroit  Le fils droit (noeud de frequence superieure ou egale).
     */


    public Noeud(Noeud filsGauche, Noeud filsDroit) {
        this.frequence = filsGauche.getFrequence() + filsDroit.getFrequence();
        this.filsGauche = filsGauche;
        this.filsDroit = filsDroit;
    }



    /**
     * Retourne la frequence associee a ce noeud.
     *
     * @return La frequence de ce noeud.
     */

    public int getFrequence() {
        return frequence;
    }



    /**
     * Retourne le fils gauche de ce noeud.
     *
     * @return Le fils gauche, ou null si ce noeud est une feuille.
     */

    public Noeud getFilsGauche() {
        return filsGauche;
    }


    /**
     * Retourne le fils droit de ce noeud.
     *
     * @return Le fils droit, ou null si ce noeud est une feuille.
     */

    public Noeud getFilsDroit() {
        return filsDroit;
    }


    /**
     * Definit le fils gauche de ce noeud.
     * Utilise lors de la reconstruction de l'arbre en decompression.
     *
     * @param g Le noeud a placer comme fils gauche.
     */

    public void setFilsGauche(Noeud g) {
        this.filsGauche = g;
    }


    /**
     * Definit le fils droit de ce noeud.
     * Utilise lors de la reconstruction de l'arbre en decompression.
     *
     * @param d Le noeud a placer comme fils droit.
     */

    public void setFilsDroit(Noeud d) {
        this.filsDroit = d;
    }


    /**
     * Indique si ce noeud est une feuille de l'arbre.
     * Un noeud est une feuille si et seulement si ses deux fils sont null.
     *
     * @return true si ce noeud n'a aucun fils, false sinon.
     */

    public boolean estUneFeuille() {
        return (this.filsGauche == null) && (this.filsDroit == null);
    }


    /**
     * Compare ce noeud a un autre selon leur frequence.
     * Permet l'utilisation des noeuds dans une {@link java.util.PriorityQueue}
     * ou le noeud de plus faible frequence est traite en priorite.
     *
     * @param autre L'autre noeud avec lequel comparer.
     * @return Un entier negatif, zero, ou positif si la frequence de ce noeud
     *         est respectivement inferieure, egale, ou superieure a celle de l'autre.
     */


    @Override
    public int compareTo(Noeud autre) {
        return Integer.compare(this.frequence, autre.getFrequence());
    }

}