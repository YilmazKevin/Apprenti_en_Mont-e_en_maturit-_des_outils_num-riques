package fr.iutfbleau.sae322025.modele;
/**
 * Cette classe sert de dictionnaire pour les fréquences d'apparition.
 * Elle stocke combien de fois chaque valeur (de 0 à 255) a été rencontrée.
 * * @author Yilmaz Kevin, Adam Barhani
 */




public class TableDeFrequences {




    private int[] frequences;




    /**
     * Initialise une table vide avec 256 entrées (une par valeur d'octet).
     */

    public TableDeFrequences() {
        this.frequences = new int[256];
    }





    /**
     * Incrémente le compteur pour une valeur donnée.
     * @param valeur L'indice de l'octet (0-255).
     */

    public void ajouter(int valeur) {
        if (valeur >= 0 && valeur < 256) {
            this.frequences[valeur]++;
        }
    }




    /**
     * Récupère le nombre d'apparitions d'une valeur précise.
     * @param valeur L'octet recherché.
     * @return Le nombre d'occurrences.
     */


    public int getFrequence(int valeur) {
        return this.frequences[valeur];
    }





    /**
     * Permet d'accéder au tableau complet (utile pour l'arbre de Huffman).
     * @return Le tableau des 256 fréquences.
     */



    public int[] getToutesLesFrequences() {
        return this.frequences;
    }



}