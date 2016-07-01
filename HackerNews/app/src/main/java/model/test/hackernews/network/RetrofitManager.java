package model.test.hackernews.network;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import model.test.hackernews.Utils.Constants;
import model.test.hackernews.Utils.NetworkCheckUtility;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Single ton class to handle Retrofit connection
 */
public class RetrofitManager {
    private static RetrofitManager ourInstance = new RetrofitManager();
    private Retrofit mRetrofit;
    private static  Context mContext;

    public static RetrofitManager getInstance() {
        return ourInstance;
    }

    private RetrofitManager() {
    }

    public Retrofit getRetroObject(Context context){

        mContext = context;
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }

        return mRetrofit;
    }




    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkCheckUtility.isNetworkAvailable(mContext)) {
                int maxAge = 180;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24; // 1 day caching
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
        private static OkHttpClient getClient(){
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

            //setup cache
            File httpCacheDirectory = new File(mContext.getCacheDir(), "cachedData");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory,cacheSize);
            clientBuilder.cache(cache);
        return clientBuilder.build();

    }
}
