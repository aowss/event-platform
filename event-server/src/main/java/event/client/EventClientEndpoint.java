package event.client;

import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@ClientEndpoint
public class EventClientEndpoint {

    private static CountDownLatch latch;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("received : " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
        latch.countDown();
    }

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println("You must provide the ip address and the topic. The port is fixed to 8025.");
            System.exit(-1);
        }

        latch = new CountDownLatch(1);

        ClientManager client = ClientManager.createClient();
        try {
            client.connectToServer(EventClientEndpoint.class, new URI("ws://"  + args[0] + ":8025/websockets/events/" + args[1]));
            latch.await();
        } catch (DeploymentException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
