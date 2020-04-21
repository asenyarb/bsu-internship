package Servlets;

import Models.Tweet;
import Serializers.TweetListSerializer;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class TweetListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Integer> pagination_indexes = get_pagination_indexes(request);
        int paginate_from = pagination_indexes.get(0);
        int paginate_to = pagination_indexes.get(1);

        List<Tweet> tweetList = Tweet.objects.all();

        List<Tweet> paginated_tweets = paginate_tweets(paginate_from, paginate_to, tweetList);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        List<JSONObject> tweets = new TweetListSerializer(paginated_tweets).data();
        String data = tweets.toString();

        response.getOutputStream().print(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String configStr = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        JSONObject obj;
        try {
            obj = new JSONObject(configStr);
        } catch (JSONException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Map<String, Object> config = obj.toMap();

        List<Integer> pagination_indexes = get_pagination_indexes(request);
        int paginate_from = pagination_indexes.get(0);
        int paginate_to = pagination_indexes.get(1);
        List<Tweet> tweetList = Tweet.objects.filter(config);
        List<Tweet> paginated_tweets = paginate_tweets(paginate_from, paginate_to, tweetList);
        makeResponse(response, paginated_tweets);
    }

    List<Integer> get_pagination_indexes(HttpServletRequest request){
        int paginate_from;
        int paginate_to;

        try {
            String paginate_from_str = request.getParameter("paginate_from");
            paginate_from = Integer.parseInt(paginate_from_str);
        } catch (NumberFormatException | NoSuchElementException e){
            paginate_from = 0;
        }

        try {
            String paginate_to_str = request.getParameter("paginate_step");
            paginate_to = Integer.parseInt(paginate_to_str);
        } catch (NumberFormatException | NoSuchElementException e){
            paginate_to = paginate_from + 10;
        }

        int finalPaginate_from = paginate_from;
        int finalPaginate_to = paginate_to;
        return new ArrayList<Integer>(){{
            add(finalPaginate_from);
            add(finalPaginate_to);
        }};
    }

    List<Tweet> paginate_tweets(int paginate_from, int paginate_to, List<Tweet> tweetList){
        List<Tweet> paginated_tweets;
        if (paginate_from >= tweetList.size()){
            paginated_tweets = new ArrayList<>();
        } else if (paginate_to > tweetList.size()){
            paginated_tweets = tweetList.subList(paginate_from, tweetList.size());
        } else {
            paginated_tweets = tweetList.subList(paginate_from, paginate_to);
        }
        return paginated_tweets;
    }

    void makeResponse(HttpServletResponse response, List<Tweet> paginated_tweets) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        List<JSONObject> tweets = new TweetListSerializer(paginated_tweets).data();
        String data = tweets.toString();

        response.getOutputStream().print(data);
    }
}
