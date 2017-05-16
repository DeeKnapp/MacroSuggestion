package com.dustin.knapp.project.macrosuggestion.navigation_drawer;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dustin.knapp.project.macrosuggestion.R;

/**
 * Created by jmai on 5/20/16.
 */
public class DrawerMenuItemViewHolder {
  @BindView(R.id.menu_text) TextView text;

  public DrawerMenuItemViewHolder(View view) {
    ButterKnife.bind(this, view);
  }

  public TextView getText() {
    return text;
  }

  public void setText(TextView text) {
    this.text = text;
  }
}
