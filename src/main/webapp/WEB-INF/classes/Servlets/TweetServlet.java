package Servlets;

import Exceptions.CreateObjectException;
import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Exceptions.ParseException;
import Models.Tweet;
import Serializers.TweetSerializer;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TweetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long tweetId;
        try {
            tweetId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            Tweet tweet = Tweet.objects.get(tweetId);
            JSONObject data = new TweetSerializer(tweet).data();
            String responseData = data.toString();

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
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
        long tweetId;
        try {
            tweetId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String responseData;
        try {
            Tweet deleted_tweet = Tweet.objects.delete(tweetId);
            JSONObject data = new TweetSerializer(deleted_tweet).data();
            responseData = data.toString();
        } catch (DoesNotExist e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseData = e.getMessage();
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().print(responseData);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long tweetId;
        try {
            tweetId = Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String configStr = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        JSONObject obj;
        try {
            obj = new JSONObject(configStr);
        } catch (JSONException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Map<String, Object> config = obj.toMap();

        Tweet updated_tweet;
        try {
            updated_tweet = Tweet.objects.update(tweetId, config);
        } catch (DoesNotExist e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (IllegalAccessException | NoSuchFieldException | CreateObjectException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getOutputStream().print(new TweetSerializer(updated_tweet).data().toString());
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

        Tweet createdTweet;
        try {
            createdTweet = Tweet.objects.create(config);
        } catch (NoSuchFieldException | ClassNotFoundException | ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");

        JSONObject serializedTweet = new TweetSerializer(createdTweet).data();

        response.getOutputStream().print(serializedTweet.toString());
    }
}
