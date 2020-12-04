package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Adapters.CategoryAdapter;
import Adapters.RecipeAdapter;
import Common.OnFavTapListener;
import Common.OnItemTapListener;
import Common.OnRecipesReceivedListener;
import Data.CategoryRepository;
import Data.RecipeRepository;
import Models.Category;
import Models.Recipe;
import uca.edu.ni.cookeasy.MainActivity;
import uca.edu.ni.cookeasy.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements OnFavTapListener, OnItemTapListener {

    private ViewGroup rootView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private View generalView;

    private static final int NEW_RECIPEES_VIEW_ID = 2131230811;
    private static final int RECOMMENDED_RECIPEES_VIEW_ID = 2131230810;
    private static final int CARDVIEW_NEW_ID = 2131230818;


    private Context context = null;
    private CategoryAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Category> categoryList = new ArrayList<>();
    CategoryRepository categoryRepository;

    private RecipeAdapter adapterRecipe;
    private RecyclerView.LayoutManager lmNewRecipes;

    List<Recipe> recipeList = new ArrayList<>();

    RecipeRepository recipeRepository;

    private RecipeAdapter adapterRecipeRecommended;
    private RecyclerView.LayoutManager lmRecipesRecommended;

    RecipeRepository recipeRepositoryRecommended;

    RecyclerView rvRecommendedRecipes;
    RecyclerView recyclerView;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public HomeFragment() {
        // Required empty public constructor
    }

    public HomeFragment(Context context) {
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rootView = view.findViewById(R.id.ly_home);

        generalView = view;

        categoryRepository = new CategoryRepository(context);
        recipeRepository = new RecipeRepository(context);
        recipeRepositoryRecommended = new RecipeRepository(context);

        recyclerView = view.findViewById(R.id.rv_Category);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CategoryAdapter(categoryList, new OnItemTapListener() {
            @Override
            public void onItemTapListener(View view, int position) {
                openCategory(view, position);
            }
        });
        recyclerView.setAdapter(mAdapter);

        RecyclerView rvNewRecipes = view.findViewById(R.id.rv_New_Recipes);
        rvNewRecipes.setHasFixedSize(true);
        lmNewRecipes = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvNewRecipes.setLayoutManager(lmNewRecipes);
        adapterRecipe = new RecipeAdapter(recipeList, 0, this, this);
        rvNewRecipes.setAdapter(adapterRecipe);

        rvRecommendedRecipes = view.findViewById(R.id.rv_Recommended_Recipes);
        rvRecommendedRecipes.setHasFixedSize(true);
        lmRecipesRecommended = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvRecommendedRecipes.setLayoutManager(lmRecipesRecommended);
        adapterRecipeRecommended = new RecipeAdapter(recipeList, 1, this, this);
        rvRecommendedRecipes.setAdapter(adapterRecipeRecommended);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataCategory();
        loadDataRecipe();
    }

    private void loadDataCategory() {
        categoryList = categoryRepository.fillData();
        mAdapter.updateList(categoryList);
    }

    private void loadDataRecipe() {
        recipeRepository.fillData(recipes -> {
            recipeList = recipes;
            adapterRecipe.updateList(recipes, "", "");
            adapterRecipeRecommended.updateList(recipeList, "", "");
        });
    }

    @Override
    public void onFavTap(View view, int position) {
        addFavourite(view, position);
    }

    private Recipe getSelected(int position) {

        Recipe selectedRecipe;

        RecipeAdapter.RecipeViewHolder viewHolder =
                (RecipeAdapter.RecipeViewHolder)rvRecommendedRecipes.findViewHolderForAdapterPosition(position);

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

        Log.d("",String.valueOf(view.getId()));

        if (view.getId() == NEW_RECIPEES_VIEW_ID) {
            selectedRecipe = recipeList.get(position);
        } else {
            selectedRecipe = getSelected(position);
        }

        // Toast.makeText(context, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();

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
        openRecipe(view, position);
    }

    private void openRecipe(View view, int position) {
        Recipe selectedRecipe;
        /*if (view.getId() == NEW_RECIPEES_VIEW_ID) {
            selectedRecipe = recipeList.get(position);
        } else {
            selectedRecipe = getSelected(position);
        }*/
        selectedRecipe = getSelected(position);
        // Toast.makeText(context, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
        if (view.getId() == CARDVIEW_NEW_ID) {
            selectedRecipe = recipeList.get(position);
        } else {
            selectedRecipe = getSelected(position);
        }

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        RecipeFragment recipeFragment = new RecipeFragment(context, selectedRecipe);
        fragmentTransaction.replace(R.id.frg_main, recipeFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);

    }

    private void openCategory(View view, int position) {
        String category = "";

        CategoryAdapter.CategoryViewHolder viewHolder =
                (CategoryAdapter.CategoryViewHolder)recyclerView.findViewHolderForAdapterPosition(position);

        category = viewHolder.category;

        Toast.makeText(context, "" + category, Toast.LENGTH_SHORT).show();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        CategoryFragment categoryFragment = new CategoryFragment(context, category);
        fragmentTransaction.replace(R.id.frg_main, categoryFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
    }
}