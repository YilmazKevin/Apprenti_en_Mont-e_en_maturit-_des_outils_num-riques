package fr.iutfbleau.sae322025.modele.io;

import fr.iutfbleau.sae322025.modele.Code;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;



/**
 * Classe d'ecriture binaire bit par bit dans un fichier.
 *
 * Gere un tampon interne d'un octet pour accumuler les bits avant de les ecrire.
 * Lorsque le tampon est plein (8 bits), il est vide dans le flux de sortie.
 * A la fermeture, les bits restants sont completes par des zeros a droite
 * pour former un dernier octet complet.
 *
 * Utilisee par le {@link fr.iutfbleau.sae322025.modele.Compresseur} pour
 * ecrire l'en-tete, les tables de longueurs et les pixels compresses du fichier PIF.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class EcritureBinaire {


    /**
     * Flux de sortie vers le fichier PIF, encapsule dans un BufferedOutputStream
     * pour limiter les acces disque.
     */


    private OutputStream flux;


    /**
     * Tampon d'un octet dans lequel les bits sont accumules avant ecriture.
     * Les bits sont places de gauche a droite (du bit de poids fort au bit de poids faible).
     */

    private int tampon;


    /**
     * Nombre de bits actuellement accumules dans le tampon (entre 0 et 7).
     * Quand il atteint 8, le tampon est ecrit dans le flux et reinitialise.
     */

    private int nbBitsDansTampon;


    /**
     * Construit un flux d'ecriture binaire vers le fichier specifie.
     *
     * @param chemin Le chemin du fichier de sortie a creer ou ecraser.
     * @throws IOException Si le fichier ne peut pas etre cree ou ouvert en ecriture.
     */


    public EcritureBinaire(String chemin) throws IOException {
        this.flux = new BufferedOutputStream(new FileOutputStream(chemin));
        this.tampon = 0;
        this.nbBitsDansTampon = 0;
    }


    /**
     * Ecrit un seul bit dans le flux.
     *
     * Decale le tampon d'un bit vers la gauche et place le nouveau bit en position
     * de poids faible. Si le tampon atteint 8 bits, il est ecrit dans le flux
     * et reinitialise a zero.
     *
     * @param bit true pour ecrire '1', false pour ecrire '0'.
     * @throws IOException Si une erreur survient lors de l'ecriture dans le flux.
     */


    public void ecrireBit(boolean bit) throws IOException {

        // Decalage a gauche pour faire de la place au nouveau bit
        this.tampon = this.tampon << 1;

        if (bit) {
            // Placement du bit '1' en position de poids faible
            this.tampon = this.tampon | 1;
        }

        this.nbBitsDansTampon++;

        // Si le tampon est plein, on ecrit l'octet dans le flux et on reinitialise
        if (this.nbBitsDansTampon == 8) {
            this.flux.write(this.tampon);
            this.tampon = 0;
            this.nbBitsDansTampon = 0;
        }
    }


    /**
     * Ecrit tous les bits d'un {@link Code} dans le flux, du bit de poids fort au plus faible.
     *
     * @param code Le code binaire dont les bits sont a ecrire.
     * @throws IOException Si une erreur survient lors de l'ecriture dans le flux.
     */


    public void ecrireCode(Code code) throws IOException {
        int taille = code.getNbBits();

        for (int i = 0; i < taille; i++) {
            boolean bit = code.getBitAt(i);
            this.ecrireBit(bit);
        }
    }


    /**
     * Ecrit une chaine de caracteres de bits ('0' et '1') dans le flux.
     *
     * @param bits La chaine de bits a ecrire (ex : "10110").
     * @throws IOException              Si une erreur survient lors de l'ecriture dans le flux.
     * @throws IllegalArgumentException Si la chaine contient un caractere autre que '0' ou '1'.
     */


    public void ecrireChaineBits(String bits) throws IOException {
        for (char c : bits.toCharArray()) {
            if (c == '1') {
                ecrireBit(true);
            } else if (c == '0') {
                ecrireBit(false);
            } else {
                throw new IllegalArgumentException("La chaine doit contenir uniquement 0 ou 1");
            }
        }
    }



    /**
     * Ecrit un octet (8 bits) dans le flux, du bit de poids fort au bit de poids faible.
     *
     * @param octet La valeur a ecrire (seuls les 8 bits de poids faible sont utilises).
     * @throws IOException Si une erreur survient lors de l'ecriture dans le flux.
     */

    public void ecrireOctet(int octet) throws IOException {
        // Ecriture des 8 bits de poids fort a poids faible
        for (int i = 7; i >= 0; i--) {
            boolean bit = ((octet >> i) & 1) == 1;
            ecrireBit(bit);
        }
    }



    /**
     * Ecrit un entier sur 2 octets (16 bits) dans le flux, en big-endian.
     * Utilise pour ecrire la largeur et la hauteur de l'image dans l'en-tete PIF.
     *
     * @param valeur La valeur a ecrire sur 2 octets (0 a 65535).
     * @throws IOException Si une erreur survient lors de l'ecriture dans le flux.
     */

    public void ecrireShort(int valeur) throws IOException {
        // Octet de poids fort (bits 15 a 8) puis octet de poids faible (bits 7 a 0)
        ecrireOctet((valeur >> 8) & 0xFF);
        ecrireOctet(valeur & 0xFF);
    }



    /**
     * Vide le tampon restant et ferme le flux de sortie.
     *
     * Si des bits sont encore dans le tampon (moins de 8), ils sont completes
     * par des zeros a droite pour former un dernier octet complet avant ecriture.
     *
     * @throws IOException Si une erreur survient lors de la fermeture du flux.
     */



    public void fermer() throws IOException {

        if (this.nbBitsDansTampon > 0) {

            // Completion du dernier octet par des zeros a droite
            while (this.nbBitsDansTampon < 8) {

                this.tampon = this.tampon << 1;
                this.nbBitsDansTampon++;
            }

            this.flux.write(this.tampon);
        }

        this.flux.close();
    }

}