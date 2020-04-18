package ObjectFactory;
import Models.User;

import java.util.Map;

public class UserFactory extends ObjectFactory{
    @Override
    public Object create(Map<String, Object> rawObj){
        return new User(rawObj);
    }
}
