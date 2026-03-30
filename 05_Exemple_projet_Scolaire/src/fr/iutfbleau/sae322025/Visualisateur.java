package fr.iutfbleau.sae322025;

import fr.iutfbleau.sae322025.vue.FenetreVisualisation;

/**
 * Point d'entree du programme Visualisateur PIF.
 * Lance la fenetre de visualisation avec les arguments fournis.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */
public class Visualisateur {

    /**
     * Lance le programme Visualisateur.
     *
     * @param args args[0] optionnel : chemin vers le fichier .pif a ouvrir.
     */
    public static void main(String[] args) {
        FenetreVisualisation fenetre = new FenetreVisualisation(args);
        fenetre.setVisible(true);
    }
}
