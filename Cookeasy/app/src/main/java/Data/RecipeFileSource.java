package Data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Helpers.FileHelper;
import Models.Category;
import Models.Recipe;

public class RecipeFileSource implements RecipeSource {

    private final Gson gson;
    private final Context mContext;
    // private static final String RECIPES_FILE_NAME = "new_recipees.json";

    public RecipeFileSource(Context context) {
        mContext = context;
        gson = new Gson();
    }

    @Override
    public List<Recipe> fillData(String recipeFile) {
        String json = FileHelper.getJsonFromAssets(mContext, recipeFile);
        ListResult listResult = gson.fromJson(json, ListResult.class);
        if(listResult == null) return null;
        return listResult.list;
    }

    static class ListResult {
        @SerializedName("data")
        List<Recipe> list;

        public ListResult(List<Recipe> list) {
            this.list = list;
        }
    }
}
