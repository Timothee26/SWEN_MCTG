package mctg.persistence.repository;

import mctg.model.Trade;
import mctg.model.User;
import mctg.model.UserData;

import java.util.Collection;
import java.util.List;

public interface TradingRepository {
    List<Trade> getTradingOffers(String token);
    void createTrade(String token, Trade trade);
    List<Trade> acceptTradingOffer(String token, String cardId, String offerId);
    String deleteTradingOffer(String token, String offerId);
    boolean checkCardOwner(String username, String cardId);
    boolean checkDeckOwner(String username, String cardId);
    Trade getTrade(String offerId);
    boolean deleteTrade(String offerId);
    String getUsername(String token);

}
