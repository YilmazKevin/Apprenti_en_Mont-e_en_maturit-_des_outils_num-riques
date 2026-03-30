package fr.iutfbleau.sae322025.modele;

import fr.iutfbleau.sae322025.modele.io.EcritureBinaire;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Controleur de compression PIF.
 * Gere uniquement la logique metier :
 * analyse des frequences, generation des codes et ecriture du fichier.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class Compresseur {


    /**
     * Les frequences du canal rouge.
     */
    private TableDeFrequences freqR;


    /**
     * Les frequences du canal vert.
     */
    private TableDeFrequences freqV;


    /**
     * Les frequences du canal bleu.
     */
    private TableDeFrequences freqB;


    /**
     * Les codes initiaux du canal rouge.
     */
    private Map<Integer, Code> initiauxR;


    /**
     * Les codes initiaux du canal vert.
     */
    private Map<Integer, Code> initiauxV;


    /**
     * Les codes initiaux du canal bleu.
     */
    private Map<Integer, Code> initiauxB;


    /**
     * Les codes canoniques du canal rouge.
     */
    private Map<Integer, Code> codesR;


    /**
     * Les codes canoniques du canal vert.
     */
    private Map<Integer, Code> codesV;


    /**
     * Les codes canoniques du canal bleu.
     */
    private Map<Integer, Code> codesB;



    /**
     * Analyse une image et calcule toutes les tables de codes.
     * Doit etre appelee avant ecrire() et les getters.
     *
     * @param image L'image a analyser.
     * @return true si l'analyse est valide, false si une composante a moins de 2 couleurs.
     */


    public boolean analyser(BufferedImage image) {

        this.freqR = new TableDeFrequences();
        this.freqV = new TableDeFrequences();
        this.freqB = new TableDeFrequences();


        // Extraction des frequences R/G/B
        AnalyseurImage analyseur = new AnalyseurImage();
        analyseur.extraireFrequences(image, this.freqR, this.freqV, this.freqB);



        // Verification : au moins 2 couleurs distinctes par canal
        if (!aAssezDeCouleurs(this.freqR) || !aAssezDeCouleurs(this.freqV) || !aAssezDeCouleurs(this.freqB)) {
            return false;
        }


        // Construction des arbres de Huffman
        ArbreDeHuffman arbreR = new ArbreDeHuffman(this.freqR.getToutesLesFrequences());
        ArbreDeHuffman arbreV = new ArbreDeHuffman(this.freqV.getToutesLesFrequences());
        ArbreDeHuffman arbreB = new ArbreDeHuffman(this.freqB.getToutesLesFrequences());


        // Generation des codes initiaux
        GenerateurDeCodes genR = new GenerateurDeCodes();
        genR.generer(arbreR.getRacine());
        this.initiauxR = genR.getDictionnaire();


        GenerateurDeCodes genV = new GenerateurDeCodes();
        genV.generer(arbreV.getRacine());
        this.initiauxV = genV.getDictionnaire();


        GenerateurDeCodes genB = new GenerateurDeCodes();
        genB.generer(arbreB.getRacine());
        this.initiauxB = genB.getDictionnaire();


        // Transformation en codes canoniques
        TransformateurCanonique trans = new TransformateurCanonique();
        this.codesR = trans.transformer(this.initiauxR);
        this.codesV = trans.transformer(this.initiauxV);
        this.codesB = trans.transformer(this.initiauxB);

        return true;

    }




    /**
     * Ecrit le fichier PIF sur le disque.
     * La methode analyser() doit avoir ete appelee avant.
     *
     * @param image L'image source.
     * @param cheminSortie Le chemin du fichier .pif a creer.
     */


    public void ecrire(BufferedImage image, String cheminSortie) {

        EcritureBinaire ecrivain = null;

        try {

            ecrivain = new EcritureBinaire(cheminSortie);

            // En-tete : largeur et hauteur
            ecrivain.ecrireShort(image.getWidth());
            ecrivain.ecrireShort(image.getHeight());

            // Tables des longueurs pour chaque canal
            ecrireTableLongueurs(ecrivain, this.codesR);
            ecrireTableLongueurs(ecrivain, this.codesV);
            ecrireTableLongueurs(ecrivain, this.codesB);

            // Pixels comprimes canal par canal
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int r = (pixel >> 16) & 0xFF;
                    int v = (pixel >> 8)  & 0xFF;
                    int b = pixel & 0xFF;

                    ecrivain.ecrireCode(this.codesR.get(r));
                    ecrivain.ecrireCode(this.codesV.get(v));
                    ecrivain.ecrireCode(this.codesB.get(b));

                }

            }

        } catch (IOException e) {

            System.out.println("Erreur ecriture : " + e.getMessage());
            e.printStackTrace();

        } finally {

            if (ecrivain != null) {
                try {
                    ecrivain.fermer();
                } catch (IOException e) {
                    System.out.println("Erreur fermeture : " + e.getMessage());
                }
            }

        }
    }


    /**
     * Compression complete sans IHM pour usage en ligne de commande.
     *
     * @param cheminEntree Le chemin de l'image source.
     * @param cheminSortie Le chemin du fichier .pif a creer.
     */

    public void compresser(String cheminEntree, String cheminSortie) {

        try {
            File fichier = new File(cheminEntree);

            if (!fichier.exists()) {
                System.out.println("ERREUR : Image introuvable.");
                return;
            }

            BufferedImage image = ImageIO.read(fichier);

            boolean valide = this.analyser(image);

            if (!valide) {
                System.out.println("ERREUR : Conversion refusee.");
                System.out.println("Raison : Une composante a moins de 2 couleurs distinctes.");
                return;
            }

            this.ecrire(image, cheminSortie);
            System.out.println("Fichier cree : " + cheminSortie);

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * Retourne les frequences du canal rouge.
     * @return Les frequences du canal rouge.
     */

    public TableDeFrequences getFreqR() { return this.freqR; }


    /**
     * Retourne les frequences du canal vert.
     * @return Les frequences du canal vert.
     */

    public TableDeFrequences getFreqV() { return this.freqV; }



    /**
     * Retourne les frequences du canal bleu.
     * @return Les frequences du canal bleu.
     */

    public TableDeFrequences getFreqB() { return this.freqB; }



    /**
     * Retourne les codes initiaux du canal rouge.
     * @return Les codes initiaux du canal rouge.
     */

    public Map<Integer, Code> getInitiauxR() { return this.initiauxR; }


    /**
     * Retourne les codes initiaux du canal vert.
     * @return Les codes initiaux du canal vert.
     */
    public Map<Integer, Code> getInitiauxV() { return this.initiauxV; }


    /**
     * Retourne les codes initiaux du canal bleu.
     * @return Les codes initiaux du canal bleu.
     */
    public Map<Integer, Code> getInitiauxB() { return this.initiauxB; }


    /**
     * Retourne les codes canoniques du canal rouge.
     * @return Les codes canoniques du canal rouge.
     */
    public Map<Integer, Code> getCodesR() { return this.codesR; }


    /**
     * Retourne les codes canoniques du canal vert.
     * @return Les codes canoniques du canal vert.
     */
    public Map<Integer, Code> getCodesV() { return this.codesV; }


    /**
     * Retourne les codes canoniques du canal bleu.
     * @return Les codes canoniques du canal bleu.
     */
    public Map<Integer, Code> getCodesB() { return this.codesB; }


    /**
     * Ecrit les 256 octets de longueurs pour un canal.
     *
     * @param ecrivain L'ecrivain binaire.
     * @param table La table des codes canoniques.
     * @throws IOException Si une erreur d'ecriture survient.
     */

    private void ecrireTableLongueurs(EcritureBinaire ecrivain, Map<Integer, Code> table) throws IOException {
        for (int i = 0; i < 256; i++) {
            if (table.containsKey(i)) {
                ecrivain.ecrireOctet(table.get(i).getNbBits());
            } else {
                ecrivain.ecrireOctet(0);
            }
        }
    }


    /**
     * Verifie si une table contient au moins 2 couleurs distinctes.
     *
     * @param table La table de frequences a verifier.
     * @return true si au moins 2 valeurs ont une frequence positive.
     */
    private boolean aAssezDeCouleurs(TableDeFrequences table) {
        int compte = 0;
        for (int frequence : table.getToutesLesFrequences()) {
            if (frequence > 0) {
                compte++;
            }
            if (compte >= 2) {
                return true;
            }
        }
        return false;
    }
    
}