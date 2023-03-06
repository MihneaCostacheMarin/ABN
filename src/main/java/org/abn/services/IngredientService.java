package org.abn.services;

import lombok.AllArgsConstructor;
import org.abn.dtos.IngredientDTO;
import org.abn.dtos.response.Message;
import org.abn.dtos.response.SaveResponse;
import org.abn.models.Ingredient;
import org.abn.repository.IngredientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class IngredientService {
    private IngredientRepository ingredientRepository;

    public ResponseEntity<Object> addIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        ingredient.setVegetarian(ingredientDTO.getVegetarian());

        ingredientRepository.save(ingredient);

        return new ResponseEntity<>(SaveResponse.builder()
                .timestamp(String.valueOf(System.currentTimeMillis()))
                .status(HttpStatus.OK.value())
                .message(Message.MSG_CONTENT_SAVED)
                .id(ingredient.getId())
                .build(), HttpStatus.OK);
    }
}
