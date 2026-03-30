package fr.iutfbleau.sae322025.modele;



/**
 * Represente un code binaire de longueur variable utilise dans la compression de Huffman.
 *
 * Un code est une sequence de bits ('0' et '1') associee a une valeur de composante coloree.
 * Cette classe est utilisee aussi bien lors de la compression (generation des codes)
 * que lors de la decompression (reconstruction et parcours des codes canoniques).
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class Code {



    /**
     * Sequence de bits representee sous forme de chaine de caracteres ('0' et '1').
     */

    private String bits;



    /**
     * Nombre de bits composant ce code (longueur de la sequence).
     */


    private int nbBits;



    /**
     * Construit un code binaire vide.
     * Les bits sont ajoutes ensuite via {@link #ajouterBit(char)}.
     */


    public Code() {

        this.bits = "";

        this.nbBits = 0;

    }



    /**
     * Construit un code binaire a partir d'une chaine de caracteres de bits.
     *
     * Parcourt chaque caractere du chemin fourni et l'ajoute via {@link #ajouterBit(char)}.
     * Utilise notamment par le generateur de codes lors du parcours de l'arbre de Huffman.
     *
     * @param chemin La chaine de bits representant le code (ex : "101").
     */


    public Code(String chemin) {
        this.bits = "";
        this.nbBits = 0;

        for (char c : chemin.toCharArray()) {

            this.ajouterBit(c);
        }

    }



    /**
     * Construit un code binaire a partir d'une valeur numerique et d'une longueur cible.
     *
     * Convertit la valeur en representation binaire, complete avec des zeros a gauche
     * si necessaire pour atteindre la longueur souhaitee, ou tronque a droite si trop long.
     * Utilise par le transformateur canonique pour reconstruire les codes depuis leurs longueurs.
     *
     * @param bits   La valeur numerique representant le code binaire.
     * @param nbBits La longueur cible du code en nombre de bits.
     */


    public Code(long bits, int nbBits) {
        String texte = Long.toBinaryString(bits);


        // Completion avec des zeros a gauche si la representation est trop courte
        while (texte.length() < nbBits) {
            texte = "0" + texte;
        }


        // Troncature a droite si la representation depasse la longueur cible
        if (texte.length() > nbBits) {
            texte = texte.substring(texte.length() - nbBits);
        }


        this.bits = texte;
        this.nbBits = nbBits;

    }




    /**
     * Ajoute un bit a la fin de la sequence de ce code.
     *
     * @param bit Le caractere a ajouter, doit valoir '0' ou '1'.
     * @throws IllegalArgumentException Si le caractere fourni n'est ni '0' ni '1'.
     */


    public void ajouterBit(char bit) {

        if (bit != '0' && bit != '1') {

            throw new IllegalArgumentException("Un bit doit valoir '0' ou '1'.");
        }

        this.bits = this.bits + bit;
        this.nbBits++;

    }



    /**
     * Retourne la sequence de bits sous forme de chaine de caracteres.
     *
     * @return La chaine de bits de ce code (ex : "101").
     */


    public String getBits() {

        return this.bits;

    }




    /**
     * Retourne la valeur du bit a la position donnee.
     *
     * @param index L'index du bit (0 pour le bit le plus a gauche).
     * @return true si le bit vaut '1', false s'il vaut '0'.
     */


    public boolean getBitAt(int index) {

        return this.bits.charAt(index) == '1';

    }



    /**
     * Retourne la longueur de ce code en nombre de bits.
     *
     * @return Le nombre de bits composant ce code.
     */

    public int getNbBits() {

        return this.nbBits;

    }



    /**
     * Retourne la representation textuelle de ce code (sa sequence de bits).
     *
     * @return La chaine de bits de ce code (ex : "101").
     */


    @Override
    public String toString() {
        return this.bits;
    }

}