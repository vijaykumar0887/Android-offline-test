

package model.test.hackernews.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import model.test.hackernews.R;
import model.test.hackernews.Utils.Article;
import model.test.hackernews.Utils.TimeUtils;
import model.test.hackernews.network.ArticleResponseListener;
import model.test.hackernews.network.NetworkAdapter;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    List<Integer> mCommentIds;

    public CommentsAdapter(Context context, List<Integer> items) {
        mContext = context;
        mCommentIds = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item_comment, parent, false);
            return new CommentItemHolder(v);

    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final Integer itemId = mCommentIds.get(position);
            final CommentItemHolder commentItemHolder = (CommentItemHolder) holder;
        commentItemHolder.mProgressBar.setVisibility(View.VISIBLE);
        NetworkAdapter.getInstance().getDetails(mContext,String.valueOf(itemId), new ArticleResponseListener() {
            @Override
            public void onSuccess(Article result) {
                commentItemHolder.mProgressBar.setVisibility(View.INVISIBLE);

                if (result.getTime() != null) {
                    commentItemHolder.txtTime.setText(TimeUtils.getTimeDifferenceToPresent(result.getTime()));
                }
                if (result.getBy() != null) {
                    commentItemHolder.txtAuthor.setText(mContext.getString(R.string.author).concat(" ").concat(result.getBy()));
                }
                if (result.getText() != null) {
                    commentItemHolder.txtComment.setText(Html.fromHtml(result.getText()));
                }
                    if(result.getKids().size() > 0){
                        populateLatestReply(commentItemHolder.mRepliesLayout,result.getKids().get(0));
                    }
            }

            @Override
            public void onFailure() {
                commentItemHolder.mProgressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void populateLatestReply(LinearLayout repliesLayout, Integer child) {
        final View childLayout = LayoutInflater.from(mContext).inflate(R.layout.view_item_comment, null, false);
        TextView latestCommentlabel = new TextView(mContext);
        latestCommentlabel.setText(mContext.getString(R.string.latest_reply));
        latestCommentlabel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        repliesLayout.addView(latestCommentlabel);
        repliesLayout.addView(childLayout);
        childLayout.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        NetworkAdapter.getInstance().getDetails(mContext,String.valueOf(child), new ArticleResponseListener() {
            @Override
            public void onSuccess(Article result) {
                childLayout.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

                if (result.getTime() != null) {
                    ((TextView)childLayout.findViewById(R.id.tv_item_time)).setText(TimeUtils.getTimeDifferenceToPresent(result.getTime()));
                }
                if (result.getBy() != null) {
                    ((TextView)childLayout.findViewById(R.id.tv_item_author)).setText(mContext.getString(R.string.author).concat(" ").concat(result.getBy()));
                }
                if (result.getText() != null) {
                    ((TextView)childLayout.findViewById(R.id.tv_item_comment)).setText(Html.fromHtml(result.getText()));
                }

            }

            @Override
            public void onFailure() {
                childLayout.findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void update(List<Integer> newData){
        mCommentIds.clear();
        mCommentIds.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCommentIds.size();
    }


    class CommentItemHolder extends RecyclerView.ViewHolder {
        TextView txtAuthor;
        TextView txtTime;
        TextView txtComment;
        ProgressBar mProgressBar;
        LinearLayout mRepliesLayout;

        public CommentItemHolder(View itemView) {
            super(itemView);
            this.txtAuthor = (TextView) itemView.findViewById(R.id.tv_item_author);
            this.txtTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            this.txtComment = (TextView) itemView.findViewById(R.id.tv_item_comment);
            this.mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            this.mRepliesLayout = (LinearLayout)itemView.findViewById(R.id.latestReply);


        }
    }


}


