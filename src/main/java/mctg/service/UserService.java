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
public class UserService extends AbstractService{
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepositoryImpl(new UnitOfWork());
    }

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
        Collection<User> weatherCollection = userRepository.findAllUser(user.getUsername());
        String json = null;
        try {
            json = this.getObjectMapper().writeValueAsString(weatherCollection);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new Response(HttpStatus.OK, ContentType.JSON, json);
    }

    public Response registerUser(Request request) {
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

        try {
            userRepository.registerUpload(user);
        } catch (Exception e) {
            return new Response(HttpStatus.INTERNAL_SERVER_ERROR, ContentType.JSON, "{ \"error\": \"Failed to insert registration data.\" }");
        }

        return new Response(HttpStatus.CREATED, ContentType.JSON, "{ \"message\": \"registration data added successfully.\" }");
    }
}
