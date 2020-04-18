package ObjectFactory;

import Models.Tweet;

import java.util.Map;

public class TweetFactory extends ObjectFactory{
    @Override
    public Object create(Map<String, Object> rawObj){
        return new Tweet(rawObj);
    }
}
