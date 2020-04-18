package Models;

import Managers.UserManager;

import java.util.Map;

public class User extends BaseModel {
    private static long lastId = 1;
    public static UserManager objects = new UserManager();

    public String name;
    public String photoPath;

    public User(String name, String photoPath){
        super(lastId);
        lastId += 1;
        this.name = name;
        this.photoPath = photoPath;
    }

    public User(Map<String, Object> rawUser){
        super(lastId);
        lastId += 1;
        this.name = (String) rawUser.get("name");
        this.photoPath = (String) rawUser.get("photoPath");
    }

}
