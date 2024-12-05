package mctg.persistence.repository;

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
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        try (PreparedStatement preparedStatement =
                this.unitOfWork.prepareStatement("""
                    select * from userdb.user
                    where username = ?
                """))
        {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            while(resultSet.next())
            {
                user = new User(
                        resultSet.getString(1),
                        resultSet.getString(1));
            }
            return user;
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
                    where username = ?
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
     * create a new user and insert the username and the password in the table
     * @param user
     */
    @Override
    public void registerUpload(User user) {
        Collection<User> isRegistered = findAllUser(user.getUsername());
        if (isRegistered != null) {
            throw new DataAccessException("User " + user.getUsername() + " already exists");
        }
        else{
            String sql = "INSERT INTO userdb.user (username, password) VALUES (?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
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
        }
    }

    public void login(User user) {
        Collection<User> isRegistered = findAllUser(user.getUsername());
        if(isRegistered!=null){
            String sql = "INSERT INTO userdb.login (username, token) VALUES (?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getToken());
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
            throw new DataAccessException("User " + user.getUsername() + " does not exist");
        }
    }
}
    //public void

