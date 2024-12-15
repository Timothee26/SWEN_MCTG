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
        String body = request.getBody();

        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }
        List<String> cardIds = request.getBodyAsList(String.class);
        deckRepository.createDeck(cardIds);
        String json = null;
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

}
