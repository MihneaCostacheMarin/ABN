create table recipe (
  id serial primary key,
  name VARCHAR(255) NOT NULL,
  instructions text,
  servings int not null
);

create table ingredient (
  id serial primary key,
  name VARCHAR(255) NOT NULL,
  vegetarian boolean not null
);

create table recipe_ingredient (
  recipe_id bigint not null,
  ingredient_id bigint not null,
  constraint recipe_ingredient_pk primary key (recipe_id, ingredient_id),
  constraint recipe_ingredient_recipe_fk foreign key (recipe_id) references recipe(id) on delete cascade,
  constraint recipe_ingredient_ingredient_fk foreign key (ingredient_id) references ingredient(id) on delete cascade
);
