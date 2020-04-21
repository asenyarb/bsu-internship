package Managers;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Models.Tweet;
import Models.User;
import ObjectFactory.TweetFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                } catch (MultipleObjectsReturned | DoesNotExist multipleObjectsReturned) {
                    System.out.println("EXCEPTION");
                }
            }
        }, new TweetFactory());
    }
}
