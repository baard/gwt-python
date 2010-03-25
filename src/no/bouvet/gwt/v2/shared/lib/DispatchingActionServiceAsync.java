package no.bouvet.gwt.v2.shared.lib;

import java.util.HashMap;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DispatchingActionServiceAsync implements ActionServiceAsync {
    final HashMap<Class<?>, ActionHandlerAsync<?, ?>> handlers = new HashMap<Class<?>, ActionHandlerAsync<?, ?>>();
    final HandlerManager eventBus;
    
    public DispatchingActionServiceAsync(HandlerManager eventBus) {
        this.eventBus = eventBus;
    }

    public <T extends Action<?>> void addHandler(Class<T> actionType, ActionHandlerAsync<T, ?> handler) {
        handlers.put(actionType, handler);
    }

    @Override
    public <T extends Result> void execute(final Action<T> action, final AsyncCallback<T> callback) {
        eventBus.fireEvent(new ActionStartedEvent(action));
        final ActionEndedEvent actionEndedEvent = new ActionEndedEvent(action);
        actionEndedEvent.recordDispatched();
        dispatch(action, new AsyncCallback<T>() {
            @Override
            public void onFailure(Throwable caught) {
                onCallbackStarted();
                try {
                    callback.onFailure(caught);
                } finally {
                    onActionEnded();
                }
            }

            void onCallbackStarted() {
                actionEndedEvent.recordCallbackStarted();
            }

            void onActionEnded() {
                actionEndedEvent.recordCallbackEnded();
                eventBus.fireEvent(actionEndedEvent);
            }

            @Override
            public void onSuccess(T result) {
                onCallbackStarted();
                try {
                    callback.onSuccess(result);
                } finally {
                    onActionEnded();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    void dispatch(Action action, AsyncCallback callback) {
        Class actionType = action.getClass();
        ActionHandlerAsync handler = handlers.get(actionType);
        if (handler == null) {
            throw new IllegalArgumentException("No handler for action: " + actionType);
        }
        handler.handle(action, callback);
    }
}
