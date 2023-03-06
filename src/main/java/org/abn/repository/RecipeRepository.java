package org.abn.repository;

import org.abn.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByServings(int servings);

    List<Recipe> findByIngredientsNameIn(List<String> ingredientNames);

    List<Recipe> findByIngredientsNameNotIn(List<String> ingredientNames);

    List<Recipe> findByInstructionsContaining(String instructions);
}
