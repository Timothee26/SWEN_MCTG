package MonsterCardTradingGameTest.persistence;

import mctg.model.User;

import java.util.Collection;
import java.util.List;

public interface UserRepositoryTest {
    User getUser();
    User login();
    String getUsernameTest(String token);
    User getEditStats(String username);
    User getUpdateStatsTest(String username);

}
