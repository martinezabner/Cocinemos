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

import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Category;
import uca.edu.ni.cookeasy.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<Category> categoryList;
    View itemView;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemView = layoutInflater.inflate(R.layout.cardview_small_category, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position).getName());
        // int image = Integer.parseInt(categoryList.get(position).getImage());
        // holder.categoryImage.setImageResource(image);
        // holder.categoryImage.setImageResource(R.drawable.logo);
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void updateList(List<Category> newList) {
        categoryList = newList;
        notifyDataSetChanged();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryName;
        public ImageView categoryImage;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_category_small);
            categoryImage = itemView.findViewById(R.id.iv_category_small);
        }
    }
}