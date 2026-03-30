package fr.iutfbleau.sae322025.vue;

import fr.iutfbleau.sae322025.controleur.ControleurSouris;
import fr.iutfbleau.sae322025.modele.Decompresseur;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Fenetre principale du Visualisateur PIF.
 * Gere le chargement du fichier .pif et l'affichage de l'image.
 * La taille s'adapte a l'image sans depasser l'ecran.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */
public class FenetreVisualisation extends JFrame {

    /**
     * L'image affichee dans la fenetre.
     */
    private BufferedImage image;

    /**
     * Construit la fenetre selon les arguments fournis.
     * Si un argument est fourni, ouvre directement le fichier .pif.
     * Sinon, ouvre un JFileChooser pour selectionner le fichier.
     *
     * @param args args[0] optionnel : chemin vers le fichier .pif.
     */
    public FenetreVisualisation(String[] args) {
        this.setTitle("Visualisateur PIF");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        if (args.length > 0) {
            // Chargement direct depuis l'argument
            this.chargerDepuisChemin(args[0]);
        } else {
            // Pas d'argument : on ouvre un JFileChooser
            this.ouvrirJFileChooser();
        }
    }

    /**
     * Charge un fichier .pif depuis un chemin et affiche l'image.
     *
     * @param chemin Le chemin vers le fichier .pif.
     */
    private void chargerDepuisChemin(String chemin) {
        File fichier = new File(chemin);

        if (!fichier.exists()) {

            JOptionPane.showMessageDialog(null,"Fichier introuvable : " + chemin,"Erreur",JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (!fichier.getName().toLowerCase().endsWith(".pif")) {

            JOptionPane.showMessageDialog(null,"Le fichier n'est pas un fichier .pif","Format invalide",JOptionPane.ERROR_MESSAGE);
            return;

        }

        this.chargerImage(fichier.getAbsolutePath());

    }

    /**
     * Ouvre un JFileChooser pour selectionner un fichier .pif.
     */
    private void ouvrirJFileChooser() {

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Fichiers PIF (*.pif)", "pif"));
        fc.setAcceptAllFileFilterUsed(false);

        int resultat = fc.showOpenDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {

            this.chargerImage(fc.getSelectedFile().getAbsolutePath());

        }

    }

    /**
     * Decompresse le fichier .pif et affiche l'image dans la fenetre.
     * Adapte la taille de la fenetre a l'image.
     *
     * @param cheminPif Le chemin vers le fichier .pif.
     */
    private void chargerImage(String cheminPif) {

        try {
            
            Decompresseur decompresseur = new Decompresseur();
            this.image = decompresseur.decompresserEnMemoire(cheminPif);

            // Adaptation de la taille de la fenetre
            Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
            int largeur = Math.min(this.image.getWidth() + 50, ecran.width - 100);
            int hauteur = Math.min(this.image.getHeight() + 50, ecran.height - 100);
            this.setSize(largeur, hauteur);
            this.setLocationRelativeTo(null);

            // Ajout du panneau d'affichage
            JImagePIF panneau = new JImagePIF(this.image);
            this.add(panneau);

            ControleurSouris controleur = new ControleurSouris(panneau);
            panneau.addMouseListener(controleur);
            panneau.addMouseMotionListener(controleur);

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null,"Erreur lors de la lecture du fichier : " + e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);

        }
    }
    
}