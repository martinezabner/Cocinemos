package Data;

import java.util.ArrayList;
import java.util.List;

import Models.Category;
import uca.edu.ni.cookeasy.R;

public class CategoryMockSource {
    List<Category> models = new ArrayList<>();

    public List<Category> fillData() {
        models.add(new Category("Pizza", R.drawable.logo));
        models.add(new Category("Burgers", R.drawable.logo));
        models.add(new Category("Pizza", R.drawable.logo));
        models.add(new Category("Burgers", R.drawable.logo));
        models.add(new Category("Pizza", R.drawable.logo));
        models.add(new Category("Burgers", R.drawable.logo));
        models.add(new Category("Pizza", R.drawable.logo));
        models.add(new Category("Burgers", R.drawable.logo));
        models.add(new Category("Pizza", R.drawable.logo));
        models.add(new Category("Burgers", R.drawable.logo));



        return models;
    }
}
