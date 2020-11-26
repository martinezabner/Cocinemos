package Data;

import java.util.List;

import Models.Recipe;

public interface RecipeSource {
    List<Recipe> fillData(String recipeFile);
}
