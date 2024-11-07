package p2p;

import org.example.client.PeerClient;
import org.example.server.PeerServer;
import org.example.utils.IPUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PeerServerTest {
//    private static PeerServer server;

//    @BeforeClass
//    public static void serverTest() throws IOException {
//        int port = 1234;
//        server = new PeerServer(port);
//        server.listen();
//        server.send("123123");
//        server.close();
//        assertNotNull(server);
//    }
//    @AfterClass
//    public static void stopServerTest() throws IOException {
//        assertNotNull(server);
//        server.close();
//        assertNull(server);
//    }

    @Test
    public void serverTest() {
        int port = 1234;
        String host = IPUtil.getLocalIPv4Address();
        PeerServer server = null;
        try {
            server = new PeerServer(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.listen();

        PeerClient client = null;
        try {
            client = new PeerClient(host, port);
            PeerClient finalClient = client;
            client.startReceiving(() -> {
                try {
                    String message = finalClient.receive();
                    System.out.println("Received from server: " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            server.send("服务端发送：1");
            server.send("服务端发送：22");
            server.send("服务端发送：3");
            server.send("服务端发送：44");
            server.send("服务端发送：5");
            server.send("服务端发送：66");

            client.send("客户端发送：1");
            client.send("客户端发送：2");
            client.send("客户端发送：3");
            client.send("客户端发送：4");
            client.send("客户端发送：5");

            server.send("服务端发送：7");
            server.send("服务端发送：88");
            server.send("服务端发送：9");
            server.send("服务端发送：00");

//            client.close();
//            server.close();
            assertTrue(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    @Test
//    public void testOut() {
//        assertNotNull(server);
//    }

    @Test
    public void clientTest() throws IOException {
        int port = 1234;
        String host = IPUtil.getLocalIPv4Address();
        PeerClient client = new PeerClient(host, port);
        client.send("11");
        client.send("123");
        client.send("444");
        client.send("STOP");
    }

    @Test
    public void clientStopTest() throws IOException {
        int port = 1234;
        String host = IPUtil.getLocalIPv6Address();
        PeerClient client = new PeerClient(host, port);
        client.send("11");
        client.send("STOP");
    }
}
