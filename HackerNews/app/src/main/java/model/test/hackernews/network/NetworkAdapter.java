package model.test.hackernews.network;

import android.content.Context;

import java.util.List;

import model.test.hackernews.Utils.Article;
import model.test.hackernews.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vijayakumara on 30/6/16.
 */
public class NetworkAdapter {
    private static final String TAG = NetworkAdapter.class.getSimpleName();
    private static NetworkAdapter ourInstance = new NetworkAdapter();

    public static NetworkAdapter getInstance() {
        return ourInstance;
    }

    private NetworkAdapter() {

    }


    public void getStories(Context context, String endPoint, final StoriesResponseListener listener){
        ApiEndPointInterface apiService = RetrofitManager.getInstance().getRetroObject(context,false).create(ApiEndPointInterface.class);

        Call<List<Integer>> call = apiService.getStories(endPoint);
        call.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                int statusCode = response.code();
               if(statusCode == Constants.SUCCESS){
                   listener.onSuccess(response.body());
               }else{
                   listener.onFailure();
               }

            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                listener.onFailure();
               // LogUtils.LOGI(TAG,"failure : ");
            }
        });
    }


    public void getDetails(Context context,String itemId, final ArticleResponseListener listener){
        ApiEndPointInterface apiService = RetrofitManager.getInstance().getRetroObject(context,true).create(ApiEndPointInterface.class);

        Call<Article> call = apiService.getArticleDetails(itemId);
        call.enqueue(new Callback<Article>() {
            @Override
            public void onResponse(Call<Article> call, Response<Article> response) {
                int statusCode = response.code();
                if(statusCode == Constants.SUCCESS){
                    listener.onSuccess(response.body());
                }else{
                    listener.onFailure();
                }

            }

            @Override
            public void onFailure(Call<Article> call, Throwable t) {
                listener.onFailure();
                //LogUtils.LOGI(TAG," stories failure");
            }
        });
    }
}
