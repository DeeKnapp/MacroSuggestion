package com.dustin.knapp.project.macrosuggestion.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;

/**
 * Created by dknapp on 4/28/17
 */
public class AddFoodDialogFragment extends DialogFragment {

  private EditText etFoodName, etCalories, etProtein, etFats, etCarbs;

  private Button btnSubmit;

  private AddFoodDialogListener listener;

  private String callingContainer;

  public AddFoodDialogFragment() {
    // Empty constructor is required for DialogFragment
    // Make sure not to add arguments to the constructor
    // Use `newInstance` instead as shown below
  }

  public static AddFoodDialogFragment newInstance(int callContainer) {
    AddFoodDialogFragment frag = new AddFoodDialogFragment();
    Bundle args = new Bundle();
    args.putInt("callContainer", callContainer);
    frag.setArguments(args);
    return frag;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.add_food_dialog, container);
  }

  @Override public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    final int callingContainer = getArguments().getInt("callContainer");

    Window window = getDialog().getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();

    wlp.gravity = Gravity.CENTER_VERTICAL;
    wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    window.setAttributes(wlp);

    etFoodName = (EditText) view.findViewById(R.id.etFoodName);
    etCalories = (EditText) view.findViewById(R.id.etCalories);
    etProtein = (EditText) view.findViewById(R.id.etProtein);
    etFats = (EditText) view.findViewById(R.id.etFats);
    etCarbs = (EditText) view.findViewById(R.id.etCarbs);
    btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (etFoodName.getText().toString().equals("")) {
          etFoodName.requestFocus();
        } else if (etCalories.getText().toString().equals("")) {
          etCalories.requestFocus();
        } else if (etProtein.getText().toString().equals("")) {
          etProtein.requestFocus();
        } else if (etCarbs.getText().toString().equals("")) {
          etCarbs.requestFocus();
        } else if (etFats.getText().toString().equals("")) {
          etFats.requestFocus();
        } else {
          String foodName = etFoodName.getText().toString();
          Float calories = Float.valueOf(etCalories.getText().toString());
          Float protein = Float.valueOf(etProtein.getText().toString());
          Float carbs = Float.valueOf(etCarbs.getText().toString());
          Float fats = Float.valueOf(etFats.getText().toString());

          BaseNutrition baseNutrition = new BaseNutrition(calories, protein, carbs, fats);

          listener.onQuickAddSubmit(callingContainer, foodName, baseNutrition);
          dismiss();
        }
      }
    });

    // Fetch arguments from bundle and set title
    String title = "Add Food";
    getDialog().setTitle(title);
    // Show soft keyboard automatically and request focus to field
    etFoodName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public void setListener(AddFoodDialogListener listener) {
    this.listener = listener;
  }

  public interface AddFoodDialogListener {
    void onQuickAddSubmit(int callContainer, String foodName, BaseNutrition baseNutrition);
  }
}