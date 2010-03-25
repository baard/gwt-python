package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ActionServiceAsync {
    <T extends Result> void execute(Action<T> action, AsyncCallback<T> callback);
}
