package mctg.persistence.repository;

import java.util.List;

public interface DeckRepository {
    public List<String> createDeck(List<String> cardIds,String token);
    public List<String> getDeck(String token);
}
