package mctg.persistence.repository;

import mctg.model.Card;
import mctg.model.Trade;
import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.repository.CardsRepository;
import mctg.persistence.repository.CardsRepositoryImpl;
import mctg.persistence.UnitOfWork;
import mctg.model.User;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TradingRepositoryImpl implements TradingRepository {
    private UnitOfWork unitOfWork;
    private CardsRepository cardsRepository;
    public TradingRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
        cardsRepository = new CardsRepositoryImpl(unitOfWork);
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

    @Override
    public List<Trade> getTradingOffers(String token) {
        String username = getUsername(token);
        List<Trade> tradingOffers = new ArrayList<>();

        String sql = "SELECT * from userdb.trades";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Trade getTrade = new Trade();
                getTrade.setId(resultSet.getString(1));
                getTrade.setCardToTrade(resultSet.getString(2));
                getTrade.setType(resultSet.getString(3));
                getTrade.setMinimumDamage(resultSet.getFloat(4));
                getTrade.setUser(resultSet.getString(5));
                tradingOffers.add(getTrade);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return tradingOffers;
    }

    public boolean checkCardOwner(String username, String cardId){
        String sql = "SELECT bought from userdb.package where Id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next() && resultSet.getString(1).equals(username)){
                return true;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not insert into database", e);
        }
        return false;
    }

    public boolean checkDeckOwner(String username, String cardId){
        String sql = "SELECT id, bought from userdb.deck where id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next() && resultSet.getString(1).equals(cardId) && resultSet.getString(2).equals(username)){
                return true;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not select from database", e);
        }
        return false;
    }
    @Override
    public void createTrade(String token, Trade trade) {
        String username = getUsername(token);
        if(checkCardOwner(username, trade.getCardToTrade())){
            if (!checkDeckOwner(username, trade.getCardToTrade())){
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
            }else{
                throw new DataAccessException("Card is in Deck");
            }
        }else{
            throw new DataAccessException("You are not the owner of this card");
        }
    }

    public Trade getTrade(String offerId){
        Trade trade = new Trade();
        String sql = "SELECT * from userdb.trades where id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, offerId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                trade.setId(resultSet.getString(1));
                trade.setCardToTrade(resultSet.getString(2));
                trade.setType(resultSet.getString(3));
                trade.setMinimumDamage(resultSet.getFloat(4));
                trade.setUser(resultSet.getString(5));
                unitOfWork.commitTransaction();
                return trade;
            } else {
                throw new DataAccessException("Trade with ID " + offerId + " not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving trade from database", e);
        }
    }

    public boolean deleteTrade(String offerId){
        String sql = "DELETE FROM userdb.trades WHERE id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, offerId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row inserted successfully.");
            } else {
                System.out.println("No rows inserted.");
                return false;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not insert into database", e);
        }
        unitOfWork.commitTransaction();
        return true;
    }

    @Override
    public List<Trade> acceptTradingOffer(String token, String cardId, String offerId) {
        String username = getUsername(token);
        if(!checkCardOwner(username,cardId)){
            throw new DataAccessException("the card does not belong to the user");
        }
        Trade trade = getTrade(offerId);
        Card card = cardsRepository.getCard(cardId);
        System.out.println(card.getDamage());
        if(card.getDamage() < trade.getMinimumDamage()){
            throw new DataAccessException("the damage of the card is not enough");
        }
        if ((card.getName().contains("Spell") && !(trade.getType().contains("spell"))) || (!(card.getName().contains("Spell")) && trade.getType().contains("spell"))) {
            throw new DataAccessException("the type of the card is incorrect");
        }
        if (card.getBought().equals(trade.getUser())){
            throw new DataAccessException("you cannot trade with yourself");
        }
        String tempUser = trade.getUser();
        String cardTradeId = trade.getCardToTrade();
        Card cardToTrade = cardsRepository.getCard(cardTradeId);
        if (!(cardsRepository.updateUser(cardToTrade.getId(), card.getBought()) && cardsRepository.updateUser(card.getId(), tempUser))) {
            throw new DataAccessException("the trade was not successful");
        }
        if (!deleteTrade(offerId)) {
            throw new DataAccessException("delete trade failed");

        }
        return null;
    }

    @Override
    public String deleteTradingOffer(String token, String offerId) {
        String username = getUsername(token);
        Trade trade = getTrade(offerId);
        if (!(trade.getUser().equals(username))) {
            throw new DataAccessException("this is not your trade");
        }
        String sql = "DELETE FROM userdb.trades WHERE id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, offerId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row deleted successfully.");
            } else {
                System.out.println("No rows deleted.");
                throw new DataAccessException("No rows deleted");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not delete trade", e);
        }
        unitOfWork.commitTransaction();
        return "";
    }
}
