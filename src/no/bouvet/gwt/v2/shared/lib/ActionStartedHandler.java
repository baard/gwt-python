package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.event.shared.EventHandler;

public interface ActionStartedHandler extends EventHandler {
    void onActionStarted(ActionStartedEvent event);
}
