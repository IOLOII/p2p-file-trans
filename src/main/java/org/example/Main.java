package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.printf("start server");
        PeerServer server = null;
        try {
            server = new PeerServer(6789);
            server.listen();
            PeerClient client = new PeerClient("127.0.0.1", 6789,new BufferedReader(new InputStreamReader(System.in)));
            client.startClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.listen();

    }
}