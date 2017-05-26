package com.kong.app.news;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.kong.R;
import com.kong.app.news.image.ImageFragment;
import com.kong.lib.share.common.fragment.BaseFragment;
import com.kong.lib.share.common.fragment.IBaseEvent;
import com.library.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainContract.View,IBaseEvent {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationLeftView;

    private MainContract.Presenter mMainPresenter;
    private BaseFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        initNavigationLeft();
        mMainPresenter = new MainPresenter(this);

        switchNews();
    }

    private void initNavigationLeft() {
        mNavigationLeftView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationLeftView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mMainPresenter.switchNavigation(item.getItemId());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {
            return super.dispatchKeyEvent(event);
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ActionUtils.startAbout(MainActivity.this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void switchNews() {
        switchFragment(NewsFragment.newInstance());
        mToolbar.setTitle(R.string.navigation_news);
    }

    public void switchImages() {
        switchFragment(ImageFragment.newInstance());
        mToolbar.setTitle(R.string.navigation_images);
    }

    @Override
    public void switchAbout() {
        ActionUtils.startAbout(MainActivity.this);
    }

    private void switchFragment(BaseFragment fragment){
        onSwitchFragment(fragment,null);
    }

    private void switchFragment(BaseFragment fragment, Bundle bundle) {
        Log.e(TAG, "switchFragment() -> fragment:" + fragment.toString());
        if (mCurrentFragment == fragment) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onSwitchFragment(BaseFragment fragment, Bundle bundle) {
        switchFragment(fragment, bundle);
    }

    @Override
    public void onAttachActivity(BaseFragment fragment) {
        mCurrentFragment = fragment;
    }

    @Override
    public void onDetachActivity(BaseFragment fragment) {
        if (mCurrentFragment == fragment) {
            mCurrentFragment = null;
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mMainPresenter = presenter;
    }
}