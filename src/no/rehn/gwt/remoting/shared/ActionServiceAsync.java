package no.rehn.gwt.remoting.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionServiceAsync {
    <T extends Result> void execute(Action<T> action, AsyncCallback<T> callback);
}
