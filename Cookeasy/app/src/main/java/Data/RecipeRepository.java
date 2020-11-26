package Data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import Models.Recipe;

public class RecipeRepository {

    RecipeSource mSource;

    public RecipeRepository(@NonNull Context context) {
        mSource = new RecipeFileSource(context);
    }

    public List<Recipe> fillData(String recipeFile) { return mSource.fillData(recipeFile); }
}
