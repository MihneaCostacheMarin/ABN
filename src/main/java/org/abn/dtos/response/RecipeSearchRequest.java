package org.abn.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeSearchRequest {
    private Boolean vegetarian;
    private Integer servings;
    private List<String> includeIngredients;
    private List<String> excludeIngredients;
    private String instructions;
}
