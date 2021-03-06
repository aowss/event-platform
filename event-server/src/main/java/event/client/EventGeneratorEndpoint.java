package event.client;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint
public class EventGeneratorEndpoint {

    private static CountDownLatch latch;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String unscrambledWord = bufferRead.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            System.out.println("You must provide the topic");
            System.exit(-1);
        }

        latch = new CountDownLatch(1);

        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(EventGeneratorEndpoint.class, new URI("ws://localhost:8025/websockets/events/" + args[0]));
            latch.await();
        } catch (DeploymentException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
