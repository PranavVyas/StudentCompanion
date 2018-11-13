package com.vyas.pranav.studentcompanion.extrautils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;

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

/**
 * The type Views utils.
 * Class to help the UI operations
 */
public class ViewsUtils {

    /**
     * The constant ID_DASHBOARD_NAVIGATION.
     */
    public static final int ID_DASHBOARD_NAVIGATION = 1;
    /**
     * The constant ID_MY_BOOK_SHELF_NAVIGATION.
     */
    public static final int ID_MY_BOOK_SHELF_NAVIGATION = 2;
    /**
     * The constant ID_TIME_TABLE_NAVIGATION.
     */
    public static final int ID_TIME_TABLE_NAVIGATION = 3;
    /**
     * The constant ID_SHARE_APP_NAVIGATION.
     */
    public static final int ID_SHARE_APP_NAVIGATION = 4;
    /**
     * The constant ID_PREFERENCE_NAVIGATION.
     */
    public static final int ID_PREFERENCE_NAVIGATION = 5;
    /**
     * The constant ID_ABOUT_THIS_APP_NAVIGATION.
     */
    public static final int ID_ABOUT_THIS_APP_NAVIGATION = 6;
    /**
     * The constant ID_LOG_OUT_APP_NAVIGATION.
     */
    public static final int ID_LOG_OUT_APP_NAVIGATION = 7;


    /**
     * Build navigation drawer drawer.
     * and returns it in DashboardActivity
     *
     * @param context the context
     * @param toolbar the toolbar in which toolbar integrates itself
     * @return the drawer
     */
    public static Drawer buildNavigationDrawer(final Context context, Toolbar toolbar) {
        final FirebaseAuth mAuth;
        final OnCustomDrawerItemClickListener mCallback;

        mCallback = (OnCustomDrawerItemClickListener) context;

        mAuth = FirebaseAuth.getInstance();
        PrimaryDrawerItem dashboard = new PrimaryDrawerItem()
                .withIdentifier(ID_DASHBOARD_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_dashboard)
                .withName(R.string.action_dashboard_navigation)
                .withIconTintingEnabled(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(ID_DASHBOARD_NAVIGATION);
                        //Toast.makeText(context, "DashBoard Clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        PrimaryDrawerItem prefences = new PrimaryDrawerItem()
                .withIdentifier(ID_PREFERENCE_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_settings)
                .withName(R.string.action_preference_navigation)
                .withIconTintingEnabled(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(ID_PREFERENCE_NAVIGATION);
                        //Toast.makeText(context, "Preference Clicked", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

        PrimaryDrawerItem aboutApp = new PrimaryDrawerItem()
                .withIdentifier(ID_ABOUT_THIS_APP_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_about_app)
                .withName(R.string.action_about_app_navigation)
                .withIconTintingEnabled(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //Toast.makeText(context, "aboutApp Clicked", Toast.LENGTH_SHORT).show();
                        mCallback.onClickedDrawerItem(ID_ABOUT_THIS_APP_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem timetable = new PrimaryDrawerItem()
                .withIdentifier(ID_TIME_TABLE_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_timetable)
                .withIconTintingEnabled(true)
                .withName(R.string.action_time_table_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //Toast.makeText(context, "Time Table Clicked", Toast.LENGTH_SHORT).show();
                        mCallback.onClickedDrawerItem(ID_TIME_TABLE_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem shareApp = new PrimaryDrawerItem()
                .withIdentifier(ID_SHARE_APP_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_share)
                .withIconTintingEnabled(true)
                .withSelectable(false)
                .withName(R.string.action_share_app_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(ID_SHARE_APP_NAVIGATION);
                        return false;
                    }
                });

        PrimaryDrawerItem logOut = new PrimaryDrawerItem()
                .withIdentifier(ID_LOG_OUT_APP_NAVIGATION)
                .withIcon(R.drawable.ic_navigation_log_out)
                .withSelectable(false)
                .withIconTintingEnabled(true)
                .withName(R.string.action_log_out_navigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mCallback.onClickedDrawerItem(ID_LOG_OUT_APP_NAVIGATION);
                        return false;
                    }
                });

        FirebaseUser currUser = mAuth.getCurrentUser();
        String userName = currUser != null ? mAuth.getCurrentUser().getDisplayName() : context.getString(R.string.drawer_default_uname);
        String email = currUser != null ? mAuth.getCurrentUser().getEmail() : context.getString(R.string.drawer_default_email);
        Uri photoUri = currUser != null ? mAuth.getCurrentUser().getPhotoUrl() : null;
        AccountHeader navigationHeader = new AccountHeaderBuilder()
                .withActivity((Activity) context)
                .withSelectionListEnabledForSingleProfile(false)
                .withHeaderBackground(R.drawable.navigation_header_bkg)
                .addProfiles(
                        new ProfileDrawerItem().withName(userName)
                                .withTextColor(context.getResources().getColor(R.color.colorWhite))
                                .withEmail(email)
                                .withIcon(photoUri)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        //Toast.makeText(context, "Profile Clicked", Toast.LENGTH_SHORT).show();
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
                        timetable,
                        shareApp,
                        prefences,
                        aboutApp,
                        logOut
                )
                .build();
        return drawer;
    }

    /**
     * The interface On custom drawer item click listener.
     * To notify if the item is clicked in the drawer to set Title of the Dashboard Activity and change the view accordingly
     */
    public interface OnCustomDrawerItemClickListener {
        /**
         * On clicked drawer item.
         *
         * @param identifier the identifier
         */
        void onClickedDrawerItem(int identifier);
    }
}
