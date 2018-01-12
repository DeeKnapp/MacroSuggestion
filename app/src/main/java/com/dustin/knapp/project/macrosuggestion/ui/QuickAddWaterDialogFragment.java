package com.dustin.knapp.project.macrosuggestion.ui;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.R;

/**
 * Created by dknapp on 4/28/17
 */
public class QuickAddWaterDialogFragment extends DialogFragment {

  private EditText etWater;

  private Button btnSubmit;

  private QuickAddDialogListener listener;

  ImageView addSuccessCheckImage;

  TextView dialogTitle;

  public QuickAddWaterDialogFragment() {
    // Empty constructor is required for DialogFragment
    // Make sure not to add arguments to the constructor
    // Use `newInstance` instead as shown below
  }

  public static QuickAddWaterDialogFragment newInstance() {
    return new QuickAddWaterDialogFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.quick_add_water_dialog, container);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Window window = getDialog().getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();

    wlp.gravity = Gravity.CENTER_VERTICAL;
    wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    window.setAttributes(wlp);

    etWater = (EditText) view.findViewById(R.id.etWater);
    btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
    addSuccessCheckImage = (ImageView) view.findViewById(R.id.successIcon);
    dialogTitle = (TextView) view.findViewById(R.id.dialogTitle);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (etWater.getText().toString().equals("")) {
          etWater.requestFocus();
        } else {
          Float water = Float.valueOf(etWater.getText().toString());
          listener.onQuickAddSubmit(water);

          btnSubmit.setEnabled(false);
          dialogTitle.setVisibility(View.GONE);
          addSuccessCheckImage.setVisibility(View.VISIBLE);

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
    String title = "Add Water";
    getDialog().setTitle(title);
    // Show soft keyboard automatically and request focus to field
    etWater.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public void setListener(QuickAddDialogListener listener) {
    this.listener = listener;
  }

  public interface QuickAddDialogListener {
    void onQuickAddSubmit(Float water);
  }
}