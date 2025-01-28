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
    public void findAllUserTest() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:register_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);
        Collection<User> testuser = userRepository.findAllUser(user.getUsername(), user.getPassword());
        assertEquals(1, testuser.size());
        assertEquals(username, testuser.iterator().next().getUsername());
        assertEquals(password, testuser.iterator().next().getPassword());
    }

    @Test
    public void findAllUserTestNotExist() {
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:register_user.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        Collection<User> testuser = userRepository.findAllUser(user.getUsername(), user.getPassword());
        assertEquals(null, testuser);
    }


    @Test
    public void findInLoginTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);
        userRepository.login(user);
        Collection<User> testuser = userRepository.findInLogin(user.getUsername());
        assertEquals(1, testuser.size());
        assertEquals(username, testuser.iterator().next().getUsername());
        assertEquals("test-mtcgToken", testuser.iterator().next().getToken());
    }

    @Test
    public void findInLoginTestNotLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);
        Collection<User> testuser = userRepository.findInLogin(user.getUsername());
        assertEquals(null, testuser);
    }


    @Test
    public void loginNotRegistratedTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            userRepository.login(user);
        });

        assertTrue(exception.getMessage().contains("User test does not exists"));
    }



    @Test
    public void loginTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);
        User testuser = userRepositoryTest.getUser();
        userRepository.login(testuser);

        User loginUser = userRepositoryTest.login();

        assertEquals(username, loginUser.getUsername());
        assertEquals("test-mtcgToken", loginUser.getToken());
    }


    @Test
    public void loginTestFail(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String password = "test";
        User user = User.builder().username(username).password(password).build();
        userRepository.registerUpload(user);
        User testuser = userRepositoryTest.getUser();
        userRepository.login(testuser);

        User loginUser = userRepositoryTest.login();

        assertEquals(username, testuser.getUsername());
        assertNotEquals("test-mtgToken", loginUser.getToken());

    }



}
