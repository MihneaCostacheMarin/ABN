package org.abn.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.abn.models.Ingredient;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class RecipeDTO {
    private Long id;
    private String name;
    private int servings;
    private String instructions;
    private Set<Ingredient> ingredients;
}
