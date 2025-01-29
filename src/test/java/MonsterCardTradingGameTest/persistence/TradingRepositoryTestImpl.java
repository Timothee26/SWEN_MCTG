package MonsterCardTradingGameTest.persistence;

import mctg.model.Trade;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.TradingRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TradingRepositoryTestImpl implements TradingRepositoryTest {
    private UnitOfWork unitOfWork;

    public TradingRepositoryTestImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Trade getCreatedTradeTest(String tradeId) {
        Trade trade = new Trade();
        String sql = "SELECT * from userdb.trades where id = ?";
        try(PreparedStatement stmt = this.unitOfWork.prepareStatement(sql)){
            stmt.setString(1, tradeId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                trade.setId(resultSet.getString(1));
                trade.setCardToTrade(resultSet.getString(2));
                trade.setType(resultSet.getString(3));
                trade.setMinimumDamage(resultSet.getFloat(4));
                trade.setUser(resultSet.getString(5));
            }
        }catch (SQLException e) {
            throw new DataAccessException("Could not insert into database", e);
        }
        unitOfWork.commitTransaction();
        return trade;    }
}
