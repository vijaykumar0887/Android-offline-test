package model.test.hackernews.network;

import model.test.hackernews.Utils.Article;

/**
 * Created by vijayakumara on 30/6/16.
 */
public interface ArticleResponseListener {

    void onSuccess(Article result);
    void onFailure();
}
