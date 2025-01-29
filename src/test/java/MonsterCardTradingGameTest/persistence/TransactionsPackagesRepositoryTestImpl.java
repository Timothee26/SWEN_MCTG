package MonsterCardTradingGameTest.persistence;

import mctg.model.Card;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionsPackagesRepositoryTestImpl implements TransactionsPackagesRepositoryTest{
    private UnitOfWork unitOfWork;

    public TransactionsPackagesRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public int getUpdateCoinsTest(String username) {
        try (PreparedStatement stmt = this.unitOfWork.prepareStatement("""
                select coins from userdb."user" where Username = ?"""))
        {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("findcoins" +resultSet.getInt("coins"));
                return resultSet.getInt("coins");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Select nicht erfolgreich", e);
        }
    }

    @Override
    public List<Card> getBuyPackageTest(String username) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * from userdb.package where bought = ?";
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
