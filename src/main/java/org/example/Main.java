package org.example;

import org.example.client.PeerClient;
import org.example.server.PeerServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static PeerServer server;

    public static void main(String[] args) {
//        System.out.printf("start server");
//        server = null;
//        try {
//            server = new PeerServer(6789);
//            server.listen();
//            PeerClient client = new PeerClient("127.0.0.1", 6789,new BufferedReader(new InputStreamReader(System.in)));
//            client.startClient();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        server.listen();
        int port = 1234;
        try {
            server = new PeerServer(port);
            server.listen();
//            server.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}