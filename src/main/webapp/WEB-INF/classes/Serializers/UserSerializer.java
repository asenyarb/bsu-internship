package Serializers;

import Models.User;
import org.json.JSONObject;


public class UserSerializer extends Serializer {
    User user;

    public UserSerializer(User user){
        super(user);
        this.user = user;
    }

    @Override
    public JSONObject data() {
        JSONObject fieldsMap = super.data();
        fieldsMap.put("userPhoto", this.user.photoPath);
        fieldsMap.put("userName", this.user.name);
        return fieldsMap;
    }
}
