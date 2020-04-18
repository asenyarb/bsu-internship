package Servlets;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Models.Tweet;
import Serializers.TweetListSerializer;
import Serializers.TweetSerializer;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TweetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long tweetId = Long.parseLong(request.getParameter("id"));
        try {
            Tweet tweet = Tweet.objects.get(tweetId);
            JSONObject data = new TweetSerializer(tweet).data();
            String responseData = data.toString();

            response.setContentType("application/json");
            response.getOutputStream().print(responseData);

        } catch (MultipleObjectsReturned e) {
            response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
            response.getOutputStream().print(e.getMessage());

        } catch (DoesNotExist e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getOutputStream().print(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Long tweetId = Long.parseLong(request.getParameter("id"));
        String responseData;
        try {
            List<Tweet> deleted_tweets = Tweet.objects.delete(tweetId);
            List<JSONObject> data = new TweetListSerializer(deleted_tweets).data();
            if (data.size() == 1) {
                responseData = data.get(0).toString();
            } else {
                responseData = data.toString();
            }
        } catch (DoesNotExist e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseData = e.getMessage();
        }

        response.setContentType("application/json");
        response.getOutputStream().print(responseData);
    }
}
