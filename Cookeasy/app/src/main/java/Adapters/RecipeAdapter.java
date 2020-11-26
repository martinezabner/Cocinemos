package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Category;
import Models.Recipe;
import uca.edu.ni.cookeasy.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipeList;
    View itemView;
    private int cardType;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        Log.d("", "On create CardType: " + cardType);

        if (this.cardType == 0) {
            itemView = layoutInflater.inflate(R.layout.cardview_new_recipe, parent, false);

        }

        if (this.cardType == 1) {
            itemView = layoutInflater.inflate(R.layout.cardview_recommended_recipe, parent, false);
        }

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(itemView, this.cardType);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeName.setText(recipeList.get(position).getName());
        holder.recipeDescription.setText(recipeList.get(position).getDescription());
        Picasso.get().load(recipeList.get(position).getImage()).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public RecipeAdapter(List<Recipe> recipeList, int cardType) {
        this.recipeList = recipeList;
        this.cardType = cardType;
    }

    public void updateList(List<Recipe> newList) {
        recipeList = newList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeName;
        public ImageView recipeImage;
        public TextView recipeDescription;
        public TextView recipeCategory;
        private int cardType;

        public RecipeViewHolder(@NonNull View itemView, int cardType) {
            super(itemView);

            Log.d("", "CardType: " + cardType);

            this.cardType = cardType;

            setView();
        }

        public void setView() {
            if (cardType == 0) {
                recipeName = itemView.findViewById(R.id.tv_recipe_new);
                recipeDescription = itemView.findViewById(R.id.tv_recipe_new_description);
                recipeImage = itemView.findViewById(R.id.iv_recipe_new);
            }

            if (cardType == 1) {
                recipeName = itemView.findViewById(R.id.tv_recipe_recommended);
                recipeDescription = itemView.findViewById(R.id.tv_recipe_recommended_description);
                recipeImage = itemView.findViewById(R.id.iv_recipe_recommended);
            }
        }
    }
}
