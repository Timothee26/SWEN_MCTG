package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.BattlesRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class BattlesRepositoryTestImpl implements BattlesRepositoryTest {
    private UnitOfWork unitOfWork;

    public BattlesRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Card getCardTest(String cardId) {
        Card card = null;
        String sql = "SELECT * from userdb.package where id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, cardId);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()) {
                card = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .bought(resultSet.getString("bought"))
                        .elementType(resultSet.getString("type"))
                        .build();
            }
        }catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
        return card;    }
}
