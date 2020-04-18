package Managers;

import Models.User;
import ObjectFactory.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class UserManager extends Manager<User>{
    protected UserFactory factory;
    protected List<User> collection;

    public UserManager(){
        super(new ArrayList<User>(){
            {
                add(new User("user 1", ""));
                add(new User("user 2", ""));
                add(new User("user 3", ""));
                add(new User("user 4", ""));
                add(new User("user 5", ""));
            }
        }, new UserFactory());
    }
}
