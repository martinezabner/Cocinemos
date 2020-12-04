package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uca.edu.ni.cookeasy.R;

public class IngredientAdapter extends BaseAdapter {

    Context mContext;
    List<String> mIngredients;
    CheckBox.OnCheckedChangeListener mOnCheckedChangeListener;

    public IngredientAdapter(@NonNull Context context, List<String> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mIngredients!=null)
        {
            ret = mIngredients.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int i) {
        Object ret = null;
        if(mIngredients!=null) {
            ret = mIngredients.get(i);
        }
        return ret;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IngredientViewHolder holder = null;

        if (view!=null) {
            holder = (IngredientViewHolder) view.getTag();

        } else {
            view = View.inflate(mContext, R.layout.listitem_ingredient, null);
            holder = new IngredientViewHolder(view);
            CheckBox cbIngredient = (CheckBox) view.findViewById(R.id.cb_item);
            holder.tvIngredient = (TextView) view.findViewById(R.id.tv_item);
            holder.cbIngredient = cbIngredient;

            view.setTag(holder);
        }

        String item = mIngredients.get(i);
        holder.tvIngredient.setText(item);
        holder.cbIngredient.setChecked(false);

        return view;
    }

    private static class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIngredient;
        public CheckBox cbIngredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
