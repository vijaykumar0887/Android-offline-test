

package model.test.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import model.test.hackernews.R;
import model.test.hackernews.Utils.Article;
import model.test.hackernews.Utils.TimeUtils;
import model.test.hackernews.network.ArticleResponseListener;
import model.test.hackernews.network.NetworkAdapter;
import model.test.hackernews.network.OnStoryBoardItemClickListener;

public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private static final String TAG = StoriesAdapter.class.getSimpleName();
    private Context mContext;
    List<Integer> mStoriesList;
    private OnStoryBoardItemClickListener mListener;

    public StoriesAdapter(Context context, List<Integer> items,OnStoryBoardItemClickListener listener) {
        mContext = context;
        mStoriesList = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_story_item, parent, false);
            return new StoryItemHolder(v);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Integer itemId = mStoriesList.get(position);
            final StoryItemHolder storyItemHolder = (StoryItemHolder) holder;
        final Article[] articleData = new Article[1];
        storyItemHolder.mProgressBar.setVisibility(View.VISIBLE);
        NetworkAdapter.getInstance().getDetails(mContext,String.valueOf(itemId), new ArticleResponseListener() {
            @Override
            public void onSuccess(Article result) {
                articleData[0] = result;
                storyItemHolder.mProgressBar.setVisibility(View.INVISIBLE);
                if (result.getTitle() != null) {
                    storyItemHolder.txtTitle.setText(result.getTitle());
                }
                if (result.getTime() != null) {
                    storyItemHolder.txtTime.setText(TimeUtils.getTimeDifferenceToPresent(result.getTime()));
                }
                if (result.getBy() != null) {
                    storyItemHolder.txtAuthor.setText(mContext.getString(R.string.author).concat(" ").concat(result.getBy()));
                }
                if (result.getScore() != null) {
                    storyItemHolder.txtPoints.setText(String.valueOf(result.getScore()));
                }
                if (result.getUrl() != null) {
                    storyItemHolder.txtUrl.setText(result.getUrl());
                }

            }

            @Override
            public void onFailure() {
                storyItemHolder.mProgressBar.setVisibility(View.INVISIBLE);
            }
        });

            storyItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(articleData[0].getKids().size() > 0){
                        mListener.onItemClick(articleData[0].getKids());
                    }
                }
            });

    }

    public void update(List<Integer> newData){
        mStoriesList.clear();
        mStoriesList.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStoriesList.size();
    }


    class StoryItemHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtAuthor;
        TextView txtTime;
        TextView txtPoints;
        TextView txtUrl;
        ProgressBar mProgressBar;

        public StoryItemHolder(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            this.txtAuthor = (TextView) itemView.findViewById(R.id.tv_item_author);
            this.txtTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            this.txtPoints = (TextView) itemView.findViewById(R.id.tv_item_points);
            this.txtUrl = (TextView) itemView.findViewById(R.id.tv_item_url);
            this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);


        }
    }


}


