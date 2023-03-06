INSERT INTO ingredient (name, vegetarian) VALUES ('Potatoes', true);
INSERT INTO ingredient (name, vegetarian) VALUES ('Salmon', false);
INSERT INTO ingredient (name, vegetarian) VALUES ('Olive Oil', true);
INSERT INTO ingredient (name, vegetarian) VALUES ('Salt', true);

INSERT INTO recipe (name, instructions, servings) VALUES ('Potato Salad', 'Boil potatoes, mix with olive oil, salt and pepper.', 4);
INSERT INTO recipe (name, instructions, servings) VALUES ('Salmon Tartare', 'Dice salmon, mix with olive oil, salt and pepper.', 2);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (1, 1);
INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (1, 3);
INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (1, 4);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (2, 2);
INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (2, 3);
INSERT INTO recipe_ingredient (recipe_id, ingredient_id) VALUES (2, 4);