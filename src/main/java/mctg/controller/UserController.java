package mctg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.UserService;
public class UserController implements RestController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    /**
     * checks the method and pathname and returns the corresponding function
     * @param request
     * @return
     */
    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && request.getPathname().equals("/sessions")) {
            return this.userService.loginUser(request);
        }
        else if (request.getMethod() == Method.POST && request.getPathname().equals("/users")) {
            return this.userService.registerUser(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }

}
