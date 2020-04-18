package Serializers;

import Models.BaseModel;
import org.json.JSONObject;

import java.util.SortedMap;
import java.util.TreeMap;

public abstract class Serializer {
    protected BaseModel obj;

    public Serializer(Object object){
        if (object != null) {
            this.obj = (BaseModel) object;
        } else {
            this.obj = null;
        }
    }

    public JSONObject data(){
        SortedMap<String, Object> map = new TreeMap<>();
        map.put("id", this.obj.id);
        return new JSONObject(map);
    };
}
