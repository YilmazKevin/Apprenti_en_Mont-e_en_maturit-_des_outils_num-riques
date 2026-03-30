package fr.iutfbleau.sae322025.modele;

import java.util.PriorityQueue;




/**
 * Represente l'arbre binaire de Huffman utilise pour la compression et la decompression.
 *
 * En compression, l'arbre est construit a partir des frequences des valeurs de couleur :
 * les valeurs les plus frequentes se retrouvent pres de la racine (codes courts),
 * les moins frequentes en profondeur (codes longs).
 *
 * En decompression, l'arbre est reconstruit a partir des codes canoniques lus dans
 * le fichier PIF, puis utilise pour decoder les bits un par un.
 *
 * @author Yilmaz Kevin, Adam Barhani
 * @version 1.0
 */


public class ArbreDeHuffman {


    /**
     * Racine de l'arbre binaire de Huffman.
     * Vaut null si l'arbre est vide (cas d'une composante absente).
     */

    private Noeud racine;


    /**
     * Construit un arbre de Huffman a partir d'une file de priorite de noeuds deja prete.
     *
     * Utile lorsque la file a ete preparee en amont avant la construction de l'arbre.
     *
     * @param file La file de priorite contenant les noeuds tries par frequence croissante.
     */


    public ArbreDeHuffman(PriorityQueue<Noeud> file) {
        this.construireArbre(file);
    }



    /**
     * Construit un arbre de Huffman a partir d'un tableau de frequences.
     *
     * Cree une feuille pour chaque valeur dont la frequence est strictement positive,
     * les insere dans une file de priorite, puis construit l'arbre par fusions successives.
     * Si le tableau est null ou vide, la racine reste null (cas de la decompression).
     *
     * @param frequences Tableau de 256 entiers ou frequences[i] est le nombre d'occurrences
     *                   de la valeur i dans l'image pour une composante donnee.
     */


    public ArbreDeHuffman(int[] frequences) {


        // Tableau vide ou null : arbre sans racine, utile pour la decompression
        if (frequences == null || frequences.length == 0) {
            this.racine = null;
            return;
        }


        PriorityQueue<Noeud> file = new PriorityQueue<>();


        // Creation d'une feuille uniquement pour les valeurs presentes dans l'image
        for (int i = 0; i < frequences.length; i++) {

            if (frequences[i] > 0) {

                file.add(new Feuille(i, frequences[i]));
            }

        }

        this.construireArbre(file);

    }



    /**
     * Construit l'arbre de Huffman par fusions successives des deux noeuds de plus faible frequence.
     *
     * A chaque iteration, retire les deux noeuds de priorite minimale de la file,
     * cree un noeud parent dont la frequence est la somme des deux, et le reinsere.
     * Repete jusqu'a ce qu'il ne reste qu'un seul noeud : la racine de l'arbre.
     *
     * @param file La file de priorite contenant les noeuds a fusionner.
     */


    private void construireArbre(PriorityQueue<Noeud> file) {

        if (file.isEmpty()) {

            this.racine = null;
            return;

        }

        while (file.size() > 1) {
            Noeud g = file.poll();
            Noeud d = file.poll();

            // Fusion des deux noeuds de plus faible frequence en un noeud parent
            Noeud parent = new Noeud(g, d);
            file.add(parent);
        }

        // Le dernier noeud restant est la racine de l'arbre
        this.racine = file.poll();

    }



    /**
     * Insere un code canonique dans l'arbre pour reconstruire la structure de decompression.
     *
     * Parcourt les bits du code de gauche a droite : un bit a 0 descend a gauche,
     * un bit a 1 descend a droite. Au dernier bit, place une feuille contenant la valeur.
     * Les noeuds intermediaires manquants sont crees a la volee.
     *
     * @param valeur La valeur de la composante coloree (entre 0 et 255) a placer en feuille.
     * @param code   L'objet {@link Code} contenant les bits et la longueur du code canonique.
     */


    public void ajouterCode(int valeur, Code code) {


        // Initialisation de la racine si l'arbre est encore vide
        if (this.racine == null) {
            this.racine = new Noeud(0);
        }



        Noeud actuel = this.racine;
        int nbBits = code.getNbBits();



        // Parcours des bits du code pour descendre ou construire le chemin dans l'arbre
        for (int i = 0; i < nbBits; i++) {
            int bit = code.getBitAt(i) ? 1 : 0;



            // Le dernier bit designe l'emplacement de la feuille
            boolean estDernierBit = (i == nbBits - 1);


            if (bit == 0) {

                // Descente a gauche : creation du noeud si absent
                if (actuel.getFilsGauche() == null) {

                    if (estDernierBit) {

                        actuel.setFilsGauche(new Feuille(valeur, 0));

                    } else {

                        actuel.setFilsGauche(new Noeud(0));

                    }

                }

                actuel = actuel.getFilsGauche();

            } else {

                // Descente a droite : creation du noeud si absent
                if (actuel.getFilsDroit() == null) {
                    if (estDernierBit) {
                        actuel.setFilsDroit(new Feuille(valeur, 0));
                    } else {
                        actuel.setFilsDroit(new Noeud(0));
                    }
                }
                actuel = actuel.getFilsDroit();
            }

        }

    }



    /**
     * Retourne la racine de l'arbre de Huffman.
     *
     * @return Le noeud racine, ou null si l'arbre est vide.
     */

    public Noeud getRacine() {
        return this.racine;
    }

}