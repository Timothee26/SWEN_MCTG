package mctg.controller;
import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.TransactionsPackagesService;


public class TransactionsPackagesController implements RestController {
    private final TransactionsPackagesService transactionsPackagesService;

    public TransactionsPackagesController() {
        this.transactionsPackagesService = new TransactionsPackagesService();
    }

    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && request.getPathname().equals("/transactionspackages")) {
            return this.transactionsPackagesService.buyPackage(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}