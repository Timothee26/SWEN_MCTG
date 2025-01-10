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
                    where Username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while(resultSet.next())
            {
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2));
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
    public Collection<User> findAllUser(String username) {
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                    select * from userdb.user
                    where Username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while(resultSet.next())
            {
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2));
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
        Collection<User> isRegistered = findAllUser(user.getUsername());
        if (isRegistered != null) {
            System.out.println("ist schon vorhanden");
            throw new DataAccessException("User " + user.getUsername() + " already exists");
        }
        else{
            System.out.println("ist noch nicht vorhanden");
            String sql = "INSERT INTO userdb.user (Username, Password, Coins) VALUES (?, ?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
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

            sql = "INSERT INTO userdb.users (Name) VALUES (?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
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
            unitOfWork.commitTransaction();

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
        Collection<User> isRegistered = findInLogin(user.getUsername());
        if(isRegistered == null){
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
            throw new DataAccessException("User " + user.getUsername() + " already exists");
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
                User user = new User(
                        resultSet.getString(1),
                        resultSet.getString(2)
                );
                System.out.println("user.token:" + user.getToken());
                if(user.getPassword().contains(token)){
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
        String sql = "SELECT * from userdb.users where Name = ?";
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


        String sql = "Update userdb.users set Name = ?, Bio = ?, Image = ? where Name = ?";
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
}


    //public void

