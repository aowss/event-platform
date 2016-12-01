package event.server;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by aowssibrahim on 2016-11-29.
 */
public class Configuration  extends ServerEndpointConfig.Configurator {
    @Override
    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }
}