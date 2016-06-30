package model.test.hackernews.network;

import java.util.List;

/**
 * Created by vijayakumara on 30/6/16.
 */
public interface StoriesResponseListener {

    void onSuccess(List<Integer> results);
    void onFailure();
}
