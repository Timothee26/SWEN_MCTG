package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.Card;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.BattlesRepository;
import mctg.persistence.repository.BattlesRepositoryImpl;
import mctg.persistence.repository.DeckRepository;
import mctg.persistence.repository.DeckRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BattlesService extends AbstractService{
    private BattlesRepository battlesRepository;
    private DeckRepository deckRepository;

    public BattlesService(){
        battlesRepository = new BattlesRepositoryImpl(new UnitOfWork());
        deckRepository = new DeckRepositoryImpl(new UnitOfWork());
    }

    int randomCard(int size){
        Random random = new Random();
        return random.nextInt(size);
    }

    float checkTypeAndEffectiveness(Card card1, Card card2){
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

    boolean checkSpecialties(Card card1, Card card2){
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

    boolean luck(Card card){
        Random random = new Random();
        int n = random.nextInt(100);
        if (n == 13){
            return true;
        }
        return false;
    }

    public void battles(List<String> tokens){
        List<Card> Player1 = new ArrayList<>();
        List<Card> Player2 = new ArrayList<>();
        Player1 = deckRepository.getDeck(tokens.get(0));
        Player2 = deckRepository.getDeck(tokens.get(1));
        int round = 0;

        while(!Player1.isEmpty() && !Player2.isEmpty() && round < 100){
            round++;
            Card card1 = Player1.get(randomCard(Player1.size()));
            Card card2 = Player2.get(randomCard(Player2.size()));

            if(luck(card1) && luck(card2)){
                System.out.println("beide Player hatten Glück, die Runde wird übersprungen");
                continue;
            } else if (luck(card1)){
                System.out.println(card1.getBought()+" hatte Glück und bekommt die Karte von "+ card2.getBought());
                Player1.add(card2);
                Player2.remove(card2);
            }else if (luck(card2)) {
                System.out.println(card2.getBought()+" hatte Glück und bekommt die Karte von "+ card1.getBought());
                Player2.add(card1);
                Player1.remove(card1);
            }

            if(checkSpecialties(card1,card2) || checkSpecialties(card2,card1)){
                continue;
            }

            float card1Damage = checkTypeAndEffectiveness(card1, card2);
            float card2Damage = checkTypeAndEffectiveness(card2, card1);
            if(card1Damage > card2Damage){
                System.out.println("Player1 won with: "+card1.getName()+"("+card1Damage+")"+" won against "+card2.getName()+"("+card2Damage+")");
                Player1.add(card2);
                Player2.remove(card2);
            }else if (card1Damage < card2Damage){
                System.out.println("Player2 won with: "+card2.getName()+"("+card2Damage+")"+" won against "+card1.getName()+"("+card1Damage+")");
                Player2.add(card1);
                Player1.remove(card1);
            }
        }

        if(Player1.isEmpty()){
            System.out.println("Player 2 won");
        }else if (Player2.isEmpty()){
            System.out.println("Player 1 won");
        }else{
            System.out.println("over 100 rounds");
            System.out.println("Player1 has "+ Player1.size()+" cards left");
            System.out.println("Player2 has "+ Player2.size()+" cards left");
        }
    }

    public List<String> tokens = new ArrayList<>();
    public Response getTokens(Request request) {

        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        tokens.add(header);

        for (String token : tokens) {
            System.out.println(token);
        }

        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }
        String json = null;
        if(tokens.size() == 2){
            battles(tokens);
            tokens.clear();
        }

        /*try {
            json = this.getObjectMapper().writeValueAsString(battlesCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }


}
