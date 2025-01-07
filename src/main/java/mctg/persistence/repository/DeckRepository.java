package mctg.persistence.repository;

import mctg.model.Card;

import java.util.List;

public interface DeckRepository {
    public List<String> createDeck(List<String> cardIds,String token);
    public List<Card> getDeck(String token);
}
