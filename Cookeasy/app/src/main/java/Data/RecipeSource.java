package Data;

import java.util.List;

import Common.OnRecipesReceivedListener;
import Models.Recipe;

public interface RecipeSource {
    void fillData(OnRecipesReceivedListener listener);
}
