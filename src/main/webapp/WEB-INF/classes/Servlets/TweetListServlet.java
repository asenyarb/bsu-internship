package Servlets;

import Models.Tweet;
import Serializers.TweetListSerializer;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class TweetListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String configStr = request.getInputStream().toString();

        //JSONObject obj = new JSONObject(configStr);

        //Map<String, Object> config = obj.toMap();

        List<Tweet> tweetList = Tweet.objects.all();//filter(config);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        List<JSONObject> tweets = new TweetListSerializer(tweetList).data();
        String data = tweets.toString();

        response.getOutputStream().print(data);
    }
}
