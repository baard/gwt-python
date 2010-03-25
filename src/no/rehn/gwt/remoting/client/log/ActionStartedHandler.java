package no.rehn.gwt.remoting.client.log;

import com.google.gwt.event.shared.EventHandler;

public interface ActionStartedHandler extends EventHandler {
    void onActionStarted(ActionStartedEvent event);
}
