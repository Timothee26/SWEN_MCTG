package mctg.persistence.repository;

import mctg.model.UserData;
import mctg.persistence.DataAccessException;
import mctg.persistence.UnitOfWork;
import mctg.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TradingRepositoryImpl implements TradingRepository {
    private UnitOfWork unitOfWork;

    public TradingRepositoryImpl(UnitOfWork unitOfWork){

        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<String> getTradingOffers(String token) {
        return List.of();
    }
}
