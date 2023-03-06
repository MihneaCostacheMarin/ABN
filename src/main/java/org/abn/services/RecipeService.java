package org.abn.services;

import lombok.AllArgsConstructor;
import org.abn.dtos.IngredientDTO;
import org.abn.dtos.RecipeDTO;
import org.abn.dtos.response.Message;
import org.abn.dtos.response.RecipeSearchRequest;
import org.abn.dtos.response.SaveResponse;
import org.abn.models.Ingredient;
import org.abn.models.Recipe;
import org.abn.repository.IngredientRepository;
import org.abn.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public ResponseEntity<Object> getRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (Boolean.FALSE.equals(recipeOptional.isPresent()))
            return new ResponseEntity<>(Message.MSG_CONTENT_NOT_FOUND, HttpStatus.NOT_FOUND);

        RecipeDTO recipeDTO = toRecipeDTO(recipeOptional.get());

        return new ResponseEntity<>(recipeDTO, HttpStatus.OK);
    }

    private RecipeDTO toRecipeDTO(Recipe recipe) {
        return RecipeDTO.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ingredients(recipe.getIngredients())
                .build();
    }

    private IngredientDTO toIngrdientDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .build();
    }

    public ResponseEntity<Object> addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = insertFromRecipe(recipeDTO);
        if (Objects.isNull(recipe)) {
            return new ResponseEntity<>(SaveResponse.builder()
                    .timestamp(String.valueOf(System.currentTimeMillis()))
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("One of the ingredients does not exist")
                    .id(recipeDTO.getId())
                    .build(), HttpStatus.BAD_REQUEST);
        }

        recipeRepository.save(recipe);

        return new ResponseEntity<>(SaveResponse.builder()
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .status(HttpStatus.OK.value())
                .message(Message.MSG_CONTENT_SAVED)
                .id(recipe.getId())
                .build(), HttpStatus.OK);
    }


    private Recipe insertFromRecipe(RecipeDTO recipeDTO) {
        if (checkIngredients(recipeDTO.getIngredients())) {
            Recipe recipe = new Recipe();
            setRecipeProperties(recipeDTO, recipe);

            return recipe;
        }

        return null;
    }

    private void setRecipeProperties(RecipeDTO recipeDTO, Recipe recipe) {
        recipe.setId(recipeDTO.getId());
        recipe.setName(recipeDTO.getName());
        recipe.setServings(recipeDTO.getServings());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setIngredients(recipeDTO.getIngredients());
    }

    private boolean checkIngredients(Set<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(ingredient.getName());
            if (!ingredientOptional.isPresent()) {
                return false;
            }
        }

        return true;
    }

    public ResponseEntity<Object> update(RecipeDTO recipeDTO) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeDTO.getId());

        if (Boolean.FALSE.equals(recipeOptional.isPresent()))
            return new ResponseEntity<>(Message.MSG_CONTENT_NOT_FOUND, HttpStatus.NOT_FOUND);

        Recipe recipe = recipeOptional.get();
        setRecipeProperties(recipeDTO, recipe);

        recipeRepository.save(recipe);

        return new ResponseEntity<>(SaveResponse.builder()
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .status(HttpStatus.OK.value())
                .message(Message.MSG_CONTENT_UPDATED)
                .id(recipe.getId())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteRecipe(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            recipeRepository.delete(recipe);

            return new ResponseEntity<>(SaveResponse.builder()
                    .timestamp(String.valueOf(System.currentTimeMillis()))
                    .status(HttpStatus.OK.value())
                    .message(Message.MSG_CONTENT_DELETED)
                    .id(recipe.getId())
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(SaveResponse.builder()
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .status(HttpStatus.BAD_REQUEST.value())
                .message("The recipe with id " + id + " does not exist")
                .id(id)
                .build(), HttpStatus.BAD_REQUEST);
    }

    public List<RecipeDTO> searchRecipes(RecipeSearchRequest searchRequest) {
        List<Recipe> recipes = recipeRepository.findAll().stream()
                .filter(recipe -> searchRequest.getVegetarian() == null || recipe.getIngredients().stream()
                        .allMatch(Ingredient::isVegetarian) == searchRequest.getVegetarian())
                .filter(recipe -> searchRequest.getServings() == null || recipe.getServings() == recipe.getServings())
                .filter(recipe -> searchRequest.getIncludeIngredients() == null || recipe.getIngredients().stream()
                        .anyMatch(ingredient -> searchRequest.getIncludeIngredients().contains(ingredient.getName())))
                .filter(recipe -> searchRequest.getExcludeIngredients() == null || recipe.getIngredients().stream()
                        .noneMatch(ingredient -> searchRequest.getExcludeIngredients().contains(ingredient.getName())))
                .filter(recipe -> searchRequest.getInstructions() == null || recipe.getInstructions().contains(searchRequest.getInstructions()))
                .collect(Collectors.toList());

        return toRecipeDTOList(recipes);
    }

    private List<RecipeDTO> toRecipeDTOList(List<Recipe> recipes) {
        List<RecipeDTO> recipeDTOS = new ArrayList<>();
        for(Recipe recipe : recipes) {
            recipeDTOS.add(toRecipeDTO(recipe));
        }

        return recipeDTOS;
    }


}
