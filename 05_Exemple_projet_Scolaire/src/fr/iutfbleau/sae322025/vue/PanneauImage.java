package fr.iutfbleau.sae322025.vue;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;



/**
 * Panneau simple pour afficher un apercu de l'image dans le convertisseur.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */

public class PanneauImage extends JPanel {


    /**
     * L'image a afficher dans ce panneau.
     */
    private Image image;


    /**
     * La largeur d'affichage de l'image.
     */
    private int largeur;


    /**
     * La hauteur d'affichage de l'image.
     */
    private int hauteur;



    /**
     * Construit un panneau pour afficher l'image donnee.
     *
     * @param image L'image a afficher.
     * @param largeur La largeur d'affichage.
     * @param hauteur La hauteur d'affichage.
     */

    public PanneauImage(Image image, int largeur, int hauteur) {
        this.image = image;
        this.largeur = largeur;
        this.hauteur = hauteur;
    }



    /**
     * Dessine l'image dans le panneau.
     *
     * @param g Le contexte graphique fourni par Swing.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, this.largeur, this.hauteur, null);
    }

}