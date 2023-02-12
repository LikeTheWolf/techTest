package com.thevirtugroup.postitnote.repository;


import com.thevirtugroup.postitnote.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private User defaultUser;

    public UserRepository() {
        defaultUser = new User();
        defaultUser.setId(999L);
        defaultUser.setName("Johnny Tango");
        defaultUser.setPassword("password");
        defaultUser.setUsername("user");
    }

    public User findUserByUsername(String username){
        if ("user".equals(username)){
            return defaultUser;
        }
        return null;
    }

    public User findById(Long id){
        if (defaultUser.getId().equals(id)){
            return defaultUser;
        }
        return null;
    }
}
