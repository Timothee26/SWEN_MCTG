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

    public void battles(List<String> tokens){
        List<Card> Player1 = new ArrayList<>();
        List<Card> Player2 = new ArrayList<>();
        Player1 = deckRepository.getDeck(tokens.get(0));
        Player2 = deckRepository.getDeck(tokens.get(1));

        while(!Player1.isEmpty() && !Player2.isEmpty()){
            Card card1 = Player1.get(randomCard(Player1.size()));
            Card card2 = Player2.get(randomCard(Player2.size()));
            if(card1.getDamage() > card2.getDamage()){
                Player1.add(card2);
                Player2.remove(card2);
            }else if (card1.getDamage() < card2.getDamage()){
                Player2.add(card1);
                Player1.remove(card1);
            }
        }
        if(Player1.isEmpty()){
            System.out.println(Pl);
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
        }

        /*try {
            json = this.getObjectMapper().writeValueAsString(battlesCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }


}
