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
public class RecipeFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private ViewGroup rootView;
    View view;
    private Recipe mSelectedRecipe;

    TextView recipeName, recipeDescription, tvClock, tvServings, tvIngredients, items;
    ImageView recipeImage, clock, servings, ingredients;
    Button btn_recipe;
    String[] imagesLinks;

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

        imagesLinks = new String[]{
                                "https://image.flaticon.com/icons/png/512/252/252055.png",
                                "https://res.cloudinary.com/franksprachen/image/upload/v1607074568/cookeasy/pie-chart_iwtavm.png",
                                "https://res.cloudinary.com/franksprachen/image/upload/v1607074566/cookeasy/ingredients_xcy1jp.png"
        };

        recipeImage = view.findViewById(R.id.iv_recipe_image);
        clock = view.findViewById(R.id.iv1);
        servings = view.findViewById(R.id.iv2);
        ingredients = view.findViewById(R.id.iv3);
        btn_recipe = view.findViewById(R.id.btn_recipe);
        recipeName = view.findViewById(R.id.tv_recipe_name);
        recipeDescription = view.findViewById(R.id.tv_recipe_description);
        tvClock = view.findViewById(R.id.tv_time);
        tvServings = view.findViewById(R.id.tv_servings);
        tvIngredients = view.findViewById(R.id.tv_ingredients);
        items = view.findViewById(R.id.tv_recipe_ingredients);
        Picasso.get().load(mSelectedRecipe.getImage()).into(recipeImage);
        Picasso.get().load(imagesLinks[0]).into(clock);
        Picasso.get().load(imagesLinks[1]).into(servings);
        Picasso.get().load(imagesLinks[2]).into(ingredients);

        setView();

        btn_recipe.setOnClickListener(this);

        return view;
    }

    private void setView() {

        recipeName.setText(mSelectedRecipe.getName());
        recipeDescription.setText(mSelectedRecipe.getDescription());
        tvClock.setText(mSelectedRecipe.getTime());
        tvServings.setText(mSelectedRecipe.getServings());
        tvIngredients.setText(mSelectedRecipe.getNumingredients());
        items.setText(setIngredientsList());

        if (mSelectedRecipe.getFavourite() == 0) {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        } else {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        }
    }

    private String setIngredientsList() {

        String ingredients = "";
        String[] arrIngredients = mSelectedRecipe.getIngredients().split(",");

        for (String item: arrIngredients) {
            ingredients = ingredients + "-" + item + "\n";
        }

        return ingredients;
    }

    @Override
    public void onClick(View view) {
        int fav = 0;
        String message = "";

        if (mSelectedRecipe.getFavourite() == 0) {
            fav = 1;
        }

        mSelectedRecipe.setFavourite(fav);

        reference = FirebaseDatabase.getInstance().getReference("recipees");
        reference.child(mSelectedRecipe.getId()).child("favourite").setValue(fav);

        if (fav == 0) {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            message = String.format("%s ha sido removido de los favoritos", mSelectedRecipe.getName());
        } else {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            message = String.format("%s ha sido añadido a los favoritos", mSelectedRecipe.getName());
        }

        Snackbar snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}