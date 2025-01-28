package MonsterCardTradingGameTest.persistence;

import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
}
