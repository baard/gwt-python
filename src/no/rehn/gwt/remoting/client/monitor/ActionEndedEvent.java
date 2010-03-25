package no.rehn.gwt.remoting.client.monitor;

import no.rehn.gwt.remoting.shared.Action;

import com.google.gwt.event.shared.GwtEvent;

public class ActionEndedEvent extends GwtEvent<ActionEndedHandler> {
    @Override
    protected void dispatch(ActionEndedHandler handler) {
        handler.onActionEnded(this);
    }
    
    private static final Type<ActionEndedHandler> TYPE = new Type<ActionEndedHandler>();
    
    public static Type<ActionEndedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<ActionEndedHandler> getAssociatedType() {
        return TYPE;
    }
    
    final Action<?> action;
    long dispatched;
    long callbackStarted;
    long callbackEnded;
    
    public long serverDuration() {
        return callbackStarted - dispatched;
    }
    
    public long clientDuration() {
        return callbackEnded - callbackStarted;
    }

    ActionEndedEvent(Action<?> action) {
        this.action = action;
    }

    public Action<?> getAction() {
        return action;
    }
    
    void recordDispatched() {
        dispatched = record(dispatched);
    }

    private long record(long timestamp) {
        if (timestamp != 0) {
            throw new IllegalStateException("Timestamp already recorded");
        }
        return System.currentTimeMillis();
    }
    
    void recordCallbackStarted() {
        callbackStarted = record(callbackStarted);
    }
    
    void recordCallbackEnded() {
        callbackEnded = record(callbackEnded);
    }
}
