package com.vyas.pranav.studentcompanion.extraUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.vyas.pranav.studentcompanion.R;
import com.vyas.pranav.studentcompanion.aboutapp.AboutAppActivity;
import com.vyas.pranav.studentcompanion.dashboard.DashboardActivity;
import com.vyas.pranav.studentcompanion.prefences.SettingsActivity;
import com.vyas.pranav.studentcompanion.timetable.TimeTableActivity;

public class viewsUtils {

    private static final long ID_DASHBOARD_NAVIGATION = 1;
    private static final long ID_MY_BOOK_SHELF_NAVIGATION = 2;
    private static final long ID_TIME_TABLE_NAVIGATION = 3;
    private static final long ID_SHARE_APP_NAVIGATION = 4;
    private static final long ID_PREFERENCE_NAVIGATION = 5;
    private static final long ID_ABOUT_THIS_APP_NAVIGATION = 6;

    public static Drawer buildNavigationDrawer(final Context context, Toolbar toolbar){
        PrimaryDrawerItem dashboard = new PrimaryDrawerItem()
                .withIdentifier(ID_DASHBOARD_NAVIGATION)
                .withName(R.string.action_dashboard_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "DashBoard Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,DashboardActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                });

        PrimaryDrawerItem prefences = new PrimaryDrawerItem()
                .withIdentifier(ID_PREFERENCE_NAVIGATION)
                .withName(R.string.action_preference_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "Preference Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,SettingsActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                });

        PrimaryDrawerItem aboutApp = new PrimaryDrawerItem()
                .withIdentifier(ID_ABOUT_THIS_APP_NAVIGATION)
                .withName(R.string.action_about_app_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "aboutApp Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,AboutAppActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                });

        PrimaryDrawerItem timetable = new PrimaryDrawerItem()
                .withIdentifier(ID_TIME_TABLE_NAVIGATION)
                .withName(R.string.action_time_table_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "Time Table Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,TimeTableActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                });

        //TODO Post 1.0 Update
//        PrimaryDrawerItem myBookShelf = new PrimaryDrawerItem()
//                .withIdentifier(ID_MY_BOOK_SHELF_NAVIGATION)
//                .withName(R.string.action_my_book_shelf_navigation)
//                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
//                    @Override
//                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
//                        Toast.makeText(context, "My book shelf clicked", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });

        PrimaryDrawerItem shareApp = new PrimaryDrawerItem()
                .withIdentifier(ID_SHARE_APP_NAVIGATION)
                .withName(R.string.action_share_app_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "Share App Clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        Drawer drawer = new DrawerBuilder()
                .withActivity((Activity) context)
                .withToolbar(toolbar)
                .addDrawerItems(
                        dashboard,
                        // TODO Post 1.0 update // myBookShelf,
                        timetable,
                        shareApp,
                        prefences,
                        aboutApp
                )
                .build();
        return drawer;
    }
}
