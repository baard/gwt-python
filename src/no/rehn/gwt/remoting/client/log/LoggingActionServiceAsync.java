package no.rehn.gwt.remoting.client.log;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.ActionServiceAsync;
import no.rehn.gwt.remoting.shared.Result;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;

//TODO clean up!
public class LoggingActionServiceAsync implements ActionServiceAsync {
    final HandlerManager eventBus;
    final ActionServiceAsync delegate;

    public LoggingActionServiceAsync(HandlerManager eventBus, ActionServiceAsync delegate) {
        this.eventBus = eventBus;
        this.delegate = delegate;
    }

    @Override
    public <T extends Result> void execute(final Action<T> action, final AsyncCallback<T> callback) {
        eventBus.fireEvent(new ActionStartedEvent(action));
        final ActionEndedEvent actionEndedEvent = new ActionEndedEvent(action);
        actionEndedEvent.recordDispatched();
        delegate.execute(action, new AsyncCallback<T>() {
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
}
