package Data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import Common.OnRecipesReceivedListener;
import Models.Recipe;

public class RecipeRepository {

    RecipeSource mSource;

    public RecipeRepository(@NonNull Context context) {
        mSource = new RecipeMockSource(context);
    }

    public void fillData(OnRecipesReceivedListener listener) {
        mSource.fillData(listener);
    }
}
