package com.dustin.knapp.project.macrosuggestion.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.activities.RecipeStepsActivity;
import com.dustin.knapp.project.macrosuggestion.models.FoodItem;
import java.util.List;

/**
 * Created by dknapp on 4/11/17
 */
public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.MyViewHolder> {

  private Activity mContext;
  private List<FoodItem> foodList;

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView title, calories, fat, protein, carbs;
    public ImageView thumbnail;
    public View viewWrapper;

    public MyViewHolder(View view) {
      super(view);
      viewWrapper = view.findViewById(R.id.viewWrapper);
      title = (TextView) view.findViewById(R.id.title);
      thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
      calories = (TextView) view.findViewById(R.id.calories);
      fat = (TextView) view.findViewById(R.id.fat);
      protein = (TextView) view.findViewById(R.id.protein);
      carbs = (TextView) view.findViewById(R.id.carbs);

      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Log.d("TAG", "Position clicked: " + getAdapterPosition());
          Intent intent = new Intent(mContext, RecipeStepsActivity.class);
          intent.putExtra("foodItemId", foodList.get(getAdapterPosition()).getId());
          intent.putExtra("foodItemName", foodList.get(getAdapterPosition()).getTitle());
          intent.putExtra("imageUrl", foodList.get(getAdapterPosition()).getImage());
          mContext.startActivity(intent);
        }
      });
    }
  }

  public FoodListAdapter(Activity mContext, List<FoodItem> foodList) {
    this.mContext = mContext;
    this.foodList = foodList;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_card_view, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, int position) {
    final FoodItem foodItem = foodList.get(position);
    holder.title.setText(foodItem.getTitle());

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