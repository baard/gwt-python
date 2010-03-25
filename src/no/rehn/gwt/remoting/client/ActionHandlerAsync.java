package no.rehn.gwt.remoting.client;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionHandlerAsync<A extends Action<R>, R extends Result> {
    void handle(A action, AsyncCallback<R> callback);
}
