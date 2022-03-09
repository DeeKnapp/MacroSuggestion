package com.dustin.knapp.project.macrosuggestion.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dustin.knapp.project.macrosuggestion.R;
import com.dustin.knapp.project.macrosuggestion.models.BaseNutrition;

/**
 * Created by dknapp on 4/28/17
 */
public class QuickAddFoodDialogFragment extends DialogFragment {

  private EditText etName, etCalories, etProtein, etFats, etCarbs;

  Button btnSubmit;

  ImageView addSuccessCheckImage;

  TextView dialogTitle;

  private int layout = -1;

  private QuickAddDialogListener listener;

  public QuickAddFoodDialogFragment() {
    // Empty constructor is required for DialogFragment
    // Make sure not to add arguments to the constructor
    // Use `newInstance` instead as shown below
  }

  public static QuickAddFoodDialogFragment newInstance() {
    return new QuickAddFoodDialogFragment();
  }

  public void setLayout(int layout) {
    this.layout = layout;
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    if (layout != -1) {
      return inflater.inflate(layout, container);
    } else {
      return inflater.inflate(R.layout.calories_add_food_dialog, container);
    }
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Window window = getDialog().getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();

    wlp.gravity = Gravity.CENTER_VERTICAL;
    wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    window.setAttributes(wlp);

    etName = (EditText) view.findViewById(R.id.etFoodName);
    etCalories = (EditText) view.findViewById(R.id.etCalories);
    etProtein = (EditText) view.findViewById(R.id.etProtein);
    etFats = (EditText) view.findViewById(R.id.etFats);
    etCarbs = (EditText) view.findViewById(R.id.etCarbs);
    btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
    addSuccessCheckImage = (ImageView) view.findViewById(R.id.successIcon);
    dialogTitle = (TextView) view.findViewById(R.id.dialogTitle);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (etName.getText().toString().equals("")) {
          etName.requestFocus();
        } else if (etCalories.getText().toString().equals("")) {
          etCalories.requestFocus();
        } else if (etProtein.getText().toString().equals("")) {
          etProtein.requestFocus();
        } else if (etCarbs.getText().toString().equals("")) {
          etCarbs.requestFocus();
        } else if (etFats.getText().toString().equals("")) {
          etFats.requestFocus();
        } else {
          String name = etName.getText().toString();
          Float calories = Float.valueOf(etCalories.getText().toString());
          Float protein = Float.valueOf(etProtein.getText().toString());
          Float carbs = Float.valueOf(etCarbs.getText().toString());
          Float fats = Float.valueOf(etFats.getText().toString());

          BaseNutrition baseNutrition = new BaseNutrition(name, calories, protein, carbs, fats);
          listener.onQuickAddSubmit(baseNutrition);

          btnSubmit.setEnabled(false);
          dialogTitle.setVisibility(View.GONE);
          addSuccessCheckImage.setVisibility(View.VISIBLE);

          //todo allow lost state dialog transaction
          final Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
            @Override public void run() {
              dismiss();
            }
          }, 2000);
        }
      }
    });

    // Fetch arguments from bundle and set title
    String title = "Add Food";
    getDialog().setTitle(title);
    // Show soft keyboard automatically and request focus to field
    etName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public void setListener(QuickAddDialogListener listener) {
    this.listener = listener;
  }

  public interface QuickAddDialogListener {
    void onQuickAddSubmit(BaseNutrition baseNutrition);
  }
}