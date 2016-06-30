package model.test.hackernews.network;

import model.test.hackernews.Utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Single ton class to handle Retrofit connection
 */
public class RetrofitManager {
    private static RetrofitManager ourInstance = new RetrofitManager();
    private Retrofit mRetrofit;

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
    }

    public Retrofit getRetroObject(){


        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
