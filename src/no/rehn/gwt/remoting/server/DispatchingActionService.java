package no.rehn.gwt.remoting.server;

import java.util.HashMap;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.ActionService;
import no.rehn.gwt.remoting.shared.Result;

public class DispatchingActionService implements ActionService {
    final HashMap<Class<?>, ActionHandler<?, ?>> handlers = new HashMap<Class<?>, ActionHandler<?, ?>>();

    public <T extends Action<?>> void addHandler(Class<T> actionType, ActionHandler<T, ?> handler) {
        handlers.put(actionType, handler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result execute(Action<Result> action) throws Exception {
        Class actionType = action.getClass();
        ActionHandler handler = handlers.get(actionType);
        if (handler == null) {
            throw new IllegalArgumentException("No handler for action: " + actionType);
        }
        return handler.handle(action);
    }
}