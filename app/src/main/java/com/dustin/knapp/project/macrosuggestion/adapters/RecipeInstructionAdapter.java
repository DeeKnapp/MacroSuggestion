package com.dustin.knapp.project.macrosuggestion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.recipe.Steps;
import java.util.List;

/**
 * Created by dknapp on 4/11/17
 */
public class RecipeInstructionAdapter extends RecyclerView.Adapter<RecipeInstructionAdapter.MyViewHolder> {

  private List<Steps> stepsList;

  public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView stepNumber, stepDescription;

    public MyViewHolder(View view) {
      super(view);
      stepNumber = (TextView) view.findViewById(R.id.stepNumber);
      stepDescription = (TextView) view.findViewById(R.id.stepDescription);
    }
  }

  public RecipeInstructionAdapter(List<Steps> stepsList) {
    this.stepsList = stepsList;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_instruction_card_view, parent, false);
    return new MyViewHolder(itemView);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, int position) {
    final Steps step = stepsList.get(position);
    int stepNumber = position + 1;
    holder.stepNumber.setText("Step #" + stepNumber);
    holder.stepDescription.setText(step.getStep());
  }

  @Override public int getItemCount() {
    return stepsList.size();
  }
}