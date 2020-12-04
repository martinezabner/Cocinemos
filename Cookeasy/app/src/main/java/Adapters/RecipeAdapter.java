package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Common.OnFavTapListener;
import Common.OnItemTapListener;
import Models.Category;
import Models.Recipe;
import uca.edu.ni.cookeasy.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    List<Recipe> recipeList;
    View itemView;
    private int cardType;
    @Nullable
    private OnFavTapListener mFavTapListener;
    @Nullable
    private OnItemTapListener mItemTapListener;

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (this.cardType == 0) {
            itemView = layoutInflater.inflate(R.layout.cardview_new_recipe, parent, false);

        }

        if (this.cardType == 1 || cardType == 2 || cardType == 3 || cardType == 4) {
            itemView = layoutInflater.inflate(R.layout.cardview_recommended_recipe, parent, false);
        }

        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(itemView, this.cardType, mFavTapListener, mItemTapListener);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.recipeName.setText(recipeList.get(position).getName());
        holder.recipeDescription.setText(recipeList.get(position).getDescription());
        Picasso.get().load(recipeList.get(position).getImage()).into(holder.recipeImage);

        Recipe currentRecipe = recipeList.get(position);

        if (currentRecipe.getFavourite() == 1) {
            holder.recipeFavButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        } else if (currentRecipe.getFavourite() == 0) {
            holder.recipeFavButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }
        holder.modelId = currentRecipe.getName(); //TODO: sustituir por ID
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public RecipeAdapter(List<Recipe> recipeList, int cardType, @Nullable OnFavTapListener favTapListener, @Nullable OnItemTapListener itemTapListener) {
        this.recipeList = recipeList;
        this.cardType = cardType;
        mFavTapListener = favTapListener;
        mItemTapListener = itemTapListener;
    }

    public void updateList(List<Recipe> newList, CharSequence search, String category) {

        List<Recipe> filteredList = new ArrayList<>();

        if (cardType == 1) {
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).getRecommended() == 1) {
                    filteredList.add(newList.get(i));
                }
            }
            newList = filteredList;
        }

        if (cardType == 2) {
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).getFavourite() == 1 && newList.get(i).getName().contains(search)) {
                    filteredList.add(newList.get(i));
                }
            }
            newList = filteredList;

        }

        if (cardType == 3) {
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).getCategory().contains(category) && newList.get(i).getName().contains(search)) {
                    filteredList.add(newList.get(i));
                }
            }
            newList = filteredList;
        }

        if (cardType == 4) {
            for (int i = 0; i < newList.size(); i++) {
                if (newList.get(i).getName().contains(search)) {
                    filteredList.add(newList.get(i));
                }
            }
            newList = filteredList;
        }

        recipeList = newList;

        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView recipeName;
        public ImageView recipeImage;
        public TextView recipeDescription;
        public Button recipeFavButton;
        public TextView recipeCategory;
        private int cardType;
        public String modelId;

        public RecipeViewHolder(@NonNull View itemView, int cardType, @Nullable OnFavTapListener favTapListener, @Nullable OnItemTapListener itemTapListener) {
            super(itemView);

            this.cardType = cardType;

            setView();

        }

        public void setView() {
            if (cardType == 0) {
                recipeName = itemView.findViewById(R.id.tv_recipe_new);
                recipeDescription = itemView.findViewById(R.id.tv_recipe_new_description);
                recipeImage = itemView.findViewById(R.id.iv_recipe_new);
                recipeFavButton = itemView.findViewById(R.id.btn_new_recipe);
                recipeFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mFavTapListener == null) {
                            return;
                        }
                        mFavTapListener.onFavTap(view, getAdapterPosition());
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mItemTapListener == null) {
                            return;
                        }
                        mItemTapListener.onItemTapListener(view, getAdapterPosition());
                    }
                });
            }

            if (cardType == 1 || cardType == 2 || cardType == 3 || cardType == 4) {
                recipeName = itemView.findViewById(R.id.tv_recipe_recommended);
                recipeDescription = itemView.findViewById(R.id.tv_recipe_recommended_description);
                recipeImage = itemView.findViewById(R.id.iv_recipe_recommended);
                recipeFavButton = itemView.findViewById(R.id.btn_recommended_recipe);
                recipeFavButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mFavTapListener == null) {
                            return;
                        }
                        mFavTapListener.onFavTap(view, getAdapterPosition());
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mItemTapListener == null) {
                            return;
                        }
                        mItemTapListener.onItemTapListener(view, getAdapterPosition());
                    }
                });
            }

        }

        @Override
        public void onClick(View clickedView) {
            if(mItemTapListener == null) {
                return;
            }
            mItemTapListener.onItemTapListener(clickedView, getAdapterPosition());
        }
    }
}