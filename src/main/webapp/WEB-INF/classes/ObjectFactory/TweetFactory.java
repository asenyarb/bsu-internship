package ObjectFactory;

import Exceptions.ParseException;
import Models.Tweet;

import java.util.Map;

public class TweetFactory extends ObjectFactory{
    @Override
    public Object create(Map<String, Object> rawObj) throws NoSuchFieldException, ClassNotFoundException, ParseException {
        return new Tweet(rawObj);
    }

    @Override
    public Object create(Long id, Map<String, Object> rawObj){
        return new Tweet(id, rawObj);
    }

    @Override
    public Object create(Long id){ return new Tweet(id); }
}
