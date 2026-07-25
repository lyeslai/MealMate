# MealMate
 
**MealMate** est une plateforme de recettes qui aide à cuisiner avec ce qu'on a déjà dans le placard.
L'utilisateur renseigne les ingrédients qu'il possède, et l'application lui propose les recettes correspondantes,
triées par taux de correspondance, avec le détail des ingrédients manquants.
## Fonctionnalités
 
### Cœur de l'application
- **Catalogue de recettes** classées par type (entrée, plat, dessert, encas, petit-déjeuner, boisson)
- **Recherche** sur les recettes (titre, ingrédients)
- **Matching par ingrédients** : l'utilisateur indique ce qu'il a sous la main, l'app retourne les recettes qui matchent le mieux, avec le score de correspondance et la liste des ingrédients manquants
- **Calcul de calories** par recette, basé sur les ingrédients et leurs quantités
### Fonctionnalités utilisateur (avec authentification)
- Favoris / recettes sauvegardées
- Notation et avis sur les recettes
- Planning de repas hebdomadaire (drag & drop)
- Ajustement automatique des quantités et des calories selon le nombre de convives
- Filtres avancés : régime alimentaire (végétarien, vegan, sans gluten...), temps de préparation, difficulté
- Substitution d'ingrédients ("pas d'œufs ? voici des alternatives")
## 🛠️ Stack technique
 
| Composant | Techno |
|---|---|
| Backend | Spring Boot 3, Java 21 |
| Persistance | Spring Data JPA, PostgreSQL |
| Recherche | Elasticsearch |?
| Frontend web | Angular, Tailwind CSS |
| Mobile | Ionic + Angular |?
| IA | Ollama (local, gratuit) |?
| Authentification | Spring Security + JWT |
