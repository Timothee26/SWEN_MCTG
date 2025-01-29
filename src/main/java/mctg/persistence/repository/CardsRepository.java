package mctg.persistence.repository;

import mctg.model.Card;

import java.util.List;

public interface CardsRepository {
    List<Card> showCards(String token);
    Card getCard(String cardId);
    boolean cardExists(String cardId);
    boolean updateUser(String cardId, String username);
    String getUsername(String token);
}
