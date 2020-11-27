package Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import Models.Category;
import Models.Recipe;

public class RecipeMockSource implements RecipeSource {

    Context mContext;
    List<Recipe> models = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("recipees");

    public RecipeMockSource(Context context) {
        mContext = context;
        getData();
    }

    @Override
    public List<Recipe> fillData(String recipeFile) {

        return models;
    }

    private void getData() {

       myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!models.isEmpty()) {
                    models.removeAll(models);
                }

                for (int i = 0; i < 7; i++) {
                    models.add(new Recipe(
                            dataSnapshot.child(String.valueOf(i)).child("name").getValue(String.class),
                            dataSnapshot.child(String.valueOf(i)).child("image").getValue(String.class),
                            dataSnapshot.child(String.valueOf(i)).child("description").getValue(String.class),
                            dataSnapshot.child(String.valueOf(i)).child("category").getValue(String.class),
                            dataSnapshot.child(String.valueOf(i)).child("favourite").getValue(Long.class).intValue(),
                            dataSnapshot.child(String.valueOf(i)).child("recommended").getValue(Long.class).intValue()
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
