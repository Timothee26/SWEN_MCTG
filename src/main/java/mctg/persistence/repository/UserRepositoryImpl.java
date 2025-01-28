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
                if(user.getToken().contains(token)){
                    System.out.println("user gefunden");
                    return user.getUsername();
                }
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

        return null;
    }

    public List<String> getData(String token, String username) {
        if (!username.equals(getUsername(token))) {
            return null;
        }
        String sql = "SELECT * from userdb.user where username = ?";
        List<String> userDetails = new ArrayList<>();

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userDetails.add("Name: " + resultSet.getString("Name"));
                userDetails.add("Bio: " + resultSet.getString("Bio"));
                userDetails.add("Image: " + resultSet.getString("Image"));
            } else {
                throw new DataAccessException("User not found in the database.");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return userDetails;
    }

    @Override
    public List<String> editData(String token, UserData data, String username) {
        if (!username.equals(getUsername(token))) {
            return null;
        }


        String sql = "Update userdb.user set Name = ?, Bio = ?, Image = ? where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
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
    public List<String> getStats(String token) {
        String username = getUsername(token);
        List<String> userStats = new ArrayList<>();

        String sql = "SELECT Name, Wins, Losses, Ties, Elo from userdb.user where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userStats.add("Name: " + resultSet.getString("Name"));
                userStats.add("Wins: " + resultSet.getInt("Wins"));
                userStats.add("Losses: " + resultSet.getInt("Losses"));
                userStats.add("Ties: " + resultSet.getInt("Ties"));
                userStats.add("Elo: " + resultSet.getInt("Elo"));
            } else {
                throw new DataAccessException("User not found in the database.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return userStats;
    }

    List<Integer> getUserStatsUpdate(String username) {
        String sql = "SELECT Wins, Losses, Ties, Elo from userdb.user where username = ?";
        List<Integer> userStats = new ArrayList<>();

        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                userStats.add(resultSet.getInt(1));
                userStats.add(resultSet.getInt(2));
                userStats.add(resultSet.getInt(3));
                userStats.add(resultSet.getInt(4));
            } else {
                throw new DataAccessException("User not found in the database.");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return userStats;
    }

    public List<String> updateStats(String token1,String token2) {
        String user1 = getUsername(token1);
        String user2 = getUsername(token2);

        List<Integer> userStats1 = getUserStatsUpdate(user1);
        String sql = "Update userdb.user set Wins = ?, Elo = ? where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setInt(1, userStats1.get(0)+1);
            stmt.setInt(2, userStats1.get(3)+3);
            stmt.setString(3,user1);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

        List<Integer> userStats2 = getUserStatsUpdate(user2);
        sql = "Update userdb.user set Losses = ?, Elo = ? where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setInt(1, userStats2.get(1)+1);
            stmt.setInt(2, userStats2.get(3)-5);
            stmt.setString(3,user2);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return null;
    }

    public List<String> updateTies(String token1,String token2) {
        String user1 = getUsername(token1);
        String user2 = getUsername(token2);
        List<Integer> userStats1 = getUserStatsUpdate(user1);
        String sql = "Update userdb.user set Ties = ? where username = ?";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setInt(1, userStats1.get(2)+1);
            stmt.setString(2, user1);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }

        List<Integer> userStats2 = getUserStatsUpdate(user2);
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)) {
            stmt.setInt(1, userStats2.get(2)+1);
            stmt.setString(2, user2);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return null;
    }

    @Override
    public List<String> getElo(String token) {
        String username = getUsername(token);
        List<String> userElos = new ArrayList<>();
        String sql = "SELECT Elo, Name from userdb.user Order by Elo desc";
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                userElos.add(String.valueOf(resultSet.getInt(1)));
                userElos.add(resultSet.getString(2));
            }

        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        unitOfWork.commitTransaction();
        return userElos;
    }
}


    //public void

