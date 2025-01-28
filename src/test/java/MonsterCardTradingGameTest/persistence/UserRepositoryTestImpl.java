package MonsterCardTradingGameTest.persistence;

import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserRepositoryTestImpl implements UserRepositoryTest {
    private UnitOfWork unitOfWork;

    public UserRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public User getUser() {
        User user = null;
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                                 select * from userdb."user"
                             """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while (resultSet.next()) {
                user = User.builder()
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .coins(resultSet.getInt("coins"))
                        .elo(resultSet.getInt("elo"))
                        .losses(resultSet.getInt("losses"))
                        .ties(resultSet.getInt("ties"))
                        .wins(resultSet.getInt("wins"))
                        .build();
                userRows.add(user);

            }
            if (!userRows.isEmpty()) {
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }


    public User login(){
        User user = null;
        try (PreparedStatement preparedStatement =
                     this.unitOfWork.prepareStatement("""
                                 select * from userdb.login
                             """)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<User> userRows = new ArrayList<>();
            while (resultSet.next()) {
                user = User.builder()
                        .username(resultSet.getString("username"))
                        .token(resultSet.getString("token"))
                        .build();
                userRows.add(user);
            }
            if (!userRows.isEmpty()) {
                return user;
            } else {
                return null;
            }
        }catch(SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }


    public String getUsernameTest(String token){
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

    public User getEditStats(String username){
        User user = null;
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT Name, Bio, Image, from userdb."user" where username = ?""")){
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
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return user;
    }

    public User getUpdateStatsTest(String username){
        User user = null;
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                SELECT username, Wins, Losses, Ties, Elo from userdb."user" where username = ?""")){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .username(resultSet.getString("username"))
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
        return user;
    }

}
