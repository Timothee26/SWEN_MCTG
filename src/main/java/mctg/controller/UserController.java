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
        else if (request.getMethod() == Method.GET && request.getPathname().contains("/users/")) {
            String name[] = request.getPathname().split("/");
            String username = name[name.length - 1];
            return this.userService.getUser(request,username);
        }else if (request.getMethod() == Method.PUT && request.getPathname().contains("/users/")) {
            String name[] = request.getPathname().split("/");
            String username = name[name.length - 1];
            return this.userService.editUser(request,username);
        }else if (request.getMethod() == Method.GET && request.getPathname().equals("/stats")) {
            return this.userService.getStats(request);
        }else if (request.getMethod() == Method.GET && request.getPathname().equals("/scoreboard")) {
            return this.userService.getScoreboard(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
