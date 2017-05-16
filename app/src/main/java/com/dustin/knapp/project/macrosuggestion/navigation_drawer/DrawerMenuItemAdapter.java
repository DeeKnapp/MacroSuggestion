package com.dustin.knapp.project.macrosuggestion.navigation_drawer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dustin.knapp.project.macrosuggestion.R;
import java.util.Arrays;

/**
 * Created by jmai on 5/20/16.
 */
public class DrawerMenuItemAdapter extends BaseAdapter {
  private static final int MAIN = 0;

  private final DrawerMenuItem[] objects;
  private final Context context;
  private int selectedPosition;

  @Override public int getViewTypeCount() {
    return 2;
  }

  @Override public int getItemViewType(int position) {
    return MAIN;
  }

  public DrawerMenuItemAdapter(final Context context, final DrawerMenuItem[] objects) {
    this.objects = Arrays.copyOf(objects, objects.length);
    this.context = context;
  }

  @Override public int getCount() {
    return objects.length;
  }

  @Override public Object getItem(int position) {
    return objects[position];
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    DrawerMenuItemViewHolder viewHolder = null;
    DrawerMenuItem listViewItem = objects[position];

    if (convertView == null) {
      View view = LayoutInflater.from(context).inflate(R.layout.drawer_menu_row, null);
      viewHolder = new DrawerMenuItemViewHolder(view);
      convertView = view;
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (DrawerMenuItemViewHolder) convertView.getTag();
    }

    TextView textView = viewHolder.getText();
    textView.setText(listViewItem.getNavName());

    if (position == selectedPosition) {
      convertView.setBackgroundColor(
          ResourcesCompat.getColor(context.getResources(), R.color.dgm_medium_green, null));
      textView.setTextColor(ResourcesCompat.getColor(context.getResources(), R.color.white, null));
    } else {
      convertView.setBackground(ResourcesCompat.getDrawable(context.getResources(),
          R.drawable.background_states_nav_others, null));
      textView.setTextColor(getColorStateList());
    }

    return convertView;
  }

  public void updateWithSelectedPosition(int position) {
    this.selectedPosition = position;
    notifyDataSetChanged();
  }

  private ColorStateList getColorStateList() {
    int[][] states = new int[][] {
        new int[] {
            android.R.attr.state_focused, android.R.attr.state_pressed
        },
        //focused and pressed
        new int[] {
            android.R.attr.state_focused, -android.R.attr.state_pressed
        }, // focused
        new int[] {
            -android.R.attr.state_focused, android.R.attr.state_pressed
        }, // pressed
        new int[] {}
    };

    int[] colors = new int[] {
        ResourcesCompat.getColor(context.getResources(), R.color.white, null),
        ResourcesCompat.getColor(context.getResources(), R.color.white, null),
        ResourcesCompat.getColor(context.getResources(), R.color.white, null),
        ResourcesCompat.getColor(context.getResources(), R.color.white, null)
    };

    return new ColorStateList(states, colors);
  }
}

