package org.abn.controllers;


import lombok.AllArgsConstructor;
import org.abn.dtos.RecipeDTO;
import org.abn.dtos.response.RecipeSearchRequest;
import org.abn.models.Recipe;
import org.abn.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@AllArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping("")
    public ResponseEntity<Object> addRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.addRecipe(recipeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@RequestBody RecipeDTO recipeDTO) {
        return recipeService.update(recipeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable Long id) {
        return recipeService.deleteRecipe(id);
    }

    @GetMapping("/search")
    public List<RecipeDTO> searchRecipes(@ModelAttribute RecipeSearchRequest request) {
        return recipeService.searchRecipes(request);
    }
}
