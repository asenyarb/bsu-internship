package Managers;

import Exceptions.CreateObjectException;
import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Exceptions.ParseException;
import Models.BaseModel;
import Models.User;
import Serializers.Serializer;
import Settings.Settings;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import ObjectFactory.ObjectFactory;


public class Manager<T extends BaseModel> {
    protected List<T> collection;
    protected ObjectFactory factory;

    public Manager(List<T> collection, ObjectFactory factory){
        this.collection = collection;
        this.factory = factory;
    }

    public T get(Long id) throws MultipleObjectsReturned, DoesNotExist {
        List<T> result = collection
                .stream()
                .filter(obj -> obj.id.equals(id))
                .collect(Collectors.toList());
        if (result.size() > 1){
            throw new MultipleObjectsReturned("Multiple objects were returned");
        }
        if (result.size() == 0){
            throw new DoesNotExist("Object matching query does not exist");
        }
        return result.get(0);
    }

    public T get(Map<String, Object> config) throws MultipleObjectsReturned, DoesNotExist{
        List<T> result = this.filter(config);
        if (result.size() > 1){
            throw new MultipleObjectsReturned(String.format("Got multiple objects with config = %s.", config));
        }
        if (result.size() == 0){
            throw new DoesNotExist(String.format("Tweet matching query config = %s does not exist.", config));
        }
        return result.get(0);
    }

