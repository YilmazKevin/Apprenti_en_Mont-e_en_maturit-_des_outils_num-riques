package fr.iutfbleau.sae322025.modele.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



/**
 * Classe de lecture binaire bit par bit depuis un fichier.
 *
 * Miroir de {@link EcritureBinaire} : lit les donnees ecrites par cette derniere
 * en respectant le meme ordre (big-endian, bit de poids fort en premier).
 * Gere un tampon interne d'un octet depuis lequel les bits sont extraits un a un.
 * Un nouvel octet est charge automatiquement dans le tampon lorsque celui-ci est epuise.
 *
 * Utilisee par le {@link fr.iutfbleau.sae322025.modele.Decompresseur} pour
 * lire l'en-tete, les tables de longueurs et les pixels compresses du fichier PIF.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */

public class LectureBinaire {


    /**
     * Flux d'entree depuis le fichier PIF, encapsule dans un BufferedInputStream
     * pour limiter les acces disque.
     */

    private InputStream flux;


    /**
     * Tampon contenant l'octet en cours de lecture.
     * Les bits sont extraits du bit de poids fort vers le bit de poids faible.
     */

    private int tampon;


    /**
     * Nombre de bits encore disponibles dans le tampon courant (entre 0 et 8).
     * Quand il atteint 0, un nouvel octet est charge depuis le flux.
     */

    private int bitsRestants;


    /**
     * Construit un flux de lecture binaire depuis le fichier specifie.
     *
     * @param chemin Le chemin du fichier PIF a lire.
     * @throws IOException Si le fichier est introuvable ou ne peut pas etre ouvert.
     */

    public LectureBinaire(String chemin) throws IOException {

        this.flux = new BufferedInputStream(new FileInputStream(chemin));
        this.tampon = 0;
        this.bitsRestants = 0;

    }



    /**
     * Lit et retourne le prochain bit du flux.
     *
     * Si le tampon est vide, charge le prochain octet depuis le flux.
     * Les bits sont extraits du plus significatif au moins significatif.
     *
     * @return true si le bit vaut 1, false s'il vaut 0.
     * @throws IOException Si la fin du fichier est atteinte de maniere inattendue.
     */

    public boolean lireBit() throws IOException {
        if (this.bitsRestants == 0) {
            // Tampon vide : chargement du prochain octet depuis le flux
            this.tampon = this.flux.read();
            if (this.tampon == -1) {
                throw new IOException("Fin de fichier inattendue (EOF)");
            }
            this.bitsRestants = 8;
        }

        // Extraction du bit de poids fort restant par decalage et masquage
        this.bitsRestants--;
        int bit = (this.tampon >> this.bitsRestants) & 1;

        return (bit == 1);
    }



    /**
     * Lit un entier sur 2 octets (16 bits) depuis le flux, en big-endian.
     * Utilise pour lire la largeur et la hauteur de l'image depuis l'en-tete PIF.
     *
     * @return La valeur lue sur 2 octets (entre 0 et 65535).
     * @throws IOException Si une erreur survient lors de la lecture du flux.
     */


    public int lireShort() throws IOException {
        int b1 = lireOctet();
        int b2 = lireOctet();

        // Recomposition : octet de poids fort decale de 8 bits puis OR avec l'octet de poids faible
        return (b1 << 8) | b2;
    }



 

    /**
     * Lit un octet (8 bits) depuis le flux, du bit de poids fort au bit de poids faible.
     *
     * Reconstruit la valeur en lisant 8 bits successifs et en les accumulant
     * par decalage a gauche.
     *
     * @return La valeur de l'octet lu (entre 0 et 255).
     * @throws IOException Si une erreur survient lors de la lecture du flux.
     */


    public int lireOctet() throws IOException {
        int valeur = 0;

        // Lecture des 8 bits et reconstruction de l'octet par decalages successifs
        for (int i = 0; i < 8; i++) {
            valeur = valeur << 1;
            if (lireBit()) {
                // Placement du bit '1' en position de poids faible
                valeur = valeur | 1;
            }
        }

        return valeur;

    }



    /**
     * Ferme le flux de lecture et libere les ressources associees.
     *
     * @throws IOException Si une erreur survient lors de la fermeture du flux.
     */

    public void fermer() throws IOException {
        this.flux.close();
    }
}