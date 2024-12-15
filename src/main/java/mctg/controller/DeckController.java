package mctg.controller;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.DeckService;

public class DeckController implements RestController {
    private final DeckService deckService;

    public DeckController() {
        this.deckService = new DeckService();
    }
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.PUT && request.getPathname().equals("/deck")) {
            return this.deckService.createDeck(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
