

import httpserver.server.Server;
import httpserver.utils.Router;
import mctg.controller.EchoController;
import mctg.controller.UserController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        router.addService("/sessions", new UserController());
        router.addService("/users", new UserController());

        return router;
    }
}
