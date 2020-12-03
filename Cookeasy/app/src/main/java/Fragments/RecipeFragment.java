package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecipeAdapter;
import Common.OnFavTapListener;
import Common.OnItemTapListener;
import Common.OnRecipesReceivedListener;
import Data.RecipeRepository;
import Models.Recipe;
import uca.edu.ni.cookeasy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeFragment extends Fragment implements View.OnClickListener, OnRecipesReceivedListener, OnFavTapListener, OnItemTapListener {

    private Context context;
    private ViewGroup rootView;
    View view;
    private Recipe mSelectedRecipe;
    private RecipeRepository recipeRepository = new RecipeRepository(context);
    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter adapterRecipe;

    TextView recipeName, recipeDescription;
    ImageView recipeImage;
    Button btn_recipe;

    DatabaseReference reference;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public RecipeFragment(Context context, Recipe selectedRecipe) {
        this.context = context;
        mSelectedRecipe = selectedRecipe;
    }

    // TODO: Rename and change types and number of parameters
    public static RecipeFragment newInstance() {
        RecipeFragment fragment = new RecipeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recipe, container, false);
        rootView = view.findViewById(R.id.fl_recipe);

        adapterRecipe = new RecipeAdapter(recipeList, 0, this, this);

        recipeImage = view.findViewById(R.id.iv_recipe_image);
        btn_recipe = view.findViewById(R.id.btn_recipe);
        recipeName = view.findViewById(R.id.tv_recipe_name);
        recipeDescription = view.findViewById(R.id.tv_recipe_description);
        Picasso.get().load(mSelectedRecipe.getImage()).into(recipeImage);

        setView();

        btn_recipe.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int fav = 0;

        if (mSelectedRecipe.getFavourite() == 0) {
            fav = 1;
        }

        reference = FirebaseDatabase.getInstance().getReference("recipees");
        reference.child(mSelectedRecipe.getId()).child("favourite").setValue(fav);

        addFavourite();
        loadData();
        updateObject();

    }

    @Override
    public void onSuccess(List<Recipe> recipes) {

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void updateObject() {
        for (int i = 0; i < recipeList.size(); i++) {
            Log.d("","First: " + recipeList.get(i).getId() + "Second: " + mSelectedRecipe.getId());
            if (recipeList.get(i).getId() == mSelectedRecipe.getId()) {
                mSelectedRecipe = recipeList.get(i);
                setView();
                Log.d("","Second: " + mSelectedRecipe.getId());
            }
        }

    }

    private void setView() {

        recipeName.setText(mSelectedRecipe.getName());
        recipeDescription.setText(mSelectedRecipe.getDescription());

        if (mSelectedRecipe.getFavourite() == 0) {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            Log.d("","NoFav");
        } else {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            Log.d("","Fav");
        }
    }

    private void loadData() {
        recipeRepository.fillData(recipes -> {
            recipeList = recipes;
            adapterRecipe.updateList(recipes);
        });
    }

    @Override
    public void onFavTap(View view, int position) {
        addFavourite();

    }

    public void addFavourite() {
        String message = "";
        Recipe selectedRecipe = new Recipe();
        int fav = 0;

        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getId() == mSelectedRecipe.getId()) {
                selectedRecipe = recipeList.get(i);
            }
        }

        if (selectedRecipe.getFavourite() == 0) {
            message = String.format("%s ha sido añadido a los favoritos", selectedRecipe.getName());
            fav = 1;
        } else if (selectedRecipe.getFavourite() == 1) {
            message = String.format("%s ha sido removido de los favoritos", selectedRecipe.getName());
            fav = 0;
        }

        updateDBFav(selectedRecipe.getId(), fav);

        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void updateDBFav(String id,  int fav) {
        reference = FirebaseDatabase.getInstance().getReference("recipees");
        reference.child(id).child("favourite").setValue(fav);
    }

    @Override
    public void onItemTapListener(View view, int position) {

    }
}