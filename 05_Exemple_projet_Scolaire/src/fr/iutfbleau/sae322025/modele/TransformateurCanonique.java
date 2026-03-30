package fr.iutfbleau.sae322025.modele;

import java.util.HashMap;
import java.util.Map;

/**
 * Transforme des codes de Huffman classiques en codes canoniques et les reconstruit.
 *
 * Les codes canoniques sont obtenus en triant les codes initiaux par longueur puis
 * par valeur, puis en leur attribuant de nouveaux codes selon un schema incrementiel.
 * Cette forme permet au decompresseur de reconstituer les codes en ne connaissant
 * que les longueurs, ce qui reduit la taille des metadonnees dans le fichier PIF.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */
public class TransformateurCanonique {



    /**
     * Applique la transformation canonique sur un dictionnaire de codes de Huffman.
     *
     * Pour chaque longueur de code (de 1 a la longueur maximale) et pour chaque
     * valeur dans l'ordre croissant, attribue un nouveau code canonique.
     * Le premier code est toujours 0. Chaque code suivant est le precedent
     * incremente de 1 puis decale a gauche pour atteindre la longueur souhaitee.
     *
     * @param codesInitiaux La map associant chaque valeur de composante a son code de Huffman initial.
     * @return Une nouvelle map associant chaque valeur a son code canonique equivalent.
     */



    public Map<Integer, Code> transformer(Map<Integer, Code> codesInitiaux) {


        Map<Integer, Code> codesCanoniques = new HashMap<>();


        // Extraction des longueurs de chaque code initial dans un tableau indexe par valeur
        int[] longueurs = new int[256];
        int longueurMax = 0;


        for (Map.Entry<Integer, Code> entree : codesInitiaux.entrySet()) {

            int valeur = entree.getKey();
            int longueur = entree.getValue().getNbBits();
            longueurs[valeur] = longueur;


            if (longueur > longueurMax) {

                longueurMax = longueur;

            }
        }

        long valeurBinaire = 0;
        int longueurPrecedente = 0;
        boolean premier = true;

        // Parcours par longueur croissante puis par valeur croissante
        for (int len = 1; len <= longueurMax; len++) {

            for (int val = 0; val < 256; val++) {


                if (longueurs[val] == len) {

                    if (premier) {

                        // Le premier code canonique est toujours 0
                        valeurBinaire = 0;
                        premier = false;

                    } else {

                        // Incrementation puis decalage a gauche pour atteindre la nouvelle longueur
                        valeurBinaire++;
                        int decalage = len - longueurPrecedente;
                        valeurBinaire = valeurBinaire << decalage;

                    }

                    longueurPrecedente = len;


                    // Conversion en chaine binaire avec zero-padding a gauche si necessaire
                    String texteBinaire = Long.toBinaryString(valeurBinaire);

                    while (texteBinaire.length() < len) {

                        texteBinaire = "0" + texteBinaire;

                    }


                    // Construction du code canonique bit par bit
                    Code nouveauCode = new Code();

                    for (char bit : texteBinaire.toCharArray()) {

                        nouveauCode.ajouterBit(bit);
                    }

                    codesCanoniques.put(val, nouveauCode);
                }

            }

        }

        return codesCanoniques;

    }



    /**
     * Reconstruit un dictionnaire de codes canoniques a partir d'un tableau de longueurs.
     *
     * Applique le meme schema incrementiel que {@link #transformer(Map)} mais en partant
     * directement des longueurs lues dans le fichier PIF (256 octets par composante).
     * Un longueur a 0 signifie que la valeur est absente de l'image et est ignoree.
     * Cette methode est utilisee par le decompresseur pour reconstruire les arbres de Huffman.
     *
     * @param longueurs Tableau de 256 entiers ou longueurs[i] est la longueur du code
     *                  canonique de la valeur i (0 signifie valeur absente).
     * @return Une map associant chaque valeur presente a son {@link Code} canonique reconstruit.
     */


    public Map<Integer, Code> reconstruireDepuisLongueurs(int[] longueurs) {


        Map<Integer, Code> codesCanoniques = new HashMap<>();


        // Recherche de la longueur maximale pour borner le parcours
        int longueurMax = 0;

        for (int i = 0; i < longueurs.length; i++) {

            if (longueurs[i] > longueurMax) {

                longueurMax = longueurs[i];

            }

        }


        long valeurBinaire = 0;
        int longueurPrecedente = 0;
        boolean premier = true;



        // Parcours par longueur croissante puis par valeur croissante
        for (int len = 1; len <= longueurMax; len++) {

            for (int val = 0; val < 256; val++) {

                if (longueurs[val] == len) {

                    if (premier) {

                        // Le premier code canonique est toujours 0
                        valeurBinaire = 0;

                        premier = false;

                    } else {

                        // Incrementation puis decalage a gauche pour atteindre la nouvelle longueur
                        valeurBinaire++;
                        int decalage = len - longueurPrecedente;
                        valeurBinaire = valeurBinaire << decalage;

                    }


                    longueurPrecedente = len;


                    // Zero-padding a gauche pour atteindre la longueur cible
                    String texteBinaire = Long.toBinaryString(valeurBinaire);
                    int zerosAAjouter = len - texteBinaire.length();
                    String prefixe = "";


                    for (int j = 0; j < zerosAAjouter; j++) {

                        prefixe = prefixe + "0";

                    }

                    texteBinaire = prefixe + texteBinaire;


                    // Construction du code canonique bit par bit
                    Code nouveauCode = new Code();


                    for (int k = 0; k < texteBinaire.length(); k++) {

                        char bit = texteBinaire.charAt(k);
                        nouveauCode.ajouterBit(bit);
                    }



                    codesCanoniques.put(val, nouveauCode);
                }

            }

        }


        return codesCanoniques;

    }


}