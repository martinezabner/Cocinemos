package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import Common.OnItemTapListener;
import Models.Category;
import uca.edu.ni.cookeasy.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    List<Category> categoryList;
    View itemView;

    @Nullable
    private OnItemTapListener mItemTapListener;

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemView = layoutInflater.inflate(R.layout.cardview_small_category, parent, false);

        return new CategoryViewHolder(itemView, mItemTapListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.categoryName.setText(categoryList.get(position).getName());
        // int image = Integer.parseInt(categoryList.get(position).getImage());
        // holder.categoryImage.setImageResource(image);
        // holder.categoryImage.setImageResource(R.drawable.logo);
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.categoryImage);
        holder.category = categoryList.get(position).getName();
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public CategoryAdapter(List<Category> categoryList, @Nullable OnItemTapListener itemTapListener) {
        this.categoryList = categoryList;
        mItemTapListener = itemTapListener;
    }

    public void updateList(List<Category> newList) {
        categoryList = newList;
        notifyDataSetChanged();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryName;
        public ImageView categoryImage;
        public String category;
        public CategoryViewHolder(@NonNull View itemView, @Nullable OnItemTapListener itemTapListener) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_category_small);
            categoryImage = itemView.findViewById(R.id.iv_category_small);

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
}
