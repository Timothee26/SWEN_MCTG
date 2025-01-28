package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.Card;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.PackageRepository;
import mctg.persistence.repository.PackageRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class PackageService extends AbstractService{
    private PackageRepository packageRepository;

    public PackageService(){packageRepository = new PackageRepositoryImpl(new UnitOfWork());}

    public Response createPackage(Request request){
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

        String body = request.getBody();

        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }
        List<Card> cards = request.getBodyAsList(Card.class);
        packageRepository.createPackage(cards, header);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(packageRepository);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.CREATED, ContentType.JSON, "");
    }
}
