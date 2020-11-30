package Data;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

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
import java.util.Comparator;
import java.util.List;

import javax.xml.namespace.QName;

import Common.OnRecipesReceivedListener;
import Models.Category;
import Models.Recipe;

public class RecipeMockSource implements RecipeSource {

    Context mContext;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("recipees");

    public RecipeMockSource(Context context) {
        mContext = context;
    }

    @Override
    public void fillData(OnRecipesReceivedListener listener) {
        getData(listener);
    }

    private void getData(OnRecipesReceivedListener listener) {
        List<Recipe> models = new ArrayList<>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                models.removeAll(models);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    models.add(new Recipe(
                            snapshot.getKey(),
                            snapshot.child("name").getValue(String.class),
                            snapshot.child("image").getValue(String.class),
                            snapshot.child("description").getValue(String.class),
                            snapshot.child("category").getValue(String.class),
                            snapshot.child("favourite").getValue(Long.class).intValue(),
                            snapshot.child("recommended").getValue(Long.class).intValue()
                    ));
                }

                listener.onSuccess(models);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}