package fr.iutfbleau.sae322025.vue;

import fr.iutfbleau.sae322025.controleur.ControleurBouton;
import fr.iutfbleau.sae322025.modele.Compresseur;
import fr.iutfbleau.sae322025.modele.Code;
import fr.iutfbleau.sae322025.modele.TableDeFrequences;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * Fenetre principale du Convertisseur PIF.
 * Gere le chargement de l'image, l'affichage des tables
 * et le declenchement de la conversion.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class FenetreConvertisseur extends JFrame {


    /**
     * L'image source a convertir.
     */
    private BufferedImage image;


    /**
     * Le compresseur utilise pour l'analyse et l'ecriture.
     */
    private Compresseur compresseur;


    /**
     * Le chemin de sortie optionnel fourni en argument.
     */
    private String cheminSortie;


    /**
     * Construit la fenetre selon les arguments fournis.
     * 3 cas possibles : aucun arg, 1 arg (image), 2 args (image + sortie).
     *
     * @param args Les arguments de la ligne de commande.
     */

    public FenetreConvertisseur(String[] args) {
        this.cheminSortie = null;

        // Recuperation des arguments
        if (args.length >= 2) {
            this.cheminSortie = args[1];
        }

        if (args.length >= 1) {
            // Chargement direct depuis l'argument
            this.chargerDepuisChemin(args[0]);
        } else {
            // Pas d'argument : on ouvre un JFileChooser
            this.ouvrirJFileChooser();
        }

        // Configuration de la fenetre
        this.setTitle("Convertisseur PIF");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(ecran.width - 100, ecran.height - 100);
        this.setLocationRelativeTo(null);

    }


    /**
     * Charge une image depuis un chemin et lance l'analyse.
     *
     * @param chemin Le chemin vers l'image source.
     */

    private void chargerDepuisChemin(String chemin) {
        File fichier = new File(chemin);

        if (!fichier.exists()) {
            JOptionPane.showMessageDialog(null,
                "Fichier introuvable : " + chemin,
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            BufferedImage img = ImageIO.read(fichier);

            if (img == null) {
                JOptionPane.showMessageDialog(null,
                    "Format non supporte : " + fichier.getName(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.chargerImage(img);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                "Erreur chargement : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Ouvre un JFileChooser pour selectionner une image.
     */

    private void ouvrirJFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(true);

        int resultat = fc.showOpenDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = ImageIO.read(fc.getSelectedFile());

                if (img == null) {
                    JOptionPane.showMessageDialog(null,
                        "Format non supporte.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                this.chargerImage(img);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                    "Erreur chargement : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    /**
     * Analyse l'image et construit l'interface graphique.
     *
     * @param img L'image a analyser et afficher.
     */


    private void chargerImage(BufferedImage img) {

        this.image = img;
        this.compresseur = new Compresseur();


        boolean valide = this.compresseur.analyser(this.image);


        if (!valide) {
            JOptionPane.showMessageDialog(null,
                "Conversion refusee : une composante a moins de 2 couleurs distinctes.",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Construction de l'interface
        this.setLayout(new BorderLayout());


        // Panneau image a gauche
        this.add(creerPanneauImage(), BorderLayout.WEST);


        // Onglets avec les trois tables au centre
        JTabbedPane onglets = new JTabbedPane();
        onglets.addTab("Rouge", creerPanneauTable(compresseur.getFreqR(), compresseur.getInitiauxR(), compresseur.getCodesR()));
        onglets.addTab("Vert",  creerPanneauTable(compresseur.getFreqV(), compresseur.getInitiauxV(), compresseur.getCodesV()));
        onglets.addTab("Bleu",  creerPanneauTable(compresseur.getFreqB(), compresseur.getInitiauxB(), compresseur.getCodesB()));
        this.add(onglets, BorderLayout.CENTER);

        // Bouton de conversion en bas
        JButton bouton = new JButton("Convertir en .pif");
        bouton.addActionListener(new ControleurBouton(this.image, this.compresseur, this.cheminSortie, this));
        this.add(bouton, BorderLayout.SOUTH);

    }



    /**
     * Cree le panneau d'affichage de l'image reduite si necessaire.
     *
     * @return Un JPanel contenant l'image reduite.
     */

    private JPanel creerPanneauImage() {
        int maxLargeur = 400;
        int maxHauteur = 400;


        int largeur = this.image.getWidth();
        int hauteur = this.image.getHeight();


        // Reduction proportionnelle si l'image depasse la taille max
        if (largeur > maxLargeur || hauteur > maxHauteur) {
            double ratioLargeur = (double) maxLargeur / largeur;
            double ratioHauteur = (double) maxHauteur / hauteur;
            double ratio = Math.min(ratioLargeur, ratioHauteur);
            largeur = (int) (largeur * ratio);
            hauteur = (int) (hauteur * ratio);
        }


        final Image apercu = this.image.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);

        JPanel panneau = new PanneauImage(apercu, largeur, hauteur);
        panneau.setPreferredSize(new Dimension(largeur, hauteur));
        return panneau;

    }



    /**
     * Cree un panneau avec une JTable affichant les donnees pour un canal.
     *
     * @param freq Les frequences du canal.
     * @param initiaux Les codes initiaux de Huffman.
     * @param canoniques Les codes canoniques.
     * @return Un JScrollPane contenant la JTable.
     */


    private JScrollPane creerPanneauTable(TableDeFrequences freq, Map<Integer, Code> initiaux, Map<Integer, Code> canoniques) {

        String[] colonnes = {"Valeur", "Frequence", "Code initial", "Code canonique"};
        DefaultTableModel modele = new DefaultTableModel(colonnes, 0);


        for (int i = 0; i < 256; i++) {

            int frequence = freq.getFrequence(i);


            if (frequence > 0) {
                String codeInitial = "-";
                String codeCanonique = "-";

                if (initiaux.containsKey(i)) {
                    codeInitial = initiaux.get(i).toString();
                }

                if (canoniques.containsKey(i)) {
                    codeCanonique = canoniques.get(i).toString();
                }

                modele.addRow(new Object[]{i, frequence, codeInitial, codeCanonique});
            }
        }


        JTable table = new JTable(modele);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        return new JScrollPane(table);
    }

}