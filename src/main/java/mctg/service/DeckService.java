package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.Card;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.CardsRepositoryImpl;
import mctg.persistence.repository.DeckRepository;
import mctg.persistence.repository.DeckRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Provider;
import java.util.Collection;
import java.util.List;
public class DeckService extends AbstractService {
    private DeckRepository deckRepository;

    public DeckService() {deckRepository = new DeckRepositoryImpl(new UnitOfWork());}

    public Response createDeck(Request request) {
        String header = request.getHeaderMap().getHeader("Authorization");

        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        String body = request.getBody();

        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }
        List<String> cardIds = request.getBodyAsList(String.class);
        List<String> deckRepositoryCollection = deckRepository.createDeck(cardIds, header);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(deckRepositoryCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    public Response getDeck(Request request) {
        String header = request.getHeaderMap().getHeader("Authorization");
        System.out.println(header);

        List<String> deckCollection = deckRepository.getDeck(header);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(deckCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
