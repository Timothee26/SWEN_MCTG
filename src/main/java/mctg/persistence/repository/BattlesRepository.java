package mctg.persistence.repository;

import mctg.model.Card;

import java.util.List;

public interface BattlesRepository {
    float checkTypeAndEffectiveness(Card card1, Card card2);
    boolean checkSpecialties(Card card1, Card card2);
    List<String> battles(List<String> tokens);
}
