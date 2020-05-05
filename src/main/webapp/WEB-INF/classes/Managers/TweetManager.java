package Managers;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Models.Tweet;
import Models.User;
import ObjectFactory.TweetFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetManager extends Manager<Tweet>{
    protected TweetFactory factory;
    protected List<Tweet> collection;

    public TweetManager() {
        super(new ArrayList<Tweet>() {
            {
                try {
                    add(
                            new Tweet(
                                    "kjsdhf",
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    User.objects.get(new HashMap<String, Object>() {{
                                        put("name", "user 2");
                                    }})
                            )
                    );
                    add(
                            new Tweet(
                                    "asdasdadsa",
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    User.objects.get(new HashMap<String, Object>() {{
                                        put("name", "user 1");
                                    }})
                            )
                    );
                } catch (MultipleObjectsReturned | DoesNotExist multipleObjectsReturned) {
                    System.out.println("EXCEPTION");
                }
            }
        }, new TweetFactory());
    }

    public static Boolean matches(Object tweet, Map<String, Object> config){
        try {
            Map<String, Object> newConfig = Tweet.handleUserFields(config);
            return Manager.matches(tweet, newConfig);
        } catch (MultipleObjectsReturned | DoesNotExist e){
            return false;
        }

    }
}
