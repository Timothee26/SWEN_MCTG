package mctg.persistence.repository;

import mctg.model.Trade;
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

public class TradingRepositoryImpl implements TradingRepository {
    private UnitOfWork unitOfWork;

    public TradingRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
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

    @Override
    public List<String> getTradingOffers(String token) {
        String username = getUsername(token);
        List<String> tradingOffers = new ArrayList<>();

        String sql = "SELECT * from userdb.trades";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                tradingOffers.add("ID: " + resultSet.getString(1));
                tradingOffers.add("CardID: " + resultSet.getString(2));
                tradingOffers.add("Type: " + resultSet.getString(3));
                tradingOffers.add("Damage: " + resultSet.getString(4));
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return tradingOffers;
    }

    @Override
    public void createTrade(String token, Trade trade) {
        String username = getUsername(token);
        String sql = "INSERT INTO userdb.trades (Id, CardToTrade, Type, MinimumDamage, Username) VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, trade.getId());
            stmt.setString(2, trade.getCardToTrade());
            stmt.setString(3, trade.getType());
            stmt.setFloat(4,trade.getMinimumDamage());
            stmt.setString(5, username);
            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Could not insert into database", e);
        }
        unitOfWork.commitTransaction();

        //return null;
    }
}
