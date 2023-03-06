package org.abn.controllers;

import lombok.AllArgsConstructor;
import org.abn.dtos.IngredientDTO;
import org.abn.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
public class IngredientController {

    private IngredientService ingredientService;

    @PostMapping("")
    public ResponseEntity<Object> addIngredient(@RequestBody IngredientDTO ingredientDTO) {
        return ingredientService.addIngredient(ingredientDTO);
    }

}
