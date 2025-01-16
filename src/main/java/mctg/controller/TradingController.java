package mctg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.TradingService;
public class TradingController implements RestController {
    private final TradingService tradingService;

    public TradingController() {
        this.tradingService = new TradingService();
    }

    @Override
    public Response handleRequest(Request request) {
        if(request.getMethod() == Method.GET && request.getPathname().equals("/tradings")) {
            return this.tradingService.getTrades(request);
        }else if(request.getMethod() == Method.POST && request.getPathname().equals("/tradings")) {
            return this.tradingService.createTrade(request);
        }else if(request.getMethod() == Method.POST && request.getPathname().contains("/tradings/")) {
            String name[] = request.getPathname().split("/");
            String tradeId = name[name.length - 1];
            return this.tradingService.acceptTrade(request, tradeId);
        }else if(request.getMethod() == Method.DELETE && request.getPathname().contains("/tradings/")) {
            String name[] = request.getPathname().split("/");
            String tradeId = name[name.length - 1];
            return this.tradingService.deleteTrade(request, tradeId);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}


