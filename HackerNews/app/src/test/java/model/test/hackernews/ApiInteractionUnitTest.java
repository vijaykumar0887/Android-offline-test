package model.test.hackernews;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import model.test.hackernews.Utils.Article;
import model.test.hackernews.Utils.Constants;
import model.test.hackernews.network.ApiEndPointInterface;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by vijayakumara on 3/7/16.
 */
public class ApiInteractionUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void testGetStoriesAPI() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody("[23445,24356546,235356]"));

        ApiEndPointInterface service = retrofit.create(ApiEndPointInterface.class);

        Call<List<Integer>> call = service.getStories(Constants.TOP_STORIES_ENDPOINT);
        assertTrue(call.execute() != null);

        //Finish web server
        mockWebServer.shutdown();
    }


    @Test
    public void testGetArticleAPI() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "  \"by\" : \"norvig\",\n" +
                "  \"id\" : 2921983,\n" +
                "  \"kids\" : [ 2922097, 2922429, 2924562, 2922709, 2922573, 2922140, 2922141 ],\n" +
                "  \"parent\" : 2921506,\n" +
                "  \"text\" : \"Aw shucks, guys ... you make me blush with your compliments.<p>Tell you what, Ill make a deal: I'll keep writing if you keep reading. K?\",\n" +
                "  \"time\" : 1314211127,\n" +
                "  \"type\" : \"comment\"\n" +
                "}"));

        ApiEndPointInterface service = retrofit.create(ApiEndPointInterface.class);

        Call<Article> call = service.getArticleDetails("35677");
        assertTrue(call.execute() != null);

        //Finish web server
        mockWebServer.shutdown();
    }
}
