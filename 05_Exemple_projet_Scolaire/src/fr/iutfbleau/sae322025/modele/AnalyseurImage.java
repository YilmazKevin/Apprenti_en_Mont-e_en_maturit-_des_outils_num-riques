package fr.iutfbleau.sae322025.modele;


import java.awt.image.BufferedImage;
// On retire l'import de java.awt.Color car on n'en a plus besoin !


/**
 * Outil optimisé pour l'extraction des fréquences RGB via opérations binaires.
 * @author Yilmaz Kevin, Adam Barhani
 */




public class AnalyseurImage {

    /**
     * Parcourt l'image et remplit les tables en utilisant des masques binaires (Bitwise).
     * Cette méthode évite la création d'objets Color inutilement coûteuse.
     * @param image  L'image source dont on analyse les pixels.
     * @param tRouge La table de frequences a remplir pour la composante rouge.
     * @param tVert  La table de frequences a remplir pour la composante verte.
     * @param tBleu  La table de frequences a remplir pour la composante bleue.
     */



    public void extraireFrequences(BufferedImage image, TableDeFrequences tRouge, TableDeFrequences tVert, TableDeFrequences tBleu) {


        int largeur = image.getWidth();
        int hauteur = image.getHeight();


        for (int y = 0; y < hauteur; y++) {

            for (int x = 0; x < largeur; x++) {

                // On récupère l'entier brut du pixel (ex: -16777216)
                int pixel = image.getRGB(x, y);

                // Extraction MATHÉMATIQUE (Opérations binaires)
                // Rouge : On décale de 16 bits vers la droite et on garde les 8 derniers
                int r = (pixel >> 16) & 0xFF;

                // Vert : On décale de 8 bits vers la droite et on garde les 8 derniers
                int v = (pixel >> 8) & 0xFF;

                // Bleu : On prend juste les 8 derniers bits
                int b = pixel & 0xFF;

                // 3. Remplissage des tables
                tRouge.ajouter(r);
                tVert.ajouter(v);
                tBleu.ajouter(b);
            }

        }


    }


    
}