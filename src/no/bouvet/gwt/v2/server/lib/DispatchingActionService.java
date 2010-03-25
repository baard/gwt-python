package no.bouvet.gwt.v2.server.lib;

import java.util.HashMap;

import no.bouvet.gwt.v2.shared.lib.Action;
import no.bouvet.gwt.v2.shared.lib.ActionService;
import no.bouvet.gwt.v2.shared.lib.Result;

class DispatchingActionService implements ActionService {
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