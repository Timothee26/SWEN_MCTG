package mctg.controller;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.RestController;
import mctg.service.PackageService;

public class PackageController implements RestController {
    private final PackageService packageService;

    public PackageController() {
        this.packageService = new PackageService();
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST && request.getPathname().equals("/package")) {
            return this.packageService.createPackage(request);
        }
        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
