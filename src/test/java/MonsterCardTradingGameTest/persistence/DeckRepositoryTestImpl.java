package MonsterCardTradingGameTest.persistence;

import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.DeckRepository;
import mctg.model.Card;
import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeckRepositoryTestImpl implements DeckRepositoryTest {
    private UnitOfWork unitOfWork;

    public DeckRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Card> getCreateDeckTest() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * from userdb.deck ";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Card card = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .bought(resultSet.getString("bought"))
                        .elementType(resultSet.getString("type"))
                        .build();
                cards.add(card);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }

    @Override
    public List<Card> getCreateDeckTestIdList(String username) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * from userdb.deck where bought = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Card card = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .bought(resultSet.getString("bought"))
                        .elementType(resultSet.getString("type"))
                        .build();
                cards.add(card);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return cards;
    }
}
