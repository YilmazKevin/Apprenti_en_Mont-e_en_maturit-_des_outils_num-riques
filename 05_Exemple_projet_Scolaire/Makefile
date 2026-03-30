### VARIABLES ###
JC = javac

ifeq ($(OS),Windows_NT)
	SEP = ;
else
	SEP = :
endif

JCFLAGS = -encoding UTF-8 -implicit:none -d build -cp "build" -sourcepath "src"
JVM = java
JVMFLAGS = -cp "build"

BUILD_PATH = build/fr/iutfbleau/sae322025/

### REGLES ESSENTIELLES ###

${BUILD_PATH}Convertisseur.class : src/fr/iutfbleau/sae322025/Convertisseur.java ${BUILD_PATH}vue/FenetreConvertisseur.class ${BUILD_PATH}Visualisateur.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/Convertisseur.java
	@echo "Fichiers compilés."
	@echo "Pour lancer le convertisseur :"
	@echo "    - make convert  (crée le jar)"
	@echo "    - java -jar convertisseur.jar / make runconvert"
	@echo "    - java -jar convertisseur.jar <fichier>"
	@echo "    - java -jar convertisseur.jar <fichier> <sortie>"
	@echo ""
	@echo "Pour lancer le visualisateur :"
	@echo "    - make visualize  (crée le jar)"
	@echo "    - java -jar visualisateur.jar / make runvisualize"
	@echo "    - java -jar visualisateur.jar <fichier.pif>"
	@echo "(Vous pouvez aussi utiliser make all pour créer les 2 archives .jar en une seule commande)"

${BUILD_PATH}Visualisateur.class : src/fr/iutfbleau/sae322025/Visualisateur.java ${BUILD_PATH}vue/FenetreVisualisation.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/Visualisateur.java

# --- MODELE ---

${BUILD_PATH}modele/Noeud.class : src/fr/iutfbleau/sae322025/modele/Noeud.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/Noeud.java

${BUILD_PATH}modele/Feuille.class : src/fr/iutfbleau/sae322025/modele/Feuille.java ${BUILD_PATH}modele/Noeud.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/Feuille.java

${BUILD_PATH}modele/Code.class : src/fr/iutfbleau/sae322025/modele/Code.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/Code.java

${BUILD_PATH}modele/TableDeFrequences.class : src/fr/iutfbleau/sae322025/modele/TableDeFrequences.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/TableDeFrequences.java

${BUILD_PATH}modele/AnalyseurImage.class : src/fr/iutfbleau/sae322025/modele/AnalyseurImage.java ${BUILD_PATH}modele/TableDeFrequences.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/AnalyseurImage.java

${BUILD_PATH}modele/ArbreDeHuffman.class : src/fr/iutfbleau/sae322025/modele/ArbreDeHuffman.java ${BUILD_PATH}modele/Noeud.class ${BUILD_PATH}modele/Feuille.class ${BUILD_PATH}modele/Code.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/ArbreDeHuffman.java

${BUILD_PATH}modele/GenerateurDeCodes.class : src/fr/iutfbleau/sae322025/modele/GenerateurDeCodes.java ${BUILD_PATH}modele/Noeud.class ${BUILD_PATH}modele/Feuille.class ${BUILD_PATH}modele/Code.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/GenerateurDeCodes.java

${BUILD_PATH}modele/TransformateurCanonique.class : src/fr/iutfbleau/sae322025/modele/TransformateurCanonique.java ${BUILD_PATH}modele/Code.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/TransformateurCanonique.java

${BUILD_PATH}modele/io/EcritureBinaire.class : src/fr/iutfbleau/sae322025/modele/io/EcritureBinaire.java ${BUILD_PATH}modele/Code.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/io/EcritureBinaire.java

${BUILD_PATH}modele/io/LectureBinaire.class : src/fr/iutfbleau/sae322025/modele/io/LectureBinaire.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/io/LectureBinaire.java

${BUILD_PATH}modele/Compresseur.class : src/fr/iutfbleau/sae322025/modele/Compresseur.java ${BUILD_PATH}modele/ArbreDeHuffman.class ${BUILD_PATH}modele/AnalyseurImage.class ${BUILD_PATH}modele/GenerateurDeCodes.class ${BUILD_PATH}modele/TransformateurCanonique.class ${BUILD_PATH}modele/io/EcritureBinaire.class ${BUILD_PATH}modele/TableDeFrequences.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/Compresseur.java

${BUILD_PATH}modele/Decompresseur.class : src/fr/iutfbleau/sae322025/modele/Decompresseur.java ${BUILD_PATH}modele/ArbreDeHuffman.class ${BUILD_PATH}modele/io/LectureBinaire.class ${BUILD_PATH}modele/Code.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/modele/Decompresseur.java

# --- CONTROLEUR ---

${BUILD_PATH}controleur/ControleurSouris.class : src/fr/iutfbleau/sae322025/controleur/ControleurSouris.java ${BUILD_PATH}vue/JImagePIF.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/controleur/ControleurSouris.java

${BUILD_PATH}controleur/ControleurBouton.class : src/fr/iutfbleau/sae322025/controleur/ControleurBouton.java ${BUILD_PATH}modele/Compresseur.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/controleur/ControleurBouton.java

# --- VUE ---

${BUILD_PATH}vue/JImagePIF.class : src/fr/iutfbleau/sae322025/vue/JImagePIF.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/vue/JImagePIF.java

${BUILD_PATH}vue/PanneauImage.class : src/fr/iutfbleau/sae322025/vue/PanneauImage.java
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/vue/PanneauImage.java

${BUILD_PATH}vue/FenetreVisualisation.class : src/fr/iutfbleau/sae322025/vue/FenetreVisualisation.java ${BUILD_PATH}modele/Decompresseur.class ${BUILD_PATH}vue/JImagePIF.class ${BUILD_PATH}controleur/ControleurSouris.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/vue/FenetreVisualisation.java

${BUILD_PATH}vue/FenetreConvertisseur.class : src/fr/iutfbleau/sae322025/vue/FenetreConvertisseur.java ${BUILD_PATH}modele/Compresseur.class ${BUILD_PATH}vue/PanneauImage.class ${BUILD_PATH}controleur/ControleurBouton.class
	${JC} ${JCFLAGS} src/fr/iutfbleau/sae322025/vue/FenetreConvertisseur.java

### REGLES OPTIONNELLES ###

convert : ${BUILD_PATH}Convertisseur.class
	echo "Main-Class: fr.iutfbleau.sae322025.Convertisseur" > MANIFEST.MF
	jar cvfm convertisseur.jar MANIFEST.MF -C build .
	rm MANIFEST.MF

visualize : ${BUILD_PATH}Visualisateur.class
	echo "Main-Class: fr.iutfbleau.sae322025.Visualisateur" > MANIFEST.MF
	jar cvfm visualisateur.jar MANIFEST.MF -C build .
	rm MANIFEST.MF

all : convert visualize

runconvert : convert
	java -jar convertisseur.jar

runvisualize : visualize
	java -jar visualisateur.jar

clean :
	-rm -r build/fr/iutfbleau/sae322025/

cleanjar :
	-rm *.jar

mrproper : clean cleanjar

javadoc :
	mkdir -p javadoc
	javadoc -d javadoc/ -sourcepath src -subpackages fr.iutfbleau.sae322025

cleanjavadoc :
	-rm -r javadoc

cleanall : clean cleanjar cleanjavadoc

### BUTS FACTICES ###
.PHONY : convert visualize runconvert runvisualize all clean cleanjar mrproper javadoc cleanjavadoc cleanall