package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.Card;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.model.Trade;
import mctg.persistence.repository.TradingRepository;
import mctg.persistence.repository.TradingRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
public class TradingService extends AbstractService {
    private TradingRepository tradingRepository;

    public TradingService() {tradingRepository = new TradingRepositoryImpl(new UnitOfWork());}

    public Response getTrades(Request request){
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }
        List<Trade> tradingController = tradingRepository.getTradingOffers(header);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(tradingController);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    public Response createTrade(Request request){
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        Trade trade;
        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }

        try{
            trade = this.getObjectMapper().readValue(request.getBody(), Trade.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        try {
            tradingRepository.createTrade(header, trade);
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Failed to insert registration data.\" }"+e.getMessage());
        }
        return new Response(HttpStatus.CREATED, ContentType.JSON, "");
    }


    public Response acceptTrade(Request request, String tradeId){
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"header is empty\"}");
        }

        String body = request.getBody().replace("\"", "");
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }

        List<Trade> tradingController = tradingRepository.acceptTradingOffer(header, body, tradeId);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(tradingController);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);

    }

    public Response deleteTrade(Request request, String tradeId){
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"header is empty\"}");
        }

        /*String body = request.getBody().replace("\"", "");
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }*/

        String tradingController = tradingRepository.deleteTradingOffer(header, tradeId);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(tradingController);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);

    }
}
