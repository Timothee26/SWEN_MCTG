package mctg.persistence.repository;

import mctg.model.User;
import mctg.model.UserData;

import java.util.Collection;
import java.util.List;

public interface TradingRepository {
    List<String> getTradingOffers(String token);
}
