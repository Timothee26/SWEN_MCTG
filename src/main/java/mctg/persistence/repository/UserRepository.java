package mctg.persistence.repository;

import mctg.model.User;

import java.util.Collection;

public interface UserRepository {
    User findByUsername(String username);
    Collection<User> findAllUser();
    void registerUpload(User user);
}
