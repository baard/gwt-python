package no.rehn.gwt.remoting.client;

import java.util.HashMap;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.ActionServiceAsync;
import no.rehn.gwt.remoting.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DispatchingActionServiceAsync implements ActionServiceAsync {
    final HashMap<Class<?>, ActionHandlerAsync<?, ?>> handlers = new HashMap<Class<?>, ActionHandlerAsync<?, ?>>();
    
    public <T extends Action<?>> void addHandler(Class<T> actionType, ActionHandlerAsync<T, ?> handler) {
        handlers.put(actionType, handler);
    }

    @Override
    public <T extends Result> void execute(final Action<T> action, final AsyncCallback<T> callback) {
        dispatch(action, callback);
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
