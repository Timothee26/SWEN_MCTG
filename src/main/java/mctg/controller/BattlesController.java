package mctg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.BattlesService;

public class BattlesController implements RestController {
    private BattlesService battlesService;

    public BattlesController() {
        this.battlesService = new BattlesService();
    }

    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && request.getPathname().equals("/battles")) {
            return this.battlesService.getTokens(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}


