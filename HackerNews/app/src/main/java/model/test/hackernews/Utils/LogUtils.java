package model.test.hackernews.Utils;

import android.util.Log;

import model.test.hackernews.BuildConfig;

/**
 * Created by vijayakumara on 30/6/16.
 */
public class LogUtils {

    public static void LOGI(String tag,String message){
        if (BuildConfig.DEBUG){
            Log.i(tag,message);
        }
    }
}
