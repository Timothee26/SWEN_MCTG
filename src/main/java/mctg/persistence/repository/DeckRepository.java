package mctg.persistence.repository;

import java.util.List;

public interface DeckRepository {
    public void createDeck(List<String> cardIds);
    public List<String> getDeck(String username);
}
