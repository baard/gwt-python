package no.rehn.gwt.remoting.client.monitor;

import com.google.gwt.event.shared.EventHandler;

public interface ActionEndedHandler extends EventHandler {
    void onActionEnded(ActionEndedEvent event);
}
