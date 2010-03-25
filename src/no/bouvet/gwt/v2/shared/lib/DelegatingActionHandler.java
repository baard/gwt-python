package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class DelegatingActionHandler<A extends Action<R>, R extends Result> implements ActionHandlerAsync<A, R> {
    final ActionServiceAsync delegate;

    public DelegatingActionHandler(ActionServiceAsync delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(A action, AsyncCallback<R> callback) {
        delegate.execute(action, callback);
    }
}
