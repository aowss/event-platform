package event.server;

import event.model.SimpleEvent;
import event.model.SimpleLocationEvent;
import event.publishers.FakeSimpleEventPublisher;
import event.publishers.FakeSimpleLocationEventPublisher;

import javax.websocket.*;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Even though, the WebSocket runtime scans the WAR file to find all implementations of <code>ServerApplicationConfig</code> and registers the endpoint returned from <code>getEndpointConfigs</code> and <code>getAnnotatedEndpointClasses</code>, this registration process is not dynamic.
 * The <code>{topic}</code> path parameter can't contain '/' ! This might be a bug in the reference implementation.
 */
@ServerEndpoint(value = "/events/{topic}")
public class EventServerEndpoint  implements Flow.Subscriber<SimpleEvent> {

    //  The key is the topic path, the value is the set of sessions linked to that topic
    private static Map<String, Set<Session>> sessions = new TreeMap<>();

    private Logger logger = Logger.getLogger(this.getClass().getName());

//    private Set<Flow.Publisher<SimpleEvent>> publishers = new HashSet<>();

    //  The constructor is not called until the endpoint is accessed so no events are generated until someone connects !
    public EventServerEndpoint() {
//        publisher = new FakeSimpleEventPublisher();
//        publisher.subscribe(this);
        new FakeSimpleEventPublisher().subscribe(this);
        new FakeSimpleLocationEventPublisher().subscribe(this);
    }

    @OnOpen
    public void onOpen(@PathParam("topic") String topic, Session session) {
        sessions.putIfAbsent(topic, new HashSet<Session>());
        sessions.get(topic).add(session);
        logger.info("[user joining][ " + topic + " ] session(s) : " + sessions.get(topic).stream().map(Session::getId).collect(Collectors.joining(", ")));
    }

    @OnMessage
    public void onMessage(@PathParam("topic") String topic, String message, Session session) {

        logger.info(session.getId() + " is sending the following message for topic " + topic + " : " + message);

        if (message.indexOf(":") < 0) return;

        String action = message.substring(0, message.indexOf(":"));
        String body = message.substring(message.indexOf(":") + 1);
        switch (action) {
            case "post":
                sessions.get(topic).stream().forEach(topicSession -> topicSession.getAsyncRemote().sendText(body));
            case "quit":
                try {
                    session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Leaving"));
                    sessions.get(topic).remove(session);
                    logger.info("[user quiting][ " + topic + " ] session(s) : " + sessions.get(topic).stream().map(Session::getId).collect(Collectors.joining(", ")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    @OnClose
    public void onClose(@PathParam("topic") String topic, Session session, CloseReason closeReason) {
        sessions.get(topic).remove(session);
        logger.info("[user leaving][ " + topic + " ] session(s) : " + sessions.get(topic).stream().map(Session::getId).collect(Collectors.joining(", ")));
    }

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(3);
    }

    @Override
    public void onNext(SimpleEvent item) {
        logger.info("[new event] : " + item);
        //  Poor-man's trie
        sessions.entrySet().stream().
                filter(entry -> item.getContext().contains(entry.getKey())).
                flatMap(entry -> entry.getValue().stream()).
                forEach(topicSession -> {
                    logger.info(topicSession.getId() + " is interested !");
                    topicSession.getAsyncRemote().sendText(item.toString());
                });
        /*
        if (sessions.containsKey(item.getContext())) {
            sessions.get(item.getContext()).stream().forEach(topicSession -> {
                logger.info(topicSession.getId() + " is interested !");
                topicSession.getAsyncRemote().sendText(item.toString());
            });
        } else {
            logger.info("None is interested in " + item.getContext() + " messages !");
        }
        */
        subscription.request(3);
    }

    @Override
    public void onError(Throwable t) {
        logger.warning("an exception occurred : " + t);
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        logger.warning("Done !");
    }

}
