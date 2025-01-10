package mctg.persistence.repository;

import mctg.model.User;
import mctg.model.UserData;

import java.util.Collection;
import java.util.List;

/**
 * created functions neaded to handle database
 */
public interface UserRepository {
    Collection<User> findInLogin(String username);
    Collection<User> findAllUser(String username);
    String login(User user);
    void registerUpload(User user);
    List<String> getData(String token, String username);
    List<String> editData(String token, UserData data, String username);
}
