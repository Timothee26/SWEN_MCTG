package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.User;
import mctg.persistence.UnitOfWork;
import mctg.persistence.repository.UserRepository;
import mctg.persistence.repository.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.List;


public class UserService extends AbstractService{
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepositoryImpl(new UnitOfWork());
    }

    /**
     * receices body from the request and checks the format and body of the request
     * if everything is correct the login function is called
     */
    public Response loginUser(Request request) {
        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        User user;
        try{
            user = new ObjectMapper().readValue(request.getBody(), User.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }
        userRepository.login(user);
        String json = null;
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    /**
     * receices body from the request and checks the format and body of the request
     * if everything is correct the registerUser function is called
     */
    public Response registerUser(Request request) {
        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        User user;
        try{
            user = this.getObjectMapper().readValue(request.getBody(), User.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        try {
            userRepository.registerUpload(user);
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"error\": \"Failed to insert registration data.\" }");
        }

        return new Response(HttpStatus.CREATED, ContentType.JSON, "{ \"message\": \"registration data added successfully.\" }");
    }

    public Response editUser(Request request, String username) {
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
        List<String> userController = userRepository.getData(header, username);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userController);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }
}
