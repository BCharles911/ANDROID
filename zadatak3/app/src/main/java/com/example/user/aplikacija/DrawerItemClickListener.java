package com.example.user.aplikacija;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingsActivity sa = new SettingsActivity();
        sa.selectItem(position);
        drawerLayout.closeDrawers();
    }
}