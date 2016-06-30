package model.test.hackernews.network;

import java.util.List;

import model.test.hackernews.Utils.Article;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vijayakumara on 30/6/16.
 */
public interface ApiEndPointInterface {


    @GET("{stories_endpoint}")
    Call<List<Integer>> getStories(@Path("stories_endpoint") String endPoint);

    @GET("item/{id}.json?print=pretty")
    Call<Article> getArticleDetails(@Path("id") String itemId);
}
