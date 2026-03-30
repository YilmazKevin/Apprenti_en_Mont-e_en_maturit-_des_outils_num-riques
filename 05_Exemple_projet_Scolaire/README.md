# PIF — Primitive Image Format 

Bienvenue sur notre projet SAE 3.2 !
Ce projet a ete realise dans le cadre de la SAE 3.2 du departement Informatique
a l'IUT de Fontainebleau. Son objectif est de creer un support logiciel pour un
nouveau format d'image appele **PIF (Primitive Image Format)**, inspire du format
JFIF (JPEG File Interchange Format), avec compression sans perte par codage de Huffman Canonique.

---

## Table des matieres

- [Presentation](#presentation)
- [Organisation du projet](#organisation-du-projet)
- [Compilation et lancement](#compilation-et-lancement)
- [Documentation](#documentation)
- [Nettoyage](#nettoyage)
- [Auteurs](#auteurs)

---

## Presentation 

Le format PIF est un format d'image compresse sans perte, base sur l'algorithme de
**Huffman Canonique**. Chaque composante coloree (Rouge, Vert, Bleu) est traitee
independamment afin d'optimiser le taux de compression tout en garantissant une
reconstruction parfaite de l'image originale.

Ce projet consiste a developper deux programmes en Java :

**Visualisateur PIF**
- Ouvre et affiche une image contenue dans un fichier `.pif`
- Le chemin du fichier peut etre fourni en argument ou choisi via un `JFileChooser`
- Fenetre redimensionnable adaptee a la taille de l'image
- Image navigable a la souris (clic gauche maintenu) si elle depasse la fenetre

**Convertisseur vers PIF**
- Charge une image dans un format supporte par `ImageIO` (PNG, JPG, BMP)
- Affiche les tables de frequences, les codes Huffman initiaux et les codes canoniques
- Valide que chaque composante possede au moins 2 valeurs distinctes avant conversion
- Permet d'exporter l'image au format `.pif`

---

## Organisation du projet
```
SAE32_2025/
│
├── src/fr/iutfbleau/sae322025/          # Code source du projet
│   │
│   ├── Visualisateur.java               # Point d'entree du visualisateur
│   ├── Convertisseur.java               # Point d'entree du convertisseur
│   │
│   ├── controleur/
│   │   ├── ControleurSouris.java        # Deplacement de l'image a la souris
│   │   └── ControleurBouton.java        # Declenchement de la conversion
│   │
│   ├── modele/
│   │   ├── Compresseur.java             # Pipeline complet de compression
│   │   ├── Decompresseur.java           # Pipeline complet de decompression
│   │   ├── AnalyseurImage.java          # Extraction des frequences RGB
│   │   ├── ArbreDeHuffman.java          # Construction et reconstruction de l'arbre
│   │   ├── Code.java                    # Representation d'un code binaire
│   │   ├── Feuille.java                 # Noeud terminal de l'arbre de Huffman
│   │   ├── GenerateurDeCodes.java       # Generation des codes par parcours
│   │   ├── Noeud.java                   # Noeud interne de l'arbre de Huffman
│   │   ├── TableDeFrequences.java       # Comptage des occurrences par composante
│   │   ├── TransformateurCanonique.java # Canonisation et reconstruction
│   │   └── io/
│   │       ├── EcritureBinaire.java     # Ecriture bit par bit dans un fichier
│   │       └── LectureBinaire.java      # Lecture bit par bit depuis un fichier
│   │
│   └── vue/
│       ├── FenetreVisualisation.java    # Fenetre principale du visualisateur
│       ├── FenetreConvertisseur.java    # Fenetre principale du convertisseur
│       ├── JImagePIF.java               # Panneau avec deplacement souris
│       └── PanneauImage.java            # Panneau avec redimensionnement auto
│
├── build/                               # Classes compilees (genere par make)
├── javadoc/                             # Documentation Javadoc (genere par make)
├── .gitignore                           # Fichiers ignores par Git
├── Makefile                             # Automatisation compilation et execution
└── README.md                            # Ce fichier
```

---

## Compilation et lancement

Pour recuperer le projet :
```bash
# Cloner le depot
git clone https://grond.iut-fbleau.fr/barhani/SAE32_2025
cd SAE32_2025
```

### Compilation et creation des JAR
```bash
make all
```

Compile toutes les classes Java et cree les deux archives :
- `convertisseur.jar`
- `visualisateur.jar`

---

### Lancer le convertisseur 
```bash
# Sans argument : ouvre une fenetre de selection
make runconvert

# Avec fichier source uniquement
java -jar convertisseur.jar image.png

# Avec fichier source et destination
java -jar convertisseur.jar image.png sortie.pif
```

---

### Lancer le visualisateur 
```bash
# Sans argument : ouvre une fenetre de selection
make runvisualize

# Avec fichier .pif directement
java -jar visualisateur.jar image.pif
```

---

## Documentation

La documentation du code source est generee via Javadoc :
```bash
make javadoc
```

Le resultat est disponible dans le dossier `javadoc/`, accessible via `javadoc/index.html`.

---

## Nettoyage
```bash
# Supprime les classes compilees
make clean

# Supprime les archives JAR
make cleanjar

# Supprime classes et JAR
make mrproper

# Supprime la documentation Javadoc
make cleanjavadoc

# Supprime tout ce qui est genere
make cleanall
```

---

## Auteurs

Ce projet a ete realise par :

- **Yilmaz Kevin**
- **Barhani Adam**

Professeur : **Luc Hernandez**
IUT de Fontainebleau — BUT Informatique 2eme annee — 2025/2026