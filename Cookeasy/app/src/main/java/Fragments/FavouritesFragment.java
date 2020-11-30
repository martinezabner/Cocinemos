package Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Adapters.RecipeAdapter;
import Common.OnFavTapListener;
import Data.RecipeRepository;
import Models.Recipe;
import uca.edu.ni.cookeasy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment implements OnFavTapListener {

    private static final int NEW_RECIPEES_VIEW_ID = 2131230811;

    private View view;
    private Context context = null;

    private RecipeAdapter adapterRecipe;
    private RecyclerView.LayoutManager lmRecipes;

    List<Recipe> recipeList = new ArrayList<>();

    RecipeRepository recipeRepository;

    RecyclerView rvRecipes;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    public FavouritesFragment() {
        // Required empty public constructor
    }

    public FavouritesFragment(Context context) {
        this.context = context;
    }


    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_favourites, container, false);

        recipeRepository = new RecipeRepository(context);

        rvRecipes = view.findViewById(R.id.rv_favourites);
        rvRecipes.setHasFixedSize(true);
        lmRecipes = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvRecipes.setLayoutManager(lmRecipes);
        adapterRecipe = new RecipeAdapter(recipeList, 2, this);
        rvRecipes.setAdapter(adapterRecipe);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        recipeRepository.fillData(recipes -> {
            recipeList = recipes;
            adapterRecipe.updateList(recipeList);
        });
    }

    @Override
    public void onFavTap(View view, int position) {
        addFavourite(view, position);
    }

    private Recipe getSelected(int position) {

        Recipe selectedRecipe;
        /*String name =
                ((TextView) rvRecommendedRecipes.findViewHolderForAdapterPosition(position)
                        .itemView.findViewById(R.id.tv_recipe_recommended)).getText().toString();*/
        RecipeAdapter.RecipeViewHolder viewHolder =
                (RecipeAdapter.RecipeViewHolder)rvRecipes.findViewHolderForAdapterPosition(position);

        if(viewHolder == null) {
            Log.e("shit", "algo salio mal");
            return null;
        }

        for (int i = 0; i < recipeList.size(); i++) {
            if (recipeList.get(i).getName().equals(viewHolder.modelId)) {
                position = i;
            }
        }

        selectedRecipe = recipeList.get(position);

        return selectedRecipe;
    }

    public void addFavourite(View view, int position) {
        String message = "";
        Recipe selectedRecipe;
        int fav = 0;

        selectedRecipe = getSelected(position);

        // Toast.makeText(context, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();

        if (selectedRecipe.getFavourite() == 0) {
            message = String.format("%s ha sido añadido a los favoritos", selectedRecipe.getName());
            fav = 1;
        } else if (selectedRecipe.getFavourite() == 1) {
            message = String.format("%s ha sido removido de los favoritos", selectedRecipe.getName());
            fav = 0;
        }

        updateDBFav(selectedRecipe.getId(), fav);

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void updateDBFav(String id,  int fav) {
        reference = FirebaseDatabase.getInstance().getReference("recipees");
        reference.child(id).child("favourite").setValue(fav);
    }
}