

import httpserver.server.Server;
import httpserver.utils.Router;
import mctg.controller.*;

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
        router.addService("/package", new PackageController());
        router.addService("/transactionspackages", new TransactionsPackagesController());
        router.addService("/cards", new CardsController());
        router.addService("/deck", new DeckController());

        return router;
    }
}

