package no.rehn.gwt.remoting.client.log;

import no.rehn.gwt.remoting.shared.Action;

import com.google.gwt.event.shared.GwtEvent;

public class ActionStartedEvent extends GwtEvent<ActionStartedHandler> {
    @Override
    protected void dispatch(ActionStartedHandler handler) {
        handler.onActionStarted(this);
    }
    
    private static final Type<ActionStartedHandler> TYPE = new Type<ActionStartedHandler>();
    
    public static Type<ActionStartedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ActionStartedHandler> getAssociatedType() {
        return TYPE;
    }
    
    final Action<?> action;
    
    ActionStartedEvent(Action<?> action) {
        this.action = action;
    }

    public Action<?> getAction() {
        return action;
    }
}
