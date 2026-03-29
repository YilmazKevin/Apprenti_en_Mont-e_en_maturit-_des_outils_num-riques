# Processus Décisionnel : Arbitrage entre Maintenance et Refonte

Face à un outil R&T (Recherche & Technologie) existant, ma démarche de prise de décision s'appuie sur une matrice de choix multicritères.

##  Les 3 Piliers de l'Arbitrage
Pour chaque brique logicielle de la future plateforme Safran, je me pose trois questions :

1. **La Robustesse (Qualité) :** Le code actuel est-il assez stable pour être industrialisé ?
2. **La Maintenabilité (Pérennité) :** Est-ce que la DSI pourra le maintenir dans 5 ans ?
3. **Le ROI Métier (Efficacité) :** Quel est le gain de temps pour l'ingénieur composite ?

##  Ma Stratégie de Choix (Exemple EasyMat/Vinci)

| Scénario | Décision Technique | Justification |
| :--- | :--- | :--- |
| **Outil instable mais fonctionnel** | **Refonte agile (Power Platform)** | Pour garantir la sécurité des données et une interface moderne (UX). |
| **Code performant mais complexe** | **Encapsulation & Documentation** | On garde le moteur de calcul (ex: Python/C++), mais on crée une interface simplifiée. |
| **Processus manuel chronophage** | **Automatisation (Power Automate)** | On élimine l'erreur humaine sans modifier l'outil de base. |

##  Ma vision pour Safran Composites
Mon rôle en tant qu'apprenti ingénieur n'est pas de tout reconstruire, mais de définir l'**écosystème optimal**. Cela signifie parfois :
* Préférer la **réutilisation** de fonctions existantes performantes (Modularité).
* Proposer des **transferts technologiques** vers des solutions standardisées par le Groupe.
* Arbitrer en faveur de la solution qui offre la meilleure **interopérabilité** avec les autres outils de la plateforme.
