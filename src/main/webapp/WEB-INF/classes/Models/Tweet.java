package Models;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Exceptions.ParseException;
import Managers.TweetManager;
import Serializers.Serializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        this.createdAt = LocalDateTime.now().withNano(0);

        this.text = "";
        this.photos = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.user = null;
    }

    public Tweet(Long id){
        super(id);
        this.createdAt = null;

        this.text = "";
        this.photos = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.user = null;

    }

    public Tweet(Map<String, Object> rawUser) throws NoSuchFieldException, ClassNotFoundException, ParseException {
        super(lastId);
        lastId += 1;
        if (!rawUser.containsKey("createdAt")){
            this.createdAt = LocalDateTime.now().withNano(0);
        } else {
            this.createdAt = (LocalDateTime) Serializer.parseValue(
                    getClass().getField("createdAt"), rawUser.get("createdAt")
            );
        }
        this.text = (String) rawUser.get("text");
        this.photos = (List<String>) rawUser.get("photos");
        this.tags = (List<String>) rawUser.get("tags");
        this.likes = (List<String>) rawUser.get("likes");
        this.user = (User) Serializer.parseValue(
                getClass().getField("user"),
                rawUser.get("user")
        );
    }

    public Tweet(Long id, Map<String, Object> rawUser){
        super(id);
        if (!rawUser.containsKey("createdAt")){
            this.createdAt = LocalDateTime.now().withNano(0);
        }
        try{
            this.createdAt = (LocalDateTime) Serializer.parseValue(
                    getClass().getField("createdAt"), rawUser.get("createdAt")
            );
        } catch (Exception e){
            this.createdAt = null;
        }
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
        this.createdAt = LocalDateTime.now().withNano(0);
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

    public void save(){
        try {
            if (objects.replaceInCollection(this.id, this) == null){
                throw new RuntimeException("Error while saving tweet");
            }
        } catch (DoesNotExist e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(){
        try{
            objects.delete(this.id);
        } catch (DoesNotExist e){
            throw new RuntimeException(e.getMessage());
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
