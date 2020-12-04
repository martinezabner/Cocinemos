package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapters.IngredientAdapter;
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
public class RecipeFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Context context;
    private ViewGroup rootView;
    private View view;
    private Recipe mSelectedRecipe;

    TextView recipeName, recipeDescription, tvClock, tvServings, tvIngredients, tvSteps;
    ImageView recipeImage, clock, servings, ingredients;
    Button btn_recipe;
    String[] imagesLinks;

    ListView lvIngredients;
    CheckBox cbIngredient;
    IngredientAdapter ingredientAdapter;

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

        lvIngredients = (ListView) view.findViewById(R.id.lv_ingredients);
        ingredientAdapter = new IngredientAdapter(context, getIngredientsList());
        lvIngredients.setAdapter(ingredientAdapter);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) lvIngredients.getLayoutParams();
        // Toast.makeText(context, "" + pxToDp(dpToPx(20) * getIngredientsList().size()), Toast.LENGTH_SHORT).show();
        params.height =  pxToDp(dpToPx(20) * getIngredientsList().size());
        lvIngredients.setLayoutParams(params);

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
        tvSteps = view.findViewById(R.id.tv_recipe_steps);
        cbIngredient = view.findViewById(R.id.cb_item);
        Picasso.get().load(mSelectedRecipe.getImage()).into(recipeImage);
        Picasso.get().load(imagesLinks[0]).into(clock);
        Picasso.get().load(imagesLinks[1]).into(servings);
        Picasso.get().load(imagesLinks[2]).into(ingredients);

        setView();

        btn_recipe.setOnClickListener(this);

        return view;
    }

    public int pxToDp(int px) {
        float density = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, getResources().getDisplayMetrics());
        return Math.round(density);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void setView() {

        recipeName.setText(mSelectedRecipe.getName());
        recipeDescription.setText(mSelectedRecipe.getDescription());
        tvClock.setText(mSelectedRecipe.getTime());
        tvServings.setText(mSelectedRecipe.getServings());
        tvIngredients.setText( getIngredientsList().size() + " ingredientes");
        tvSteps.setText(getRecipeSteps());

        if (mSelectedRecipe.getFavourite() == 0) {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        } else {
            btn_recipe.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        }
    }


    private List<String> getIngredientsList() {

        String[] strings = mSelectedRecipe.getIngredients().split("::");

        List<String> ingredientsList = Arrays.asList(strings);

        return ingredientsList;
    }

    private String getRecipeSteps() {

        String[] strings = mSelectedRecipe.getPreparation().split("::");
        String steps = "";

        for (String item: strings) {
            steps = steps + item + "\n";
        }

        return steps;
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        cbIngredient.setChecked(!cbIngredient.isChecked());

        /*int position = lvIngredients.getPositionForView(compoundButton);

        if (position != ListView.INVALID_POSITION) {

        }*/
    }
}