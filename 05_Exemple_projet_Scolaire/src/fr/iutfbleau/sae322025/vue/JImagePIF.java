package fr.iutfbleau.sae322025.vue;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;



/**
 * Panneau graphique responsable de l'affichage d'une image decompressee.
 *
 * Gere deux modes d'affichage selon la taille de l'image par rapport au panneau :
 * si l'image est plus petite que le panneau, elle est centree automatiquement.
 * Si elle est plus grande, elle est positionnee selon les offsets de deplacement
 * mis a jour par le {@link fr.iutfbleau.sae322025.controleur.ControleurSouris}
 * lors du glissement a la souris.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class JImagePIF extends JPanel {


    /**
     * L'image a afficher dans ce panneau.
     */

    private BufferedImage image;


    /**
     * Decalage horizontal en pixels applique lors du deplacement a la souris.
     * Modifie directement par le {@link fr.iutfbleau.sae322025.controleur.ControleurSouris}.
     */
    public int offsetX;


    /**
     * Decalage vertical en pixels applique lors du deplacement a la souris.
     * Modifie directement par le {@link fr.iutfbleau.sae322025.controleur.ControleurSouris}.
     */
    public int offsetY;



    /**
     * Construit un panneau d'affichage pour l'image donnee.
     * Les offsets de deplacement sont initialises a zero.
     *
     * @param image L'image a afficher dans ce panneau.
     */


    public JImagePIF(BufferedImage image) {
        this.image = image;
        this.offsetX = 0;
        this.offsetY = 0;
    }



    /**
     * Dessine l'image dans le panneau selon son mode d'affichage.
     *
     * Si l'image est entierement contenue dans le panneau, elle est centree.
     * Sinon, elle est positionnee aux coordonnees definies par offsetX et offsetY,
     * permettant de faire defiler la portion visible par glissement de la souris.
     *
     * @param g Le contexte graphique fourni par Swing lors du redessin.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        if (this.image == null) {
            return;
        }


        int panneauLargeur = this.getWidth();

        int panneauHauteur = this.getHeight();

        int imageLargeur = this.image.getWidth();

        int imageHauteur = this.image.getHeight();

        int drawX;

        int drawY;


        if (imageLargeur <= panneauLargeur && imageHauteur <= panneauHauteur) {
            // Image plus petite que le panneau : centrage automatique
            drawX = (panneauLargeur - imageLargeur) / 2;
            drawY = (panneauHauteur - imageHauteur) / 2;
        } else {

            // Image plus grande que le panneau : positionnement selon les offsets de deplacement
            drawX = this.offsetX;
            drawY = this.offsetY;
        }

        g.drawImage(this.image, drawX, drawY, null);
    }

}