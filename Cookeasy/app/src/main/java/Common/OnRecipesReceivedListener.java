package Common;

import java.util.List;

import Models.Recipe;

public interface OnRecipesReceivedListener {
    public void onSuccess(List<Recipe> recipes);

}
