package mctg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.CardsService;

public class CardsController implements RestController {
    private final CardsService cardsService;

    public CardsController() {
        this.cardsService = new CardsService();
    }

    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.GET && request.getPathname().equals("/cards")) {
            return this.cardsService.showCards(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
