package sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(10001)) {
            while (true) {
                final Socket clientConnection = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                OutputStream out = clientConnection.getOutputStream();
                System.out.println(reader.readLine());
                clientConnection.getOutputStream().write("passt\n".getBytes());
            }
        }
    }
}
