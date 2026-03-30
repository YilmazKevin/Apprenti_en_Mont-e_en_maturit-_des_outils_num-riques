# Processus Décisionnel : Maintenance vs Refonte

L'évolution des outils R&T vers la plateforme Safran repose sur une évaluation multicritère systématique de chaque brique logicielle.

## Critères d'Arbitrage
Trois indicateurs clés guident le choix technique :

* **Robustesse (Qualité) :** Aptitude du code ou de la structure actuelle à supporter une industrialisation.
* **Maintenabilité (Pérennité) :** Capacité de la DSI à assurer le support à un horizon de 5 ans.
* **ROI Métier (Efficacité) :** Gain de productivité concret pour l'ingénieur composite.

## Matrice de Décision

| État Initial | Décision Technique | Justification |
| :--- | :--- | :--- |
| **Outil instable / obsolète** | **Refonte (Power Platform)** | Sécurisation des données et modernisation de l'interface utilisateur. |
| **Logique métier performante** | **Optimisation & Documentation** | Conservation de l'intelligence métier existante avec simplification de l'accès. |
| **Processus manuel** | **Automatisation** | Suppression des tâches répétitives via les outils de flux (Power Automate). |

## Objectifs du Projet (Périmètre Apprentissage)
La démarche privilégie la cohérence de l'écosystème à la reconstruction systématique :

1. **Modularité :** Réutilisation des briques fonctionnelles existantes et validées.
2. **Standardisation :** Alignement sur les solutions logicielles préconisées par le Groupe.
3. **Interopérabilité :** Garantie de la communication fluide entre les modules de la plateforme.
