package fr.iutfbleau.sae322025.modele;




/**
 * Represente une feuille de l'arbre binaire de Huffman.
 *
 * Une feuille est un noeud terminal de l'arbre : elle ne possede pas de fils
 * et contient la valeur d'une composante coloree (entre 0 et 255) ainsi que
 * sa frequence d'apparition dans l'image. Le code binaire de Huffman associe
 * a cette valeur est determine par le chemin parcouru depuis la racine jusqu'a
 * cette feuille.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class Feuille extends Noeud {



    /**
     * Valeur de la composante coloree representee par cette feuille (entre 0 et 255).
     */

    private int valeur;


    /**
     * Construit une feuille avec la valeur de composante et la frequence donnees.
     *
     * @param valeur    La valeur de la composante coloree (entre 0 et 255).
     * @param frequence Le nombre d'occurrences de cette valeur dans l'image.
     */
    
    public Feuille(int valeur, int frequence) {
        super(frequence);
        this.valeur = valeur;
    }



    /**
     * Retourne la valeur de la composante coloree stockee dans cette feuille.
     *
     * @return La valeur de la composante (entre 0 et 255).
     */


    public int getValeur() {
        return valeur;
    }



    /**
     * Indique que ce noeud est toujours une feuille.
     * Surcharge la methode de {@link Noeud} pour retourner systematiquement true,
     * sans verifier les fils (une feuille n'en a jamais).
     *
     * @return true dans tous les cas.
     */

    @Override
    public boolean estUneFeuille() {
        return true;
    }

}