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
            String sql = "Select Id, Name, Damage, Bought from userdb.package WHERE Id = ?";
            try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                stmt.setString(1, cardId);
                ResultSet resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    Card deckCard = new Card();
                    deckCard.setId(resultSet.getString("Id"));
                    deckCard.setName(resultSet.getString("Name"));
                    deckCard.setDamage(resultSet.getInt("Damage"));
                    deckCard.setBought(resultSet.getString("Bought"));
                    cards.add(deckCard);
                }
            }catch (SQLException e) {
                throw new DataAccessException("Select nicht erfolgreich", e);
            }
        }
        return cards;
    }

   /* public boolean userDeckExists(List<Card> cards, String username){
        for(Card card : cards){
            if(card.getName().equals(username)){
                return true;
            }
        }
        return false;
    }*/

    public void createDeck(List<String> cardIds){
        List<Card> cards = getCards(cardIds);
        //if(userDeckExists(cards,username)){
            for(Card card : cards){
                String sql = "Update userdb.deck set Id = ?, Name = ?, Damage = ?, Bought = ? where Bought = ?";
                try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                    stmt.setString(1, card.getId());
                    stmt.setString(2, card.getName());
                    stmt.setInt(3, card.getDamage());
                    stmt.setString(4, card.getBought());
                    //stmt.setString(5, username);
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
        //}else{
            for(Card card : cards){
                String sql = "Insert into userdb.deck (Id, Name, Damage, Bought) values (?, ?, ?, ?)";
                try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
                    stmt.setString(1, card.getId());
                    stmt.setString(2, card.getName());
                    stmt.setInt(3, card.getDamage());
                    stmt.setString(4, card.getBought());

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
        //}
    }

    public List<String> getDeck(String username){
        String sql = "SELECT Id, Name, Damage from userdb.deck where Bought = ?";
        List<String> cards = new ArrayList<>();
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            int i = 1;
            while (resultSet.next()) {
                JSONObject card = new JSONObject();
                card.put("Id", resultSet.getString("Id"));
                card.put("Name", resultSet.getString("Name"));
                card.put("Damage", resultSet.getString("Damage"));

                cards.add(card.toString());
                System.out.println("card "+ i +": "+ card);
                i++;
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }
}
