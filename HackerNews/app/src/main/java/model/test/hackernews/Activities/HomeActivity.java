package model.test.hackernews.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import model.test.hackernews.R;
import model.test.hackernews.Utils.Constants;
import model.test.hackernews.Utils.DialogUtils;
import model.test.hackernews.Utils.LogUtils;
import model.test.hackernews.Utils.NetworkCheckUtility;
import model.test.hackernews.adapters.StoriesAdapter;
import model.test.hackernews.network.NetworkAdapter;
import model.test.hackernews.network.OnStoryBoardItemClickListener;
import model.test.hackernews.network.StoriesResponseListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnStoryBoardItemClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private StoriesAdapter mStoriesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        loadStories(Constants.TOP_STORIES_ENDPOINT);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView storiesRecycler = (RecyclerView) findViewById(R.id.rv_stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        storiesRecycler.setLayoutManager(linearLayoutManager);
        mStoriesAdapter = new StoriesAdapter(this,new ArrayList<Integer>(),this);
        storiesRecycler.setAdapter(mStoriesAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){
            case R.id.nav_top :
                LogUtils.LOGI(TAG,"top stories");
                loadStories(Constants.TOP_STORIES_ENDPOINT);
                break;

            case R.id.nav_new :
                LogUtils.LOGI(TAG,"new stories");
                loadStories(Constants.NEW_STORIES_ENDPOINT);
                break;

            case R.id.nav_best :
                LogUtils.LOGI(TAG,"besrt stories");
                loadStories(Constants.BEST_STORIES_ENDPOINT);
                break;

            case R.id.nav_ask :
                LogUtils.LOGI(TAG,"ask stories");
                loadStories(Constants.ASK_ENDPOINT);
                break;
            case R.id.nav_show :
                LogUtils.LOGI(TAG,"show stories");
                loadStories(Constants.SHOW_ENDPOINT);
                break;
            case R.id.nav_jobs :
                LogUtils.LOGI(TAG,"jobs stories");
                loadStories(Constants.JOBS_ENDPOINT);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void loadStories(String storyType){
        showProgress();
        if(NetworkCheckUtility.isNetworkAvailable(this)) {
            NetworkAdapter.getInstance().getStories(this,storyType, new StoriesResponseListener() {
                @Override
                public void onSuccess(List<Integer> results) {
                    updateStoryBoard(results);
                    hideProgress();
                }

                @Override
                public void onFailure() {
                    hideProgress();
                    showNetworkError("Unable to load the data");
                }
            });
        }else{
            hideProgress();
            showNoNetworkDialog();
        }
    }

    public void showProgress() {
        DialogUtils.showProgressDialog(this, getString(R.string.loading));
    }

    public void hideProgress() {
        DialogUtils.dismissProgress();
    }

    public void updateStoryBoard(final List<Integer> items) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStoriesAdapter.update(items);
            }
        });

    }

    public void showNetworkError(String errorMsg) {
        Snackbar.make(findViewById(R.id.main_coordinatorLayout), errorMsg, Snackbar.LENGTH_LONG).show();
    }



    public void showNoNetworkDialog() {
            DialogUtils.showNoNetworkDialog(this,false);
    }

    @Override
    public void onItemClick(List<Integer> comments) {
            Intent intent = new Intent(this,CommentsActivity.class);
            intent.putIntegerArrayListExtra(Constants.COMMENT_IDS, (ArrayList<Integer>) comments);
            startActivity(intent);
    }
}
