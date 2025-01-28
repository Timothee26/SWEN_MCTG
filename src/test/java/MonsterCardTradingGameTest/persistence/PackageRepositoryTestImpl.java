package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;
import mctg.persistence.repository.PackageRepository;
import mctg.model.User;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PackageRepositoryTestImpl implements PackageRepositoryTest {
    private UnitOfWork unitOfWork;

    public PackageRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Card> getCreatedPackageTest() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * from userdb.package ";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Card card = Card.builder()
                        .id(resultSet.getString("id"))
                        .name(resultSet.getString("name"))
                        .damage(resultSet.getFloat("damage"))
                        .pid(resultSet.getInt("pid"))
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