    public List<T> filter(Map<String, Object> config){
        return this.collection.stream().filter(
                obj -> {
                    try {
                        Class<?> objectClass = obj.getClass();
                        if (objectClass.equals(Class.forName("Models.Tweet"))){
                            return TweetManager.matches(obj, config);
                        } else if (objectClass.equals(Class.forName("Models.User"))){
                            return UserManager.matches(obj, config);
                        } else {
                            return Manager.matches(obj, config);
                        }
                    } catch (ClassNotFoundException e) {
                        if (Settings.DEBUG) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                }
        ).collect(Collectors.toList());
    }

    public List<T> all(){
        return collection;
    }

    public T create(Map<String, Object> rawObj) throws NoSuchFieldException, ClassNotFoundException, ParseException {
        T instance = (T)factory.create(rawObj);
        collection.add(instance);
        return instance;
    }

    public T update(Long id, Map<String, Object> config) throws NoSuchFieldException, ClassCastException, IllegalAccessException, DoesNotExist, CreateObjectException {
        int objectIndex = getObjectIndexById(id);
        Set<String> configKeys = config.keySet();

        T objectToUpdate;
        try {
            objectToUpdate = collection.get(objectIndex);
        } catch (java.lang.ArrayIndexOutOfBoundsException e){
            throw new DoesNotExist("Object with provided id does not exist");
        }

        // Field.set throws IllegalArgumentException so making workaround
        Map<String, Object> newObjectConfig = new HashMap<>();

        for (Field objectField: objectToUpdate.getClass().getFields()){
            if (configKeys.contains(objectField.getName())){
                Object valueFromConfig = config.get(objectField.getName());
                newObjectConfig.put(objectField.getName(), valueFromConfig);
            } else {
                Object oldObjectValue = objectField.get(objectToUpdate);
                newObjectConfig.put(objectField.getName(), oldObjectValue);
            }
        }
        T newObject;
        try {
            newObject = (T) factory.create(objectToUpdate.id, newObjectConfig);
        } catch (Exception e){
            throw new CreateObjectException("Couldn't create update object using provided data");
        }

        return this.replaceInCollection(objectToUpdate.id, newObject);
    }

    public T replaceInCollection(Long id, T object) throws DoesNotExist {
        if (!id.equals(object.id)){
            return null;
        }
        int objectIndex = getObjectIndexById(id);
        if (objectIndex == -1){
            throw new DoesNotExist("Object matching query does not exist");
        }
        collection.set(objectIndex, object);
        return object;
    }

    public T delete(Long objectId) throws DoesNotExist {
        int objectIndex = getObjectIndexById(objectId);
        if (objectIndex == -1) {
            throw new DoesNotExist("Object matching query not found");
        }
        T deletedObject = collection.get(objectIndex);
        collection.remove(objectIndex);
        return deletedObject;
    }

    public List<T> delete(Map<String, Object> config) throws DoesNotExist{
        List<T> objectsToDelete = this.filter(config);

        if (objectsToDelete.size() == 0){
            throw new DoesNotExist("Object matching query does not exist");
        }

        collection.removeAll(objectsToDelete);

        return objectsToDelete;
    }

    private int getObjectIndexById(Long id){
        for (int i = 0; i < collection.size(); ++i){
            if (collection.get(i).id.equals(id)){
                return i;
            }
        }
        return -1;
    }

    public static Boolean matches(Object obj, Map<String, Object> config){
        if (config.isEmpty())
            return false;
        try {
            for (String fieldInConfig : config.keySet()) {
                Field field = obj.getClass().getField(fieldInConfig);
                Object fieldInConfigValue = config.get(fieldInConfig);

                boolean withComparison = fieldInConfigValue instanceof Map &&
                        ((Map<String, String>) fieldInConfigValue).containsKey("comparison");

                //-------------------------------------------------------------------
                if (field.getType().equals(Class.forName("Models.User"))){
                    User user = (User)field.get(obj);
                    boolean matches;
                    if (fieldInConfigValue instanceof Map) {
                        Map<String, Object> userConfig = (Map<String, Object>) fieldInConfigValue;
                        matches = UserManager.matches(user, userConfig);
                    } else {
                        matches = user.id.equals(Long.parseLong(fieldInConfigValue.toString()));
                    }
                    if (matches){
                        continue;
                    } else {
                        return false;
                    }
                }

                //-------------------------------------------------------------------

                boolean arrayWithComparisonKey = field.getType().equals(Class.forName("java.util.List")) &&
                        withComparison;

                boolean arrayContains = arrayWithComparisonKey &&
                        ((String) ((Map<String, Object>) fieldInConfigValue).get("comparison")).equals("contains");
                // Check if instance field as List contains all of values
                if (arrayContains) {
                    ArrayList<String> valuesFromConfig = (ArrayList<String>) ((Map<String, Object>) fieldInConfigValue).get("value");
                    ArrayList<String> fieldValues = (ArrayList<String>) (field.get(obj));
                    if (!(fieldValues).containsAll(valuesFromConfig)) {
                        return false;
                    } else {
                        continue;
                    }
                }

                boolean arraySomeOf = arrayWithComparisonKey &&
                        ((String) ((Map<String, Object>) fieldInConfigValue).get("comparison")).equals("some_of");
                // Check if instance field as List contains some of values
                if (arraySomeOf) {
                    List<String> valuesFromConfig = (ArrayList<String>) ((Map<String, Object>) fieldInConfigValue).get("value");
                    List<String> fieldValues = (ArrayList<String>) field.get(obj);
                    if ((valuesFromConfig)
                            .stream()
                            .noneMatch(fieldValues::contains)) {
                        return false;
                    }
                }

                //-----------------------------------------------------

                boolean isDate = field.getType().equals(Class.forName("java.time.LocalDateTime"));

                boolean isDateWithComparison = isDate && withComparison;
                // Check if field date is later
                if (
                        isDateWithComparison &&
                                ((String)(((Map<String, Object>) fieldInConfigValue).get("comparison"))).equals("later than")
                ){
                    LocalDateTime fieldDate = (LocalDateTime)field.get(obj);
                    LocalDateTime dateInConfig = (LocalDateTime)Serializer.parseValue(
                            field,
                            (String)(((Map<String, Object>) fieldInConfigValue).get("value"))
                    );
                    if (!fieldDate.isAfter(dateInConfig)){
                        return false;
                    } else {
                        continue;
                    }
                }
                // Check if field date is before
                if (
                        isDateWithComparison &&
                                ((String)(((Map<String, Object>) fieldInConfigValue).get("comparison"))).equals("earlier that")
                ){
                    LocalDateTime fieldDate = (LocalDateTime)field.get(obj);
                    LocalDateTime dateInconfig = (LocalDateTime) Serializer.parseValue(
                            field,
                            (String)(((Map<String, Object>) fieldInConfigValue).get("value"))
                    );
                    if (!fieldDate.isBefore(dateInconfig)){
                        return false;
                    } else {
                        continue;
                    }
                }

                //---------------------------------------------------------------

                boolean stringContains = field.getType().equals(Class.forName("java.lang.String")) &&
                        withComparison &&
                        ((Map<String, String>) fieldInConfigValue).get("comparison").equals("contains");
                // Check if instance field as string contains value
                if (stringContains) {
                    String fieldValue = (String) field.get(obj);
                    String valueFromConfig = ((Map<String, String>) fieldInConfigValue).get("value");
                    if (!fieldValue.contains(valueFromConfig)) {
                        return false;
                    } else {
                        continue;
                    }
                }
                //-----------------------------------------------------

                boolean checkFieldIn = withComparison &&
                        ((Map<String, Object>) fieldInConfigValue).get("comparison").equals("in");
                // Check if instance field as string contains value
                if (checkFieldIn) {
                    Object fieldValue = field.get(obj);
                    List<Object> valuesFromConfig = (List<Object>)((Map<String, Object>) fieldInConfigValue).get("value");
                    if (!valuesFromConfig.contains(fieldValue)) {
                        return false;
                    } else {
                        continue;
                    }
                }

                //-----------------------------------------------------

                // Simply compare Dates
                if (isDate){
                    LocalDateTime fieldDate = (LocalDateTime)field.get(obj);
                    LocalDateTime dateInConfig = (LocalDateTime) Serializer.parseValue(
                            field,
                            fieldInConfigValue
                    );
                    if (!(fieldDate).isEqual(dateInConfig)){
                        return false;
                    } else {
                        continue;
                    }
                }

                if (field.getType().equals(Long.class)){
                    fieldInConfigValue = Long.parseLong(fieldInConfigValue.toString());
                }

                // Simply compare 2 values
                if (!field.get(obj).equals(fieldInConfigValue)){
                    return false;
                }
            }
        } catch (
                ClassCastException |
                ParseException |
                ClassNotFoundException |
                NoSuchFieldException |
                IllegalAccessException e
        ){
            return false;
        }

        return true;
    }
}
