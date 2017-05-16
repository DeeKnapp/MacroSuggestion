package com.dustin.knapp.project.macrosuggestion.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.FoodItem;
import java.util.List;

/**
 * Created by dknapp on 4/11/17
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

  private Context mContext;
  private List<FoodItem> foodList;

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, calories, fat, protein, carbs;
    public ImageView thumbnail;

    public MyViewHolder(View view) {
      super(view);
      title = (TextView) view.findViewById(R.id.title);
      thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
      calories = (TextView) view.findViewById(R.id.calories);
      fat = (TextView) view.findViewById(R.id.fat);
      protein = (TextView) view.findViewById(R.id.protein);
      carbs = (TextView) view.findViewById(R.id.carbs);
    }
  }

  public FoodListAdapter(Context mContext, List<FoodItem> foodList) {
    this.mContext = mContext;
    this.foodList = foodList;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_view, parent, false);

    return new MyViewHolder(itemView);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, int position) {
    FoodItem foodItem = foodList.get(position);
    holder.title.setText(foodItem.getTitle());

    // loading foodItem cover using Glide library
    Glide.with(mContext).load(foodItem.getImage()).into(holder.thumbnail);

    holder.calories.setText("Calories: " + foodItem.getCalories());
    holder.fat.setText("Fat: " + foodItem.getFat());
    holder.protein.setText("Protein: " + foodItem.getProtein());
    holder.carbs.setText("Carbohydrates: " + foodItem.getCarbs());
  }

  @Override public int getItemCount() {
    return foodList.size();
  }
}