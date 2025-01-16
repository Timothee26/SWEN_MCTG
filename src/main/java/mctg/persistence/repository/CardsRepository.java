package mctg.persistence.repository;

import mctg.model.Card;

import java.util.List;

public interface CardsRepository {
    List<String> showCards(String token);
    Card getCard(String cardId);
    boolean updateUser(String cardId, String username);
}
