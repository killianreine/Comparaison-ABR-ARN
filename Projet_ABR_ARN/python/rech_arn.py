import csv
import matplotlib.pyplot as plt
import numpy as np

# Listes pour stocker les temps et tailles
liste_execution = []
liste_taille = []

# Chemin du fichier CSV (RECHERCHE)
chemin_fichier = 'src/main/ressources/csv/rechercheARN.csv'   # adapte si ABR

# Lecture du fichier CSV
with open(chemin_fichier, mode='r', newline='') as fichier:
    lecteur_csv = csv.reader(fichier)

    for ligne in lecteur_csv:
        if len(ligne) > 1:  # (taille, temps)
            try:
                valeur = int(ligne[1].replace(',', '').strip())
                liste_execution.append(valeur)
                liste_taille.append(int(ligne[0]))
            except ValueError:
                print(f"Erreur de conversion pour la ligne : {ligne}")

# Vérification des données
if not liste_execution:
    raise ValueError("Aucune donnée valide lue dans le fichier CSV.")

# Fonction de moyenne mobile
def moyenne_mobile(liste, fenetre=10):
    if len(liste) < fenetre:
        return liste
    return np.convolve(liste, np.ones(fenetre) / fenetre, mode='valid')

# Lissage des données
liste_execution_lisse = moyenne_mobile(liste_execution, fenetre=10)
tailles_lisse = liste_taille[:len(liste_execution_lisse)]

# Moyenne globale (utile pour le rapport)
print("Temps moyen de recherche :", sum(liste_execution) / len(liste_execution))

# Création du graphique
plt.figure(figsize=(10, 5))
# Courbe brute
plt.plot(liste_taille,liste_execution,label="Temps de recherche (brut)",alpha=0.5,color='b')

# Courbe lissée
plt.plot(tailles_lisse,liste_execution_lisse,label="Temps de recherche moyen",linewidth=1.5,color='r')

# Labels et titre
plt.xlabel("Taille de l'arbre")
plt.ylabel("Temps de recherche (ns)")
plt.title("Temps de recherche en fonction de la taille de l'arbre")
plt.legend()
plt.grid()

# Sauvegarde
plt.savefig("src/main/ressources/image/recherche_arn_moy.png")

# Affichage
plt.show()
