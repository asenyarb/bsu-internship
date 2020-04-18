package Models;

import java.lang.reflect.Field;
import java.util.Map;

public class BaseModel {
    public Long id;

    public BaseModel(Long id){
        this.id = id;
    }

    public Boolean matches(Map<String, Object> config) throws NoSuchFieldException, IllegalAccessException {
        if (config.isEmpty())
            return false;
        for (String fieldStr: config.keySet()){
            Field field = this.getClass().getField(fieldStr);
            if (!field.get(this).equals(config.get(fieldStr))){
                return false;
            }
        }

        return true;
    }
}
