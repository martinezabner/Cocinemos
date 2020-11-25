package Data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Helpers.FileHelper;
import Models.Category;

public class CategoryFileSource implements CategorySource {

    private final Gson gson;
    private final Context mContext;
    private static final String CATEGORIES_FILE_NAME = "categories.json";

    public CategoryFileSource(Context context) {
        mContext = context;
        gson = new Gson();
    }

    @Override
    public List<Category> fillData() {
        String json = FileHelper.getJsonFromAssets(mContext, CATEGORIES_FILE_NAME);
        ListResult listResult = gson.fromJson(json, ListResult.class);
        if(listResult == null) return null;
        return listResult.list;
    }

    static class ListResult {
        @SerializedName("data")
        List<Category> list;

        public ListResult(List<Category> list) {
            this.list = list;
        }
    }
}
