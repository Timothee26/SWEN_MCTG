package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CardsRepositoryTestImpl implements CardsRepositoryTest {
    private UnitOfWork unitOfWork;

    public CardsRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Card> getUpdateUserTest(String cardId) {
        String sql = "SELECT * FROM userdb.package WHERE ID = ?";
        List<Card> card = new ArrayList<>();
        Card card1 = null;
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                card1 = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .pid(resultSet.getInt("pid"))
                        .bought(resultSet.getString("bought"))
                        .elementType(resultSet.getString("type"))
                        .build();
                card.add(card1);
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not select card", e);
        }
        return card;
    }
}
