package mctg.persistence.repository;

import mctg.model.Card;

import java.util.List;

public interface DeckRepository {
    public void createDeck(List<String> cardIds);
}
