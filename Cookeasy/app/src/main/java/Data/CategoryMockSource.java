package Data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Models.Category;
import uca.edu.ni.cookeasy.MainActivity;
import uca.edu.ni.cookeasy.R;

public class CategoryMockSource implements CategorySource {
    List<Category> models = new ArrayList<>();

    public CategoryMockSource() {
    }

    public List<Category> fillData() {

        models.add(new Category("Pizza",  Integer.toString(R.drawable.logo)));
        models.add(new Category("Burgers", Integer.toString(R.drawable.logo)));
        models.add(new Category("Pastas", Integer.toString(R.drawable.logo)));
        models.add(new Category("Tacos", Integer.toString(R.drawable.logo)));


        return models;
    }
}
