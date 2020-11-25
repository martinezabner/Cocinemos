package Data;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import Models.Category;

public class CategoryRepository {

    CategorySource mSource;

    public CategoryRepository(@NonNull Context context) {
        mSource = new CategoryFileSource(context);
    }

    public List<Category> fillData() { return mSource.fillData(); }
}
