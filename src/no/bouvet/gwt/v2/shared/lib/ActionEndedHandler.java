package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.event.shared.EventHandler;

public interface ActionEndedHandler extends EventHandler {
    void onActionEnded(ActionEndedEvent event);
}
