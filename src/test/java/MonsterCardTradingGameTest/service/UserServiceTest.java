package MonsterCardTradingGameTest.service;

import mctg.model.User;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.UserRepository;
import mctg.persistence.repository.UserRepositoryImpl;
import MonsterCardTradingGameTest.persistence.*;

import org.junit.jupiter.api.*;
import org.postgresql.core.ConnectionFactory;

import java.util.Collection;
import java.util.List;

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

    @Test
    public void getUsernameTestNotLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String token = "test-mtgToken";
        userRepository.getUsername(token);
        assertEquals(null, userRepository.getUsername(token));
    }

    @Test
    public void getUsernameTestLoggedIn(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String token = "test-mtcgToken";
        loginTest();
        assertEquals("test", userRepository.getUsername(token));
    }

    @Test
    public void getUsernameTestLoggedInDatabase(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:getUsernameTest.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String token = "neu-mtcgToken";
        assertEquals("neu", userRepositoryTest.getUsernameTest(token));
    }

    @Test
    public void getDataTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String token = "test-mtcgToken";
        loginTest();
        List<String> userDetails = userRepository.getData(token, username);
        assertEquals(null, userDetails.get(0));
        assertEquals(null, userDetails.get(1));
        assertEquals(null, userDetails.get(2));
    }
    @Test
    public void editDataTest(){
        String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
        UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
        userRepository = new UserRepositoryImpl(unitOfWork);
        userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
        String username = "test";
        String token = "test-mtcgToken";
        UserData userdata = new UserData();
        userdata.setName("test");
        userdata.setBio("neu");
        userdata.setImage("hallo");
        loginTest();
        userRepository.editData(token, userdata, username);
        List<String> resultUserDetails =userRepositoryTest.getEditStats(username);
        assertEquals("test", resultUserDetails.get(0));
        assertEquals("neu", resultUserDetails.get(1));
        assertEquals("hallo", resultUserDetails.get(2));
    }


   @Test
   public void getStatsTestNotLoggedIn(){
       String jdbcUrl = "jdbc:h2:~/mctg;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;INIT=RUNSCRIPT FROM 'classpath:login.sql'";
       UnitOfWork unitOfWork = new UnitOfWork(jdbcUrl);
       userRepository = new UserRepositoryImpl(unitOfWork);
       userRepositoryTest = new UserRepositoryTestImpl(unitOfWork);
       String token = "test-mtcgToken";
       userRepository.getStats(token);
       assertEquals(null, userRepository.getStats(token));
   }

}
