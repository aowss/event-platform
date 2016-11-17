package event.model;

/**
 * Created by aowssibrahim on 2016-11-16.
 */
public class SimpleLocationEvent extends SimpleEvent {

    private double latitude;
    private double longitude;
    private double altitude;

    public SimpleLocationEvent(String type, String context, String content, double latitude, double longitude, double altitude) {
        super(type, context, content);
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{").append("\n");
        json.append("\t").append("\"id\": \"").append(getId()).append("\",\n");
        json.append("\t").append("\"timestamp\": \"").append(getTimestamp()).append("\",\n");
        json.append("\t").append("\"type\": \"").append(getType()).append("\",\n");
        json.append("\t").append("\"context\": \"").append(getContext()).append("\",\n");
        json.append("\t").append("\"content\": \"").append(getContent()).append("\",\n");
        json.append("\t").append("\"location\": ").append("{").append("\n");
        json.append("\t\t").append("\"latitude\": ").append(latitude).append(",\n");
        json.append("\t\t").append("\"longitude\": ").append(longitude).append(",\n");
        json.append("\t\t").append("\"altitude\": ").append(altitude).append("\n");
        json.append("\t").append("}").append("\n");
        json.append("}");
        return json.toString();
    }

}
