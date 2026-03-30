package fr.iutfbleau.sae322025;

import fr.iutfbleau.sae322025.vue.FenetreConvertisseur;


/**
 * Point d'entree du programme Convertisseur PIF.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */

public class Convertisseur {

    /**
     * Lance le programme Convertisseur.
     *
     * @param args args[0] optionnel : chemin de l'image source.
     *             args[1] optionnel : chemin du fichier .pif de sortie.
     */

    public static void main(String[] args) {
        FenetreConvertisseur fenetre = new FenetreConvertisseur(args);
        fenetre.setVisible(true);
    }
}
