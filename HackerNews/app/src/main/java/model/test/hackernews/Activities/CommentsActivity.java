package model.test.hackernews.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import model.test.hackernews.R;
import model.test.hackernews.Utils.Constants;
import model.test.hackernews.Utils.DialogUtils;
import model.test.hackernews.Utils.NetworkCheckUtility;
import model.test.hackernews.adapters.CommentsAdapter;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initViews();

        if(!NetworkCheckUtility.isNetworkAvailable(this)) {
            DialogUtils.showNoNetworkDialog(this,false);
        }
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView commentsList = (RecyclerView) findViewById(R.id.rv_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        commentsList.setLayoutManager(linearLayoutManager);

        if(getIntent().getIntegerArrayListExtra(Constants.COMMENT_IDS) != null ){
            CommentsAdapter commentsAdapter = new CommentsAdapter(this,getIntent().getIntegerArrayListExtra(Constants.COMMENT_IDS));
            commentsList.setAdapter(commentsAdapter);
        }

    }


}
