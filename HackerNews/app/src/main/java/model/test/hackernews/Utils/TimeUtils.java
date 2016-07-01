package model.test.hackernews.Utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by vijayakumara on 1/7/16.
 */
public class TimeUtils {

    public static String getTimeDifferenceToPresent(Long timeInUnixFormat){
        String timeDiff = "";
        Date publishedTime = new Date(timeInUnixFormat*1000);
        Date present = new Date();

        if(TimeUnit.MILLISECONDS.toDays(present.getTime() - publishedTime.getTime()) > 0 ){
            timeDiff = TimeUnit.MILLISECONDS.toDays(present.getTime() - publishedTime.getTime())  + " days ago";
        }else if(TimeUnit.MILLISECONDS.toHours(present.getTime() - publishedTime.getTime()) > 0 ){
            timeDiff = TimeUnit.MILLISECONDS.toHours(present.getTime() - publishedTime.getTime() ) + " hours ago";
        }else if(TimeUnit.MILLISECONDS.toMinutes(present.getTime() - publishedTime.getTime()) > 0 ){
            timeDiff = TimeUnit.MILLISECONDS.toMinutes(present.getTime() - publishedTime.getTime() ) + " minutes ago";
        }

        return timeDiff;

    }
}
