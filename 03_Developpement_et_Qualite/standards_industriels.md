# Standards de Développement et Mise en Qualité Logicielle

Pour garantir le transfert technologique vers les sociétés du Groupe Safran, j'applique des standards de qualité stricts lors de la phase de développement.

## 1. Robustesse et Fiabilisation
Un outil numérique doit être capable de gérer les erreurs sans interrompre le travail de l'ingénieur :
* **Gestion des exceptions :** Mise en place de blocs de capture d'erreurs (Try/Catch) pour fournir des messages explicites à l'utilisateur.
* **Contrôle d'intégrité :** Validation systématique des données d'entrée (ex: vérifier qu'une valeur de contrainte de matériau n'est pas négative).
* **Tests Unitaires :** Création de scénarios de tests pour chaque brique technologique avant son intégration.

## 2. Architecture et Maintenabilité (Le "Pipeline")
* **Modularité :** Découpage du code en fonctions indépendantes pour favoriser la réutilisation (Don't Repeat Yourself - DRY).
* **Versionning (Git) :** Utilisation rigoureuse de Git pour l'historisation des modifications et le travail collaboratif.
* **Clean Code :** Nommage des variables et fonctions en anglais technique ou français explicite (ex: `calcul_module_Young` au lieu de `f1`).

## 3. Documentation (Le livrable final)
La mission chez Safran demande une "documentation de développeur". Voici ma structure type :
1. **Architecture simplifiée :** Schéma des flux de données (ex: entre Power Apps et le Modèle Sémantique).
2. **Guide d'installation :** Prérequis techniques et dépendances.
3. **Dictionnaire des données :** Définition de chaque variable utilisée dans l'outil.
4. **Journal des modifications (Changelog) :** Historique des versions pour assurer le suivi par la DSI.

---
**Note sur mon expérience :** Chez Solumat, l'automatisation de la maintenance via Power BI reposait sur cette rigueur documentaire : sans un dictionnaire de données clair, le modèle sémantique de la DSI n'aurait pas pu être pérennisé.
