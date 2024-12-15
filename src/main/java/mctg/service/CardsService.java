package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.Card;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.CardsRepository;
import mctg.persistence.repository.CardsRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Provider;
import java.util.Collection;
import java.util.List;

public class CardsService extends AbstractService {
    private CardsRepository cardsRepository;

    public CardsService() {cardsRepository = new CardsRepositoryImpl(new UnitOfWork());}

    public Response showCards(Request request) {
        String body = request.getBody();

        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }
        User user;
        try{
            user = new ObjectMapper().readValue(request.getBody(), User.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        List<String> cardCollection = cardsRepository.showCards(user.getUsername());
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(cardCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
