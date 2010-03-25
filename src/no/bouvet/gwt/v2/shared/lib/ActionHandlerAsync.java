package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionHandlerAsync<A extends Action<R>, R extends Result> {
    void handle(A action, AsyncCallback<R> callback);
}
