package ObjectFactory;
import Models.User;

import java.util.Map;

public class UserFactory extends ObjectFactory{
    @Override
    public Object create(Map<String, Object> rawObj){
        return new User(rawObj);
    }

    @Override
    public Object create(Long id, Map<String, Object> rawObj){
        return new User(id, rawObj);
    }

    @Override
    public Object create(Long id) { return new User(id); }
}
