package com.vyas.pranav.studentcompanion.extraUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.vyas.pranav.studentcompanion.R;

import androidx.appcompat.widget.Toolbar;

public class ViewsUtils {

    public static final int ID_DASHBOARD_NAVIGATION = 1;
    public static final int ID_MY_BOOK_SHELF_NAVIGATION = 2;
    public static final int ID_TIME_TABLE_NAVIGATION = 3;
    public static final int ID_SHARE_APP_NAVIGATION = 4;
    public static final int ID_PREFERENCE_NAVIGATION = 5;
    public static final int ID_ABOUT_THIS_APP_NAVIGATION = 6;
    public static final int ID_LOG_OUT_APP_NAVIGATION = 7;


    public static Drawer buildNavigationDrawer(final Context context, Toolbar toolbar) {
        final FirebaseAuth mAuth;
        final OnCustomDrawerItemClickListener mCallback;

        mCallback = (OnCustomDrawerItemClickListener) context;

        mAuth = FirebaseAuth.getInstance();
        PrimaryDrawerItem dashboard = new PrimaryDrawerItem()
                .withIdentifier(ID_DASHBOARD_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_dashboard)
                .withName(R.string.action_dashboard_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_dashboard_navigation), ID_DASHBOARD_NAVIGATION);
                        Toast.makeText(context, "DashBoard Clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        PrimaryDrawerItem prefences = new PrimaryDrawerItem()
                .withIdentifier(ID_PREFERENCE_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_settings)
                .withName(R.string.action_preference_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_preference_navigation), ID_PREFERENCE_NAVIGATION);
                        Toast.makeText(context, "Preference Clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        PrimaryDrawerItem aboutApp = new PrimaryDrawerItem()
                .withIdentifier(ID_ABOUT_THIS_APP_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_about_app)
                .withName(R.string.action_about_app_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "aboutApp Clicked", Toast.LENGTH_SHORT).show();
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_about_app_navigation), ID_ABOUT_THIS_APP_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem timetable = new PrimaryDrawerItem()
                .withIdentifier(ID_TIME_TABLE_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_timetable)
                .withName(R.string.action_time_table_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "Time Table Clicked", Toast.LENGTH_SHORT).show();
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_time_table_navigation), ID_TIME_TABLE_NAVIGATION);
                        return false;
                    }
                });

        //TODO Post 1.0 Update
//        PrimaryDrawerItem myBookShelf = new PrimaryDrawerItem()
//                .withIdentifier(ID_MY_BOOK_SHELF_NAVIGATION)
//                .withIcon(R.drawable.ic_lnavigation_book_shelf)
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
                .withIcon(R.drawable.ic_navigation_share)
                .withSelectable(false)
                .withName(R.string.action_share_app_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_share_app_navigation), ID_SHARE_APP_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem logOut = new PrimaryDrawerItem()
                .withIdentifier(ID_LOG_OUT_APP_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_share)
                .withSelectable(false)
                .withName(R.string.action_log_out_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(context.getString(R.string.action_log_out_navigation), ID_LOG_OUT_APP_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem testApp = new PrimaryDrawerItem()
                .withIdentifier(100)
                .withIcon(R.drawable.ic_navigation_share)
                .withName("Developer Mode")
                .withSelectable(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(context, "Developer Mode Enabled", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,DeveloperActivity.class);
                        context.startActivity(intent);
                        return false;
                    }
                });
        FirebaseUser currUser = mAuth.getCurrentUser();
        String userName = currUser != null ? mAuth.getCurrentUser().getDisplayName() : "ANYNOMOUS";
        String email = currUser != null ? mAuth.getCurrentUser().getEmail() : "PLEASE REGISTER";
        Uri photoUri = currUser != null ? mAuth.getCurrentUser().getPhotoUrl() : null;
        AccountHeader navigationHeader = new AccountHeaderBuilder()
                .withActivity((Activity) context)
                .withHeaderBackground(R.drawable.navigation_header_bkg_new)
                .addProfiles(
                        new ProfileDrawerItem().withName(userName)
                                .withTextColor(context.getResources().getColor(R.color.colorWhite))
                                .withEmail(email)
                                .withIcon(photoUri)
                                //TODO Change background or text color to white
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        Toast.makeText(context, "Profile Changed", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        Toast.makeText(context, "Selected New Account", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        Drawer drawer = new DrawerBuilder()
                .withActivity((Activity) context)
                .withToolbar(toolbar)
                .withAccountHeader(navigationHeader)
                .addDrawerItems(
                        dashboard,
                        // TODO Post 1.0 update // myBookShelf,
                        timetable,
                        shareApp,
                        prefences,
                        aboutApp,
                        testApp,
                        logOut
                )
                .build();
        return drawer;
    }

    public interface OnCustomDrawerItemClickListener {
        void onClickedDrawerItem(String name, int identifier);
    }
}
