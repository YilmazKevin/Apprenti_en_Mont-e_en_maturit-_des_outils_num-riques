package fr.iutfbleau.sae322025.modele;

import fr.iutfbleau.sae322025.modele.ArbreDeHuffman;
import fr.iutfbleau.sae322025.modele.Code;
import fr.iutfbleau.sae322025.modele.Feuille;
import fr.iutfbleau.sae322025.modele.Noeud;
import fr.iutfbleau.sae322025.modele.TransformateurCanonique;
import fr.iutfbleau.sae322025.modele.io.LectureBinaire;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;



/**
 * Classe responsable de la decompression d'un fichier au format PIF.
 *
 * Lit l'en-tete, reconstruit les arbres de Huffman canoniques pour chaque
 * composante (R, V, B), puis decode les pixels bit a bit pour reconstruire
 * l'image originale. Propose deux modes : sauvegarde sur disque ou retour
 * en memoire pour affichage direct.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class Decompresseur {



    /**
     * Decompresse un fichier .pif et sauvegarde le resultat sur disque au format PNG.
     *
     * Lit les dimensions, reconstruit les trois arbres de Huffman,
     * decode chaque pixel et ecrit l'image reconstruite dans le fichier de sortie.
     *
     * @param cheminEntree Le chemin vers le fichier .pif a decompresser.
     * @param cheminSortie Le chemin du fichier PNG a generer.
     */


    public void decompresser(String cheminEntree, String cheminSortie) {
        LectureBinaire lecteur = null;

        try {
           

            lecteur = new LectureBinaire(cheminEntree);

            // Lecture de l'en-tete : largeur et hauteur sur 2 octets chacune
            int largeur = lecteur.lireShort();
            int hauteur = lecteur.lireShort();

            System.out.println(" Dimensions : " + largeur + " x " + hauteur);

            // Reconstruction des trois arbres de Huffman depuis les tables de longueurs
           
            ArbreDeHuffman arbreR = reconstruireArbre(lecteur);
            ArbreDeHuffman arbreV = reconstruireArbre(lecteur);
            ArbreDeHuffman arbreB = reconstruireArbre(lecteur);

            BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

            // Decodage pixel par pixel de gauche a droite, de haut en bas
            
            for (int y = 0; y < hauteur; y++) {
                for (int x = 0; x < largeur; x++) {
                    int r = lireProchainPixel(lecteur, arbreR);
                    int v = lireProchainPixel(lecteur, arbreV);
                    int b = lireProchainPixel(lecteur, arbreB);

                    // Recomposition de la valeur RGB sur 24 bits
                    int rgb = (r << 16) | (v << 8) | b;
                    image.setRGB(x, y, rgb);
                }
            }

            // Ecriture de l'image reconstruite sur le disque au format PNG
           
            ImageIO.write(image, "png", new File(cheminSortie));
            System.out.println("Image creee : " + cheminSortie);

        } catch (IOException e) {
            System.out.println("Erreur d'Entree/Sortie pendant la decompression : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erreur inattendue pendant la decompression : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture du flux garantie meme en cas d'exception
            if (lecteur != null) {
                try {
                    lecteur.fermer();
                } catch (IOException e) {
                    System.out.println("Erreur lors de la fermeture du fichier PIF : " + e.getMessage());
                }
            }
        }
    }



    /**
     * Reconstruit un arbre de Huffman canonique a partir de la table de longueurs lue dans le flux.
     *
     * Lit 256 octets representant la longueur du code canonique pour chaque valeur possible (0 a 255).
     * Un octet a 0 indique que la valeur est absente de l'image.
     * Utilise le {@link TransformateurCanonique} pour regenerer les codes, puis les insere dans l'arbre.
     *
     * @param lecteur Le flux de lecture binaire positionne au debut d'une table de longueurs.
     * @return L'arbre de Huffman reconstruit pret a decoder les bits du flux.
     * @throws IOException Si une erreur survient lors de la lecture des octets.
     */


    private ArbreDeHuffman reconstruireArbre(LectureBinaire lecteur) throws IOException {
        int[] longueurs = new int[256];

        // Lecture des 256 longueurs de codes pour cette composante
        for (int i = 0; i < 256; i++) {
            longueurs[i] = lecteur.lireOctet();
        }

        // Regeneration des codes canoniques a partir des longueurs lues
        TransformateurCanonique trans = new TransformateurCanonique();
        Map<Integer, Code> dico = trans.reconstruireDepuisLongueurs(longueurs);

        // Construction de l'arbre vide puis insertion de chaque code
        ArbreDeHuffman arbre = new ArbreDeHuffman(new int[0]);

        for (Map.Entry<Integer, Code> entree : dico.entrySet()) {
            arbre.ajouterCode(entree.getKey(), entree.getValue());
        }

        return arbre;
    }



    /**
     * Decompresse un fichier .pif et retourne l'image reconstruite en memoire.
     *
     * Contrairement a {@link #decompresser(String, String)}, cette methode ne sauvegarde
     * pas l'image sur disque. Elle est destinee au Visualisateur pour un affichage direct.
     *
     * @param cheminEntree Le chemin vers le fichier .pif a decompresser.
     * @return La {@link BufferedImage} reconstruite depuis le fichier PIF.
     * @throws IOException Si le fichier est illisible ou corrompu.
     */




    public BufferedImage decompresserEnMemoire(String cheminEntree) throws IOException {
        LectureBinaire lecteur = null;


        try {
            lecteur = new LectureBinaire(cheminEntree);

            // Lecture de l'en-tete : largeur et hauteur sur 2 octets chacune
            int largeur = lecteur.lireShort();
            int hauteur = lecteur.lireShort();

            // Reconstruction des trois arbres de Huffman depuis les tables de longueurs
            ArbreDeHuffman arbreR = reconstruireArbre(lecteur);
            ArbreDeHuffman arbreV = reconstruireArbre(lecteur);
            ArbreDeHuffman arbreB = reconstruireArbre(lecteur);

            BufferedImage image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

            // Decodage pixel par pixel de gauche a droite, de haut en bas
            for (int y = 0; y < hauteur; y++) {
                for (int x = 0; x < largeur; x++) {
                    int r = lireProchainPixel(lecteur, arbreR);
                    int v = lireProchainPixel(lecteur, arbreV);
                    int b = lireProchainPixel(lecteur, arbreB);

                    // Recomposition de la valeur RGB sur 24 bits
                    int rgb = (r << 16) | (v << 8) | b;
                    image.setRGB(x, y, rgb);
                }
            }


            return image;


        } finally {
            // Fermeture du flux garantie meme en cas d'exception
            if (lecteur != null) {
                lecteur.fermer();
            }
        }
    }




    /**
     * Decode la valeur d'une composante coloree en parcourant l'arbre de Huffman bit par bit.
     *
     * Parcourt l'arbre depuis la racine : un bit a 0 oriente vers le fils gauche,
     * un bit a 1 vers le fils droit. S'arrete lorsqu'une feuille est atteinte
     * et retourne la valeur qu'elle contient.
     *
     * @param lecteur Le flux de lecture binaire depuis lequel les bits sont lus.
     * @param arbre   L'arbre de Huffman utilise pour le decodage de cette composante.
     * @return La valeur de la composante decodee (entre 0 et 255).
     * @throws IOException Si une erreur survient lors de la lecture des bits.
     */


    private int lireProchainPixel(LectureBinaire lecteur, ArbreDeHuffman arbre) throws IOException {
        Noeud actuel = arbre.getRacine();


        // Descente dans l'arbre bit par bit jusqu'a atteindre une feuille
        while (!actuel.estUneFeuille()) {
            boolean bit = lecteur.lireBit();


            // bit = true (1) -> fils droit, bit = false (0) -> fils gauche
            if (bit) {
                actuel = actuel.getFilsDroit();
            } else {
                actuel = actuel.getFilsGauche();
            }

        }

        // La feuille atteinte contient la valeur de la composante
        return ((Feuille) actuel).getValeur();
    }

}