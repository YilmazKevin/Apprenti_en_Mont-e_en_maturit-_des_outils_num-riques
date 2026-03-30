# Étude de Cas : Migration et Modernisation du logiciel EasyMat (Solumat)

Ce document détaille la méthodologie appliquée lors de la migration du logiciel EasyMat vers une solution agile sur Power Platform, afin de répondre aux nouveaux défis métiers de Solumat GAT.

## 1. Analyse de l'existant 
* **Logiciel :** EasyMat (outil de gestion de parc matériel).
* **Problématiques identifiées :** Interface vieillissante (UX), manque de flexibilité pour les rapports terrain, et difficultés d'accès en mobilité pour les collaborateurs.
* **Ma Mission :** Recueillir les besoins métiers pour concevoir une application Power Apps capable de remplacer ou compléter les fonctions critiques.

## 2. Phase d'immersion et Audit 
Pour ce projet, j'ai mis en place un cycle d'audit rigoureux :
* **Tests unitaires des fonctions :** J'ai testé chaque module d'EasyMat pour comprendre la logique de calcul et les flux de données.
* **Interviews Terrain :** Discussions directes avec les chefs d'atelier et gestionnaires de parc pour identifier les points de blocage (ex: lenteur de saisie, manque de photos).
* **Analyse de la donnée :** Identification des sources SQL et Excel, compréhension du modèle de données (clés primaires, relations) pour garantir l'intégrité lors de la migration.

## 3. Prise de décision et Architecture
Après l'audit, j'ai structuré la solution autour de 3 piliers :
1. **Application Mobile (Power Apps) :** Pour une saisie intuitive directement sur le terrain (mode déconnecté, photos, scans).
2. **Modèle Sémantique DSI :** Connexion directe aux bases de données via des passerelles sécurisées pour éviter la saisie en double.
3. **Automatisation (Power Automate) :** Envoi d'alertes automatiques en cas d'anomalies détectées sur le matériel.

## 4. Résultat et Valeur Ajoutée
* **Fiabilité :** Réduction des erreurs de saisie grâce à des menus déroulants et des contrôles de cohérence.
* **Maintenance :** Solution maintenable par la DSI car basée sur un modèle de données standardisé.
* **Adoption :** Taux d'utilisation en hausse grâce à une interface co-construite avec les utilisateurs finaux.

---
**Lien avec le poste chez Safran :** Cette expérience de migration "EasyMat vers Power Apps" est directement transposable à la mission de Safran : analyser des outils R&T existants pour les intégrer de manière robuste dans une plateforme d'ingénierie moderne.
