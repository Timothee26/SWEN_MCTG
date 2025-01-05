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

    public List<String> showCards(String token){
        String username = getUsername(token);
        String sql = "SELECT Id, Name, Damage, Type FROM userdb.package WHERE bought = ?";
        List<String> cards = new ArrayList<>();

        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                JSONObject card = new JSONObject();
                card.put("Id", resultSet.getString("Id"));
                card.put("Name", resultSet.getString("Name"));
                card.put("Damage", resultSet.getString("Damage"));
                card.put("ElementType", resultSet.getString("Type"));

                cards.add(card.toString());
                System.out.println("Found card: " + card);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }
}
