package no.bouvet.gwt.v2.shared.lib;

import com.google.gwt.user.client.rpc.RemoteService;

public interface ActionService extends RemoteService {
    Result execute(Action<Result> action) throws Exception;
}
