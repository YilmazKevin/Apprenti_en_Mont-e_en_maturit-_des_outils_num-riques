package fr.iutfbleau.sae322025.controleur;

import fr.iutfbleau.sae322025.vue.JImagePIF;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Controleur gerant le deplacement de l'image a la souris.
 * <p>
 * Permet de faire glisser l'image dans le panneau d'affichage
 * en maintenant le bouton gauche de la souris enfonce.
 * Le deplacement est calcule par difference entre la position
 * courante et la derniere position enregistree.
 * </p>
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class ControleurSouris implements MouseListener, MouseMotionListener {

    /**
     * Panneau d'affichage sur lequel ce controleur agit.
     * Les offsets de ce panneau sont modifies lors du glissement.
     */
    private JImagePIF panneau;

    /**
     * Derniere position X enregistree de la souris (en pixels).
     * Mise a jour a chaque appui ou deplacement.
     */


    private int dernierX;

    /**
     * Derniere position Y enregistree de la souris (en pixels).
     * Mise a jour a chaque appui ou deplacement.
     */


    private int dernierY;

    /**
     * Construit un controleur souris associe au panneau d'affichage donne.
     * Initialise les dernieres positions connues de la souris a zero.
     *
     * @param panneau Le panneau {@link JImagePIF} dont l'offset sera modifie lors du glissement.
     */


    public ControleurSouris(JImagePIF panneau) {
        this.panneau = panneau;
        this.dernierX = 0;
        this.dernierY = 0;
    }



    /**
     * Memorise la position de la souris au moment du clic gauche.
     * <p>
     * Cette position sert de reference pour calculer le deplacement
     * lors du glissement suivant.
     * </p>
     *
     * @param e L'evenement souris declenche lors de l'appui sur un bouton.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // On ne reagit qu'au bouton gauche (BUTTON1)
        if (e.getButton() == MouseEvent.BUTTON1) {
            this.dernierX = e.getX();
            this.dernierY = e.getY();
        }
    }

    /**
     * Deplace l'image en mettant a jour les offsets du panneau.
     * <p>
     * Calcule la difference (delta) entre la position actuelle de la souris
     * et la derniere position enregistree, puis l'applique aux offsets
     * du panneau avant de le redessiner.
     * </p>
     *
     * @param e L'evenement souris declenche lors du deplacement avec bouton enfonce.
     */



    @Override
    public void mouseDragged(MouseEvent e) {
        // Calcul du deplacement depuis la derniere position connue
        int deltaX = e.getX() - this.dernierX;
        int deltaY = e.getY() - this.dernierY;

        // Application du deplacement sur les offsets du panneau
        this.panneau.offsetX += deltaX;
        this.panneau.offsetY += deltaY;

        // Mise a jour de la reference pour le prochain evenement drag
        this.dernierX = e.getX();
        this.dernierY = e.getY();

        // Redemande l'affichage avec les nouveaux offsets
        this.panneau.repaint();
    }





    /**
     * Non utilise. Requis par l'interface {@link MouseListener}.
     *
     * @param e L'evenement souris.
     */
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    
    
    /**
     * Non utilise. Requis par l'interface {@link MouseListener}.
     *
     * @param e L'evenement souris.
     */
    
    @Override
    public void mouseReleased(MouseEvent e) {}

    
    
    /**
     * Non utilise. Requis par l'interface {@link MouseListener}.
     *
     * @param e L'evenement souris.
     */
    
    @Override
    public void mouseEntered(MouseEvent e) {}


    /**
     * Non utilise. Requis par l'interface {@link MouseListener}.
     *
     * @param e L'evenement souris.
     */

    @Override
    public void mouseExited(MouseEvent e) {}


    /**
     * Non utilise. Requis par l'interface {@link MouseMotionListener}.
     *
     * @param e L'evenement souris.
     */

    @Override
    public void mouseMoved(MouseEvent e) {}

}