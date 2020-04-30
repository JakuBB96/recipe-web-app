package com.barancewicz.recipewebapp.bootstrap;

import com.barancewicz.recipewebapp.domain.*;
import com.barancewicz.recipewebapp.repositories.CategoryRepository;
import com.barancewicz.recipewebapp.repositories.RecipeRepository;
import com.barancewicz.recipewebapp.repositories.UnitOfMeasureRepository;
import com.barancewicz.recipewebapp.services.RoleService;
import com.barancewicz.recipewebapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class BootstrapDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UserService userService;
    private final RoleService roleService;
    public BootstrapDataLoader(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, UserService userService, RoleService roleService) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadUsers();
        loadRoles();
        assignUsersToUserRole();
        assignUsersToAdminRole();
    }

    private Byte[] bootstrapImage(String image){
        try {
//            File image = new File("");
//            byte[] bytes = new byte[0];
//            bytes = Files.readAllBytes(image.toPath());
//            Byte[] bytesBoxed = new Byte[bytes.length];
//            int i = 0;
//            for (byte primByte : bytes) {
//                bytesBoxed[i++] = primByte;
//            }
            InputStream in = getClass().getResourceAsStream("/static/images/" + image);

            Byte[] byteObjects = new Byte[in.available()];

            int i=0;
            for(byte b: in.readAllBytes())
                byteObjects[i++] = b;

            return byteObjects;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private List<Recipe> getRecipes() {
        log.debug("Start initializing data in bootstrap class");
        List<Recipe> recipes = new ArrayList<>(2);

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each");

        if(!eachUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon");

        if(!tableSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if(!teaSpoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash");

        if(!dashUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint");

        if(!pintUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup");

        if(!cupsUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> ounceUomOptional = unitOfMeasureRepository.findByDescription("Ounce");

        if(!ounceUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teaspoon = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = dashUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();
        UnitOfMeasure ounceUom = ounceUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByName("American");

        if(!americanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByName("Mexican");

        if(!mexicanCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByName("Italian");

        if(!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category americanCategory = americanCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setServings(4);
        guacRecipe.setUrl("http:www.sda.com");
        guacRecipe.setSource("Example Recipes");
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");

        guacRecipe.setNotes(guacNotes);

        //very redundent - could add helper method, and make this simpler
        guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), teaspoon));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom));
        guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        guacRecipe.setImage(bootstrapImage("guacamole.jpg"));

        //add to return list
        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);

        tacosRecipe.setServings(4);
        tacosRecipe.setUrl("http:www.sda.com");
        tacosRecipe.setSource("Example Recipes");

        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoon));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);
        tacosRecipe.setImage(bootstrapImage("tacos.jpg"));
        recipes.add(tacosRecipe);

        //salmon
        Recipe salmonRecipe = new Recipe();
        salmonRecipe.setDescription("Salmon with Vegetables");
        salmonRecipe.setPrepTime(15);
        salmonRecipe.setCookTime(20);
        salmonRecipe.setServings(2);
        salmonRecipe.setUrl("https://www.simplyrecipes.com/recipes/cooking_for_two_easy_salmon_and_vegetable_foil_packets/");
        salmonRecipe.setSource("www.simplyrecipes.com/recipes");
        salmonRecipe.setDifficulty(Difficulty.EASY);
        salmonRecipe.setDirections("1 Preheat the oven to 375ºF." +
                "\n" +
                "2 Make the harissa mayonnaise: In a small bowl, stir the mayonnaise, harissa paste, honey, and lime juice." +
                "\n" +
                "3 Assemble the packets: On a work surface, place an 18-inch-long piece of foil, preferably non-stick, with the long side parallel to the countertop." +
                " Brush the face-up side of the foil with 1 teaspoon of oil. (Optional: Instead of oiling the foil, cut a piece of parchment that is 9- by 12-inches and place it over one half of the foil.)\n" +
                "\n" +
                "Arrange half the potatoes on the oiled side of the foil and sprinkle with a pinch of salt and a pinch of pepper.\n" +
                "Place half the snap peas over the potatoes, followed by half the cherry tomatoes. Sprinkle lightly with salt and pepper, and drizzle with 1 teaspoon oil. " +
                "Sprinkle with 1 teaspoon of lime juice. Place a salmon fillet over the vegetables and spread with half the harissa mayonnaise." +
                "\n" +
                "Read more: https://www.simplyrecipes.com/recipes/cooking_for_two_easy_salmon_and_vegetable_foil_packets/");

        Notes salmonNotes = new Notes();
        salmonNotes.setRecipeNotes("  This meal-in-one little package highlights the method of oven-steaming fish and is outrageously easy.\n" +
                "Layer potatoes, snap peas, and tomatoes in a foil packet, top with salmon fillets, and slather it with spicy harissa mayonnaise. \n" +
                "The flavors of the harissa mayonnaise combined with the salmon and vegetables infuse the whole dish with bold, spicy flavors. Hallelujah! Dinner is done!\n" +
                "\n" +
                "\n" +
                "Read more: https://www.simplyrecipes.com/recipes/cooking_for_two_easy_salmon_and_vegetable_foil_packets/");

        salmonRecipe.setNotes(salmonNotes);

        //very redundent - could add helper method, and make this simpler
        salmonRecipe.addIngredient(new Ingredient("Rip mayonnaise", new BigDecimal("0.25"), cupsUom));
        salmonRecipe.addIngredient(new Ingredient("Salt", new BigDecimal("0.25"), teaspoon));
        salmonRecipe.addIngredient(new Ingredient("Pepper", new BigDecimal("0.12"), teaspoon));
        salmonRecipe.addIngredient(new Ingredient("Harissa paste", new BigDecimal(1), teaspoon));
        salmonRecipe.addIngredient(new Ingredient("Honey", new BigDecimal(1), teaspoon));
        salmonRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(3), teaspoon));
        salmonRecipe.addIngredient(new Ingredient("Lime juice", new BigDecimal(3), tableSpoonUom));
        salmonRecipe.addIngredient(new Ingredient("Yellow Potato", new BigDecimal(6), ounceUom));
        salmonRecipe.addIngredient(new Ingredient("Skinless salmon fillets", new BigDecimal(6), ounceUom));
        salmonRecipe.addIngredient(new Ingredient("Fresh sugar snap peas, ends trimmed", new BigDecimal(4), ounceUom));
        salmonRecipe.addIngredient(new Ingredient("Cherry tomatoes, halved", new BigDecimal(16), eachUom));

        salmonRecipe.getCategories().add(italianCategory);
        salmonRecipe.setImage(bootstrapImage("salmon.jpg"));

        //add to return list
        recipes.add(salmonRecipe);

        Recipe breadRecipe = new Recipe();
        breadRecipe.setDescription("Ketogenic bread");
        breadRecipe.setPrepTime(25);
        breadRecipe.setCookTime(70);
        breadRecipe.setServings(1);
        breadRecipe.setUrl("https://www.recipes.com/keto_diet/bread");
        breadRecipe.setSource("www.foodrecipes.com/recipes");
        breadRecipe.setDifficulty(Difficulty.MODERATE);
        breadRecipe.setDirections(
                "1 Preheat the oven to 375ºF.\n" +
                "2 Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                "3 dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "4It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                "5 It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                "6 desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");

        Notes breadNotes = new Notes();
        breadNotes.setRecipeNotes(
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        breadRecipe.setNotes(breadNotes);
        breadRecipe.addIngredient(new Ingredient("Eggs", new BigDecimal(2), eachUom));
        breadRecipe.addIngredient(new Ingredient("Salt", new BigDecimal("0.25"), teaspoon));
        breadRecipe.addIngredient(new Ingredient("Pepper", new BigDecimal("0.12"), teaspoon));
        breadRecipe.addIngredient(new Ingredient("Flour", new BigDecimal(20), ounceUom));
        breadRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(3), teaspoon));
        breadRecipe.getCategories().add(americanCategory);

        Recipe burgerRecipe = new Recipe();

        burgerRecipe.setDescription("Becon Burgers");
        burgerRecipe.setPrepTime(30);
        burgerRecipe.setCookTime(15);
        burgerRecipe.setServings(1);
        burgerRecipe.setUrl("https://www.recipes.com/tastyBurgers/burgers");
        burgerRecipe.setSource("www.foodrecipes.com/burgers");
        burgerRecipe.setDifficulty(Difficulty.MODERATE);
        burgerRecipe.setDirections(
                "1 Prepare meat.\n" +
                        "2 Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                        "3 dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "4It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                        "5 It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                        "6 desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");

        Notes burgerNotes = new Notes();
        burgerNotes.setRecipeNotes(
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                        "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                        "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                        "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        burgerRecipe.setNotes(burgerNotes);
        burgerRecipe.addIngredient(new Ingredient("Ground beef", new BigDecimal(10), ounceUom));
        burgerRecipe.addIngredient(new Ingredient("Becon", new BigDecimal(3), ounceUom));
        burgerRecipe.addIngredient(new Ingredient("Small Onion", new BigDecimal(1), eachUom));
        burgerRecipe.addIngredient(new Ingredient("Tomatos", new BigDecimal(0.5), eachUom));
        burgerRecipe.addIngredient(new Ingredient("Cheese", new BigDecimal(1), ounceUom));
        burgerRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(0.1), teaspoon));
        burgerRecipe.addIngredient(new Ingredient("Pepper", new BigDecimal(0.1), teaspoon));
        burgerRecipe.getCategories().add(americanCategory);
        Recipe pizzaRecipe = new Recipe();

        pizzaRecipe.setDescription("Pepperoni Pizza");
        pizzaRecipe.setPrepTime(50);
        pizzaRecipe.setCookTime(40);
        pizzaRecipe.setServings(3);
        pizzaRecipe.setUrl("https://www.recipes.com/pizza");
        pizzaRecipe.setSource("www.foodrecipes.com/bestPizza");
        pizzaRecipe.setDifficulty(Difficulty.HARD);

        pizzaRecipe.setDirections(
                "1 Honestly, just grab your phone and call PizzaHut .\n" +
                        "2 Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                        "3 dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "4It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                        "5 It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                        "6 desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");

        Notes pizzaNotes = new Notes();
        pizzaNotes.setRecipeNotes(
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard \n" +
                        "dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.\n" +
                        "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. \n" +
                        "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with \n" +
                        "desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum");
        pizzaRecipe.setNotes(pizzaNotes);

        pizzaRecipe.addIngredient(new Ingredient("Eggs", new BigDecimal(2), eachUom));
        pizzaRecipe.addIngredient(new Ingredient("Salt", new BigDecimal("0.25"), teaspoon));
        pizzaRecipe.addIngredient(new Ingredient("Pepper", new BigDecimal("0.12"), teaspoon));
        pizzaRecipe.addIngredient(new Ingredient("Flour", new BigDecimal(20), ounceUom));
        pizzaRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(3), teaspoon));
        pizzaRecipe.addIngredient(new Ingredient("Pepperoni", new BigDecimal(5), ounceUom));
        pizzaRecipe.getCategories().add(italianCategory);
        breadRecipe.setImage(bootstrapImage("bread.jpg"));
        burgerRecipe.setImage(bootstrapImage("burger.jpg"));
        pizzaRecipe.setImage(bootstrapImage("pizza.jpg"));
        recipes.add(breadRecipe);
        recipes.add(burgerRecipe);
        recipes.add(pizzaRecipe);


        return recipes;
    }

    private void loadUsers() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setFirstName("Mark");
        user.setLastName("Thompson");
        user.setAvatar("avatar.jpg");
        user.addRecipe(getRecipes().get(0));
        user.addRecipe(getRecipes().get(1));
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("user1");
        user1.setFirstName("Fionna");
        user1.setLastName("Smith");
        user1.setAvatar("avatar.jpg");
        user1.addRecipe(getRecipes().get(2));
        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("user2");
        user2.setFirstName("Thomas");
        user2.setLastName("Jones");
        user2.setAvatar("avatar.jpg");
        user2.addRecipe(getRecipes().get(4));
        user2.addRecipe(getRecipes().get(5));
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setFirstName("Sam");
        admin.setAvatar("avatar.jpg");
        admin.setLastName("Axe");

//        Comment comment = new Comment();
//        comment.setUser(user1);
//        comment.setRecipe(getRecipes().get(0));
//        comment.setText("Very nice");
//        getRecipes().get(0).addComment(comment);
//        user1.getComments().add(comment);

        userService.saveOrUpdate(user);
        userService.saveOrUpdate(user1);
        userService.saveOrUpdate(user2);
        userService.saveOrUpdate(admin);


    }
    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveOrUpdate(role);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveOrUpdate(adminRole);

    }
    private void assignUsersToUserRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    if (user.getUsername().contains("user")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.listAll();
        List<User> users = (List<User>) userService.listAll();
        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("admin")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }
}