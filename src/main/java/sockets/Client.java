package sockets;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket =  new Socket("127.0.0.1",10001);
        Writer out = new PrintWriter(clientSocket.getOutputStream(),true);
        clientSocket.getOutputStream().write("Test\n".getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(reader.readLine());
    }
}
