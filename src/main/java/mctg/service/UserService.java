package mctg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import mctg.model.User;
import mctg.model.UserData;
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
            user = this.getObjectMapper().readValue(request.getBody(), User.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        String userCollection = userRepository.login(user);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userCollection);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Failed to login.\" }");
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    /**
     * receices body from the request and checks the format and body of the request
     * if everything is correct the registerUser function is called
     */
    public Response registerUser(Request request) {
        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            System.out.println("body ist leer");
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        User user = null;
        try{
            user = this.getObjectMapper().readValue(request.getBody(), User.class);
            System.out.println(user.getUsername());
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        try {
            userRepository.registerUpload(user);
        } catch (Exception e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Failed to insert registration data.\" } "+e.getMessage());
        }

        return new Response(HttpStatus.CREATED, ContentType.JSON, "{ \"message\": \"registration data added successfully.\" }");
    }

    public Response getUser(Request request, String username) {
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");
        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Heather is empty\"}");
        }
        User userController = userRepository.getData(header, username);
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userController);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST,ContentType.JSON,json+e.getMessage());
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    public Response editUser(Request request, String username) {
        String header = request.getHeaderMap().getHeader("Authorization");
        String parts[] = header.split(" ");

        String body = request.getBody();
        if (body == null || body.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }

        UserData userData;
        try{
            userData = this.getObjectMapper().readValue(request.getBody(), UserData.class);
        }catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{ \"error\": \"Invalid JSON format.\" }");
        }

        if (parts.length >1) {
            header = parts[1];
        }else{
            header = parts[0];
        }
        if (header == null || header.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Invalid username or password\"}");
        }
        List<String> userController = userRepository.editData(header, userData, username);

        if(userController == null || userController.isEmpty()) {
            return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "{\"error\": \"Failed to update user data.\" }");
        }
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userController);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST,ContentType.JSON,json+e.getMessage());
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    public Response getStats(Request request) {
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
        User userController = userRepository.getStats(header);

        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userController);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST,ContentType.JSON,json+e.getMessage());
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);

    }

    public Response getScoreboard(Request request) {
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
        List<User> userController = userRepository.getElo(header);

        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(userController);
        } catch (JsonProcessingException e) {
            return new Response(HttpStatus.BAD_REQUEST,ContentType.JSON,json+e.getMessage());
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);

    }
}
