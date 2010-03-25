package no.rehn.gwt.remoting.client;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.ActionServiceAsync;
import no.rehn.gwt.remoting.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DelegatingActionHandlerAsync<A extends Action<R>, R extends Result> implements ActionHandlerAsync<A, R> {
    final ActionServiceAsync delegate;

    public DelegatingActionHandlerAsync(ActionServiceAsync delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(A action, AsyncCallback<R> callback) {
        delegate.execute(action, callback);
    }
}
