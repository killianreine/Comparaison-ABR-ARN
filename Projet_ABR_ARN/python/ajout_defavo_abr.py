import csv
import matplotlib.pyplot as plt
import numpy as np

# Liste pour stocker les différents temps d'exécution
liste_execution = []
liste_taille = []

# Génération de la liste des tailles du tableau
chemin_fichier = '../csv/AjoutPireABR.csv'

# Lecture du fichier CSV
with open(chemin_fichier, mode='r', newline='') as fichier:
    lecteur_csv = csv.reader(fichier)

    for ligne in lecteur_csv:
        if len(ligne) > 1:  # Vérifie qu'il y a au moins deux valeurs (taille, temps)
            try:
                valeur = int(ligne[1].replace(',', '').strip())  # Suppression des virgules éventuelles
                liste_execution.append(valeur)
                liste_taille.append(int(ligne[0]))
            except ValueError:
                print(f"Erreur de conversion pour la ligne : {ligne}")

# Vérification que la liste des exécutions n'est pas vide
if not liste_execution:
    raise ValueError("Aucune donnée valide lue dans le fichier CSV.")

# Génération de la liste des tailles en fonction des données valides

# Fonction de calcul de la moyenne mobile
def moyenne_mobile(liste, fenetre=10):
    if len(liste) < fenetre:
        return liste  # Retourne la liste originale si elle est trop courte
    return np.convolve(liste, np.ones(fenetre)/fenetre, mode='valid')

liste_execution_lisse = moyenne_mobile(liste_execution, fenetre=10)
print(sum(liste_execution)/len(liste_execution))
# Ajustement de la liste des tailles pour correspondre à la moyenne mobile
tailles_lisse = liste_taille[:len(liste_execution_lisse)]

# Création du graphique
plt.figure(figsize=(10, 5))
plt.plot(liste_taille, liste_execution, label="Temps d'exécution", color='b', alpha=0.5)
plt.plot(tailles_lisse, liste_execution_lisse, label="Temps d'exécution moyen", color='r', linewidth=1.5)

# Ajout des labels et du titre
plt.xlabel("Taille de l'ABR")
plt.ylabel("Temps d'exécution (ms)")
plt.title("Temps d'exécution en fonction de la taille de l'arbre")
plt.legend()
plt.grid()

# Affichage du graphique
plt.savefig("../image/ajout_abr_defavo_moy.png")

# Affichage de la moyenne d'exécution
print(sum(liste_execution)/len(liste_execution))