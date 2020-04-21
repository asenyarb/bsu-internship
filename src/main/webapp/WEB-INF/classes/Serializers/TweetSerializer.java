package Serializers;

import Models.Tweet;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;

public class TweetSerializer extends Serializer{
    Tweet tweet;
    public TweetSerializer(Tweet tweet){
        super(tweet);
        this.tweet = tweet;
    }

    @Override
    public JSONObject data(){
        JSONObject fieldsMap = super.data();

        fieldsMap.put("user", new UserSerializer(this.tweet.user).data());

        fieldsMap.put("createdAt", this.tweet.createdAt.format(DateTimeFormatter.ofPattern("HH:mm:ss, dd MMM yyyy")));
        fieldsMap.put("text", this.tweet.text);
        fieldsMap.put("photos", this.tweet.photos.toString());
        fieldsMap.put("tags", this.tweet.tags.toString());

        return fieldsMap;
    }
}
