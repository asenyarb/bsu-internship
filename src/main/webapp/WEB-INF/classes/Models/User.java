package Models;

import Exceptions.DoesNotExist;
import Managers.UserManager;

import java.util.Map;

public class User extends BaseModel {
    private static long lastId = 1;
    public static UserManager objects = new UserManager();

    public String name;
    public String photoPath;

    public User(){
        super(lastId);
        lastId += 1;
        this.name = "";
        this.photoPath = "";
    }

    public User(Long id){
        super(id);
        this.name = "";
        this.photoPath = "";
    }

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

    public User(Long id, Map<String, Object> rawUser){
        super(id);
        this.name = (String) rawUser.get("name");
        this.photoPath = (String) rawUser.get("photoPath");
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
}
