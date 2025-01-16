package mctg.persistence.repository;

import mctg.model.Trade;
import mctg.model.User;
import mctg.model.UserData;

import java.util.Collection;
import java.util.List;

public interface TradingRepository {
    List<String> getTradingOffers(String token);
    void createTrade(String token, Trade trade);

}
