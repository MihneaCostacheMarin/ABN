import org.abn.controllers.IngredientController;
import org.abn.dtos.IngredientDTO;
import org.abn.services.IngredientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    private IngredientController ingredientController;

    @Test
    public void testAddIngredient() {
        // given
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName("Test Ingredient");
        ingredientDTO.setVegetarian(true);
        when(ingredientService.addIngredient(ingredientDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        // when
        ResponseEntity<Object> response = ingredientController.addIngredient(ingredientDTO);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
