package mctg.persistence.repository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.Card;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class DeckRepositoryImpl implements DeckRepository {
    private UnitOfWork unitOfWork;

    public DeckRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    public List<Card> getCards(List<String> cardIds){
        List<Card> cards = new ArrayList<>();
        for(String cardId : cardIds){
            String sql = "Select Id, Name, Damage from userdb.package WHERE Id = ?";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, cardId);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    Card deckCard = new Card();
                    deckCard.setId(resultSet.getString("Id"));
                    deckCard.setName(resultSet.getString("Name"));
                    deckCard.setDamage(resultSet.getInt("Damage"));

                    cards.add(deckCard);
                }
            }catch (SQLException e) {
                throw new DataAccessException("Select nicht erfolgreich", e);
            }
        }
        return cards;
    }

    public void createDeck(List<String> cardIds){
        List<Card> cards = getCards(cardIds);
        for(Card card : cards){
            String sql = "Insert into userdb.deck (Id, Name, Damage) values (?, ?, ?)";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, card.getId());
                stmt.setString(2, card.getName());
                stmt.setInt(3, card.getDamage());
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Row inserted successfully.");
                } else {
                    System.out.println("No rows inserted.");
                }
            }catch (SQLException e) {
                throw new DataAccessException("Select nicht erfolgreich", e);
            }
            unitOfWork.commitTransaction();
        }
    }
}
