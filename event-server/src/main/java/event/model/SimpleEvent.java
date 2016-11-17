package event.model;

import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aowssibrahim on 2016-11-04.
 */
public class SimpleEvent {

    private String type;
    private String context;
    private String id;
    private ZonedDateTime timestamp;
    private String content;

    private static final AtomicInteger nextId = new AtomicInteger();

    public SimpleEvent(String type, String context, String content) {
        this.type = type;
        this.context = context;
        this.id = "" + nextId.getAndIncrement();
        this.timestamp = ZonedDateTime.now();
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContext() {
        return context;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{").append("\n");
        json.append("\t").append("\"id\": \"").append(id).append("\",\n");
        json.append("\t").append("\"timestamp\": \"").append(timestamp).append("\",\n");
        json.append("\t").append("\"type\": \"").append(type).append("\",\n");
        json.append("\t").append("\"context\": \"").append(context).append("\",\n");
        json.append("\t").append("\"content\": \"").append(content).append("\"\n");
        json.append("}");
        return json.toString();
    }

}
