package Servlets;

import Models.Tweet;
import Serializers.TweetListSerializer;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TweetListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Integer> pagination_indexes = get_pagination_indexes(request);
        int paginate_from = pagination_indexes.get(0);
        int paginate_to = pagination_indexes.get(1);

        List<Tweet> tweetList = Tweet.objects.all();

        List<Tweet> paginated_tweets = tweetList.subList(paginate_from, paginate_to);

        makeResponse(response, paginated_tweets);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String configStr = buffer.toString();

        JSONObject obj = new JSONObject(configStr);
        Map<String, Object> config = obj.toMap();

        List<Integer> pagination_indexes = get_pagination_indexes(request);
        int paginate_from = pagination_indexes.get(0);
        int paginate_to = pagination_indexes.get(1);

        List<Tweet> tweetList = Tweet.objects.filter(config);

        List<Tweet> paginated_tweets = tweetList.subList(paginate_from, paginate_to);

        makeResponse(response, paginated_tweets);
    }

    List<Integer> get_pagination_indexes(HttpServletRequest request){
        String paginate_from_str = request.getParameter("paginate_from");
        String paginate_to_str = request.getParameter("paginate_step");

        int paginate_from;
        int paginate_to;
        if (paginate_from_str.isEmpty()){
            paginate_from = 0;
        } else {
            paginate_from = Integer.parseInt(paginate_from_str);
        }

        if (paginate_to_str.isEmpty()){
            paginate_to = paginate_from + 10;
        } else {
            paginate_to = Integer.parseInt(paginate_to_str);
        }

        return new ArrayList<Integer>(){{
            add(paginate_from);
            add(paginate_to);
        }};
    }

    void makeResponse(HttpServletResponse response, List<Tweet> paginated_tweets) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        List<JSONObject> tweets = new TweetListSerializer(paginated_tweets).data();
        String data = tweets.toString();

        response.getOutputStream().print(data);
    }
}
