package mctg.persistence.repository;

import mctg.model.Card;
import mctg.persistence.UnitOfWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BattlesRepositoryImpl implements BattlesRepository {
    private UnitOfWork unitOfWork;
    private DeckRepository deckRepository;
    private UserRepository userRepository;

    public BattlesRepositoryImpl(UnitOfWork unitOfWork) {

        this.unitOfWork = unitOfWork;
        deckRepository = new DeckRepositoryImpl(new UnitOfWork());
        userRepository = new UserRepositoryImpl(new UnitOfWork());
    }

    int randomCard(int size){
        Random random = new Random();
        return random.nextInt(size);
    }

    public float checkTypeAndEffectiveness(Card card1, Card card2){
        if(card1.getName().contains("Spell")){
            if(card1.getElementType().equals("Water") && card2.getElementType().equals("Fire")){
                return card1.getDamage()*2;
            }else if(card1.getElementType().equals("Fire") && card2.getElementType().equals("Normal")){
                return card1.getDamage()*2;
            }else if(card1.getElementType().equals("Normal") && card2.getElementType().equals("Water")) {
                return card1.getDamage() * 2;
            }else if(card1.getElementType().equals("Fire") && card2.getElementType().equals("Water")) {
                return card1.getDamage() /2;
            }else if(card1.getElementType().equals("Normal") && card2.getElementType().equals("Fire")) {
                return card1.getDamage() /2;
            } else if(card1.getElementType().equals("Water") && card2.getElementType().equals("Normal")) {
                return card1.getDamage() /2;
            }else if(card1.getName().contains("Knight") && card2.getName().equals("WaterSpell")) {
                return 0;
            }
        }
        return card1.getDamage();
    }

    public boolean checkSpecialties(Card card1, Card card2){
        if(card1.getName().contains("Goblin") && card2.getName().contains("Dragon")){
            return true;
        }else if (card1.getName().contains("Wizzard") && card2.getName().contains("Ork")){
            return true;
        }else if (card1.getName().contains("Dragon") && card2.getName().contains("Dragon")){
            return true;
        }else if (card1.getName().contains("Kraken") && card2.getName().contains("Spell")){
            return true;
        } else if (card1.getName().contains("FireElve") && card2.getName().contains("Dragon")){
            return true;
        }
        return false;
    }

    boolean luck(){
        Random random = new Random();
        int n = random.nextInt(100);
        if (n == 13){
            return true;
        }
        return false;
    }

    public List<String> battles(List<String> tokens){
        List<Card> Player1 = new ArrayList<>();
        List<Card> Player2 = new ArrayList<>();
        Player1 = deckRepository.getDeck(tokens.get(0));
        Player2 = deckRepository.getDeck(tokens.get(1));
        List<String> battleLog = new ArrayList<>();
        int round = 0;

        while(!(Player1.isEmpty()) && !(Player2.isEmpty()) && round < 100){
            round++;
            Card card1 = Player1.get(randomCard(Player1.size()));
            Card card2 = Player2.get(randomCard(Player2.size()));


            boolean Player1Luck = luck();
            boolean Player2Luck = luck();
            if(Player1Luck && Player2Luck){
                System.out.println("beide Player hatten Glück, die Runde wird übersprungen");
                battleLog.add("beide Player hatten Glück, die Runde wird übersprungen\n");
                continue;
            } else if (Player1Luck){
                System.out.println(card1.getBought()+" hatte Glück und bekommt die Karte "+card2.getName()+ " von " + card2.getBought());
                battleLog.add(card1.getBought()+" hatte Glück und bekommt die Karte "+card2.getName()+ " von " + card2.getBought()+"\n");

                Player1.add(card2);
                Player2.remove(card2);
            }else if (Player2Luck) {
                System.out.println(card2.getBought()+" hatte Glück und bekommt die Karte"+card1.getName()+ " von " + card1.getBought());
                battleLog.add(card2.getBought()+" hatte Glück und bekommt die Karte"+card1.getName()+ " von " + card1.getBought()+"\n");

                Player2.add(card1);
                Player1.remove(card1);
            }

            if(checkSpecialties(card1,card2) || checkSpecialties(card2,card1)){
                continue;
            }

            float card1Damage = checkTypeAndEffectiveness(card1, card2);
            float card2Damage = checkTypeAndEffectiveness(card2, card1);
            if(card1Damage > card2Damage){
                System.out.println(card1.getBought()+" won with: "+card1.getName()+"("+card1Damage+")"+" won against "+card2.getName()+"("+card2Damage+")");
                battleLog.add(card1.getBought()+" won with: "+card1.getName()+"("+card1Damage+")"+" won against "+card2.getName()+"("+card2Damage+")"+"\n");

                Player1.add(card2);
                Player2.remove(card2);
            }else if (card1Damage < card2Damage){
                System.out.println(card2.getBought()+" won with: "+card2.getName()+"("+card2Damage+")"+" won against "+card1.getName()+"("+card1Damage+")");
                battleLog.add(card2.getBought()+" won with: "+card2.getName()+"("+card2Damage+")"+" won against "+card1.getName()+"("+card1Damage+")"+"\n");

                Player2.add(card1);
                Player1.remove(card1);
            }else{
                System.out.println("This round is a tie");
                battleLog.add("This round is a tie"+"\n");

                continue;
            }
            System.out.println(Player1.size());
            System.out.println(Player2.size());
        }

        if(Player1.isEmpty()){
            System.out.println("Player 2 won");
            battleLog.add("Player 2 won"+"\n");

            userRepository.updateStats(tokens.get(1),tokens.get(0));
        }else if (Player2.isEmpty()){
            System.out.println("Player 1 won");
            battleLog.add("Player 1 won"+"\n");
            userRepository.updateStats(tokens.get(0),tokens.get(1));

        }else{
            System.out.println("over 100 rounds");
            battleLog.add("over 100 rounds"+"\n");
            System.out.println("Player1 has "+ Player1.size() + " cards left");
            battleLog.add("Player1 has \"+ Player1.size() + \" cards left"+"\n");
            System.out.println("Player2 has "+ Player2.size() + " cards left");
            battleLog.add("Player2 has \"+ Player2.size() + \" cards left"+"\n");

            userRepository.updateTies(tokens.get(0),tokens.get(1));
        }
        return battleLog;
    }
}
