package Models;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Managers.TweetManager;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tweet extends BaseModel implements Cloneable{
    private static long lastId = 1;
    public static TweetManager objects = new TweetManager();

    public LocalDateTime createdAt;
    public String text;
    public List<String> photos;
    public List<String> tags;
    public List<String> likes;
    public User user;

    public Tweet() {
        super(lastId);
        lastId += 1;
        this.createdAt = LocalDateTime.now();
    }

    public Tweet(Map<String, Object> rawUser){
        super(lastId);
        lastId += 1;
        this.createdAt = (LocalDateTime) rawUser.get("createdAt");
        this.text = (String) rawUser.get("text");
        this.photos = (List<String>) rawUser.get("photos");
        this.tags = (List<String>) rawUser.get("tags");
        this.likes = (List<String>) rawUser.get("likes");
        this.user = (User) rawUser.get("user");
    }

    public Tweet(String text, List<String> photos, List<String> tags, List<String> likes, User user){
        super(lastId);
        lastId += 1;
        this.text = text;
        this.photos = photos;
        this.tags = tags;
        this.likes = likes;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public Boolean matches(Map<String, Object> config) throws NoSuchFieldException, IllegalAccessException {
        try {
            Map<String, Object> newConfig = handleUserFields(config);
            try {
                return super.matches(newConfig);
            } catch (ClassNotFoundException e){
                return false;
            }
        } catch (MultipleObjectsReturned | DoesNotExist e){
            return false;
        }

    }

    private static Map<String, Object> handleUserFields(Map<String, Object> config) throws MultipleObjectsReturned, DoesNotExist {
        if (config.containsKey("user")) {
            Map<String, Object> userConfig = (Map<String, Object>) config.get("user");

            User user;
            if (userConfig.containsKey("id")){
                user = User.objects.get((Long)userConfig.get("id"));
            } else {
                user = User.objects.get(userConfig);
            }
            config.put("user", user);
        }
        return config;
    }
}
