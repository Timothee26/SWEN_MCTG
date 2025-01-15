package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.persistence.UnitOfWork;
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
        List<String> tradingController = tradingRepository.getTradingOffers(header);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(tradingController);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
