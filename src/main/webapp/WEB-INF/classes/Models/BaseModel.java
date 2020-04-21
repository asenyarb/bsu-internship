package Models;

import Exceptions.ParseException;
import Serializers.Serializer;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseModel {
    public Long id;

    public BaseModel(Long id){
        this.id = id;
    }

    public Boolean matches(Map<String, Object> config) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        if (config.isEmpty())
            return false;
        try {
            for (String fieldInConfig : config.keySet()) {
                Field field = this.getClass().getField(fieldInConfig);
                Object fieldInConfigValue = config.get(fieldInConfig);

                boolean withComparison = fieldInConfigValue instanceof Map &&
                        ((Map<String, String>) fieldInConfigValue).containsKey("comparison");

                //-------------------------------------------------------------------
                if (field.getType().equals(Class.forName("Models.User"))){
                    User user = (User)field.get(this);
                    boolean matches;
                    if (fieldInConfigValue instanceof Map) {
                        Map<String, Object> userConfig = (Map<String, Object>) fieldInConfigValue;
                        matches = user.matches(userConfig);
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
                    ArrayList<String> fieldValues = (ArrayList<String>) (field.get(this));
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
                    List<String> fieldValues = (ArrayList<String>) field.get(this);
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
                    LocalDateTime fieldDate = (LocalDateTime)field.get(this);
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
                    LocalDateTime fieldDate = (LocalDateTime)field.get(this);
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
                    String fieldValue = (String) field.get(this);
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
                    Object fieldValue = field.get(this);
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
                    LocalDateTime fieldDate = (LocalDateTime)field.get(this);
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
                if (!field.get(this).equals(fieldInConfigValue)){
                    return false;
                }
            }
        } catch (ClassCastException | ParseException e){
            return false;
        }

        return true;
    }
}
