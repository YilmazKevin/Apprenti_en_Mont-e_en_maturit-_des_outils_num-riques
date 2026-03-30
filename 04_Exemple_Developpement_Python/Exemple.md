# Étude de Code : Robustesse et Fiabilisation Numérique

Ce document analyse un exemple de fonction Python conçue pour répondre aux exigences de **mise en qualité** et de **robustesse** demandées par Safran Composites.

## Le Code Source (Exemple : Calcul de Densité)

```python
def calculer_densite_composite(masse, volume):
    """Calcule la densité en vérifiant la validité des données (Robustesse)."""
    try:
        # 1. Validation métier : une masse ou un volume ne peuvent être <= 0
        if masse <= 0 or volume <= 0:
            raise ValueError("La masse et le volume doivent être positifs.")
        
        # 2. Calcul et précision
        densite = masse / volume
        return round(densite, 3)
    
    # 3. Gestion des exceptions (Pipeline de maintenabilité)
    except ZeroDivisionError:
        return "Erreur : Le volume ne peut pas être nul."
    except TypeError:
        return "Erreur : Les entrées doivent être des nombres."
    except Exception as e:
        return f"Erreur critique : {e}"
