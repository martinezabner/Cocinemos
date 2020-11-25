package Data;

import java.util.List;

import Models.Category;

public class CategoryRepository {
    CategoryMockSource mSource = new CategoryMockSource();

    public List<Category> fillData() { return mSource.fillData(); }
}
