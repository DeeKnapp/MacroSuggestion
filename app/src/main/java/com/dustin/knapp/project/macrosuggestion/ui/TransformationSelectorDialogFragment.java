package com.dustin.knapp.project.macrosuggestion.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.dustin.knapp.project.macrosuggestion.R;

/**
 * Created by dknapp on 4/28/17
 */
public class TransformationSelectorDialogFragment extends DialogFragment {

  private RadioButton accordionButton, cubeInButton, cubeOutButton, stackButton, zoomOutButton;
  private Button btnSubmit;

  private TransformSelectorListener listener;

  public TransformationSelectorDialogFragment() {
    // Empty constructor is required for DialogFragment
    // Make sure not to add arguments to the constructor
    // Use `newInstance` instead as shown below
  }

  public static TransformationSelectorDialogFragment newInstance() {
    return new TransformationSelectorDialogFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.transformation_selector_dialog, container);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Window window = getDialog().getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();

    wlp.gravity = Gravity.CENTER_VERTICAL;
    wlp.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
    window.setAttributes(wlp);

    accordionButton = (RadioButton) view.findViewById(R.id.accordionTransformerButton);
    cubeInButton = (RadioButton) view.findViewById(R.id.cubeInTransformer);
    cubeOutButton = (RadioButton) view.findViewById(R.id.cubeOutTransformer);
    stackButton = (RadioButton) view.findViewById(R.id.stackTransformer);
    zoomOutButton = (RadioButton) view.findViewById(R.id.zoomOutTransformer);

    btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String transformer = "";

        if (accordionButton.isChecked()) {
          transformer = "Accordion";
        } else if (cubeInButton.isChecked()) {
          transformer = "Cube In";
        } else if (cubeOutButton.isChecked()) {
          transformer = "Cube Out";
        } else if (stackButton.isChecked()) {
          transformer = "Stack";
        } else if (zoomOutButton.isChecked()) {
          transformer = "Zoom Out";
        }

        listener.onTransformerSelected(transformer);
        dismiss();
      }
    });

    // Fetch arguments from bundle and set title
    String title = "Select Transformer";

    getDialog().setTitle(title);
    // Show soft keyboard automatically and request focus to field
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
  }

  public void setListener(TransformSelectorListener listener) {
    this.listener = listener;
  }

  public interface TransformSelectorListener {
    void onTransformerSelected(String transformer);
  }
}