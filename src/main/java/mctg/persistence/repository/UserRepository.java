package mctg.persistence.repository;

import mctg.model.User;

import java.util.Collection;
import java.util.List;

/**
 * created functions neaded to handle database
 */
public interface UserRepository {
    User findByUsername(String username);
    Collection<User> findAllUser(String username);
    void login(User user);
    void registerUpload(User user);
    List<String> getData(String token, String username);
    List<String> editData(String token, String data, String username);
}
