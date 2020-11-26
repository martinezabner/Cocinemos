package Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Adapters.CategoryAdapter;
import Adapters.RecipeAdapter;
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
public class HomeFragment extends Fragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

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

    List<Recipe> recipeListRecommened = new ArrayList<>();
    RecipeRepository recipeRepositoryRecommended;



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

        categoryRepository = new CategoryRepository(context);
        recipeRepository = new RecipeRepository(context);
        recipeRepositoryRecommended = new RecipeRepository(context);

        RecyclerView recyclerView = view.findViewById(R.id.rv_Category);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CategoryAdapter(categoryList);
        recyclerView.setAdapter(mAdapter);

        RecyclerView rvNewRecipes = view.findViewById(R.id.rv_New_Recipes);
        rvNewRecipes.setHasFixedSize(true);
        lmNewRecipes = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvNewRecipes.setLayoutManager(lmNewRecipes);
        adapterRecipe = new RecipeAdapter(recipeList, 0);
        rvNewRecipes.setAdapter(adapterRecipe);

        RecyclerView rvRecommendedRecipes = view.findViewById(R.id.rv_Recommended_Recipes);
        rvRecommendedRecipes.setHasFixedSize(true);
        lmRecipesRecommended = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvRecommendedRecipes.setLayoutManager(lmRecipesRecommended);
        adapterRecipeRecommended = new RecipeAdapter(recipeListRecommened, 1);
        rvRecommendedRecipes.setAdapter(adapterRecipeRecommended);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataCategory();
        loadDataRecipe("new_recipees.json");
        //loadDataRecipe("recommended_recipees.json");
    }

    private void loadDataCategory() {
        categoryList = categoryRepository.fillData();
        mAdapter.updateList(categoryList);
    }

    private void loadDataRecipe(String recipeFile) {
        recipeList = recipeRepository.fillData(recipeFile);
        adapterRecipe.updateList(recipeList);

        recipeListRecommened = recipeRepositoryRecommended.fillData(recipeFile);
        adapterRecipeRecommended.updateList(recipeListRecommened);
    }
}