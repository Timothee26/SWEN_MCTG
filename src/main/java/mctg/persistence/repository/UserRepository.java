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
    Collection<User> findAllUser(String username, String password);
    String login(User user);
    void registerUpload(User user);
    User getData(String token, String username);
    List<String> editData(String token, UserData data, String username);
    User getStats(String token);
    User updateStats(String token1,String token2);
    User updateTies(String token1, String token2);
    List<User> getElo(String token);
    String getUsername(String token);

    }

