package fr.iutfbleau.sae322025.controleur;

import fr.iutfbleau.sae322025.modele.Compresseur;
import fr.iutfbleau.sae322025.modele.Code;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;



/**
 * Controleur du bouton "Convertir en .pif".
 * Gere la sauvegarde de l'image compressee au format PIF.
 * Delegue l'ecriture au Compresseur.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class ControleurBouton implements ActionListener {



    /**
     * L'image source a compresser.
     */
    private BufferedImage image;



    /**
     * Le compresseur deja initialise avec les donnees analysees.
     */
    private Compresseur compresseur;



    /**
     * Le chemin de sortie optionnel (peut etre null).
     */
    private String cheminSortie;



    /**
     * La fenetre parente pour afficher les dialogues.
     */
    private JFrame parent;




    /**
     * Construit le controleur du bouton de conversion.
     *
     * @param image L'image a compresser.
     * @param compresseur Le compresseur deja initialise.
     * @param cheminSortie Le chemin de sortie (peut etre null).
     * @param parent La fenetre parente.
     */



    public ControleurBouton(BufferedImage image, Compresseur compresseur, String cheminSortie, JFrame parent) {
        this.image = image;
        this.compresseur = compresseur;
        this.cheminSortie = cheminSortie;
        this.parent = parent;
    }





    /**
     * Declenche la conversion quand le bouton est clique.
     * Ouvre un JFileChooser si aucun chemin de sortie n'est defini.
     *
     * @param e L'evenement declencheur.
     */




    @Override
    public void actionPerformed(ActionEvent e) {
        String chemin = this.cheminSortie;


        // Si pas de chemin fourni on ouvre un JFileChooser
        if (chemin == null) {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Fichiers PIF (*.pif)", "pif"));
            fc.setSelectedFile(new File("image.pif"));

            int resultat = fc.showSaveDialog(this.parent);

            if (resultat != JFileChooser.APPROVE_OPTION) {
                return;
            }

            chemin = fc.getSelectedFile().getAbsolutePath();
        }



        // Extension .pif obligatoire
        if (!chemin.toLowerCase().endsWith(".pif")) {

            chemin = chemin + ".pif";

        }

        // Ecriture via le compresseur
        this.compresseur.ecrire(this.image, chemin);

        // Calcul des stats de compression
        long pifSize = new File(chemin).length();
        long rawSize = (long) this.image.getWidth() * this.image.getHeight() * 3;
        double gain = 100.0 * (1.0 - ((double) pifSize / rawSize));

        String info = String.format("Taille : %.2f Mo (Gain %.1f%%)", pifSize / 1048576.0, gain);

        JOptionPane.showMessageDialog(this.parent,"Conversion reussie !\n" + info + "\nSauvegarde : " + chemin,"Succes",JOptionPane.INFORMATION_MESSAGE);


    }

}