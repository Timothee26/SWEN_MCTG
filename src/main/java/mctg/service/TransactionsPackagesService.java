package mctg.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.TransactionsPackagesRepository;
import mctg.persistence.repository.TransactionsPackagesRepositoryImpl;


public class TransactionsPackagesService extends AbstractService{
   private TransactionsPackagesRepository transactionsPackagesRepository;

   public TransactionsPackagesService(){transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(new UnitOfWork());}

    public Response buyPackage(Request request) {
       String body = request.getBody();

        /*if (body == null || body.isEmpty()) {
            transactionsPackagesRepository.buyPackage(body);
        }else{
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"smth went wrong\"}");
        }*/

        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }
        User user;
        try{
            user = new ObjectMapper().readValue(request.getBody(), User.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }
        transactionsPackagesRepository.buyPackage(user.getUsername());
        String json = null;
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
