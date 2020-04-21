package Managers;

import Exceptions.CreateObjectException;
import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Exceptions.ParseException;
import Models.BaseModel;
import Serializers.Serializer;
import Settings.Settings;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
                        return obj.matches(config);
                    } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException e) {
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
        T objectToUpdate = collection.get(objectIndex);
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
}
