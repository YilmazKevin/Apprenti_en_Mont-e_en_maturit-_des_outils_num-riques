def calculer_densite_composite(masse, volume):
    """Calcule la densité en vérifiant la validité des données (Robustesse)."""
    try:
        # Validation métier : une masse ou un volume ne peuvent être <= 0
        if masse <= 0 or volume <= 0:
            raise ValueError("La masse et le volume doivent être positifs.")
        
        densite = masse / volume
        return round(densite, 3)
    
    except ZeroDivisionError:
        return "Erreur : Le volume ne peut pas être nul."
    except TypeError:
        return "Erreur : Les entrées doivent être des nombres."
    except Exception as e:
        return f"Erreur critique : {e}"

# Exemple d'utilisation sécurisée
print(f"Résultat : {calculer_densite_composite(1500, 0.85)} kg/m3")