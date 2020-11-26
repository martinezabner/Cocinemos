package Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Category;
import Models.Recipe;
import uca.edu.ni.cookeasy.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipeList;
    View itemView;
    int cardType = 0;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemView = layoutInflater.inflate(R.layout.cardview_new_recipe, parent, false);

        return new RecipeViewHolder(itemView, cardType);
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

    public RecipeAdapter(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void updateList(List<Recipe> newList, int cardType) {
        recipeList = newList;
        notifyDataSetChanged();
        this.cardType = cardType;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        int cardType;

        public TextView recipeName;
        public ImageView recipeImage;
        public TextView recipeDescription;
        public TextView recipeCategory;

        public RecipeViewHolder(@NonNull View itemView, int cardType) {
            super(itemView);
            if (cardType == 0) {
                recipeName = itemView.findViewById(R.id.tv_recipe_new);
                recipeDescription = itemView.findViewById(R.id.tv_recipe_new_description);
                recipeImage = itemView.findViewById(R.id.iv_recipe_new);
            }

            if (cardType == 1) {

            }

            this.cardType = cardType;
        }
    }
}
