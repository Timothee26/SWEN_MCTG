package mctg.persistence.repository;

import com.fasterxml.jackson.databind.util.JSONPObject;
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

    public List<String> showCards(String username){
        String sql = "SELECT Id, Name, Damage FROM userdb.package WHERE bought = ?";
        List<String> cards = new ArrayList<>();

        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                JSONObject card = new JSONObject();
                card.put("Id", resultSet.getString("Id"));
                card.put("Name", resultSet.getString("Name"));
                card.put("Damage", resultSet.getString("Damage"));

                cards.add(card.toString());
                System.out.println("Found card: " + card);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }
}
