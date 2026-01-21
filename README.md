# Projet : Études sur les arbres binaires

## 📚 Contexte académique

Projet réalisé dans le cadre de l'unité d'enseignement **Algorithmique avancée** (L3 Informatique) 
**Université Le Havre Normandie** - Année 2025-2026 
**Enseignants :** Stefan Balev et Véronique Jay 
**Auteur :** Killian Reine

## 🎯 Objectifs du projet

Ce projet vise à comparer deux structures de données arborescentes :
- **ABR** : Arbres Binaires de Recherche
- **ARN** : Arbres Rouge et Noir

L'implémentation est réalisée en **Java** en s'appuyant sur l'interface `Collection<T>`.

## 🌳 Structures implémentées

### Arbre Binaire de Recherche (ABR)
Structure où chaque nœud respecte la propriété :
- Les valeurs du sous-arbre gauche sont **inférieures** à la valeur du nœud
- Les valeurs du sous-arbre droit sont **supérieures** à la valeur du nœud

### Arbre Rouge et Noir (ARN)
Arbre binaire de recherche **auto-équilibré** avec les propriétés suivantes :
1. Chaque nœud est soit rouge, soit noir
2. La racine est noire
3. Les feuilles (sentinelles) sont noires
4. Un nœud rouge a toujours deux fils noirs
5. Tous les chemins d'un nœud vers ses feuilles contiennent le même nombre de nœuds noirs

## 🏗️ Architecture du projet
```
BinaryTree<T> (classe abstraite)
    │
    ├── Node (classe interne)
    ├── TreeIterator (itérateur infixe)
    └── Méthodes abstraites et concrètes
         │
         └── ABR<T> (hérite de BinaryTree)
              │
              └── ARN<T> (hérite de ABR)
                   └── NodeRN (nœuds colorés + sentinelle)
```

### Classe `BinaryTree<T>`
Classe abstraite définissant les caractéristiques communes :
- **Attributs :** racine, nombre de nœuds, comparateur
- **Méthodes abstraites :** `add()`, `removeValue()`, `research()`
- **Méthodes concrètes :** `size()`, `clear()`, `contains()`, `isEmpty()`, `toString()`
- **Itérateur :** Parcours infixe (gauche → racine → droite)

### Classe `ABR<T>`
Implémentation des arbres binaires de recherche :
- Ajout respectant l'ordre binaire
- Suppression avec gestion des trois cas (0, 1 ou 2 enfants)
- Recherche en O(h) où h est la hauteur

### Classe `ARN<T>`
Extension des ABR avec équilibrage automatique :
- Nœuds colorés (rouge/noir)
- Sentinelle unique pour toutes les feuilles
- Corrections après ajout/suppression via rotations et recolorations
- **Rotations gauche/droite** pour maintenir l'équilibre

## 📊 Résultats des tests de performance

### Ajout de valeurs

| Cas | ABR | ARN |
|-----|-----|-----|
| **Aléatoire** | Variabilité importante | Temps réguliers |
| **Ordre croissant** | Dégénérescence (O(n)) | Stable (O(log n)) |

### Suppression
- **ABR :** Pics importants (jusqu'à 13 000 ms), forte volatilité
- **ARN :** Comportement plus stable (< 10 000 ms max), variations périodiques dues au rééquilibrage

### Recherche
Les deux structures présentent des performances similaires en **O(log n)** avec un léger avantage en régularité pour l'ABR sur des données aléatoires.

## 🔑 Fonctionnalités principales

- ✅ Ajout d'éléments avec respect des propriétés
- ✅ Suppression avec maintien de la structure
- ✅ Recherche efficace
- ✅ Parcours infixe via itérateur
- ✅ Affichage graphique dans le terminal
- ✅ Support du comparateur personnalisé
- ✅ Gestion de collections

## 🛠️ Technologies

- **Langage :** Java
- **Interface :** `Collection<T>`
- **Comparaisons :** `Comparator<T>`
- **Tests :** Scripts Python pour génération de courbes

## 📈 Conclusion

Les **ABR** offrent de bonnes performances en conditions aléatoires mais dégénèrent en O(n) dans le pire cas. Les **ARN** garantissent une complexité O(log n) constante grâce à leur mécanisme d'auto-équilibrage, au prix d'une implémentation plus complexe.

**Recommandation :** Privilégier les ARN pour des applications nécessitant des performances prévisibles et stables.

## 📁 Structure du dépôt
```
projet/
├── src/
│   ├── BinaryTree.java
│   ├── ABR.java
│   └── ARN.java
├── python/
│  
├── csv/
│ 
├── images/
│ 
└── README.md
```

## 📝 Références

- Cours d'Algorithmique avancée - Stefan Balev & Véronique Jay
- Méthode d'affichage des arbres - Stefan Balev (TP2)

---

**Université Le Havre Normandie** | L3 Informatique | 2025-2026
