package Serializers;

import Models.Tweet;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class TweetListSerializer extends ListSerializer{
    List<Tweet> tweets;

    public TweetListSerializer(List<Tweet> tweets){
        this.tweets = tweets;
    }

    @Override
    public List<JSONObject> data() {
        List<JSONObject> data = new LinkedList<>();

        for (Tweet tweet: tweets){
            data.add(new TweetSerializer(tweet).data());
        }

        return data;
    }
}
