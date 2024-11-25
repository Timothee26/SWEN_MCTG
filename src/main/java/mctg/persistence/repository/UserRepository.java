package mctg.persistence.repository;

import mctg.model.User;

import java.util.Collection;

public interface UserRepository {
    User findByUsername(String username);
    Collection<User> findAllUser(String username);
    void login(User user);
    void registerUpload(User user);
}
