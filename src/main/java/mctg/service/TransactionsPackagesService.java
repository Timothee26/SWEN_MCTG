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

import java.util.List;
public class TransactionsPackagesService extends AbstractService{
   private TransactionsPackagesRepository transactionsPackagesRepository;

   public TransactionsPackagesService(){transactionsPackagesRepository = new TransactionsPackagesRepositoryImpl(new UnitOfWork());}

    public Response buyPackage(Request request) {
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
        List<String> transactionsPackageCollection = transactionsPackagesRepository.buyPackage(header);

        if (transactionsPackageCollection.contains("No packages available\"")) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"No packages available\"}");
        }

        if (transactionsPackageCollection.contains("Not enough money")) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough money");
        }

        try {
            String json = this.getObjectMapper().writeValueAsString(transactionsPackageCollection);
            return new Response(HttpStatus.CREATED, ContentType.JSON, json);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{\"error\": \"Failed to process response\"}");
        }
    }
}
