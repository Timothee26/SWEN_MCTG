package mctg.persistence.repository;

import com.fasterxml.jackson.databind.util.JSONPObject;
import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

public class CardsRepositoryImpl implements CardsRepository {
    private UnitOfWork unitOfWork;

    public CardsRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    public String getUsername(String token){
        System.out.println("token :" + token);
        System.out.println("in der abfrage");
        String sql = "SELECT * from userdb.login where token = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, token);
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
        throw new DataAccessException("User not found");
    }


    public boolean cardExists(String cardId){
        String sql = "SELECT count(*) from userdb.package where ID = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next() && resultSet.getInt(1) > 0){
                return true;
            }else{
                throw new DataAccessException("Could not select card");
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not select card", e);
        }
    }

    public boolean updateUser(String cardId, String username){
        String sql = "UPDATE userdb.package set bought = ? where ID = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.setString(2, cardId);
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Row inserted successfully.");
            } else {
                System.out.println("No rows inserted.");
                return false;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not select card", e);
        }
        unitOfWork.commitTransaction();
        return true;
    }

    public Card getCard(String cardId){
        if(!cardExists(cardId)){
            throw new DataAccessException("Card does not exist");
        }
        Card cards = new Card();
        String sql = "SELECT * from userdb.package where id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                cards.setId(resultSet.getString(1));
                cards.setName(resultSet.getString(2));
                cards.setDamage(resultSet.getFloat(3));
                cards.setBought(resultSet.getString(5));
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not select card", e);
        }
        unitOfWork.commitTransaction();
        return cards;
    }

    public List<Card> showCards(String token){
        String username = getUsername(token);
        String sql = "SELECT Id, Name, Damage, Type FROM userdb.package WHERE bought = ?";
        List<Card> cards = new ArrayList<>();

        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Card card = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .elementType(resultSet.getString("type"))
                        .build();

                cards.add(card);
                System.out.println("Found card: " + card);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }
}
