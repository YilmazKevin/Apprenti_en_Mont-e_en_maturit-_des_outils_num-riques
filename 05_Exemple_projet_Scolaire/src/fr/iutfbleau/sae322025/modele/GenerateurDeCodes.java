package fr.iutfbleau.sae322025.modele;

import java.util.HashMap;
import java.util.Map;



/**
 * Generateur de codes binaires de Huffman par parcours recursif de l'arbre.
 *
 * Parcourt l'arbre de Huffman en profondeur et associe a chaque valeur de composante
 * coloree le code binaire correspondant a son chemin depuis la racine.
 * Un deplacement vers le fils gauche ajoute un '0', vers le fils droit un '1'.
 * Le resultat est stocke dans un dictionnaire (valeur -> code).
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class GenerateurDeCodes {



    /**
     * Dictionnaire associant chaque valeur de composante coloree (0 a 255)
     * a son {@link Code} binaire de Huffman genere par parcours de l'arbre.
     */


    private Map<Integer, Code> dictionnaire;


    /**
     * Construit un generateur de codes avec un dictionnaire vide.
     * Le dictionnaire est rempli lors de l'appel a {@link #generer(Noeud)}.
     */

    public GenerateurDeCodes() {

        this.dictionnaire = new HashMap<>();
    }



    /**
     * Lance la generation des codes binaires a partir de la racine de l'arbre de Huffman.
     *
     * Demarre le parcours recursif depuis la racine avec un chemin vide.
     * Ne fait rien si la racine est null.
     *
     * @param racine La racine de l'arbre de Huffman a parcourir.
     */


    public void generer(Noeud racine) {

        if (racine != null) {

            this.parcourir(racine, "");
        }

    }



    /**
     * Parcourt recursivement l'arbre de Huffman pour construire les codes binaires.
     *
     * A chaque noeud interne, descend a gauche en ajoutant '0' au chemin,
     * et a droite en ajoutant '1'. Lorsqu'une feuille est atteinte,
     * le chemin accumule devient le code binaire de la valeur qu'elle contient.
     *
     * @param noeud  Le noeud courant du parcours.
     * @param chemin La sequence de bits accumulee depuis la racine jusqu'au noeud courant.
     */


    private void parcourir(Noeud noeud, String chemin) {


        if (noeud.estUneFeuille()) {

            // Feuille atteinte : le chemin parcouru est le code de cette valeur
            Feuille feuille = (Feuille) noeud;
            Code nouveauCode = new Code(chemin);
            this.dictionnaire.put(feuille.getValeur(), nouveauCode);


        } else {

            // Noeud interne : on continue la descente dans les deux sous-arbres
            if (noeud.getFilsGauche() != null) {

                // Descente a gauche : on ajoute '0' au chemin
                parcourir(noeud.getFilsGauche(), chemin + "0");
            }


            if (noeud.getFilsDroit() != null) {
                // Descente a droite : on ajoute '1' au chemin
                parcourir(noeud.getFilsDroit(), chemin + "1");
            }

        }

    }



    /**
     * Retourne le dictionnaire des codes generes apres le parcours de l'arbre.
     *
     * @return La map associant chaque valeur de composante coloree a son {@link Code} de Huffman.
     */


    public Map<Integer, Code> getDictionnaire() {
        return this.dictionnaire;
    }


}
