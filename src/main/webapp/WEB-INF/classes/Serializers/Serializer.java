package Serializers;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Models.BaseModel;
import Models.User;
import Exceptions.ParseException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
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

    public static Object parseValue(Field field, Object value) throws ClassNotFoundException, ParseException {
        Class<?> valueClass = value.getClass();
        if (
                field.getType().equals(Class.forName("java.time.LocalDateTime")) &&
                !valueClass.equals(Class.forName("java.time.LocalDateTime"))
        ){
            // Date in config is represented as String
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss, dd MMM yyyy");
            try {
                return LocalDateTime.parse((String) value, formatter);
            } catch (DateTimeParseException e){
                throw new ParseException("Could not parse date value from provided data");
            }
        }
        if (field.getType().equals(Class.forName("Models.User"))){
            Object id;
            if (
                    valueClass.equals(Class.forName("java.util.Map")) ||
                    valueClass.equals(Class.forName("java.util.HashMap"))
            ){
                id = (((Map<String, Object>)value).get("id"));
            } else {
                id = value;
            }
            Long longId = Long.parseLong(id.toString());

            try {
                return User.objects.get(longId);
            } catch (MultipleObjectsReturned | DoesNotExist e){
                throw new ParseException("Could not parse user value from provided data");
            }
        }
        return value;
    }
}
