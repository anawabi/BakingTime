package com.amannawabi.bakingtime.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amannawabi.bakingtime.Model.Ingredient;
import com.amannawabi.bakingtime.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private static final String TAG = "IngredientAdapter";
    Context mContext;
    List<Ingredient> mItems;

    public IngredientAdapter(Context mContext, List<Ingredient> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView recipeAmount;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.ingredient_name_tv);
            recipeAmount = itemView.findViewById(R.id.ingredient_amount_tv);
        }

    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient,parent,false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.recipeName.setText(mItems.get(position).getIngredient());
        holder.recipeAmount.setText(mItems.get(position).getQuantity() + " " + mItems.get(position).getMeasure());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " +mItems.size());
        return mItems.size();
    }
}
