package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.UserRepository;
import mctg.persistence.repository.UserRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest {
    static UserRepository userRepository;
    static UserRepositoryTest userRepositoryTest;

    @Test
    public void createUserTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:register_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);

        User testuser = userRepositoryTest.getUser();

        assertEquals(username, testuser.getUsername());
        assertEquals(password, testuser.getPassword());
        assertEquals(20, testuser.getCoins());
        assertEquals(0, testuser.getWins());
        assertEquals(0, testuser.getLosses());
        assertEquals(0, testuser.getTies());
        assertEquals(100, testuser.getElo());
    }

    @Test
    public void createUserTestFalseElo() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:register_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);

        User testuser = userRepositoryTest.getUser();

        assertEquals(username, testuser.getUsername());
        assertEquals(password, testuser.getPassword());
        assertEquals(20, testuser.getCoins());
        assertEquals(0, testuser.getWins());
        assertEquals(0, testuser.getLosses());
        assertEquals(0, testuser.getTies());
        assertNotEquals(0, testuser.getElo());
    }

    @Test
    public void createUserTestFalseCoins() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:register_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);

        User testuser = userRepositoryTest.getUser();

        assertEquals(username, testuser.getUsername());
        assertEquals(password, testuser.getPassword());
        assertNotEquals(0, testuser.getCoins());
        assertEquals(0, testuser.getWins());
        assertEquals(0, testuser.getLosses());
        assertEquals(0, testuser.getTies());
        assertEquals(100, testuser.getElo());
    }


    @Test
    public void loginTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.login(user);

        User testuser = userRepositoryTest.login();

        assertEquals(username, testuser.getUsername());
        assertEquals("test-mtcgToken", testuser.getToken());
    }

    @Test
    public void loginTestFail(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.login(user);

        User testuser = userRepositoryTest.login();

        assertEquals(username, testuser.getUsername());
        assertNotEquals("test-mtgToken", testuser.getToken());

    }

}
