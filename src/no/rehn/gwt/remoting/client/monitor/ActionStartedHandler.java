package no.rehn.gwt.remoting.client.monitor;

import com.google.gwt.event.shared.EventHandler;

public interface ActionStartedHandler extends EventHandler {
    void onActionStarted(ActionStartedEvent event);
}
