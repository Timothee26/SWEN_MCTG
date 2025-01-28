package mctg.persistence.repository;

import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private UnitOfWork unitOfWork;

    public UserRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    /**
     * searching for user in table and receive all information to the user as return
     *
     * @param username
     * @return
     */
    @Override
    public Collection<User> findInLogin(String username) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from userdb.login
                    where username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while(resultSet.next())
            {
                User user = User.builder()
                        .username(resultSet.getString(1))
                        .token(resultSet.getString(2))
                        .build();
                userRows.add(user);
            }
            if(!userRows.isEmpty()){
                return userRows;
            }
            else{
                return null;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }
    /**
     * searching for every user in the table and returning all information
     * @param username
     * @return
     */
    @Override
    public Collection<User> findAllUser(String username, String password) {
        try (PreparedStatement preparedStatement = this.unitOfWork.prepareStatement("""
                    select * from userdb."user"
                    where username = ? and password = ?
                    """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while(resultSet.next())
            {
                User user = User.builder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                userRows.add(user);
            }
            if(!userRows.isEmpty()){
                return userRows;
            }
            else{
              return null;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    /**
     * create a new user and insernt the username and the password in the table
     * @param user
     */
    @Override
    public void registerUpload(User user) {
        Collection<User> isRegistered = findAllUser(user.getUsername(),user.getPassword());
        if (isRegistered != null) {
            System.out.println("ist schon vorhanden");
            throw new DataAccessException("User " + user.getUsername() + " already exists");
        }
        else{
            System.out.println("ist noch nicht vorhanden");
            //String sql = "INSERT INTO userdb.user (Username, Password, Coins) VALUES (?, ?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                    INSERT INTO userdb."user" (Username, Password, Coins) VALUES (?, ?, ?)
                    """)){
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, 20);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Register data inserted successfully.");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (SQLException e) {
                throw new DataAccessException("Could not insert into database", e);
            }
            unitOfWork.commitTransaction();

            /*sql = "Update userdb.user (Name) VALUES (?) where username=?";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, user.getUsername());
                stmt.setString(1, user.getUsername());
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("token inserted successfully.");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (SQLException e) {
                throw new DataAccessException("Could not insert into database", e);
            }
            unitOfWork.commitTransaction();*/

        }
    }

    /**
     * checks if the user is registered
     * creates new login user and uploads the username and the created token
     *
     * @param user
     * @return
     */
    public String login(User user) {
        System.out.println("login wird irgendwo aufgerufen");
        Collection<User> isLoggedIn = findInLogin(user.getUsername());
        Collection<User> isRegistered = findAllUser(user.getUsername(),user.getPassword());
        if(isLoggedIn == null && isRegistered != null){
            String sql = "INSERT INTO userdb.login (Username, token) VALUES (?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.createToken());
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("token inserted successfully.");
                } else {
                    System.out.println("No rows inserted.");
                }
            } catch (SQLException e) {
                throw new DataAccessException("Could not insert into database", e);
            }
            unitOfWork.commitTransaction();

        }
        else{
            System.out.println("ist schon vorhanden");
            if(isLoggedIn != null){
                throw new DataAccessException("User " + user.getUsername() + " already logged in");

            }else{
                throw new DataAccessException("User " + user.getUsername() + " does not exists");

            }
        }
        System.out.println(user.createToken());
        return user.createToken();
    }

    public String getUsername(String token){
        System.out.println("token :" + token);
        System.out.println("in der abfrage");
        String sql = "SELECT * from userdb.login";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("in der while");
                User user = User.builder()
                        .username(resultSet.getString(1))
                        .token(resultSet.getString(2))
                        .build();
                System.out.println("user.token:" + user.getToken());
                if(user.getToken().equals(token)){
                    System.out.println("user gefunden");
                    return user.getUsername();
                }
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

        return null;
    }

    public User getData(String token, String username) {
        if (!username.equals(getUsername(token))) {
            return null;
        }
        User user = null;

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT * from userdb."user" where username = ?""")) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user = User.builder()
                        .name(resultSet.getString("Name"))
                        .bio(resultSet.getString("Bio"))
                        .image(resultSet.getString("Image"))
                        .build();
            } else {
                throw new DataAccessException("User not found in the database.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return user;
    }

    @Override
    public List<String> editData(String token, UserData data, String username) {
        if (!username.equals(getUsername(token))) {
            return null;
        }
        //String sql = "Update userdb.user set Name = ?, Bio = ?, Image = ? where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                Update userdb."user" set Name = ?, Bio = ?, Image = ? where username = ?""")) {

            stmt.setString(1, data.getName());
            stmt.setString(2, data.getBio());
            stmt.setString(3, data.getImage());
            stmt.setString(4, username);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("No rows updated.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return null;
    }

    @Override
    public User getStats(String token) {
        String username = getUsername(token);
        User user = null;

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT Name, Wins, Losses, Ties, Elo from userdb."user" where username = ?""")){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user = User.builder()
                        .name(resultSet.getString("Name"))
                        .wins(resultSet.getInt("Wins"))
                        .losses(resultSet.getInt("Losses"))
                        .ties(resultSet.getInt("Ties"))
                        .elo(resultSet.getInt("Elo"))
                        .build();

            } else {
                throw new DataAccessException("User not found in the database.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return user;
    }

    User getUserStatsUpdate(String username) {
        User userStats = null;

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT Wins, Losses, Ties, Elo from userdb."user" where username = ?""")){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userStats = User.builder()
                        .wins(resultSet.getInt("Wins"))
                        .losses(resultSet.getInt("Losses"))
                        .ties(resultSet.getInt("Ties"))
                        .elo(resultSet.getInt("Elo"))
                        .build();
            } else {
                throw new DataAccessException("User not found in the database.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return userStats;
    }

    public User updateStats(String token1,String token2) {
        String user1 = getUsername(token1);
        String user2 = getUsername(token2);

        User userStats1 = getUserStatsUpdate(user1);
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                Update userdb."user" set Wins = ?, Elo = ? where username = ?""")) {
            stmt.setInt(1, userStats1.getWins()+1);
            stmt.setInt(2, userStats1.getElo()+3);
            stmt.setString(3,user1);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

        User userStats2 = getUserStatsUpdate(user2);
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                Update userdb."user" set Losses = ?, Elo = ? where username = ?""")) {
            stmt.setInt(1, userStats2.getLosses()+1);
            stmt.setInt(2, userStats2.getElo()-5);
            stmt.setString(3,user2);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return null;
    }

    public User updateTies(String token1,String token2) {
        String user1 = getUsername(token1);
        String user2 = getUsername(token2);
        User userStats1 = getUserStatsUpdate(user1);
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                Update userdb."user" set Ties = ? where username = ?""")) {
            stmt.setInt(1, userStats1.getTies()+1);
            stmt.setString(2, user1);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

       User userStats2 = getUserStatsUpdate(user2);
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                Update userdb."user" set Ties = ? where username = ?""")) {
            stmt.setInt(1, userStats2.getTies()+1);
            stmt.setString(2, user2);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return null;
    }

    @Override
    public List<User> getElo(String token) {
        List<User> users = new ArrayList<>();
        String username = getUsername(token);
        User user = null;
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT Elo, Name from userdb."user" Order by Elo desc""")){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                user = User.builder()
                        .elo(resultSet.getInt("Elo"))
                        .name(resultSet.getString("Name"))
                        .build();
                users.add(user);
            }

        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return users;
    }
}


    //public void

