package Managers;

import Exceptions.DoesNotExist;
import Exceptions.MultipleObjectsReturned;
import Models.BaseModel;
import Settings.Settings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<T> delete(Long id) throws DoesNotExist{
        Map<String, Object> config = new HashMap<String, Object>(){{put("id", id);}};
        List<T> objectsToDelete = this.filter(config);

        if (objectsToDelete.size() == 0){
            throw new DoesNotExist("Object matching query does not exist");
        }

        collection.removeAll(objectsToDelete);

        return objectsToDelete;
    }

    public List<T> all(){
        return collection;
    }

    public T create(Map<String, Object> rawObj){
        T instance = (T)factory.create(rawObj);
        collection.add(instance);
        return instance;
    }
}
