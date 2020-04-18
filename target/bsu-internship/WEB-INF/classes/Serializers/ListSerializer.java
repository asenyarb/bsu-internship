package Serializers;

import org.json.JSONObject;

import java.util.List;

public abstract class ListSerializer {
    abstract public List<JSONObject> data();
}
